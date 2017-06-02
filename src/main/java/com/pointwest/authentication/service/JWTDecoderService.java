package com.pointwest.authentication.service;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.pointwest.authentication.domain.Employee;

public interface JWTDecoderService {
	
	public Employee decode(String token) throws JWTDecodeException;
}
