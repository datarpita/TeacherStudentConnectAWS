package com.example.entity;

public class Inputs {
	
	private String sessionid;
	
	private String sessionname;

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getSessionname() {
		return sessionname;
	}

	public void setSessionname(String sessionname) {
		this.sessionname = sessionname;
	}

	@Override
	public String toString() {
		return "Inputs [sessionid=" + sessionid + ", sessionname=" + sessionname + "]";
	}
	
}
