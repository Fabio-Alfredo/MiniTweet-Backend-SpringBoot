package com.task.minitweet.services.impl;

import com.task.minitweet.domains.dtos.auth.RegisterUserDto;
import com.task.minitweet.domains.models.Token;
import com.task.minitweet.domains.models.User;
import com.task.minitweet.exceptions.HttpError;
import com.task.minitweet.repositories.TokenRepository;
import com.task.minitweet.repositories.UserRepository;
import com.task.minitweet.services.contract.RoleService;
import com.task.minitweet.services.contract.UserService;
import com.task.minitweet.utils.JWTTools;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JWTTools jwtTools;
    private final TokenRepository tokenRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;


    public UserServiceImpl(UserRepository userRepository, JWTTools jwtTools, TokenRepository tokenRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.jwtTools = jwtTools;
        this.tokenRepository = tokenRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public void registerUser(RegisterUserDto userDto) {
        try{
            User user = userRepository.findByUsernameOrEmail(userDto.getUsername(), userDto.getEmail());
            if(user != null){
                throw new HttpError(HttpStatus.CONFLICT, "User already exists, username or email is already taken");
            }
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user = modelMapper.map(userDto, User.class);
            user.setRoles(List.of(roleService.findRoleById("USER")));

            userRepository.save(user);
        }catch (HttpError e){
            throw e;
        }
    }

    @Override
    public Token loginUser(String identifier, String password) {
        try{
            User user = userRepository.findByUsernameOrEmail(identifier, identifier);
            if(user == null || !passwordEncoder.matches(password, user.getPassword())){
                throw new HttpError(HttpStatus.UNAUTHORIZED, "Invalid credentials");
            }
            Token token = registerToken(user);
            return token;
        }catch (HttpError e){
            throw e;
        }
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
