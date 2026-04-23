package com.example.gmahk.security.service;

import com.example.gmahk.config.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    // JJWT 0.12.x: pakai SecretKey bukan Key
    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtProperties.getSecret().getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)                          // 0.12.x: claims() bukan setClaims()
                .subject(userDetails.getUsername())           // 0.12.x: subject() bukan setSubject()
                .issuedAt(new Date())                         // 0.12.x: issuedAt() bukan setIssuedAt()
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getExpirationMs())) // 0.12.x
                .signWith(getSigningKey())                    // 0.12.x: tidak perlu explicit algorithm
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()                                  // 0.12.x: parser() bukan parserBuilder()
                .verifyWith(getSigningKey())                   // 0.12.x: verifyWith() bukan setSigningKey()
                .build()
                .parseSignedClaims(token)                     // 0.12.x: parseSignedClaims() bukan parseClaimsJws()
                .getPayload();                                // 0.12.x: getPayload() bukan getBody()
    }
}