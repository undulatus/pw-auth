package com.pointwest.authentication.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pointwest.authentication.data.EmployeeRepository;
import com.pointwest.authentication.domain.Employee;
import com.pointwest.authentication.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	@Override
	public Employee fetchEmployee(String username) {
		log.debug("MCI >> fetchEmployee");
		Employee employee = employeeRepository.findByUsername(username);
		log.debug("MCO << fetchEmployee");
		return employee;
	}

	@Override
	public List<Employee> fetchAllEmployee() {
		log.debug("MCI >> fetchEmployee");
		List<Employee> employees = (List<Employee>) employeeRepository.findAll();
		log.debug("MCO << fetchEmployee");
		return employees;
	}
}
