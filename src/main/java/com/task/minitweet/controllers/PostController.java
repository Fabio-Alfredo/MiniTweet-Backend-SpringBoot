package com.task.minitweet.controllers;

import com.task.minitweet.domains.dtos.GeneralResponse;
import com.task.minitweet.domains.dtos.post.CreatePostDto;
import com.task.minitweet.domains.dtos.post.FindPostDto;
import com.task.minitweet.domains.models.Post;
import com.task.minitweet.domains.models.User;
import com.task.minitweet.exceptions.HttpError;
import com.task.minitweet.services.contract.PostService;
import com.task.minitweet.services.contract.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<GeneralResponse>createNewPost(@ModelAttribute @Valid CreatePostDto postDto){
        try{
            User user = userService.findUserAuthenticated();

            FindPostDto post = postService.createPost(postDto, user);
            return GeneralResponse.getResponse(HttpStatus.CREATED, "Post created successfully", post);
        }catch (HttpError e){
            return GeneralResponse.getResponse(e.getHttpStatus(), e.getMessage());
        }
    }

    @GetMapping("/{userId}/all")
    public ResponseEntity<GeneralResponse>findAllPostsByUser(@PathVariable String userId){
        try{
            User user = userService.findUserAuthenticated();
            Boolean isOwner = user!= null && user.getId().toString().equals(userId);

            List<FindPostDto>posts = postService.findAllPostsByUser(user, isOwner);

            return GeneralResponse.getResponse(HttpStatus.OK, "success", posts);
        }catch (HttpError e){
            return GeneralResponse.getResponse(e.getHttpStatus(), e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<GeneralResponse>findAllPosts(){
        try{
            List<Post>posts = postService.findAllPosts();
            return GeneralResponse.getResponse(HttpStatus.OK, "success", posts);
        }catch (HttpError e){
            return GeneralResponse.getResponse(e.getHttpStatus(), e.getMessage());
        }
    }

    @GetMapping("/by-id/{postId}")
    public ResponseEntity<GeneralResponse>findPostById(@PathVariable UUID postId){
        try{
            FindPostDto post = postService.findPostById(postId);
            return GeneralResponse.getResponse(HttpStatus.OK, "success", post);
        }catch (HttpError e){
            return GeneralResponse.getResponse(e.getHttpStatus(), e.getMessage());
        }
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<GeneralResponse>deletePostById(@PathVariable UUID postId){
        try{
            User user = userService.findUserAuthenticated();

            postService.deletePostByIdAndUser(postId, user);
            return GeneralResponse.getResponse(HttpStatus.OK, "Post deleted successfully");
        }catch (HttpError e){
            return GeneralResponse.getResponse(e.getHttpStatus(), e.getMessage());
        }
    }

    @PostMapping("/like/{postId}")
    public ResponseEntity<GeneralResponse>likePost(@PathVariable UUID postId){
        try{
            User user = userService.findUserAuthenticated();

            postService.updateLikesInPost(postId, user);
            return GeneralResponse.getResponse(HttpStatus.OK, "Post liked successfully");
        }catch (HttpError e){
            return GeneralResponse.getResponse(e.getHttpStatus(), e.getMessage());
        }
    }


}
