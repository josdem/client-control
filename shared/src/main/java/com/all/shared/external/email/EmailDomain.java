package com.all.shared.external.email;

public enum EmailDomain{
	HOTMAIL ("hotmailBackground", "hotmail"),
	YAHOO ("yahooBackground", "yahoo"),
	GMAIL ("gmailBackground", "gmail");
	
	private final String backgroundImage;
	private final String provider;

	private EmailDomain(String backgroundImage, String provider){
		this.backgroundImage = backgroundImage;
		this.provider = provider;
		
		}

	public String getBackgroundStyle() {
		return backgroundImage;
	}
	
	public String getProvider() {
		return provider;
	}
	
}