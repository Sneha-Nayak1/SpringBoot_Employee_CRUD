package com.example.demo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.MyUser;
import com.example.demo.repository.MyuserRepository;

@Service
public class MyUserService implements UserDetailsService{
	
	@Autowired
	private MyuserRepository myUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<MyUser> user=myUserRepository.findByUserName(username);
		if(user.isPresent()) {
			var userObj=user.get();
			return User.builder()
					.username(userObj.getUserName())
					.password(userObj.getPassword())
					.roles(getRoles(userObj))
					.build();
		}else {
			throw new UsernameNotFoundException(username);
		}
		
	}
	public String[] getRoles(MyUser user) {
		if(user.getRole()==null) {
			return new String[] {"USER"};
		}
		return user.getRole().split(",");
	}

}
