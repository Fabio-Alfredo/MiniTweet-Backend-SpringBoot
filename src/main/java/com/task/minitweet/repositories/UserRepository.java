package com.task.minitweet.repositories;

import com.task.minitweet.domains.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsernameOrEmail(String username, String email);
    User findByEmail(String email);
}
