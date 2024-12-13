package com.fullstack.projet.services.security;

import io.jsonwebtoken.Claims;

import java.util.function.Function;

public interface IJwtService {
    String generateAccessToken(String username);

    String generateRefreshToken(String username);

    Long extractId(String jwtToken, TokenType tokenType);

    <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver, TokenType tokenType);

    boolean tokenIsValid(String jwtToken, TokenType tokenType);
}
