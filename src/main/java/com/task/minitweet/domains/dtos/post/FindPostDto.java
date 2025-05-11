package com.task.minitweet.domains.dtos.post;

import com.task.minitweet.domains.dtos.user.UserInPostDto;
import com.task.minitweet.domains.models.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class FindPostDto {

    private String id;
    private String content;
    private String image;
    private Date createdAt;
    private List<UserInPostDto>likedBy;
    private UserInPostDto author;

}
