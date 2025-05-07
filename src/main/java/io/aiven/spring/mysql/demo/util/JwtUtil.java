// util/JwtUtil.java
package io.aiven.spring.mysql.demo.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "d1f2f7a9b0e3c6d1f8a0b2d3c6e7f1d2a5c0d6e8f7a9b1c6e5a2d4e7a9c1d2";
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false; // Includes ExpiredJwtException, MalformedJwtException, etc.
        }
    }
}
/*
The JwtUtil class helps in generating, validating, and extracting information from JWT tokens using a secret key.
It generates a token with the username as the subject, sets issued and expiry time, and signs it using HMAC-SHA256.(Hash-based Message Authentication Code)
It validates the token by parsing it with the same secret key and handles exceptions like expiry or tampering.
It can also extract the username from a valid token using the getSubject() method of the parsed claims.
 */