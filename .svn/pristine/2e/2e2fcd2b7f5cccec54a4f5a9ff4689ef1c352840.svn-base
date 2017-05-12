package com.pointwest.authentication.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pointwest.authentication.service.JWTDecoderService;

@Service
public class JWTDecoderServiceImpl implements JWTDecoderService {
	private static final Logger log = LoggerFactory.getLogger(JWTDecoderServiceImpl.class);
	
	public String decode(String token){
		log.debug("MCI >> decode");
		try {
		    DecodedJWT jwt = JWT.decode(token);
		    Map<String, Claim> claims = jwt.getClaims();
		    
		    for (Claim name : claims.values()) {
	            System.out.println(name.asString());
	        }
		    
		} catch (JWTDecodeException exception){
		    exception.printStackTrace();
		}
		log.debug("MCO << decode");
		return null;
	}
}
