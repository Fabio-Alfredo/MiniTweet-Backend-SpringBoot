package com.task.minitweet.repositories;

import com.task.minitweet.domains.models.Post;
import com.task.minitweet.domains.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post>findAllByAuthor(User author);
    Post findByIdAndAuthor(UUID id, User author);

    //Traer los posts de los usuarios que sigo
    List<Post>findByAuthorInOrderByCreatedAtDesc(List<User> authors, Pageable pageable);

    //Traer los posts de los usuarios que sigo y que fueron creados antes de una fecha
    List<Post>findByAuthorInAndCreatedAtBeforeOrderByCreatedAtDesc(Collection<User> author, Date createdAt, Pageable pageable);
}
