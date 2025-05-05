package com.task.minitweet.services.impl;

import com.task.minitweet.domains.models.Token;
import com.task.minitweet.domains.models.User;
import com.task.minitweet.exceptions.HttpError;
import com.task.minitweet.repositories.TokenRepository;
import com.task.minitweet.repositories.UserRepository;
import com.task.minitweet.services.contract.UserService;
import com.task.minitweet.utils.JWTTools;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JWTTools jwtTools;
    private final TokenRepository tokenRepository;


    public UserServiceImpl(UserRepository userRepository, JWTTools jwtTools, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.jwtTools = jwtTools;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public User findByIdentifier(String identifier) {
        try{
            User user = userRepository.findByUsernameOrEmail(identifier, identifier);
            if(user == null){
                throw new HttpError(HttpStatus.NOT_FOUND, "User not found");
            }
            return user;
        }catch (HttpError e){
            throw  e;
        }
    }

    @Override
    public User findByEmail(String email) {
        try{
            User user = userRepository.findByEmail(email);
            if(user == null){
                throw new HttpError(HttpStatus.NOT_FOUND, "User not found");
            }
            return user;
        }catch (HttpError e){
            throw  e;
        }
    }

    @Override
    public Token registerToken(User user) {
        try{
            cleanToken(user);
            String token = jwtTools.generateToken(user);
            Token newToken = new Token(token, user);
            tokenRepository.save(newToken);

            return newToken;
        }catch (HttpError e){
            throw  e;
        }
    }

    @Override
    public Boolean isTokenValid(User user, String token) {
        try {
            cleanToken(user);
            List<Token> tokens = tokenRepository.findByUserAndActive(user, true);
            tokens.stream()
                    .map(t -> t.getContent().equals(token))
                    .findAny()
                    .orElseThrow(() -> new HttpError(HttpStatus.UNAUTHORIZED, "Token not valid"));
            return true;
        } catch (HttpError e) {
            throw e;
        }
    }

    @Override
    public void cleanToken(User user) {
        List<Token>token = tokenRepository.findByUserAndActive(user, true);
        token.forEach(t -> {
            if(!jwtTools.verifyToken(t.getContent())){
                t.setActive(false);
                tokenRepository.save(t);
            }
        });
    }

    @Override
    public User findUserAuthenticated() {
        try{
            String email = SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName();

            User user = findByEmail(email);
            return user;
        }catch (HttpError e){
            throw e;
        }
    }
}
