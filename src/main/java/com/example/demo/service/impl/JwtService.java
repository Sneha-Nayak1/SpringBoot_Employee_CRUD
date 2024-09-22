package com.example.demo.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private String secretKey;
	
	public JwtService() {
		try {
			KeyGenerator key=KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk=key.generateKey();
			secretKey=Base64.getEncoder().encodeToString(sk.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	String generateToken(String userName) {
		Map<String,Object> claims = new HashMap<>();
		return Jwts.builder()
				.claims()
				.add(claims)
				.subject(userName)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+60*60*30))
				.and()
				.signWith(getKey())
				.compact();
	}

	private SecretKey getKey() {
		byte[] keyBytes=Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUserName(String token) {
		return extractClaim(token,Claims::getSubject);
	}

	private <T>T  extractClaim(String token, Function<Claims,T> claimResolver) {
		final Claims claims=extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String username=extractUserName(token);
		return(username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
		
	}

	private Date extractExpiration(String token) {
		return extractClaim(token,Claims::getExpiration);
	}


}
