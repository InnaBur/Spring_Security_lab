package com.todo.config;

import com.todo.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    private static final String SECRET_KEY = "skA+5dA0fBOd/dZsCidpRWR8LnfXHEfXCf6fNId0o8I=";
//    String SECRET_KEY = Encoders.BASE64.encode(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded());
//    @Value("${jwt.secret}")
//    private String SECRET_KEY;

//    @Value("${jwt.exptime}")
//    private String EXP_TIME;

    public JwtService() throws NoSuchAlgorithmException {
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
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
         String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token).getExpiration().after(new Date());
    }


    private Claims extractAllClaims(String token) {
        return Jwts.
                parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }

private void generateKey() {
    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    String secretString = Encoders.BASE64.encode(key.getEncoded());

    logger.info("Secret key: " + secretString);
}
}
