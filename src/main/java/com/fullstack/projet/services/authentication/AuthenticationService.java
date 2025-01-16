package com.fullstack.projet.services.authentication;

import com.fullstack.projet.models.User;
import com.fullstack.projet.models.authentication.AuthenticationResult;
import com.fullstack.projet.repositories.UserRepository;
import com.fullstack.projet.services.UserUtils;
import com.fullstack.projet.services.security.JwtService;
import com.fullstack.projet.services.security.TokenType;
import io.micrometer.common.util.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    @Transactional
    public AuthenticationResult register(@NonNull User newUser) {

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
        
        return setJWTAndGetAuthResponse(newUser.getEmail());
    }

    public AuthenticationResult authenticate(@NonNull User userLogIn) throws BadCredentialsException {

        Optional<User> user = userRepository.findByEmail(userLogIn.getEmail());
        if (user.isPresent()) {
            System.out.println(user.get().getEmail());
            System.out.println(user.get().getPassword());
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.get().getEmail(), user.get().getPassword())
                );
                if (authentication.isAuthenticated()) {
                    return setJWTAndGetAuthResponse(user.get().getEmail());
                }
            } catch (BadCredentialsException e) {
                throw new BadCredentialsException("Email or mot de passe incorrect");
            }
        }
        throw new BadCredentialsException("Email or mot de passe incorrect");
    }

    public AuthenticationResult renewToken(String refreshToken) {
        if (StringUtils.isBlank(refreshToken))
            throw new IllegalArgumentException("Refresh token not provided");

        if (jwtService.tokenIsValid(refreshToken, TokenType.REFRESH)) {
            return setJWTAndGetAuthResponse(UserUtils.getCurrentUserEmail());
        } else {
            throw new IllegalArgumentException("Invalid refresh token");
        }
    }

    public AuthenticationResult setJWTAndGetAuthResponse(String username) {
        String jwtAccessToken = jwtService.generateAccessToken(username);
        String jwtRefreshToken = jwtService.generateRefreshToken(username);

        return new AuthenticationResult(jwtAccessToken, jwtRefreshToken);
    }

    public boolean isTokenValid(String token, TokenType tokenType) {
        return jwtService.tokenIsValid(token, tokenType);
    }


}
