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
    FindPostDto findPostById(UUID id);
    void updateLikesInPost(UUID id, User user);
    void deletePostByIdAndUser(UUID id, User user);
    List<FindPostDto>findAllPostsByUser(User user, Boolean isOwner);
}
