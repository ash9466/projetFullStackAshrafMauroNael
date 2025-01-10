package com.fullstack.projet.exceptions;

public class InvalidEmailException extends ValidationException {
    public InvalidEmailException(String message) {
        super(message);
    }
}
