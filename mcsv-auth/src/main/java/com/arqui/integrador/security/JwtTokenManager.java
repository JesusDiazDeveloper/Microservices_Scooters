package com.arqui.integrador.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenManager {
	
	private static final int TOKEN_VALIDITY = 3600000;
	
	@Value("${jwt.secret}")
	private String secret;

	public String generateJwtToken(UserDetails userDetails) {
		String authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", authorities);
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
				.signWith(SignatureAlgorithm.HS512, this.secret).compact();
	}

	public boolean validateJwtToken(String token, UserDetails userDetails) {
		String username = getUsernameFromToken(token);

		Claims claims = Jwts.parser()
				.setSigningKey(this.secret)
				.parseClaimsJws(token)
				.getBody();
		
		boolean isTokenExpired = claims.getExpiration().before(new Date());

		return (username.equals(userDetails.getUsername()) && !isTokenExpired);
	}

	public String getUsernameFromToken(String token) {
		final Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();

		return claims.getSubject();
	}
}
