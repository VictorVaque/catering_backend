package com.calblay.catering_api.security;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;
import com.calblay.catering_api.users.application.port.out.TokenServicePort;

@Component
public class JwtTokenServiceAdapter implements TokenServicePort {

    private final Key key;
    private static final long EXPIRATION_TIME = 86400000;

    public JwtTokenServiceAdapter(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Override
    public String generate(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("rol", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getEmail(String token) {
        return parse(token).getSubject();
    }

    public String getRole(String token) {
        return (String) parse(token).get("rol");
    }
}