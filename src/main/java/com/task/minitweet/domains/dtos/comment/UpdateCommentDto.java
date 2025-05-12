package com.task.minitweet.domains.dtos.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class UpdateCommentDto {

    @NotBlank(message = "Content cannot be empty")
    private String content;
    @NotNull(message = "Comment ID cannot be null")
    private UUID id;
}
