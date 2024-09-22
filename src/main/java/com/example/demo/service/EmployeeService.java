package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Employee;



public interface EmployeeService {
	void saveEmployee(Employee employee);
	List<Employee> getAllEmployee();
	Employee getById(Long id);
	Employee updateEmployee(Employee employee, Long id);
	void deleteEmployee(Long id);
	//Employee insertEmployee(Employee employee);
	//Address insertaddress(Address address);
}
