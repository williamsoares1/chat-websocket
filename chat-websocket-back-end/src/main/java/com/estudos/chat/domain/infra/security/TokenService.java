package com.estudos.chat.domain.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.estudos.chat.domain.entity.Usuario;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secretKey;

    public String generateToken(Usuario usuario) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return JWT.create()
                .withIssuer("quickstock-api")
                .withSubject(usuario.getEmail())
                .withExpiresAt(expirationDate())
                .sign(algorithm);

        } catch (JWTCreationException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public String validateToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return JWT.require(algorithm)
                .withIssuer("quickstock-api")
                .build()
                .verify(token)
                .getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    private Instant expirationDate() {
        return LocalDateTime.now().plusHours(9).toInstant(ZoneOffset.of("-03:00"));
    }

}
