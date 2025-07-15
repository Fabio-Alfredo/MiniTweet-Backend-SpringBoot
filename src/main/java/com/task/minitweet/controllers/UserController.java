package com.task.minitweet.controllers;

import com.task.minitweet.domains.dtos.GeneralResponse;
import com.task.minitweet.domains.dtos.user.UpdateRolesDto;
import com.task.minitweet.domains.models.User;
import com.task.minitweet.exceptions.HttpError;
import com.task.minitweet.services.contract.UserService;
import jakarta.validation.Valid;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<GeneralResponse> getUserInfo() {
        try {
            User user = userService.findUserAuthenticated();

            return GeneralResponse.getResponse(HttpStatus.OK, "success", user);
        } catch (HttpError e) {
            return GeneralResponse.getResponse(e.getHttpStatus(), e.getMessage());
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<GeneralResponse> getAllUsers() {
        try {
            List<User>users = userService.findAll();
            return GeneralResponse.getResponse(HttpStatus.OK, "success", users);
        } catch (HttpError e) {
            return GeneralResponse.getResponse(e.getHttpStatus(), e.getMessage());
        }
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<GeneralResponse> getUserById(@PathVariable UUID id) {
        try {
            User user = userService.findById(id);
            return GeneralResponse.getResponse(HttpStatus.OK, "success", user);
        } catch (HttpError e) {
            return GeneralResponse.getResponse(e.getHttpStatus(), e.getMessage());
        }
    }

    @PutMapping("/update/{userId}")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<GeneralResponse> updateUserRole(@PathVariable UUID userId, @RequestBody @Valid UpdateRolesDto updateRolesDto) {
        try {
            userService.updateRoles(userId, updateRolesDto);
            return GeneralResponse.getResponse(HttpStatus.OK, "success");
        } catch (HttpError e) {
            return GeneralResponse.getResponse(e.getHttpStatus(), e.getMessage());
        }
    }
}
