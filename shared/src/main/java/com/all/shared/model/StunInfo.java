package com.all.shared.model;

import java.io.Serializable;
import java.net.InetAddress;

public class StunInfo implements Serializable {
	public static final long serialVersionUID = 1L;
	private InetAddress localIP;
  

	private boolean openAccess = false;
	private boolean blockedUDP = false;
	private boolean fullCone = false;
	private boolean restrictedCone = false;
	private boolean portRestrictedCone = false;
	private boolean symmetric = false;
	private boolean symmetricUDPFirewall = false;
	private InetAddress publicIP;
	private String mail;
	private int publicPort;
	private int localPort;
  
	public StunInfo(InetAddress localIP) {
		this.localIP = localIP;
	}

	public int getLocalPort() {
		return localPort;
	}
	
	public int getPublicPort() {
		return publicPort;
	}

	public void setPublicPort(int port) {
		this.publicPort = port;
	}
	
	public void setLocalPort(int privatePort) {
		this.localPort = privatePort;
	}

	public void setOpenAccess(boolean openAccess) {
		this.openAccess = openAccess;
	}

	public void setBlockedUDP(boolean blockedUDP) {
		this.blockedUDP = blockedUDP;
	}

	public void setFullCone(boolean fullCone) {
		this.fullCone = fullCone;
	}

	public void setRestrictedCone(boolean restrictedCone) {
		this.restrictedCone = restrictedCone;
	}

	public void setPortRestrictedCone(boolean portRestrictedCone) {
		this.portRestrictedCone = portRestrictedCone;
	}

	public void setSymmetric(boolean symmetric) {
		this.symmetric = symmetric;
	}

	public void setSymmetricUDPFirewall(boolean symmetricUDPFirewall) {
		this.symmetricUDPFirewall = symmetricUDPFirewall;
	}

	public void setPublicIP(InetAddress publicIP) {
		this.publicIP = publicIP;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMail() {
		return mail;
	}

	public boolean isOpenAccess() {
		return openAccess;
	}

	public boolean isBlockedUDP() {
		return blockedUDP;
	}

	public boolean isFullCone() {
		return fullCone;
	}

	public boolean isRestrictedCone() {
		return restrictedCone;
	}

	public boolean isPortRestrictedCone() {
		return portRestrictedCone;
	}

	public boolean isSymmetric() {
		return symmetric;
	}

	public boolean isSymmetricUDPFirewall() {
		return symmetricUDPFirewall;
	}

	public InetAddress getPublicIP() {
		return publicIP;
	}

	public InetAddress getLocalIP() {
		return localIP;
	}

	public String toString() {
		return localIP + " .... " + publicIP + " ....  " + fullCone + " " + publicPort ;
	}
}
