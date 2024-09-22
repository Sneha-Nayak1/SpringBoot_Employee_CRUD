package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.MyUser;
import com.example.demo.repository.MyuserRepository;
import com.example.demo.service.impl.MyUserServiceToken;

@RestController
public class RegistrationController {
	
	@Autowired
	private MyuserRepository myUserRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MyUserServiceToken serviceToken;
	
	@PostMapping("/register")
	public MyUser createUser(@RequestBody MyUser myUser) {
		myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
		return myUserRepository.save(myUser);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody MyUser myUser) {
		System.out.println("Token has been generated!");
		return serviceToken.verify(myUser);
	}

}
