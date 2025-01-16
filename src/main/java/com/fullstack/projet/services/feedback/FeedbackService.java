package com.fullstack.projet.services.feedback;

import com.fullstack.projet.models.feedback.Feedback;
import com.fullstack.projet.models.user.User;
import com.fullstack.projet.repositories.FeedbackRepository;
import com.fullstack.projet.repositories.UserRepository;
import com.fullstack.projet.services.UserUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService implements IFeedbackService {

    private final FeedbackRepository feedbackRepository;

    private final UserRepository userRepository;

    public FeedbackService(FeedbackRepository feedbackRepository, UserRepository userRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Feedback save(Feedback feedback) {
        User creator = userRepository.findByEmail(UserUtils.getCurrentUserEmail()).get();
        feedback.setCreator(creator);
        return feedbackRepository.save(feedback);
    }

    @Override
    public Optional<Feedback> findById(Long id) {
        return feedbackRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        feedbackRepository.deleteById(id);
    }

    @Override
    public List<Feedback> getFeedbacksByToolId(Long toolId){
        return feedbackRepository.getFeedbacksByToolId(toolId);
    }
}
