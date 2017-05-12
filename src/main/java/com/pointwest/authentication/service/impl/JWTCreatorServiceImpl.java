package com.pointwest.authentication.service.impl;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.pointwest.authentication.domain.Employee;
import com.pointwest.authentication.service.EmployeeService;
import com.pointwest.authentication.service.JWTCreatorService;

@Service
public class JWTCreatorServiceImpl implements JWTCreatorService{
	
	@Autowired
	EmployeeService employeeService;
	
	private static final Logger log = LoggerFactory.getLogger(JWTCreatorServiceImpl.class);
	
	public String create(Employee employee){
		log.debug("MCI >> create");
		String token = "";
		try {
		    Algorithm algorithm = Algorithm.HMAC256("secret");
		    token = JWT.create()
		        .withIssuer("auth0")
		        .withClaim("usr", employee.getUsername())
		        .withClaim("rle", employee.getRole())
		        // TODO: add domain
		        .sign(algorithm);
		    
		} catch (UnsupportedEncodingException exception){
		    //UTF-8 encoding not supported
		} catch (JWTCreationException exception){
		    //Invalid Signing configuration / Couldn't convert Claims.
		}
		
		log.debug("MCO << create");
		log.debug("token: " + token); //TODO remove to prod
		return token;
	}
	
}
