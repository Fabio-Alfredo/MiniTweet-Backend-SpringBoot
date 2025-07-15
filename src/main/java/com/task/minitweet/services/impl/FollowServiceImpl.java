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

    /**
     * Este método permite a un usuario seguir a otro usuario.
     *
     * @param follower El usuario que sigue.
     * @param followed El usuario que es seguido.
     * @throws HttpError Si el usuario ya sigue al otro usuario.
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void followUser(User follower, UUID followed) {
        try {
            User followedEntity = userService.findById(followed);

            if(followRepository.existsByFollowerAndFollowed(follower, followedEntity)){
                //quiero eliminar el seguimiento
                followRepository.deleteByFollowerAndFollowed(follower, followedEntity);

            }

            Follow follow = new Follow();
            follow.setFollower(follower);
            follow.setFollowed(followedEntity);

            followRepository.save(follow);
        }catch (HttpError e){
            throw e;
        }
    }

    /**
     * Este método permite traer la lista de seguidores de un usuario.
     *
     * @param userId El id del usuario.
     * @param authenticatedUser El usuario autenticado.
     * @return La lista de seguidores del usuario.
     * @throws HttpError Si el usuario no es el propietario de la cuenta o no sigue al usuario.
     */
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

    /**
     * Este método permite traer la lista de usuarios que sigue un usuario.
     *
     * @param userId El id del usuario.
     * @param authenticatedUser El usuario autenticado.
     * @return La lista de usuarios que sigue el usuario.
     * @throws HttpError Si el usuario no es el propietario de la cuenta o no sigue al usuario.
     */
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
