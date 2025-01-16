package com.fullstack.projet.models.feedback;

import com.fullstack.projet.models.user.UserDto;

public record FeedbackDto(
        Long id,
        String comment,
        Long tool,
        UserDto creator
) {
    public static FeedbackDto fromFeedback(Feedback feedback) {
        return new FeedbackDto(
                feedback.getId(),
                feedback.getComment(),
                feedback.getTool().getId(),
                UserDto.fromUser(feedback.getCreator())
        );
    }

}
