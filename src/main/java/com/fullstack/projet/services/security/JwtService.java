package com.fullstack.projet.services.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
public class JwtService implements IJwtService {

    @Value("${hope.security.accessTokenSecret}")
    private String ACCESS_TOKEN_SECRET;
    @Value("${hope.security.refreshTokenSecret}")
    private String REFRESH_TOKEN_SECRET;
    @Value("${hope.security.accessTokenExpiration}")
    private long ACCESS_EXPIRATION_TIME;
    @Value("${hope.security.refreshTokenExpiration}")
    private long REFRESH_EXPIRATION_TIME;
    
    @Override
    public String generateAccessToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(ACCESS_EXPIRATION_TIME)))
                .signWith(getSignInKey(TokenType.ACCESS), SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(REFRESH_EXPIRATION_TIME)))
                .signWith(getSignInKey(TokenType.REFRESH), SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public String extractUsername(String jwtToken, TokenType tokenType) {
        return extractClaim(jwtToken, Claims::getSubject, tokenType);
    }

    @Override
    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver, TokenType tokenType) {
        final Claims claims = extractAllClaims(jwtToken, tokenType);
        return claimsResolver.apply(claims);
    }
    
    private Claims extractAllClaims(String jwtToken, TokenType tokenType) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey(tokenType))
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getSignInKey(TokenType tokenType) {
        byte[] keyBytes = Decoders.BASE64.decode(tokenType.equals(TokenType.ACCESS) ? ACCESS_TOKEN_SECRET : REFRESH_TOKEN_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public boolean tokenIsValid(String jwtToken, TokenType tokenType) {
        return !tokenIsExpired(jwtToken, tokenType);
    }

    private boolean tokenIsExpired(String jwtToken, TokenType tokenType) {
        return extractExpiration(jwtToken, tokenType).before(new Date());
    }

    private Date extractExpiration(String jwtToken, TokenType tokenType) {
        return extractClaim(jwtToken, Claims::getExpiration, tokenType);
    }


    
}
