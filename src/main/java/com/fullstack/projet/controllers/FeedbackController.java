package com.fullstack.projet.controllers;

import com.fullstack.projet.models.Feedback;
import com.fullstack.projet.services.feedback.IFeedbackService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    private final IFeedbackService feedbackService;

    public FeedbackController(IFeedbackService feedbackService){
        this.feedbackService = feedbackService;
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping
    public Feedback createFeedback(@RequestBody Feedback feedback) {
        return feedbackService.save(feedback);
    }

}
