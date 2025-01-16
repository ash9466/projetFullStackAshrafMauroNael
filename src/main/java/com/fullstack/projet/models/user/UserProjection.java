package com.fullstack.projet.models.user;

import com.fullstack.projet.models.Role;

public interface UserProjection {
    String getFirstName();
    String getLastName();
    Role getRole();
}
