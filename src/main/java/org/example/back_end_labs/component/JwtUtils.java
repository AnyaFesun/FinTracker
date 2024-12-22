package org.example.back_end_labs.component;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {
    private final SecretKey jwtSecretKey;
    private final int jwtExpirationMs = 86400000; // 1 day

    public JwtUtils(@Value("${jwt.secret}") String jwtSecret) {
        if (jwtSecret == null || jwtSecret.isEmpty()) {
            throw new IllegalArgumentException("JWT secret cannot be null or empty");
        }
        this.jwtSecretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret.getBytes(StandardCharsets.UTF_8)));


    }

    public String generateJwtToken(Long userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(jwtSecretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public Long getUserIdFromJwtToken(String token) {
        return Long.parseLong(Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
    }
}
