package com.task.minitweet.services.contract;

import com.task.minitweet.domains.models.User;

import java.util.List;
import java.util.UUID;

public interface FollowService {
    //funcion para seguir a un usuario
    void followUser(User follower, UUID followed);
    List<User>findFollowersOf(User user);
}
