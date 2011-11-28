package com.all.shared.messages;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.all.shared.command.LoginCommand;
import com.all.shared.external.email.EmailDomain;

public class CrawlerRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Map<EmailDomain, List<LoginCommand>> accounts;
	
	public CrawlerRequest(){}
	
	public Map<EmailDomain, List<LoginCommand>> getAccounts() {
		return accounts;
	}
	
	public void setAccounts(Map<EmailDomain, List<LoginCommand>> accounts) {
		this.accounts = accounts;
	}
	
}
