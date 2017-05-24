package com.pointwest.authentication.service;

import java.io.UnsupportedEncodingException;

public interface JWTVerifierService {
	
	public String verify(String token) throws UnsupportedEncodingException ;
}
