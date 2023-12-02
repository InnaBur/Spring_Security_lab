package com.todo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class JwtService {
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    private static final String SECRET_KEY = "skA+5dA0fBOd/dZsCidpRWR8LnfXHEfXCf6fNId0o8I=";
    private final SecretKey secretKey;

    @Value("${jwt.exptime}")
    private long EXP_TIME;

    public JwtService() {
        this.secretKey = (SecretKey) getSignInKey();
    }

    public String extractUsername(String token) {
        return extractClaim(token).getSubject();
    }

    public Claims extractClaim(String token) {
        return extractAllClaims(token);
    }

    public String generateToken(String username) {
        return Jwts
                .builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000*24*60))
                .signWith(secretKey)
                .compact();
    }

    public boolean isTokenValid(String token) {
        String username = extractUsername(token);
        return isTokenNotExpired(token);
    }

    private boolean isTokenNotExpired(String token) {
        return extractClaim(token).getExpiration().after(new Date());
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }


}
