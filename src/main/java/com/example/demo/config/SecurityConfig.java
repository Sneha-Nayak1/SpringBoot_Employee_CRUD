package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.Customizer;

import com.example.demo.filter.JwtFilter;
import com.example.demo.service.impl.MyUserService;

@Configuration
public class SecurityConfig {
	@Autowired
	private MyUserService myUserService;
	
	@Autowired
	private JwtFilter jwtFilter;
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFlterChain(HttpSecurity http) throws Exception {
		return  http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(aut->{
				aut.requestMatchers("/register").hasRole("ADMIN");
				aut.requestMatchers("/login").permitAll();
				aut.requestMatchers("/employee/save","/employee/update/**","/employee/delete/**").hasRole("ADMIN");
				aut.requestMatchers("/employee/getAll","/employee/getById/**").hasRole("USER");
				//.hasRole("USER");
				aut.anyRequest().authenticated();
				} )
				.httpBasic(Customizer.withDefaults())
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
		 
		// return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return myUserService;
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setUserDetailsService(myUserService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	@Bean
	public AuthenticationManager authenticationmanager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
		}

}
