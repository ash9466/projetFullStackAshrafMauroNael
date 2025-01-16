package com.fullstack.projet.controllers.feedback;

import com.fullstack.projet.controllers.ErrorHandlingUtils;
import com.fullstack.projet.exceptions.ValidationException;
import com.fullstack.projet.models.feedback.Feedback;
import com.fullstack.projet.models.feedback.FeedbackDto;
import com.fullstack.projet.services.feedback.IFeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    private final Logger LOG = LoggerFactory.getLogger(FeedbackController.class);

    private final IFeedbackService feedbackService;

    public FeedbackController(IFeedbackService feedbackService){
        this.feedbackService = feedbackService;
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @PostMapping("/create")
    public ResponseEntity<Object> createFeedback(@RequestBody Feedback feedback) {
        LOG.info("Creating feedback");
        try{
            FeedbackDto feedbackDto = FeedbackDto.fromFeedback(feedbackService.save(feedback));
            LOG.info("Successfully created feedback");
            return ResponseEntity.ok(feedbackDto);
        } catch (ValidationException e) {
            return ErrorHandlingUtils.handleBadRequest(LOG,e);
        } catch (Exception e) {
            return ErrorHandlingUtils.handleInternalServerError(LOG, "L'ajout du feedback a echoué", e);
        }
    }

    @PreAuthorize("hasAuthority('TEACHER') || hasAuthority('ADMIN')")
    @GetMapping("/tool/{toolId}")
    public ResponseEntity<Object> getFeedbacksByToolId(@PathVariable Long toolId) {
        LOG.info("Getting feedbacks for tool {}", toolId);
        try{
            List<FeedbackDto> feedbackDtos = feedbackService.getFeedbacksByToolId(toolId).stream().map(FeedbackDto::fromFeedback).toList();
            LOG.info("Successfully retrieved feedbacks");
            return ResponseEntity.ok(feedbackDtos);
        } catch (ValidationException e) {
            return ErrorHandlingUtils.handleBadRequest(LOG,e);
        } catch (Exception e) {
            return ErrorHandlingUtils.handleInternalServerError(LOG, "L'ajout du feedback a echoué", e);
        }
    }

}
