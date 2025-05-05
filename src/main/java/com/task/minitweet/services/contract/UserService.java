package com.task.minitweet.services.contract;

import com.task.minitweet.domains.models.Token;
import com.task.minitweet.domains.models.User;

public interface UserService {
    User findByIdentifier(String identifier);
    User findByEmail(String email);

    Token registerToken(User user);
    Boolean isTokenValid(User user, String token) ;
    void cleanToken(User user);
    User findUserAuthenticated();
}
