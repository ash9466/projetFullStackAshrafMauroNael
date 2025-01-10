package com.fullstack.projet.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Entity
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String domain;

    private String description;

    @ManyToOne
    private User addedBy;

    @OneToMany(mappedBy = "tool", cascade = CascadeType.ALL)
    private List<Feedback> feedbacks;
}
