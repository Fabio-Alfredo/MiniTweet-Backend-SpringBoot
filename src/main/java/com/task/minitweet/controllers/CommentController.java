package com.task.minitweet.controllers;

import com.task.minitweet.domains.dtos.GeneralResponse;
import com.task.minitweet.domains.dtos.comment.CreateCommentDto;
import com.task.minitweet.domains.models.Comment;
import com.task.minitweet.domains.models.User;
import com.task.minitweet.exceptions.HttpError;
import com.task.minitweet.services.contract.CommentService;
import com.task.minitweet.services.contract.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<GeneralResponse>createNewComment(@RequestBody @Valid CreateCommentDto commentDto){
        try{
            User user = userService.findUserAuthenticated();
            commentService.createComment(commentDto, user);
            return GeneralResponse.getResponse(HttpStatus.CREATED, "Comment created successfully", null);
        }catch (HttpError e){
            return GeneralResponse.getResponse(e.getHttpStatus(), e.getMessage());
        }
    }

    @GetMapping("/all/{postId}")
    public ResponseEntity<GeneralResponse>findAllComments(@PathVariable UUID postId){
        try{
            List<Comment> comments = commentService.findAllByPostId(postId);
            return GeneralResponse.getResponse(HttpStatus.OK, "success", comments);
        }catch (HttpError e){
            return GeneralResponse.getResponse(e.getHttpStatus(), e.getMessage());
        }
    }
}
