package com.pointwest.authentication.response;

public class Error {

	private String code;

	private String message;

	public Error(String code) {
		this.code = code;
	}

	public Error(ErrorCode code, String message) {
		this.code = code.getCode();
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
