package com.fullstack.projet.models;

import com.fullstack.projet.exceptions.ValidationException;

import java.util.regex.Pattern;

public abstract class BaseModel {
    protected void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new ValidationException("Email cannot be null or empty.");
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!Pattern.matches(emailRegex, email)) {
            throw new ValidationException("Invalid email format: " + email);
        }
    }

    protected void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new ValidationException("Password must be at least 8 characters long.");
        }
        if (!password.matches(".*\\d.*")) {
            throw new ValidationException("Password must contain at least one digit.");
        }
    }

    protected void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Name cannot be null or empty.");
        }
    }
}
