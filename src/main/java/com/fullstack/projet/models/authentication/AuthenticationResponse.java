package com.fullstack.projet.models.authentication;

import com.fullstack.projet.models.user.UserProjection;

public class AuthenticationResponse {

    private final Boolean authenticated;

    private UserProjection user;

    public AuthenticationResponse(Boolean authenticated) {
        this.authenticated = authenticated;
    }

    public AuthenticationResponse(Boolean authenticated, UserProjection projection) {
        this.authenticated = authenticated;
        this.user = projection;
    }

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public UserProjection getUser() {
        return user;
    }
}