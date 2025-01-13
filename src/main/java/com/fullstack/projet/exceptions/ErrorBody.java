package com.fullstack.projet.exceptions;

public class ErrorBody {

    private final String error;

    public ErrorBody(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}