package com.example.demo.generate_validate;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
	private final String SECRET_KEY="fbac298300b237cfec43621bf9a807ce83115bdf61b7cf7137251c3100b95512";
	
	public String generateToken(com.example.demo.entity.User user) {
		String token=Jwts.builder()
				.subject(user.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+24*60*60*1000))
				.signWith(getSigninKey())
				.compact();
		return token;
	}

	private SecretKey getSigninKey() {
		// TODO Auto-generated method stub
		byte[] KeyBytes=Decoders.BASE64URL.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(KeyBytes);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.
				parser()
				.verifyWith(getSigninKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public <T> T extractClaim(String token,Function<Claims, T> resolver) {
		Claims claims=extractAllClaims(token);
		return resolver.apply(claims);
	}
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	//validating
	
	public boolean isValid(String token ,UserDetails user) {
		String username=extractUsername(token);
		return (username.equals(user.getUsername()))&& !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		// TODO Auto-generated method stub
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		// TODO Auto-generated method stub
		return extractClaim(token, Claims::getExpiration);
	
	}
	

}
