package com.example.authservice.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtUtils {

    private static final int MILLIS_IN_SECOND = 1000;
    private static final int SECONDS_IN_MINUTES = 60;
    private static final int MINUTES_IN_HOUR = 60;
    private static final int HOURS_IN_DAY = 24;
    private static final int TEN_DAYS_IN_MILLIS = 10 * HOURS_IN_DAY * MINUTES_IN_HOUR * SECONDS_IN_MINUTES * MILLIS_IN_SECOND;


    private final SecretKey SECRET_KEY;

    public JwtUtils(@Value("${jwt.secret}") String jwtSecret) {
        SECRET_KEY = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }


    public String generateToken(String email , String role){

        return Jwts
                .builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TEN_DAYS_IN_MILLIS))
                .signWith(SECRET_KEY)
                .compact();
    }


    public boolean validateJwt(String token){
        try {
            Jwts
                    .parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    private  Claims getClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getEmail(String token) {
        return getClaims(token).getSubject();
    }

    public String getRole(String token) {
        return getClaims(token).get("role").toString();
    }

    public long getExpirationDate(String token) {
        return getClaims(token).getExpiration().getTime();
    }
    public Boolean isExpired(String token) {
        return getClaims(token).getExpiration().before(new Date(System.currentTimeMillis()));
    }



}
