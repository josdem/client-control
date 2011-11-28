package com.all.networking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.all.messengine.MessEngine;
import com.all.shared.model.AllMessage;

@Service
public class PeerNetworkingService extends AbstractNetworkingService {

	private static final Log log = LogFactory.getLog(PeerNetworkingService.class);

	@Autowired
	private NetworkingSocketFactory socketConfigurator;
	@Autowired
	private MessEngine messEngine;
	
	private Map<String, IoSession> currentSessions = new HashMap<String, IoSession>();

	private String defaultDestination;

	private int defaultPort;


	public PeerNetworkingService(){}
	
	public PeerNetworkingService(NetworkingSocketFactory socketConfigurator, MessEngine messEngine) {
		this.socketConfigurator = socketConfigurator;
		this.messEngine = messEngine;
	}

	public void setDefaultDestination(String defaultAddress, int defaultPort) {
		this.defaultDestination = defaultAddress;
		this.defaultPort = defaultPort;
	}

	public synchronized IoSession getSession(final String destinationAddress, final int destinationPort)
			throws IOException, GeneralSecurityException, InterruptedException {
		String sessionKey = createSessionKey(destinationAddress, destinationPort);
		IoSession ioSession = currentSessions.get(sessionKey);
		if (ioSession == null || !ioSession.isConnected()) {
			SocketAddress destinationSocketAddress = new InetSocketAddress(destinationAddress, destinationPort);
			IoConnector connector = socketConfigurator.newConnector();
			connector.setHandler(this);
			log.info("Trying to connect to " + destinationSocketAddress);
			ConnectFuture connectionFuture = connector.connect(destinationSocketAddress);
			connectionFuture.await();
			ioSession = connectionFuture.getSession();
			log.info("Connected!");
			currentSessions.put(sessionKey, ioSession);
		}
		return ioSession;
	}

	public synchronized boolean send(String sender, AllMessage<?> message) {
		return send(sender, message, defaultDestination, defaultPort);
	}

	public synchronized boolean send(String sender, AllMessage<?> message, String destinationAddress, int destinationPort) {
		try {
			IoSession session = getSession(destinationAddress, destinationPort);
			return write(session, sender, message);
		} catch (Exception e) {
			log.error(e, e);
			return false;
		}
	}

	public boolean hasDefaultSession() {
		try {
			return (defaultDestination != null && defaultPort > 0 && getSession(defaultDestination, defaultPort) != null);
		} catch (Exception e) {
			log.error(e, e);
			return false;
		}
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		log.info("Session to " + session.getRemoteAddress() + " was closed.");
		InetSocketAddress remoteSocketAddress = (InetSocketAddress) session.getRemoteAddress();
		String remoteAddress = remoteSocketAddress.getAddress().getHostAddress();
		int remotePort = remoteSocketAddress.getPort();
		IoSession removed = currentSessions.remove(createSessionKey(remoteAddress, remotePort));
		if (removed != null) {
			log.info("Removing " + remoteAddress + " from current sessions.");
		}
	}

	public void shutdown() {
		log.info("Shutting down peer networking service...");
		resetSessions();
	}

	public void resetSessions() {
		for (IoSession session : currentSessions.values()) {
			session.close(true);
		}
		defaultDestination = null;
		defaultPort = -1;
		currentSessions.clear();
	}

	@Override
	protected MessEngine getMessEngine() {
		return messEngine;
	}

	private String createSessionKey(String address, int port) {
		return address + ":" + port;
	}


}
