package com.sepulsa.sheetUpdater.object;

import java.io.Serializable;

public class Meta implements Serializable {

	private static final long serialVersionUID = 2124371439479247L;
	private Boolean status;
	private String message;
	private String version="v1";
	private String user;
	
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	
}
