package com.example.demo.service.impl;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.entity.MyUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class MyUserServiceToken {
	
//	private String secretKey;
	
//	public MyUserServiceToken() {
//		try {
//			KeyGenerator key=KeyGenerator.getInstance("HmacSHA256");
//			SecretKey sk=key.generateKey();
//			secretKey=Base64.getEncoder().encodeToString(sk.getEncoded());
//		} catch (NoSuchAlgorithmException e) {
//			throw new RuntimeException(e);
//		}
//		
//	}
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authManager;

	public String verify(MyUser myUser) {
		Authentication authentication=authManager.authenticate(new UsernamePasswordAuthenticationToken(myUser.getUserName(),myUser.getPassword()));
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(myUser.getUserName());
		}
		return "Fail";
	}

//	private String generateToken(String userName) {
//		Map<String,Object> claims = new HashMap<>();
//		return Jwts.builder()
//				.claims()
//				.add(claims)
//				.subject(userName)
//				.issuedAt(new Date(System.currentTimeMillis()))
//				.expiration(new Date(System.currentTimeMillis()+60*60*30))
//				.and()
//				.signWith(getKey())
//				.compact();
//	}
//
//	private SecretKey getKey() {
//		byte[] keyBytes=Decoders.BASE64.decode(secretKey);
//		return Keys.hmacShaKeyFor(keyBytes);
//	}
//
//	public String extractUserName(String token) {
//		return extractClaim(token,Claims::getSubject);
//	}
//
//	private <T>T  extractClaim(String token, Function<Claims,T> claimResolver) {
//		final Claims claims=extractAllClaims(token);
//		return claimResolver.apply(claims);
//	}
//
//	private Claims extractAllClaims(String token) {
//		return Jwts.parser()
//				.verifyWith(getKey())
//				.build()
//				.parseSignedClaims(token)
//				.getPayload();
//	}
//
//	public boolean validateToken(String token, UserDetails userDetails) {
//		final String username=extractUserName(token);
//		return(username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//	}
//
//	private boolean isTokenExpired(String token) {
//		return extractExpiration(token).before(new Date());
//		
//	}
//
//	private Date extractExpiration(String token) {
//		return extractClaim(token,Claims::getExpiration);
//	}

}
