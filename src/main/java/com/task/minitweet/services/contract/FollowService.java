package com.task.minitweet.services.contract;

import com.task.minitweet.domains.models.User;

import java.util.List;
import java.util.UUID;

public interface FollowService {
    //funcion para seguir a un usuario
    void followUser(User follower, UUID followed);

    //funcion para encontrar los seguidores de un usuario
    List<User>findFollowersOf(UUID userId, User authenticatedUser);
}
