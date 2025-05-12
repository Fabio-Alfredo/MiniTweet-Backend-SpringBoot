package com.task.minitweet.services.contract;

import com.task.minitweet.domains.dtos.auth.RegisterUserDto;
import com.task.minitweet.domains.dtos.user.UpdateRolesDto;
import com.task.minitweet.domains.models.Token;
import com.task.minitweet.domains.models.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void registerUser(RegisterUserDto userDto);
    Token loginUser(String identifier, String password);
    User findByIdentifier(String identifier);
    User findByEmail(String email);
    User findById(UUID id);
    List<User> findAll();
    void updateRoles(UUID userId, UpdateRolesDto updateRolesDto) ;

    Token registerToken(User user);
    Boolean isTokenValid(User user, String token) ;
    void cleanToken(User user);
    User findUserAuthenticated();
}
