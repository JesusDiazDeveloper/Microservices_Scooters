package com.arqui.integrador.security;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String secret;
	
	public boolean validateJwtToken(String token, String path) {
		
		Claims claims = Jwts.parser()
				.setSigningKey(this.secret)
				.parseClaimsJws(token)
				.getBody();
		
		boolean hasRole = false;
		
		if(path.contains("/users") || path.contains("/accounts")) {
			hasRole = hasRole(claims, "ADMIN,USER");
		} else if(path.contains("/maintenance")) {
			hasRole = hasRole(claims, "ADMIN,MAINTENANCE");
		} else {
			hasRole = hasRole(claims, "ADMIN");
		}
		
		boolean isTokenExpired = claims.getExpiration().before(new Date());
		
		return (!isTokenExpired && hasRole);
	}
	
	private boolean hasRole(Claims claims, String role) {
		String roles = (String)claims.get("roles");
		
		List<String> rolesList = Arrays.asList(roles.split(","));
		
		for(String s: rolesList) {
			if(role.contains(s.toUpperCase())) {
				return true;
			}
		}
		throw new RuntimeException("Incorrect role");
	}
}
