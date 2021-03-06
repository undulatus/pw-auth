package com.pointwest.authentication.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.pointwest.authentication.domain.CustomError;
import com.pointwest.authentication.domain.Employee;
import com.pointwest.authentication.response.Error;
import com.pointwest.authentication.response.ErrorCode;
import com.pointwest.authentication.response.PointwestAuthToken;
import com.pointwest.authentication.service.EmployeeService;
import com.pointwest.authentication.service.JWTCreatorService;
import com.pointwest.authentication.service.JWTDecoderService;
import com.pointwest.authentication.service.JWTVerifierService;

@RestController
public class AuthenticationController {

	@Value("${client_id}")
	private String applicationClientId = "";

	@Autowired
	JWTCreatorService jwtCreate;
	@Autowired
	JWTVerifierService jwtVerify;
	@Autowired
	JWTDecoderService jwtDecode;
	@Autowired
	EmployeeService employeeService;

	private GoogleIdTokenVerifier googleTokenVerifier;

	private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

	@PostConstruct
	public void init() {
		// TODO remove to prod
		log.debug("Setting proxy settings");
		System.setProperty("http.proxyHost", "cache.srv.pointwest.com.ph");
		System.setProperty("http.proxyPort", "3128");
		System.setProperty("https.proxyHost", "cache.srv.pointwest.com.ph");
		System.setProperty("https.proxyPort", "3128");

		log.info("Initializing Google Token Verifier with client: " + applicationClientId);
		googleTokenVerifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
				.setAudience(Collections.singletonList(applicationClientId)).build();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/google/authenticate")
	public ResponseEntity<Object> createToken(@RequestParam("googleToken") String googleToken) {
		GoogleIdToken idToken = null;
		try {
			idToken = googleTokenVerifier.verify(googleToken);
		} catch (GeneralSecurityException | IOException e) {
			return new ResponseEntity<>(new Error(ErrorCode.SERVER_ERROR, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR); // TODO
		}
		if (idToken == null) {
			log.info("Invalid Google token: " + googleToken);
			return new ResponseEntity<>(new Error(ErrorCode.INVALID_TOKEN, "Invalid Google Token"),
					HttpStatus.BAD_REQUEST);
		} else {
			String username = idToken.getPayload().getEmail();
			int index = username.indexOf('@');
			username = username.substring(0, index);
			Employee employee = employeeService.fetchEmployee(username);

			if (employee != null) {
				try {
					String token = jwtCreate.create(employee);
					return new ResponseEntity<>(new PointwestAuthToken(token), HttpStatus.OK);
				} catch (JWTCreationException | UnsupportedEncodingException exception) {
					return new ResponseEntity<>(new Error(ErrorCode.SERVER_ERROR, exception.getMessage()),
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				return new ResponseEntity<>(
						new Error(ErrorCode.USER_NOT_ALLOWED, "User not allowed to access application"),
						HttpStatus.FORBIDDEN);
			}
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/employees")
	public ResponseEntity<Object> fetchAllEmployee() {
		List<Employee> employees = employeeService.fetchAllEmployee();
		if (employees == null || employees.isEmpty()) {
			return new ResponseEntity<>(new CustomError("No Employees retrieved"), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(employees, HttpStatus.OK);
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/decode")
	public ResponseEntity<Object> decodeToken(@RequestParam("token") String token) {
		Employee employee = new Employee();
		try {
			employee = jwtDecode.decode(token);
		} catch (JWTDecodeException e) {
			log.error("An error of type " + e.getClass() + " occurred while trying to run decode(). " + e.getMessage());
		}
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/verify")
	public void verifyToken(@RequestParam("token") String token) {
		try {
			String result = jwtVerify.verify(token);
		} catch (UnsupportedEncodingException | JWTVerificationException e) {
			log.error("An error of type " + e.getClass() + " occurred while trying to run verifyToken(). ");
		}
	}

}
