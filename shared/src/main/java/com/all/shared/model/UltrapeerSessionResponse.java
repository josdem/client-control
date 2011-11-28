package com.all.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UltrapeerSessionResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private boolean accepted;
	
	private List<UltrapeerNode>  newUltrapeers = new ArrayList<UltrapeerNode>();

	private List<UltrapeerNode>  deprecatedUltrapeers = new ArrayList<UltrapeerNode>();

	public UltrapeerSessionResponse() {
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public List<UltrapeerNode> getNewUltrapeers() {
		return newUltrapeers;
	}

	public void setNewUltrapeers(List<UltrapeerNode> newUltrapeers) {
		this.newUltrapeers = newUltrapeers;
	}

	public List<UltrapeerNode> getDeprecatedUltrapeers() {
		return deprecatedUltrapeers;
	}

	public void setDeprecatedUltrapeers(List<UltrapeerNode> deprecatedUltrapeers) {
		this.deprecatedUltrapeers = deprecatedUltrapeers;
	}
	
}
