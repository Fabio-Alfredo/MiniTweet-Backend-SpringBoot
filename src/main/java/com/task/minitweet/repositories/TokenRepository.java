package com.task.minitweet.repositories;

import com.task.minitweet.domains.models.Token;
import com.task.minitweet.domains.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {
    List<Token>findByUserAndActive(User user, Boolean active);
}
