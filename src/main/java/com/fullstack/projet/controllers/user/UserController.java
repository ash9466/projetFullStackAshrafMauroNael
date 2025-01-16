package com.fullstack.projet.controllers.user;

import com.fullstack.projet.controllers.ErrorHandlingUtils;
import com.fullstack.projet.models.user.User;
import com.fullstack.projet.models.user.UserDto;
import com.fullstack.projet.services.UserUtils;
import com.fullstack.projet.services.user.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        LOG.info("Getting user {}", id);
        return userService.findById(id);
    }

    @GetMapping("/me")
    public UserDto getCurrentUser() {
        return UserDto.fromUser(userService.findByEmail(UserUtils.getCurrentUserEmail()).get());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Object> deleteUserById(@PathVariable Long userId){
        LOG.info("Deleting user {} as admin", userId);
        try {
            userService.deleteById(userId);
            LOG.info("Successfully deleted new user");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ErrorHandlingUtils.handleInternalServerError(LOG, "La suppression de l'utilisateur a echoue", e);
        }
    }

}
