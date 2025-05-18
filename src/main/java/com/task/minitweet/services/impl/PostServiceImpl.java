package com.task.minitweet.services.impl;

import com.task.minitweet.domains.dtos.post.CreatePostDto;
import com.task.minitweet.domains.dtos.post.FindPostDto;
import com.task.minitweet.domains.models.Post;
import com.task.minitweet.domains.models.User;
import com.task.minitweet.exceptions.HttpError;
import com.task.minitweet.repositories.PostRepository;
import com.task.minitweet.services.contract.CloudinaryService;
import com.task.minitweet.services.contract.FollowService;
import com.task.minitweet.services.contract.PostService;
import com.task.minitweet.services.contract.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final FollowService followService;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper, CloudinaryService cloudinaryService, FollowService followService) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.followService = followService;
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
    @Transactional(rollbackOn = Exception.class)
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
    public Post findPostById(UUID id) {
        try {
         Post post = postRepository.findById(id).orElse(null);
         if(post == null){
             throw new HttpError(HttpStatus.NOT_FOUND, "Post not found");
         }

         return post;
        }catch (HttpError e){
            throw e;
        }
    }

    /**
     * Este método actualiza los likes de un post.
     * Si el usuario ya ha dado like al post, se elimina el like.
     * Si el usuario no ha dado like al post, se agrega el like.
     *
     * @param id El ID del post a actualizar.
     * @param user El usuario que da like al post.
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void updateLikesInPost(UUID id, User user) {
        try{
            Post post = postRepository.findById(id).orElse(null);
            if(post == null){
                throw new HttpError(HttpStatus.NOT_FOUND, "Post not found");
            }
            if(post.getLikedBy().contains(user)){
                post.getLikedBy().remove(user);
            }else{
                post.getLikedBy().add(user);
            }
            postRepository.save(post);
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
    @Transactional(rollbackOn = Exception.class)
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

    /**
     * Este método busca todos los posts de los usuarios que sigue el usuario.
     *
     * @param user El usuario que está buscando los posts.
     * @return Una lista de posts de los usuarios que sigue el usuario.
     * @throws HttpError Si no se encuentran posts para el usuario.
     */
    @Override
    public List<FindPostDto> findAllPostsByFollowing(User user, Date createAt, int size) {
        try{
            List<User>allFollowing = followService.findFollowingOf(user.getId(), user);
            Pageable pageable = PageRequest.of(0, size);
            List<Post>posts = (createAt == null)
                    ? postRepository.findByAuthorInOrderByCreatedAtDesc(allFollowing, pageable)
                    : postRepository.findByAuthorInAndCreatedAtBeforeOrderByCreatedAtDesc(allFollowing, createAt, pageable);

            return posts.stream()
                    .map(post -> modelMapper.map(post, FindPostDto.class))
                    .toList();
        }catch (HttpError e){
            throw e;
        }
    }

    /**
     * Este método actualiza un post.
     * Si el post no se encuentra, se lanza una excepción HttpError con el código 404.
     *
     * @param id El ID del post a actualizar.
     * @param postDto El DTO del post a actualizar.
     * @param user El usuario que actualiza el post.
     * @return El post actualizado.
     * @throws HttpError Si no se encuentra el post o si no es el propietario del post.
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public FindPostDto updatePost(UUID id, CreatePostDto postDto, User user) {
        try{
            Post post = postRepository.findByIdAndAuthor(id, user);
            if(post == null){
                throw new HttpError(HttpStatus.NOT_FOUND, "Post not found");
            }
            String imageUrl = null;

            if(postDto.getFile() != null) {
                imageUrl = cloudinaryService.uploadImage(postDto.getFile(), "posts");
                post.setImage(imageUrl);
            }
            if(postDto.getContent()!= null) {
                post.setContent(postDto.getContent());
            }

            Post updatedPost = postRepository.save(post);
            return modelMapper.map(updatedPost, FindPostDto.class);
        }catch (HttpError e){
            throw e;
        }
    }
}
