package com.fullstack.projet.services.feedback;

import com.fullstack.projet.models.Feedback;
import com.fullstack.projet.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService implements IFeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public Feedback save(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @Override
    public Optional<Feedback> findById(Long id) {
        return feedbackRepository.findById(id);
    }

    @Override
    public List<Feedback> findByToolId(Long toolId) {
        return feedbackRepository.findAll().stream()
                .filter(f -> f.getTool().getId().equals(toolId))
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        feedbackRepository.deleteById(id);
    }
}
