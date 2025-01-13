package com.fullstack.projet.services.feedback;

import com.fullstack.projet.models.Feedback;

import java.util.List;
import java.util.Optional;

public interface IFeedbackService {
    Feedback save(Feedback feedback);
    Optional<Feedback> findById(Long id);
    void deleteById(Long id);
}
