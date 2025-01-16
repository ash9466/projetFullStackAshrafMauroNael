package com.fullstack.projet.models.tool;

import com.fullstack.projet.exceptions.ValidationException;
import com.fullstack.projet.models.ValidatableObject;
import com.fullstack.projet.models.feedback.Feedback;
import com.fullstack.projet.models.user.User;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "tool")
public class Tool implements ValidatableObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String domain;
    private String link;
    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;
    @OneToMany(mappedBy = "tool")
    private List<Feedback> feedbacks;
    @ManyToOne
    @JoinColumn(name = "creator")
    private User creator;

    public Tool(){}

    public Tool(String name, String domain, String link, String description) {
        this.name = name;
        this.domain = domain;
        this.description = description;
        this.link = link;
    }

    @Override
    public void validate() {
        if (StringUtils.isBlank(name))
            throw new ValidationException("Le nom de l'outil est requis.");
        if (StringUtils.isBlank(domain))
            throw new ValidationException("Le domaine de l'outil est requis.");
        if (StringUtils.isBlank(description))
            throw new ValidationException("La description de l'outil est requise.");
        if (StringUtils.isBlank(link))
            throw new ValidationException("Le lien vers l'outil est requis.");
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}