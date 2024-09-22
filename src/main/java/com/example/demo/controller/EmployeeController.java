package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping("/save")
	public ResponseEntity<String> saveEmployee(@RequestBody Employee employee) {
		//return new ResponseEntity<Employee>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
		try {
			employeeService.saveEmployee(employee);
			return new ResponseEntity<String>("Employee saved!!",HttpStatus.OK);
		}catch(RuntimeException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<Employee>> getAllEmployee(){
		return new ResponseEntity<List<Employee>>(employeeService.getAllEmployee(),HttpStatus.OK);
	}
	
//	public List<Employee> getAllEmployee(){
//	return employeeService.getAllEmployee();
//}
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<Employee> getByID(@PathVariable("id") Long empId){
		return new ResponseEntity<Employee>(employeeService.getById(empId), HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable Long id){
		return new ResponseEntity<Employee>(employeeService.updateEmployee(employee, id), HttpStatus.OK);
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long empId){
		employeeService.deleteEmployee(empId);
		return new ResponseEntity<String>("Employee deleted succsessfully!", HttpStatus.OK);
	}
	
	

}
