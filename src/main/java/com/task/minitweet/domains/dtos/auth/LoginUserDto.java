package com.task.minitweet.domains.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginUserDto {

    @NotBlank(message = "Identifier is required")
    private String identifier;
    @NotBlank(message = "Password is required")
    private String password;
}
