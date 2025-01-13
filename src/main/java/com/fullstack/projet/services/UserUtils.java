package com.fullstack.projet.services;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {
    public static String getCurrentUserEmail() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

}
