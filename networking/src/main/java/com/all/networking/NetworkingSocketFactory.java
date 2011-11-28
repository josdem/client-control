package com.all.networking;

import java.security.GeneralSecurityException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoService;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.prefixedstring.PrefixedStringCodecFactory;
import org.apache.mina.filter.ssl.SslFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.all.networking.codec.BogusSslContextFactory;
import com.all.networking.util.IoFactory;

@Service
public class NetworkingSocketFactory {

	private static final String[] CIPHER_SUITES = new String[] { "SSL_RSA_WITH_RC4_128_MD5", "SSL_RSA_WITH_RC4_128_SHA",
			"TLS_RSA_WITH_AES_128_CBC_SHA", "TLS_DHE_RSA_WITH_AES_128_CBC_SHA", "TLS_DHE_DSS_WITH_AES_128_CBC_SHA",
			"SSL_RSA_WITH_3DES_EDE_CBC_SHA", "SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA", "SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA",
			"SSL_RSA_WITH_DES_CBC_SHA", "SSL_DHE_RSA_WITH_DES_CBC_SHA", "SSL_DHE_DSS_WITH_DES_CBC_SHA",
			"SSL_RSA_EXPORT_WITH_RC4_40_MD5", "SSL_RSA_EXPORT_WITH_DES40_CBC_SHA", "SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA",
			"SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA" };

	private final Log log = LogFactory.getLog(this.getClass());

	private IoFactory ioFactory;

	@Autowired
	public NetworkingSocketFactory(IoFactory ioFactory) {
		this.ioFactory = ioFactory;
	}

	public IoConnector newConnector() {
		IoConnector connector = ioFactory.createConnector();
		try {
			addSSLFilter(connector);
			log.debug("Using SSL");
		} catch (GeneralSecurityException e) {
			log.error(e, e);
		}
		addCodecFilter(connector);
		return connector;
	}

	public IoAcceptor newAcceptor() {
		IoAcceptor acceptor = ioFactory.createAcceptor();
		try {
			addSSLFilter(acceptor);
			log.debug("Using SSL");
		} catch (GeneralSecurityException e) {
			log.error(e, e);
		}
		addCodecFilter(acceptor);
		return acceptor;
	}

	private void addSSLFilter(IoAcceptor ioAcceptor) throws GeneralSecurityException {
		SslFilter sslFilter = new SslFilter(BogusSslContextFactory.getInstance(true));
		sslFilter.setEnabledCipherSuites(CIPHER_SUITES);
		ioAcceptor.getFilterChain().addLast(NetworkingConstants.SSL_FILTER, sslFilter);
	}

	private void addSSLFilter(IoConnector connector) throws GeneralSecurityException {
		SslFilter sslFilter = new SslFilter(BogusSslContextFactory.getInstance(false));
		sslFilter.setEnabledCipherSuites(CIPHER_SUITES);
		sslFilter.setUseClientMode(true);
		connector.getFilterChain().addLast(NetworkingConstants.SSL_FILTER, sslFilter);
	}

	private void addCodecFilter(IoService socketService) {
		PrefixedStringCodecFactory codec = new PrefixedStringCodecFactory();
		codec.setEncoderMaxDataLength(NetworkingConstants.PROTOCOL_CAPACITY);
		codec.setDecoderMaxDataLength(NetworkingConstants.PROTOCOL_CAPACITY);
		codec.setEncoderPrefixLength(NetworkingConstants.FIX_LENGTH);
		codec.setDecoderPrefixLength(NetworkingConstants.FIX_LENGTH);
		socketService.getFilterChain().addLast(NetworkingConstants.CODEC_FILTER, new ProtocolCodecFilter(codec));
		log.debug("Using PrefixedStringCodecFactory");
	}

}
