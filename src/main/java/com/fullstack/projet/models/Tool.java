package com.fullstack.projet.models;

import com.fullstack.projet.exceptions.ValidationException;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Entity
public class Tool extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String domain;

    @Lob
    private String description;

    @ManyToOne
    private User addedBy;

    public Tool(String name, String domain, String description, User addedBy) {
        validateName(name);
        validateName(domain);
        if (description == null || description.isBlank()) {
            throw new ValidationException("Description cannot be null or empty.");
        }

        this.name = name;
        this.domain = domain;
        this.description = description;
        this.addedBy = addedBy;
    }
}