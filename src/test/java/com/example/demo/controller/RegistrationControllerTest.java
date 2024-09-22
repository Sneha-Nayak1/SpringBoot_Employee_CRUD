package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.entity.MyUser;
import com.example.demo.repository.MyuserRepository;
import com.example.demo.service.impl.JwtService;
import com.example.demo.service.impl.MyUserServiceToken;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class RegistrationControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper; 
	   
	@Autowired
	private MyuserRepository myUserRepository;
	
	@MockBean
	private MyUserServiceToken myUserServiceToken;
	@MockBean
    private JwtService jwtService;

    @MockBean
    private AuthenticationManager authManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Test
    @WithMockUser(username = "sneha", roles = {"ADMIN"})
    void testRegisterUser() throws Exception {
        
		 MyUser newUser = new MyUser();
		    newUser.setUserName("sneha");
		    newUser.setPassword("password"); 
		    newUser.setRole("ADMIN");

		    mockMvc.perform(post("/register")
		            .contentType(MediaType.APPLICATION_JSON)
		            .content(objectMapper.writeValueAsString(newUser)))
		            .andExpect(status().isOk());

	
	}

	    @Test
	    void testLoginUserSuccess() throws Exception {

	        String requestBody = "{ \"username\": \"suraj\", \"password\": \"password1\" }";
	        String token = "";

	        Mockito.when(myUserServiceToken.verify(Mockito.any(MyUser.class))).thenReturn(token);

	        mockMvc.perform(post("/login")
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .content(requestBody))
	                .andExpect(MockMvcResultMatchers.status().isOk()) 
	                .andExpect(MockMvcResultMatchers.content().string(token));  
	    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	   @Test
//	    public void testRegisterUser() throws Exception {
//	        MyUser newUser = new MyUser();
//	        newUser.setUserName("sneha");
//	        newUser.setPassword(passwordEncoder.encode("password"));
//	        newUser.setRole("ADMIN");
//
//	        mockMvc.perform(post("/register")
//	                .contentType(MediaType.APPLICATION_JSON)
//	                .content(objectMapper.writeValueAsString(newUser)))
//	                .andExpect(status().isOk())
//	                .andExpect(jsonPath("$.userName").value("sneha"))
//	                .andExpect(jsonPath("$.role").value("ADMIN"));
//
//	        
//	    }
//
//	    @Test
//	    public void testLoginUser() throws Exception {
//	    	 MyUser newUser = new MyUser();
//	         newUser.setUserName("banana");
//	         newUser.setPassword(passwordEncoder.encode("banana"));
//	         newUser.setRole("USER");
//	         myUserRepository.save(newUser);
//
//	         MyUser loginRequest = new MyUser();
//	         loginRequest.setUserName("banana");
//	         loginRequest.setPassword("banana");
//
//	         mockMvc.perform(post("/login")
//	                 .contentType(MediaType.APPLICATION_JSON)
//	                 .content(objectMapper.writeValueAsString(loginRequest)))
//	                 .andExpect(status().isOk())
//	                 .andExpect(jsonPath("$").value(org.hamcrest.Matchers.containsString("Token has been generated!")));
//	     }
}
