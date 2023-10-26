package com.bsuiramt.servetogetherbackend.service;

import com.bsuiramt.servetogetherbackend.model.AccountInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {
	
	@Value("${jwt.secret}")
	private String secret;
	
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, username);
	}
	
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + 7 * 24 * 60 * 60 * 1000);
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(now)
				.setExpiration(validity)
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}
	
	public boolean validateToken(String token, String username) {
		final String usernameFromToken = getUsernameFromToken(token);
		return (username.equals(usernameFromToken) && !isTokenExpired(token));
	}
	
	public String getUsernameFromToken(String token) {
		return Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	private Date getExpirationDateFromToken(String token) {
		return Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody()
				.getExpiration();
	}
	
	public boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration == null || expiration.before(new Date());
	}
}
