package com.fullstack.projet.models;

import com.fullstack.projet.exceptions.ValidationException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.springframework.data.annotation.Id;

import jakarta.persistence.*;

@Data
@Entity
public class Feedback extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String comment;

    @ManyToOne
    private Tool tool;

    @ManyToOne
    private User user;

    public Feedback(String comment, Tool tool, User user) {
        if (comment == null || comment.isBlank()) {
            throw new ValidationException("Comment cannot be null or empty.");
        }
        if (tool == null) {
            throw new ValidationException("Tool cannot be null.");
        }
        if (user == null) {
            throw new ValidationException("User cannot be null.");
        }

        this.comment = comment;
        this.tool = tool;
        this.user = user;
    }
}
