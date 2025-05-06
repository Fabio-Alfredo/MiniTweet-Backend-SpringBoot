package com.task.minitweet.services.impl;

import com.task.minitweet.domains.dtos.post.CreatePostDto;
import com.task.minitweet.domains.models.Post;
import com.task.minitweet.domains.models.User;
import com.task.minitweet.exceptions.HttpError;
import com.task.minitweet.repositories.PostRepository;
import com.task.minitweet.services.contract.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Post createPost(CreatePostDto postDto) {
        try {
            Post post = modelMapper.map(postDto, Post.class);
            return  postRepository.save(post);
        }catch (HttpError e){
            throw e;
        }
    }

    @Override
    public List<Post> findAllPosts() {
        return List.of();
    }

    @Override
    public Post findPostById(UUID id) {
        return null;
    }

    @Override
    public void deletePostByIdAndUser(UUID id, User user) {

    }
}
