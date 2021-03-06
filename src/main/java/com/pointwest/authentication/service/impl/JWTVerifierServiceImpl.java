package com.pointwest.authentication.service.impl;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.pointwest.authentication.service.JWTDecoderService;
import com.pointwest.authentication.service.JWTVerifierService;

@Service
public class JWTVerifierServiceImpl implements JWTVerifierService {
	
	@Autowired
	JWTDecoderService jwtDecoder;
	
	private static final Logger log = LoggerFactory.getLogger(JWTVerifierServiceImpl.class);
	
	public String verify(String token) throws UnsupportedEncodingException, JWTVerificationException{
		log.debug("MCI >> verify");
		try {
		    Algorithm algorithm = Algorithm.HMAC256("secret");
		    JWTVerifier verifier = JWT.require(algorithm)
		        .withIssuer("auth0")
		        .build(); //Reusable verifier instance
		    jwtDecoder.decode(token);
		} catch (UnsupportedEncodingException uee){
			log.error("An error of type " + uee.getClass() + " occurred while trying to run create(). "
					+ "UTF-8 encoding not supported");
			throw uee;
		} catch (JWTVerificationException jwte){
			log.error("An error of type " + jwte.getClass() + " occurred while trying to run create(). "
					+ "Invalid Signing configuration / Couldn't convert Claims.");
			throw jwte;
		}
		log.debug("MCO << verify");
		return null;
		
	}
}
