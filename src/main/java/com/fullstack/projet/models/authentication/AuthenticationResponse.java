package com.fullstack.projet.models.authentication;

public class AuthenticationResponse {

    private final Boolean authenticated;

    public AuthenticationResponse(Boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Boolean getAuthenticated() {
        return authenticated;
    }
}