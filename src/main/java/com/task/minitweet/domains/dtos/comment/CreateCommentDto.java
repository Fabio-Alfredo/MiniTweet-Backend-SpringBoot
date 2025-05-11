package com.task.minitweet.domains.dtos.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
public class CreateCommentDto {

    @NotBlank(message = "Content cannot be blank")
    private String content;
//    @NotNull(message = "Post ID cannot be null")
    private UUID postId;
}
