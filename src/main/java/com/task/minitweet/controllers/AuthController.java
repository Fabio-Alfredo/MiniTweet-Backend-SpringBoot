package com.task.minitweet.controllers;

import com.task.minitweet.domains.dtos.GeneralResponse;
import com.task.minitweet.domains.dtos.auth.LoginUserDto;
import com.task.minitweet.domains.dtos.auth.RegisterUserDto;
import com.task.minitweet.domains.dtos.auth.TokenDto;
import com.task.minitweet.exceptions.HttpError;
import com.task.minitweet.services.contract.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<GeneralResponse>registerUser(@RequestBody @Valid RegisterUserDto userDto){
        try{
            userService.registerUser(userDto);

            return GeneralResponse.getResponse(HttpStatus.CREATED, "User registered successfully");
        }catch (HttpError e){
            return GeneralResponse.getResponse(e.getHttpStatus(), e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<GeneralResponse>loginUser(@RequestBody LoginUserDto userDto){
        try{
            var token = userService.loginUser(userDto.getIdentifier(), userDto.getPassword());
            return GeneralResponse.getResponse(HttpStatus.OK, "Login successful", new TokenDto(token));
        }catch (HttpError e){
            return GeneralResponse.getResponse(e.getHttpStatus(), e.getMessage());
        }
    }
}

