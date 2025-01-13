package com.fullstack.projet.models.authentication;

public class AuthenticationResult {

    private final String accessToken;
    private final String refreshToken;

    public AuthenticationResult(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}