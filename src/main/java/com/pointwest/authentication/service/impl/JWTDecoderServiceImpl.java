package com.pointwest.authentication.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pointwest.authentication.domain.Employee;
import com.pointwest.authentication.service.JWTDecoderService;

@Service
public class JWTDecoderServiceImpl implements JWTDecoderService {
	private static final Logger log = LoggerFactory.getLogger(JWTDecoderServiceImpl.class);

	public Employee decode(String token) throws JWTDecodeException {
		log.debug("MCI >> decode");
		Employee employee = new Employee();
		try {
			DecodedJWT jwt = JWT.decode(token);
			Map<String, Claim> claims = jwt.getClaims();

			for (Claim name : claims.values()) {
				if (name.asString() != null) {
					if (name.asString().contains(".")) {
						employee.setUsername(name.asString());
						System.out.println("Employee username: " + employee.getUsername());
					}
				}
			}
		} catch (JWTDecodeException jwte) {
			log.error("An error of type " + jwte.getClass() + " occurred while trying to run decode(). "
					+ "Invalid on decoding token." + jwte.getMessage());
			throw jwte;
		}
		log.debug("MCO << decode");
		return employee;
	}
}
