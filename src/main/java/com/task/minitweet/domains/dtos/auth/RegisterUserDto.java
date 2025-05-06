package com.task.minitweet.domains.dtos.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegisterUserDto {

    @NotEmpty(message = "Username is required")
    private String username;
    @NotEmpty(message = "Password is required")
    private String email;
    @NotEmpty(message = "Password is required")
    private String password;
    private String biography;
    private String profilePicture;
}
