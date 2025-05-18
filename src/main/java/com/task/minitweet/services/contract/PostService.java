package com.task.minitweet.services.contract;

import com.task.minitweet.domains.dtos.post.CreatePostDto;
import com.task.minitweet.domains.dtos.post.FindPostDto;
import com.task.minitweet.domains.models.Post;
import com.task.minitweet.domains.models.User;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface PostService {
    FindPostDto createPost(CreatePostDto postDto, User user);
    List<Post>findAllPosts();
    Post findPostById(UUID id);
    void updateLikesInPost(UUID id, User user);
    void deletePostByIdAndUser(UUID id, User user);
    List<FindPostDto>findAllPostsByUser(User user, Boolean isOwner);

    //Traer los posts de los usuarios que sigo
    List<FindPostDto>findAllPostsByFollowing(User user, Date createAt, int size);

    FindPostDto updatePost(UUID id, CreatePostDto postDto, User user);
}
