package com.task.minitweet.repositories;

import com.task.minitweet.domains.models.Post;
import com.task.minitweet.domains.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post>findAllByAuthor(User author);
}
