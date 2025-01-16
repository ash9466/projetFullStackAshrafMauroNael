package com.fullstack.projet.models.feedback;

import com.fullstack.projet.exceptions.ValidationException;
import com.fullstack.projet.models.ValidatableObject;
import com.fullstack.projet.models.tool.Tool;
import com.fullstack.projet.models.user.User;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;

@Entity(name = "feedback")
public class Feedback implements ValidatableObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String comment;

    @ManyToOne
    private Tool tool;

    @ManyToOne
    @JoinColumn(name = "creator")
    private User creator;

    public Feedback(){}

    public Feedback(String comment, Tool tool, User user) {
        this.comment = comment;
        this.tool = tool;
        this.creator = user;
    }

    @Override
    public void validate() {
        if (StringUtils.isBlank(comment)) {
            throw new ValidationException("Comment cannot be null or empty.");
        }
        if (tool == null) {
            throw new ValidationException("Tool cannot be null.");
        }
        if (creator == null) {
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

    public User getCreator() {
        return creator;
    }

    public void setCreator(User user) {
        this.creator = user;
    }
}
