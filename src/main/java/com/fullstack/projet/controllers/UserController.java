package com.fullstack.projet.controllers;

import com.fullstack.projet.models.user.User;
import com.fullstack.projet.services.UserUtils;
import com.fullstack.projet.services.user.IUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/me")
    public Optional<User> getCurrentUser() {
        return userService.findByEmail(UserUtils.getCurrentUserEmail());
    }

}
