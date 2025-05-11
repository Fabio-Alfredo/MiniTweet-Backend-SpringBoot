package com.task.minitweet.domains.dtos.user;

import com.task.minitweet.domains.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserInPostDto {

    private UUID id;
    private String username;
    private String email;
    private String biography;
    private String profilePicture;

    public UserInPostDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.biography = user.getBiography();
        this.profilePicture = user.getProfilePicture();
    }

}
