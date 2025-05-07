package com.task.minitweet.services.impl;

import com.task.minitweet.domains.dtos.post.CreatePostDto;
import com.task.minitweet.domains.dtos.post.FindPostDto;
import com.task.minitweet.domains.models.Post;
import com.task.minitweet.domains.models.User;
import com.task.minitweet.exceptions.HttpError;
import com.task.minitweet.repositories.PostRepository;
import com.task.minitweet.services.contract.CloudinaryService;
import com.task.minitweet.services.contract.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper, CloudinaryService cloudinaryService) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
    }

    /**
     * Este método crea un nuevo post.
     * Si el post tiene una imagen, se sube a Cloudinary y se guarda la URL en el post.
     *
     * @param postDto El DTO del post a crear.
     * @return El post creado.
     * @throws HttpError Si ocurre un error al subir la imagen a Cloudinary.
     */
    @Override
    public FindPostDto createPost(CreatePostDto postDto, User user) {
        try {
            String imageUrl = null;

            if(postDto.getFile() != null) {
                imageUrl = cloudinaryService.uploadImage(postDto.getFile(), "posts");
            }

            Post post = modelMapper.map(postDto, Post.class);
            post.setImage(imageUrl);
            post.setAuthor(user);

            return  modelMapper.map(postRepository.save(post), FindPostDto.class);
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

    /**
     * Este método busca todos los posts de un usuario.
     * Si el usuario es el propietario de los posts, se le permite ver todos los posts.
     * Si el usuario no es el propietario, solo se le permiten ver los posts públicos.
     *
     * @param user El usuario que está buscando los posts.
     * @param isOwner Indica si el usuario es el propietario de los posts.
     * @return Una lista de posts del usuario.
     * @throws HttpError Si no se encuentran posts para el usuario.
     */
    @Override
    public List<Post> findAllPostsByUser(User user, Boolean isOwner) {
        //Verificar el propietario del post proximamente
        try {
            List<Post> posts = postRepository.findAllByAuthor(user);
            if(posts.isEmpty()){
                throw new HttpError(HttpStatus.NOT_FOUND, "No posts found for this user");
            }
            return posts;
        }catch (HttpError e){
            throw e;
        }
    }
}
