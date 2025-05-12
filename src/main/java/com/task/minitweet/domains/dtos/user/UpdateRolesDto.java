package com.task.minitweet.domains.dtos.user;

import com.task.minitweet.domains.enums.RoleActions;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateRolesDto {

    @NotBlank(message = "Role ID cannot be blank")
    private String roleId;
    @NotNull(message = "Action cannot be null")
    private RoleActions action;
}
