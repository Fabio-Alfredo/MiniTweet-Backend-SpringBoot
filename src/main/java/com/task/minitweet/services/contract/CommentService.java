package com.task.minitweet.services.contract;

import com.task.minitweet.domains.dtos.comment.CreateCommentDto;
import com.task.minitweet.domains.dtos.comment.UpdateCommentDto;
import com.task.minitweet.domains.models.Comment;
import com.task.minitweet.domains.models.User;

import java.util.List;
import java.util.UUID;

public interface CommentService {

    void createComment(CreateCommentDto commentDto, User author);
    List<Comment>findAllByPostId(UUID postId);
    void updateComment(UpdateCommentDto commentDto, User author);


}
