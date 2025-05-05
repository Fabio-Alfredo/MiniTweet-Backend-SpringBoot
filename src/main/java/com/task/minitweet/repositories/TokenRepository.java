package com.task.minitweet.domains.repositories;

import com.task.minitweet.domains.models.Token;
import com.task.minitweet.domains.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {
    List<Token>findByUserAndActive(User user, Boolean active);
}
