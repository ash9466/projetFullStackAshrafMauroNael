package com.fullstack.projet.repositories;

import com.fullstack.projet.models.feedback.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> getFeedbacksByToolId(Long toolId);

}
