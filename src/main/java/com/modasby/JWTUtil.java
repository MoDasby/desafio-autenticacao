package com.modasby;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.enterprise.context.ApplicationScoped;

import javax.crypto.SecretKey;
import java.util.Date;

@ApplicationScoped
public class JWTUtil {
    private static final String jwtSecretKey = "478fg4367fgb4fh8943g8947f1pzvn6i";
    SecretKey key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());

    public String generateToken(String credential) {
        int expiration = 7 * 24 * 60 * 60 * 1000; // 7 dias

        return Jwts
                .builder()
                .signWith(key)
                .subject(credential)
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }

    public boolean authenticateToken(String token) {
        try {
            parseToken(token);

            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    private void parseToken(String token) {
        Jwts
            .parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token);
    }
}
