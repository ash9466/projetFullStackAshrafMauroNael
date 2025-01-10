package com.fullstack.projet.controllers;

import com.fullstack.projet.models.Feedback;
import com.fullstack.projet.services.feedback.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    private IFeedbackService feedbackService;

    @PostMapping
    public Feedback createFeedback(@RequestBody Feedback feedback) {
        return feedbackService.save(feedback);
    }

    @GetMapping("/tool/{toolId}")
    public List<Feedback> getFeedbacksByTool(@PathVariable Long toolId) {
        return feedbackService.findByToolId(toolId);
    }
}
