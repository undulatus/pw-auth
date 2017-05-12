package com.pointwest.authentication.response;

public class PointwestAuthToken {

	private String token;

	public PointwestAuthToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
