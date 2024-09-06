package com.estudos.chat.infra.security.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.estudos.domain_ms.entity.Usuario;
import com.estudos.chat.infra.security.dtos.TokenDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class TokenService {

    @Value("${auth.jtw.token.secret}")
    private String secretKey;

    @Value("${auth.jwt.token.expiration}")
    private Integer hoursTokenExpiration;

    @Value("${auth.jwt.refresh-token.expiration}")
    private Integer hoursRefreshTokenExpiration;

    public String generateToken(Usuario usuario, Integer expiration) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return JWT.create()
                    .withIssuer("websocket-api")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(expirationDate(expiration))
                    .sign(algorithm);

        } catch (JWTCreationException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public TokenDTO obterToken(Usuario usuario) {
        return TokenDTO.builder()
                .token(generateToken(usuario, hoursTokenExpiration))
                .refreshToken(generateToken(usuario, hoursRefreshTokenExpiration))
                .build();
    }

    public void salvarTokenCookie(Usuario usuario, HttpServletResponse response) {
        TokenDTO tokens = obterToken(usuario);

        Cookie accessTokenCookie = new Cookie("accessToken", tokens.token());
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(hoursTokenExpiration * 60 * 60);

        Cookie refreshTokenCookie = new Cookie("refreshToken", tokens.refreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/auth/refresh-token");
        refreshTokenCookie.setMaxAge(hoursRefreshTokenExpiration * 60 * 60);

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return JWT.require(algorithm)
                    .withIssuer("websocket-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    private Instant expirationDate(Integer expiration) {
        return LocalDateTime.now().plusHours(expiration).toInstant(ZoneOffset.of("-03:00"));
    }

}
