package com.RestFulAPI.Utilis;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private String SECRET_KEY = "MySuperSecretKeyForJWTMustBeAtLeast32Bytes!";
	private int jwtExpirationMs = (1000 * 60 * 60);

	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}

	public String generateToken(String userName) {
		Map<String, Object> claimsMap = new HashMap<>();
		return createToken(claimsMap, userName);
	}

	public String createToken(Map<String, Object> claimsMap, String userName) {
		return Jwts.builder().claims(claimsMap) // ✅ Correct method for setting claims
				.subject(userName) // ✅ Set the subject (username)
				.header().empty().add("typ", "JWT") // ✅ Correct header setting for type
				.and().issuedAt(new Date(System.currentTimeMillis())) // ✅ Issue time
				.expiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // ✅ Expiry time
				.signWith(getSigningKey()) // ✅ Signing key + algo
				.compact(); // ✅ Build the token
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	public Boolean validateToken(String token) {
		return !isTokenExpired(token);
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public Date extractExpiration(String token) {
		return extractAllClaims(token).getExpiration();
	}

}
