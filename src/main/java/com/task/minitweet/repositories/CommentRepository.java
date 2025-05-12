package com.task.minitweet.repositories;

import com.task.minitweet.domains.models.Comment;
import com.task.minitweet.domains.models.Post;
import com.task.minitweet.domains.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment>findAllByPost(Post post);
    Comment findByIdAndAuthor(UUID id, User authorId);
}
