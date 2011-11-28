package com.all.shared.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.all.shared.AllConstants;

@Entity
public class UltrapeerNode implements Comparable<UltrapeerNode> {

	@Id
	private String address;
	private int succesfulConnections;
	private int unsuccesfulConnections;

	public UltrapeerNode() {
	}

	public UltrapeerNode(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void incrementSuccesfulConnections() {
		succesfulConnections++;
	}

	public void incrementUnsuccesfulConnections() {
		unsuccesfulConnections++;
	}

	public int getPort() {
		return AllConstants.ULTRAPEER_PORT;
	}

	@Override
	public int compareTo(UltrapeerNode o) {
		return Integer.valueOf(getConnectionBalance()).compareTo(Integer.valueOf(o.getConnectionBalance()));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof UltrapeerNode) {
			UltrapeerNode other = (UltrapeerNode) obj;
			if (address != null) {
				return address.equals(other.address);
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (address != null) {
			return 13 * address.hashCode();
		}
		return super.hashCode();
	}

	private int getConnectionBalance() {
		return succesfulConnections - unsuccesfulConnections;
	}

	@Override
	public String toString() {
		return "Ultrapeer=" + address + ":" + getPort();
	}

	public int getSc() {
		return succesfulConnections;
	}

	public void setSc(int succesfulConnections) {
		this.succesfulConnections = succesfulConnections;
	}

	public int getUc() {
		return unsuccesfulConnections;
	}

	public void setUc(int unsuccesfulConnections) {
		this.unsuccesfulConnections = unsuccesfulConnections;
	}

}
