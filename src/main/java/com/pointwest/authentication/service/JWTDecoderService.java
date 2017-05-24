package com.pointwest.authentication.service;

import com.auth0.jwt.exceptions.JWTDecodeException;

public interface JWTDecoderService {
	
	public String decode(String token) throws JWTDecodeException;
}
