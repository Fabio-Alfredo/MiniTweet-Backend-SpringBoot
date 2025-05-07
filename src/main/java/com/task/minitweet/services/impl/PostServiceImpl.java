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

    /**
     * Este método busca todos los posts.
     * Si no se encuentran posts, se lanza una excepción HttpError con el código 404.
     *
     * @return Una lista de posts.
     * @throws HttpError Si no se encuentran posts.
     */
    @Override
    public List<Post> findAllPosts() {
        try {
            List<Post> posts = postRepository.findAll();
            if(posts.isEmpty()){
                throw new HttpError(HttpStatus.NOT_FOUND, "No posts found");
            }
            return posts.stream()
                    .map(post -> modelMapper.map(post, Post.class))
                    .toList();
        }catch (HttpError e){
            throw e;
        }
    }

    /**
     * Este método busca un post por su ID.
     * Si no se encuentra el post, se lanza una excepción HttpError con el código 404.
     *
     * @param id El ID del post a buscar.
     * @return El post encontrado.
     * @throws HttpError Si no se encuentra el post.
     */
    @Override
    public FindPostDto findPostById(UUID id) {
        try {
         Post post = postRepository.findById(id).orElse(null);
         if(post == null){
             throw new HttpError(HttpStatus.NOT_FOUND, "Post not found");
         }

         return modelMapper.map(post, FindPostDto.class);
        }catch (HttpError e){
            throw e;
        }
    }

    /**
     * Este método elimina un post por su ID y el usuario que lo creó.
     * Si el post no se encuentra, se lanza una excepción HttpError con el código 404.
     *
     * @param id El ID del post a eliminar.
     * @param user El usuario que creó el post.
     * @throws HttpError Si no se encuentra el post o si no es el propietario del post.
     */
    @Override
    public void deletePostByIdAndUser(UUID id, User user) {
        try{
            Post post = postRepository.findByIdAndAuthor(id, user);
            if(post == null){
                throw new HttpError(HttpStatus.NOT_FOUND, "Post not found");
            }
            postRepository.delete(post);
        }catch (HttpError e) {
            throw e;
        }
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
    public List<FindPostDto> findAllPostsByUser(User user, Boolean isOwner) {
        //Verificar el propietario del post proximamente
        try {
            List<Post> posts = postRepository.findAllByAuthor(user);
            if(posts.isEmpty()){
                throw new HttpError(HttpStatus.NOT_FOUND, "No posts found for this user");
            }

            return posts.stream()
                    .map(post -> modelMapper.map(post, FindPostDto.class))
                    .toList();
        }catch (HttpError e){
            throw e;
        }
    }
}
