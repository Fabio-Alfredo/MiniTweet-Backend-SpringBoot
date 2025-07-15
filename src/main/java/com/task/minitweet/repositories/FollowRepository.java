package com.task.minitweet.repositories;

import com.task.minitweet.domains.models.Follow;
import com.task.minitweet.domains.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FollowRepository extends JpaRepository<Follow, UUID> {

    //Devuelve los usuarios que sigue un usuario
    @Query("SELECT f.follower FROM Follow f WHERE f.followed =:user")
    List<User>findFollowersOf(@Param("user") User user);

    //Devuelve los usuarios que siguen a un usuario
    @Query("SELECT f.followed FROM Follow f WHERE f.follower =:user")
    List<User>findFollowingOf(@Param("user") User user);

    //Elimina el seguimiento de un usuario a otro
    void deleteByFollowerAndFollowed(User follower, User followed);

    //Devuelve true si el usuario ya sigue a otro
    Boolean existsByFollowerAndFollowed(User follower, User followed);
}
