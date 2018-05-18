package com.cts.moviecruiser.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cts.moviecruiser.domain.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtSecurityTokenGeneratorImpl implements SecurityTokenGenerator{

	@Override
	public Map<String, String> generateToken(User user) {
		
		String jwtToken = "";
		int minutes = 30;
		long expirationTime = 1000 * 60 * minutes ;  // seconds
		jwtToken = Jwts.builder()
				.setSubject(user.getUserId())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+expirationTime))
				.signWith(SignatureAlgorithm.HS256, "VennilaKeyy").compact();
		
		Map<String, String> jwtMap = new HashMap<>();
		jwtMap.put("token", jwtToken);
		jwtMap.put("userId", user.getUserId());
		jwtMap.put("message", "User successfully logged in");
		return jwtMap;
	}

	

}
