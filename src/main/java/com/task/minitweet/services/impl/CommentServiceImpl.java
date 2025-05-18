package com.task.minitweet.services.impl;

import com.task.minitweet.domains.dtos.comment.CreateCommentDto;
import com.task.minitweet.domains.dtos.comment.UpdateCommentDto;
import com.task.minitweet.domains.models.Comment;
import com.task.minitweet.domains.models.Post;
import com.task.minitweet.domains.models.User;
import com.task.minitweet.exceptions.HttpError;
import com.task.minitweet.repositories.CommentRepository;
import com.task.minitweet.repositories.PostRepository;
import com.task.minitweet.services.contract.CommentService;
import com.task.minitweet.services.contract.PostService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    private final PostService postService;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(PostService postService, CommentRepository commentRepository, ModelMapper modelMapper) {
        this.postService = postService;
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Este método crea un nuevo comentario.
     *
     * @param commentDto El DTO del comentario a crear.
     * @return El comentario creado.
     * @throws HttpError
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void createComment(CreateCommentDto commentDto, User author) {
        try{
            Post post = postService.findPostById(commentDto.getPostId());

            Comment comment = new Comment();
            comment.setContent(commentDto.getContent());
            comment.setPost(post);
            comment.setAuthor(author);

            commentRepository.save(comment);
        }catch (HttpError e){
            throw  e;
        }
    }

    /**
     * Este método busca todos los comentarios de un post.
     *
     * @param postId El ID del post.
     * @return La lista de comentarios del post.
     * @throws HttpError
     */
    @Override
    public List<Comment> findAllByPostId(UUID postId) {
        try {
            Post post = postService.findPostById(postId);
            List<Comment> comments = commentRepository.findAllByPost(post);

            return comments;
        }catch (HttpError e){
            throw e;
        }
    }

    /**
     * Este metodo permite editar un comentario.
     * Si el comentario no existe, se lanza una excepción HttpError con el código 404.
     *
     * @param commentDto El DTO del comentario a editar.
     * @param author El autor del comentario.
     */
    @Override
    public void updateComment(UpdateCommentDto commentDto, User author) {
        try{
            Comment comment = commentRepository.findByIdAndAuthor(commentDto.getId(), author);
            if(comment == null){
                throw new HttpError(HttpStatus.NOT_FOUND, "Comment not found");
            }
            comment.setContent(commentDto.getContent());
            commentRepository.save(comment);

        }catch (HttpError e){
            throw e;
        }
    }
}
