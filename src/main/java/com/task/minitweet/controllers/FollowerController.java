package com.task.minitweet.controllers;

import com.task.minitweet.domains.dtos.GeneralResponse;
import com.task.minitweet.domains.models.User;
import com.task.minitweet.exceptions.HttpError;
import com.task.minitweet.services.contract.FollowService;
import com.task.minitweet.services.contract.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/follower")
public class FollowerController {

    private final FollowService followService;
    private final UserService userService;

    public FollowerController(FollowService followService, UserService userService) {
        this.followService = followService;
        this.userService = userService;
    }

    @PostMapping("/follow/{followedId}")
    public ResponseEntity<GeneralResponse> followUser(@PathVariable UUID followedId) {
        try {
            User user = userService.findUserAuthenticated();
            followService.followUser(user, followedId);
            return GeneralResponse.getResponse(HttpStatus.OK, "Followed user successfully");
        } catch (HttpError e) {
            return GeneralResponse.getResponse(e.getHttpStatus(), e.getMessage());
        }
    }

    //Solo los seguidores y el usuario pueden ver los seguidoresio
    @GetMapping("/followers/{userId}")
    public ResponseEntity<GeneralResponse> findFollowers(@PathVariable UUID userId) {
        try {
            User user = userService.findUserAuthenticated();
            List<User>followers = followService.findFollowersOf(userId, user);
            return GeneralResponse.getResponse(HttpStatus.OK, "success", followers);
        } catch (HttpError e) {
            return GeneralResponse.getResponse(e.getHttpStatus(), e.getMessage());
        }
    }
}
