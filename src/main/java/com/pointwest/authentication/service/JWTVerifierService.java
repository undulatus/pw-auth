package com.pointwest.authentication.service;


public interface JWTVerifierService {
	
	public String verify(String token) ;
}
