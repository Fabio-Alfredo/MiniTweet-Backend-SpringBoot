package com.task.minitweet.domains.dtos.post;

import com.task.minitweet.domains.dtos.user.UserInPostDto;
import com.task.minitweet.domains.models.User;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FindPostDto {

    private String id;
    private String content;
    private String image;
    private Date createdAt;
    private List<User>likedBy;
    private UserInPostDto author;

}
