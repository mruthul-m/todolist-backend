package com.todolistbackend.service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private static final CharSequence SECRET = System.getenv("JWT_KEY");
	
	public boolean isTokenValid(String token) {
		try {
			extractAllClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}
	
	public String extractToken(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts
				.parser()
				.verifyWith((SecretKey) getSignKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
		
	}

	public String generateToken(String userName) {
		return Jwts
			.builder()
			.header()
			.add("typ","JWT").and()
			.subject(userName)
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis() + 1000*60*30))
			.signWith(getSignKey())
			.compact();
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Boolean isToeknExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public boolean validateToke(String username, UserDetails userDetails) {
		return username.equals(userDetails.getUsername());
		
	}


}
 