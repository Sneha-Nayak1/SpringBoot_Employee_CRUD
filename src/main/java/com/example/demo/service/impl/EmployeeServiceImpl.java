package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;


@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private AddressServiceImpl addressService;
	
	@Override
	public void saveEmployee(Employee employee) {
		try {
			employeeRepository.save(employee);
			//System.out.println("Hii");
			
			if(employee.getAddress()!=null) {
				//System.out.println("address");
				addressService.saveAddress(employee.getAddress());
				//System.out.println("HIIIIIIII");
			}
			
		}catch(Exception e) {
			throw new RuntimeException("Error in saving");
		}
		//throw new RuntimeException("its not working");
		
	
	}

	@Override
	public List<Employee> getAllEmployee() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee getById(Long id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		//System.out.println(employee.toString());
		if(employee.isPresent()) {
			return employee.get();
		}else {
			throw new ResourceNotFoundException("Employee","ID",id);
		}
	}

	@Override
	public Employee updateEmployee(Employee employee, Long id) {
		Employee employee1=employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee","id",id));
		employee1.setEmpName(employee.getEmpName());
		return employeeRepository.save(employee1);
	}

	@Override
	public void deleteEmployee(Long id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		if(employee.isPresent()) {
			employeeRepository.deleteById(id);;
		}else {
			throw new ResourceNotFoundException("Employee","ID",id);
		}
		
	}

//	@Override
//	public Employee insertEmployee(Employee employee) {
//		employeeRepository.save(employee);
//		if(a)
//		return null;
//	}
	
	



}
