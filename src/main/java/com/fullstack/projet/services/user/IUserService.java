package com.fullstack.projet.services.user;

import com.fullstack.projet.models.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface IUserService extends UserDetailsService {

    Optional<User> findById(Long id);

}
