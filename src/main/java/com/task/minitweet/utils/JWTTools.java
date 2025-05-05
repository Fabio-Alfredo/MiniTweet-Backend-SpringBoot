package com.task.minitweet.utils;

import com.task.minitweet.domains.models.User;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class JWTTools {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Integer expiration;

    public String generateToken(User user){
        HashMap<String, Object>claims = new HashMap<>();

        return Jwts.builder()
                .claims(claims)
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public Boolean verifyToken(String token){
        try{
            JwtParser parser = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build();
            parser.parse(token);

            return true;
        }catch (Exception e){
            return false;
        }
    }

    public String getEmailByFrom(String token){
        try{
            JwtParser parser = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build();
            return parser.parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        }catch (Exception e){
            return null;
        }
    }
}
