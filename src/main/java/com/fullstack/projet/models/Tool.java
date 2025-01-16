package com.fullstack.projet.models;

import com.fullstack.projet.exceptions.ValidationException;
import com.fullstack.projet.models.user.User;
import jakarta.persistence.*;

@Entity(name = "tool")
public class Tool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String domain;

    @Lob
    private String description;

    @ManyToOne
    private User addedBy;

    public Tool(){}

    public Tool(String name, String domain, String description, User addedBy) {
        if (description == null || description.isBlank()) {
            throw new ValidationException("Description cannot be null or empty.");
        }

        this.name = name;
        this.domain = domain;
        this.description = description;
        this.addedBy = addedBy;
    }
}