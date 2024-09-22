package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MySQLContainer;

import com.example.demo.entity.Address;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import org.springframework.security.test.context.support.WithMockUser;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
//	@MockBean
//	private EmployeeService employeeService;
//	@BeforeEach
//    public void setUpp() {
//        employeeRepository.deleteAll(); 
//    }
	
	
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest");


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @BeforeAll
    static void beforeAll() {
        mySQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        mySQLContainer.stop();
    }

	@Autowired
	private EmployeeRepository employeeRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
   

	 @Test
	 @Order(3)
	  @WithMockUser(username = "sneha", roles = {"USER"})
	    public void testGetAllEmployees() throws Exception {
		 mockMvc.perform(get("/employee/getAll"))
	                .andExpect(status().isOk());
	    }
	 
	   @Test 
	   @Order(2)
	    @WithMockUser(username = "sneha", roles = {"USER"})
	    public void testGetById() throws Exception {
    	 mockMvc.perform(get("/employee/getById/{id}", 1L))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$.empName").value("Sneha"));
   
	    }
	 
	 @Test
	 @Order(5)
	 @WithMockUser(username="sneha",roles={"ADMIN"})
	 public void testDeleteById() throws Exception{
		 mockMvc.perform(delete("/employee/delete/{id}", 1L))
		 .andExpect(status().isOk());
	 }
	 
	 @Test
	 @Order(1)
	 @WithMockUser(username="sneha",roles= {"ADMIN"})
	 public void testsaveEmployee() throws Exception {
		 Address address=new Address(1L,"Mangalore",null);
		 Employee employee=new Employee(1L,"Sneha",address);
		 
		 mockMvc.perform(post("/employee/save")
		            .contentType("application/json")
		            .content("{\"id\":1,\"empName\":\"Sneha\",\"address\":{\"id\":1,\"addressNme\":\"Mangalore\"}}"))
		            .andExpect(status().isOk())
		            .andExpect(content().string("Employee saved!!"));
		 
	 }
	 
	 @Test
	 @Order(4)
	 @WithMockUser(username = "sneha", roles = {"ADMIN"})
	 public void testUpdateEmployee() throws Exception {
//	     Address initialAddress = new Address(1L, "Pune", null);
//	     Employee initialEmployee = new Employee(1L, "OldName", initialAddress);
//	     employeeRepository.save(initialEmployee);

	     mockMvc.perform(put("/employee/update/{id}", 1L)
	             .contentType("application/json")
	             .content("{\"empName\":\"Hari\",\"address\":{\"id\":11,\"addressNme\":\"Mangalore\"}}"))
	             .andExpect(status().isOk())
	             .andExpect(jsonPath("$.empName").value("Hari"))
	             .andExpect(jsonPath("$.address.addressNme").value("Mangalore"));
	 }
	 
	 
	 


}
