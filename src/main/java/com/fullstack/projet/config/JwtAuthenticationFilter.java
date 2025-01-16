package com.fullstack.projet.config;

import com.fullstack.projet.models.user.User;
import com.fullstack.projet.services.security.IJwtService;
import com.fullstack.projet.services.security.TokenType;
import com.fullstack.projet.services.user.IUserService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${hope.security.accessTokenName}")
    private String ACCESS_TOKEN_PROPERTY_NAME;

    private final IJwtService jwtService;

    private final IUserService userService;

    public JwtAuthenticationFilter(IJwtService jwtService, IUserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        String jwtToken = null;
        String username;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ACCESS_TOKEN_PROPERTY_NAME.equals(cookie.getName())) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        }

        if (jwtToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        username = jwtService.extractUsername(jwtToken, TokenType.ACCESS);

        if (StringUtils.isNotBlank(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<User> user = userService.findByEmail(username);
            if(user.isPresent()){
                if (jwtService.tokenIsValid(jwtToken, TokenType.ACCESS)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(user.get(), null, user.get().getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

        }

        filterChain.doFilter(request, response);
    }

}
