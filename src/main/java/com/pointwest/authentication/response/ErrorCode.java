package com.pointwest.authentication.response;

public enum ErrorCode {
	
	INVALID_TOKEN("101"),

	USER_NOT_ALLOWED("401"),
	
	SERVER_ERROR("901");
	
	private String code;
	
	private ErrorCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
