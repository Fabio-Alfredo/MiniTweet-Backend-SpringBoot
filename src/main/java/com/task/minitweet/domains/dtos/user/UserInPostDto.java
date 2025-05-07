package com.task.minitweet.domains.dtos.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
public class UserInPostDto {

    private UUID id;
    private String username;
    private String email;
    private String biography;
    private String profilePicture;

}
