package com.fullstack.projet.models.user;

public record UserDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        Role role
) {
    public static UserDto fromUser(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }
}