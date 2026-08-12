package com.anusaha.beatbox.controller;

import com.anusaha.beatbox.dto.UserRegistrationRequest;
import com.anusaha.beatbox.dto.UserResponse;
import com.anusaha.beatbox.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserRegistrationRequest request) {
        return userService.registerUser(request);
    }
}