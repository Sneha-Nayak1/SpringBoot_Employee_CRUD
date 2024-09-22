package com.example.demo.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;



import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.entity.Address;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.AddressService;
import com.example.demo.service.EmployeeService;

@SpringBootTest
class EmployeeServiceImplTest {

	@Autowired
	EmployeeService employeeService;

	@MockBean
	private AddressServiceImpl addressService;
	
	@MockBean
	private EmployeeRepository employeeRepository;
	

	@Test
	public void testGetAllEmployee() {
		
		Address address1=new Address(1L,"Pune",null);
		Address address2=new Address(2L,"Mumbai",null);
		Employee employee1=new Employee(1L,"Ravi",address1);
		Employee employee2=new Employee(2L,"Pankaj",address2);
		
		List<Employee> empList=Arrays.asList(employee1,employee2);
		Mockito.when(employeeRepository.findAll()).thenReturn(empList);
		
		List<Employee> result=employeeService.getAllEmployee();
		assertEquals(empList,result);
		
	}
	
	@Test
	public void testGetEmployeeById() {
		Address address=new Address(1L,"Mangalore",null);
		Employee employee=new Employee(1L,"Sneha",address);
		Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		
		Employee result=employeeService.getById(1L);
		assertEquals(1L,result.getEmpId());
	
//  	assertEquals("Sneha",result.getEmpName());
//		assertEquals(address.getAddressId(), result.getAddress().getAddressId());
//		assertEquals(address.getAddressNme(), result.getAddress().getAddressNme());
	}
	
	
	
	@Test
	public void testIdNotFound() {
		Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
		
		Exception exception=assertThrows(ResourceNotFoundException.class, ()->{
			employeeService.getById(1L);
		});
		
		 assertEquals("Employee not found with ID: 1", exception.getMessage());
	}
	
	
	@Test
	public void testUpdateEmployeeById() {
		Address address=new Address(1L,"Kochi",null);
		Employee initialEmp=new Employee(1L,"Kishan",address);
		Employee updateEmp=new Employee(1L,"Sneha",address);
		
		Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(initialEmp));
		Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(updateEmp);
		
		Employee results=employeeService.updateEmployee(updateEmp, 1L);
		assertEquals("Sneha",results.getEmpName());
		
	}
	
	@Test
	public void testNotFoundEmployeeId() {
		Employee emp=new Employee(1L,"Sneha",null);
		Exception exception=assertThrows(ResourceNotFoundException.class, ()->{
			employeeService.updateEmployee(emp,1L);
		});
		
		 assertEquals("Employee not found with id: 1", exception.getMessage());
		
	}
	
	@Test
	public void testDeleteEmployeeById() {
		Address address=new Address(1L,"Lucknow",null);
		Employee employee=new Employee(1L,"Sneha",address);
		
		Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		employeeService.deleteEmployee(1L);
		Mockito.verify(employeeRepository).deleteById(1L);
	}
	
	@Test
	public void testDeleteIdNotFound() {
		Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
		
		Exception exception=assertThrows(ResourceNotFoundException.class, ()->{
			employeeService.getById(1L);
		});
		
		 assertEquals("Employee not found with ID: 1", exception.getMessage());
	}
	
	@Test
	public void testSaveEmployee() {
		Address address=new Address(1L,"Mangalore",null);
		Employee employee=new Employee(1L,"Sneha",address);
        Mockito.when(addressService.saveAddress(address)).thenReturn(address);
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);

        employeeService.saveEmployee(employee);

        Mockito.verify(employeeRepository).save(employee);
       // Mockito.verify(addressService).saveAddress(address);
        
	}
	
	
}



