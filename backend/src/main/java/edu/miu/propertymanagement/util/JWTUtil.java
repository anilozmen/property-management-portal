package edu.miu.propertymanagement.util;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {
    final private static long ACCESS_TOKEN_LIFE_IN_MS = 5 * 60 * 60 * 1000; // 5 hours
    final private static long REFRESH_TOKEN_LIFE_IN_MS = 7 * 24 * 60 * 60 * 1000; // 7 days
    final private static String TOKEN_SECRET = "Thisisthesecret";

    public String generateAccessToken(UserDetails userDetailsDto) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetailsDto.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_LIFE_IN_MS))
                .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_LIFE_IN_MS))
                .signWith(SignatureAlgorithm.HS256, TOKEN_SECRET)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(TOKEN_SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private void validateToken(String token) {
        Jwts.parser()
                .setSigningKey(TOKEN_SECRET)
                .parseClaimsJws(token);
    }
}
