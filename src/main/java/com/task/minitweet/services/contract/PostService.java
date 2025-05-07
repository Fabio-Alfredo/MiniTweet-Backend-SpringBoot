package com.task.minitweet.services.contract;

import com.task.minitweet.domains.dtos.post.CreatePostDto;
import com.task.minitweet.domains.dtos.post.FindPostDto;
import com.task.minitweet.domains.models.Post;
import com.task.minitweet.domains.models.User;

import java.util.List;
import java.util.UUID;

public interface PostService {
    FindPostDto createPost(CreatePostDto postDto, User user);
    List<Post>findAllPosts();
    Post findPostById(UUID id);
    void deletePostByIdAndUser(UUID id, User user);
    List<Post>findAllPostsByUser(User user, Boolean isOwner);
}
