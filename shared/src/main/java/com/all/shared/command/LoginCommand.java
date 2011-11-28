package com.all.shared.command;

import java.io.Serializable;

import javax.validation.constraints.Size;

import com.all.shared.model.constraints.Email;
import com.all.shared.model.constraints.NotEmpty;

public class LoginCommand implements Serializable {

	private static final long serialVersionUID = 6136927762060238017L;

	@NotEmpty(message="This is not a valid e-mail address, please submit it again.")
	@Email(message="This is not a valid e-mail address, please submit it again.")
	private String email;

	@Size(max=25, min=8, message="Your password has to have at least 8 characters and no more than 25 characters and without spaces.")
	private String password;

  private String encryptedPwd;
  
	private String version;

	private String os;

	private String jvm;

	private String timezone;

	private long timestamp;

	private String publicIp;
	
	private String osVersion;

	private String screenSize;

	public LoginCommand(){}
	
	public LoginCommand(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public void setEncryptedPwd(String encryptedPwd) {
		this.encryptedPwd = encryptedPwd;
	}

	public String getEncryptedPwd() {
		return encryptedPwd;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getJvm() {
		return jvm;
	}

	public void setJvm(String jvm) {
		this.jvm = jvm;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getPublicIp() {
		return publicIp;
	}

	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getScreenSize() {
		return screenSize;
	}

	public void setScreenSize(String screenSize) {
		this.screenSize = screenSize;
	}
	
	
}
