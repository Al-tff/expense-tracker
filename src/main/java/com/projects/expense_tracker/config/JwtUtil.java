package com.projects.expense_tracker.config;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
    public class JwtUtil {

        private final Key key;
        private final long expirationMs;

        public JwtUtil(@Value("${jwt.secret}") String secret,
                       @Value("${jwt.expiration}") long expirationMs) {
            if (secret == null || secret.length() < 32) {
                throw new IllegalArgumentException("jwt.secret must be at least 32 chars");
            }
            this.key = Keys.hmacShaKeyFor(secret.getBytes());
            this.expirationMs = expirationMs;
        }

        public String generateToken(String username) {
            Date now = new Date();
            Date exp = new Date(now.getTime() + expirationMs);
            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(now)
                    .setExpiration(exp)
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
        }

        public String extractUsername(String token) {
            try {
                return Jwts.parserBuilder().setSigningKey(key).build()
                        .parseClaimsJws(token).getBody().getSubject();
            } catch (JwtException e) {
                return null;
            }
        }

        public boolean validateToken(String token) {
            try {
                Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
                return true;
            } catch (JwtException e) {
                return false;
            }
        }
    }

