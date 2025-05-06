package com.task.minitweet.domains.dtos.role;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateRoleDto {
    @NotBlank(message = "Role ID cannot be blank")
    private String id;
    @NotBlank(message = "Role name cannot be blank")
    private String name;
}
