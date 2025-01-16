package com.fullstack.projet.models.tool;

import com.fullstack.projet.models.feedback.FeedbackDto;
import com.fullstack.projet.models.user.UserDto;

import java.util.List;
import java.util.stream.Collectors;

public record ToolDto(
        Long id,
        String name,
        String domain,
        String link,
        String description,
        List<FeedbackDto> feedbacks,
        UserDto creator
) {

    public static ToolDto fromTool(Tool tool){
        return new ToolDto(
                tool.getId(),
                tool.getName(),
                tool.getDomain(),
                tool.getLink(),
                tool.getDescription(),
                tool.getFeedbacks() != null
                        ? tool.getFeedbacks().stream()
                        .map(FeedbackDto::fromFeedback)
                        .collect(Collectors.toList())
                        : null,
                UserDto.fromUser(tool.getCreator())
        );
    }
}
