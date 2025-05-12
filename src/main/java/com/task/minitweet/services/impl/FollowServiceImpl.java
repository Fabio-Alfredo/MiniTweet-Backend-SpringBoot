package com.task.minitweet.services.impl;

import com.task.minitweet.domains.models.Follow;
import com.task.minitweet.domains.models.User;
import com.task.minitweet.exceptions.HttpError;
import com.task.minitweet.repositories.FollowRepository;
import com.task.minitweet.services.contract.FollowService;
import com.task.minitweet.services.contract.UserService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FollowServiceImpl implements FollowService {

    private final UserService userService;
    private final FollowRepository followRepository;


    public FollowServiceImpl(UserService userService, FollowRepository followRepository) {
        this.userService = userService;
        this.followRepository = followRepository;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void followUser(User follower, UUID followed) {
        try {
            User followedEntity = userService.findById(followed);

            if(followRepository.existsByFollowerAndFollowed(follower, followedEntity)){
                throw new HttpError(HttpStatus.CONFLICT, "You already follow this user");
            }

            Follow follow = new Follow();
            follow.setFollower(follower);
            follow.setFollowed(followedEntity);

            followRepository.save(follow);
        }catch (HttpError e){
            throw e;
        }
    }

    @Override
    public List<User> findFollowersOf(UUID userId, User authenticatedUser) {
        try {

            User user = userService.findById(userId);
            if(!user.getId().equals(authenticatedUser.getId()) && !followRepository.existsByFollowerAndFollowed(authenticatedUser, user)){
                throw new HttpError(HttpStatus.FORBIDDEN, "You are not allowed to see this user's followers");
            }

            List<User> followers = followRepository.findFollowersOf(user);
            return followers;
        } catch (HttpError e) {
            throw e;
        }
    }

    @Override
    public List<User> findFollowingOf(UUID userId, User authenticatedUser) {
        try {
            User user = userService.findById(userId);

            if(!user.getId().equals(authenticatedUser.getId()) && !followRepository.existsByFollowerAndFollowed(authenticatedUser, user)){
                throw new HttpError(HttpStatus.FORBIDDEN, "You are not allowed to see this user's following");
            }

            List<User> following = followRepository.findFollowingOf(user);
            return following;
        } catch (HttpError e) {
            throw e;
        }
    }




}
