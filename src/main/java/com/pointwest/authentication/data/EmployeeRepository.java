package com.pointwest.authentication.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pointwest.authentication.domain.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

	public Employee findByUsername(String username);
	
	public List<Employee> findAll();
}
