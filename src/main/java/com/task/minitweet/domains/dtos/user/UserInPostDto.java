package com.task.minitweet.domains.dtos.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserInPostDto {

    private String username;
    private String email;
    private String biography;
    private String profilePicture;

}
