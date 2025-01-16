package com.fullstack.projet.models.user;

import com.fullstack.projet.exceptions.ValidationException;
import com.fullstack.projet.models.ValidatableObject;
import com.fullstack.projet.models.feedback.Feedback;
import com.fullstack.projet.models.tool.Tool;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity(name = "user")
public class User implements UserDetails, ValidatableObject {

    public final static long MIN_PASSWORD_LENGTH = 8;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.PERSIST)
    private List<Tool> toolsCreated;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.REMOVE)
    private List<Feedback> feedbackGiven;

    public User(){}

    public User(String firstName, String lastName, String email, String password, Role role, List<Tool> toolsCreated, List<Feedback> feedbackGiven) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.toolsCreated = toolsCreated;
        this.feedbackGiven = feedbackGiven;
    }

    @Override
    public void validate() {
        if (StringUtils.isBlank(firstName))
            throw new ValidationException("Votre prenom est requis.");
        if (StringUtils.isBlank(lastName))
            throw new ValidationException("Votre nom est requis.");
        if (StringUtils.isBlank(email))
            throw new ValidationException("Votre mail est requis.");
        if (StringUtils.isBlank(password) || password.length() < MIN_PASSWORD_LENGTH)
            throw new ValidationException("Le mot de passe est obligatoire et doit contenir au moins 8 caractères.");
        if(role == null)
            throw new ValidationException("Le role est requis.");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Tool> getToolsCreated() {
        return toolsCreated;
    }

    public void setToolsCreated(List<Tool> toolsCreated) {
        this.toolsCreated = toolsCreated;
    }

    public List<Feedback> getFeedbackGiven() {
        return feedbackGiven;
    }

    public void setFeedbackGiven(List<Feedback> feedbackGiven) {
        this.feedbackGiven = feedbackGiven;
    }
}
