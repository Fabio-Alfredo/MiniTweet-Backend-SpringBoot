package com.task.minitweet.domains.dtos.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreatePostDto {

    @NotBlank(message = "Content is required")
    private String content;
    private MultipartFile file;
}
