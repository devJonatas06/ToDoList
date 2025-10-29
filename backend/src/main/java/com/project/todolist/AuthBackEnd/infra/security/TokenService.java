package com.project.todolist.AuthBackEnd.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.project.todolist.AuthBackEnd.Domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private  String secret;

    public String genareteToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("login-auth-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(this.generateExpirationDate())
                    .sign(algorithm);
            return token;
        }catch (JWTCreationException exception){
            throw new RuntimeException("Error while authenticating");
        }
    }
    public String getSubject(String token){
        if (token == null || token.isBlank()) return null;
        DecodedJWT decoded = verifyToken(token);
        return decoded != null ? decoded.getSubject() : null;
    }

    public boolean isValidToken(String token){
        if (token == null || token.isBlank()) return false;
        DecodedJWT decoded = verifyToken(token);
        return decoded != null && !decoded.getExpiresAt().before(new Date());
    }

    private DecodedJWT verifyToken(String token){
        if (token == null || token.isBlank()) return null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("login-auth-api")
                    .build()
                    .verify(token);
        } catch (JWTVerificationException e){
            return null;
        }
    }

    private Date generateExpirationDate(){
        Instant instant = Instant.now().plus(3, ChronoUnit.HOURS);
        return Date.from(instant);
    }
}
