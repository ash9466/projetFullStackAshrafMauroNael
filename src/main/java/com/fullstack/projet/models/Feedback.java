package com.fullstack.projet.models;

import com.fullstack.projet.exceptions.ValidationException;
import com.fullstack.projet.models.user.User;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "feedback")
public class Feedback implements ValidatableObject{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String comment;

    @ManyToOne
    private Tool tool;

    @ManyToOne
    private User user;

    public Feedback(){}

    public Feedback(String comment, Tool tool, User user) {
        this.comment = comment;
        this.tool = tool;
        this.user = user;
    }

    @Override
    public void validate() {
        if (StringUtils.isBlank(comment)) {
            throw new ValidationException("Comment cannot be null or empty.");
        }
        if (tool == null) {
            throw new ValidationException("Tool cannot be null.");
        }
        if (user == null) {
            throw new ValidationException("User cannot be null.");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
