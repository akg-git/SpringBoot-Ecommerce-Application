package edu.ecomm.practice.service.impl;

import java.security.Key;
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

import edu.ecomm.practice.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	
	private String secretKey = "";
	

	public JWTService() {
		super();
		
		try {
			// generate secret key
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey newGeneratedKey = keyGen.generateKey();
			
			secretKey = Base64.getEncoder().encodeToString(newGeneratedKey.getEncoded());
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public String generateToken(User user) {
		
		Map<String, Object> claimMap = new HashMap<>();
		
		return Jwts
				.builder()
				.claims()
				.add(claimMap)
				.subject(user.getUserName())
				.issuedAt(new Date(System.currentTimeMillis()))	
				.expiration(new Date(System.currentTimeMillis()+1000*60*30))	//  30 minutes token expiration time
				.and()
				.signWith(getKey())
				.compact()
				;	
	}

	// To get new dynamic secret key through constructor
	private SecretKey getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractusername(String token) {
		return extractClaim( token, Claims::getSubject);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts
				.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				;
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractusername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}
	


}
