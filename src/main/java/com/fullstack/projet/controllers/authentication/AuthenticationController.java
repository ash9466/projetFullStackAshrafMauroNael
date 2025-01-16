package com.fullstack.projet.controllers.authentication;

import com.fullstack.projet.controllers.ErrorHandlingUtils;
import com.fullstack.projet.exceptions.ErrorBody;
import com.fullstack.projet.exceptions.ValidationException;
import com.fullstack.projet.models.authentication.AuthenticationResponse;
import com.fullstack.projet.models.authentication.AuthenticationResult;
import com.fullstack.projet.models.user.User;
import com.fullstack.projet.services.authentication.AuthenticationService;
import com.fullstack.projet.services.security.TokenType;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

    private final static String REFRESH_TOKEN_PROPERTY = "hope_refreshToken";

    private final static String ACCESS_TOKEN_PROPERTY = "hope_accessToken";

    @Value("${hope.security.refreshTokenExpiration}")
    private long REFRESH_EXPIRATION_TIME;

    @Value("${hope.security.accessTokenExpiration}")
    private long ACCESS_EXPIRATION_TIME;

    @Value("${hope.security.cookies.secure}")
    private boolean COOKIES_ARE_SECURE;

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody User newUser) {
        LOG.info("Registering new user");
        try {
            AuthenticationResult result = authenticationService.register(newUser);
            LOG.info("Successfully registered new user");

            return getAuthenticationResponse(result, newUser.getEmail());
        } catch (ValidationException e) {
            return ErrorHandlingUtils.handleBadRequest(LOG,e);
        } catch (Exception e) {
            return ErrorHandlingUtils.handleInternalServerError(LOG, "Failed to register new user", e);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@RequestBody User request) {
        LOG.info("Authenticating user");
        try {
            AuthenticationResult result = authenticationService.authenticate(request);
            LOG.info("Successfully authenticated user");
            return getAuthenticationResponse(result, request.getEmail());

        } catch (BadCredentialsException e) {
            return ErrorHandlingUtils.handleBadRequest(LOG, e);
        } catch (Exception e) {
            return ErrorHandlingUtils.handleInternalServerError(LOG, "Failed to authenticate user", e);
        }
    }

    @GetMapping("/renew-token")
    public ResponseEntity<Object> renewToken(@CookieValue(name = REFRESH_TOKEN_PROPERTY, required = false) String refreshToken) {
        try {
            if (StringUtils.isBlank(refreshToken))
                throw new IllegalArgumentException("Refresh token not provided");
            AuthenticationResult result = authenticationService.renewToken(refreshToken);
            String userEmail = authenticationService.getEmailFromToken(refreshToken, TokenType.REFRESH);
            return getAuthenticationResponse(result, userEmail);
        } catch (IllegalArgumentException e) {
            return removeTokensResponse(e);
        } catch (Exception e) {
            return ErrorHandlingUtils.handleInternalServerError(LOG, "Failed to renew refresh token", e);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<Object> logout() {
        ResponseCookie refreshTokenCookie = createCookie(REFRESH_TOKEN_PROPERTY, "", Duration.ofSeconds(0));
        ResponseCookie accessTokenCookie = createCookie(ACCESS_TOKEN_PROPERTY, "", Duration.ofSeconds(0));

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .cacheControl(CacheControl.noCache())
                .body(true);
    }
    
    @GetMapping("/authenticated")
    public ResponseEntity<Object> isAuthenticated(@CookieValue(name = ACCESS_TOKEN_PROPERTY, required = false) String accessToken) {
        try {
            if (StringUtils.isBlank(accessToken))
                ResponseEntity.ok()
                        .body(new AuthenticationResponse(false));

            boolean isTokenValid = authenticationService.isTokenValid(accessToken, TokenType.ACCESS);

            if(isTokenValid) {
                String userEmail = authenticationService.getEmailFromToken(accessToken, TokenType.ACCESS);
                return ResponseEntity.ok(new AuthenticationResponse(true, authenticationService.getUserBasicInfo(userEmail)));
            }

            return ResponseEntity.ok()
                    .body(new AuthenticationResponse(false));
        } catch (Exception e) {
            return ResponseEntity.ok()
                    .body(new AuthenticationResponse(false));
        }
    }

    private ResponseEntity<Object> getAuthenticationResponse(AuthenticationResult result, String email) {
        ResponseCookie refreshTokenCookie = createCookie(REFRESH_TOKEN_PROPERTY, result.getRefreshToken(), Duration.ofDays(REFRESH_EXPIRATION_TIME));
        ResponseCookie accessTokenCookie = createCookie(ACCESS_TOKEN_PROPERTY, result.getAccessToken(), Duration.ofMinutes(ACCESS_EXPIRATION_TIME));

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .cacheControl(CacheControl.noCache())
                .body(new AuthenticationResponse(true, authenticationService.getUserBasicInfo(email)));
    }

    private ResponseEntity<Object> removeTokensResponse(Exception e) {
        ResponseCookie refreshTokenCookie = createCookie(REFRESH_TOKEN_PROPERTY, "", Duration.ofSeconds(0));
        ResponseCookie accessTokenCookie = createCookie(ACCESS_TOKEN_PROPERTY, "", Duration.ofSeconds(0));

        return ResponseEntity.badRequest()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .cacheControl(CacheControl.noCache())
                .body(new ErrorBody(e.getMessage()));
    }

    private ResponseCookie createCookie(String propertyName, String value, Duration age) {
        return ResponseCookie.from(propertyName, value)
                .httpOnly(true)
                .secure(COOKIES_ARE_SECURE)
                .path("/")
                .maxAge(age).build();
    }

}
