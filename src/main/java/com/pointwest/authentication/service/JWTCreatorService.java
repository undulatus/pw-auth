package com.pointwest.authentication.service;

import com.pointwest.authentication.domain.Employee;


public interface JWTCreatorService {
	
	public String create(Employee employee) ;
}
