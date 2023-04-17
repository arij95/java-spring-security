package com.springjwt.security.controller;

import com.springjwt.security.model.User;
import com.springjwt.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        return ResponseEntity.ok(userService.login(user));
    }

    @PostMapping("/register")
    public ResponseEntity<?> signup(@RequestBody User user) {
        return ResponseEntity.ok(userService.register(user));
    }

    @GetMapping("/retrieveMyProfile")
    public ResponseEntity<User> retrieveMyProfile(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(userService.retrieveMyProfile(httpServletRequest).get());
    }

}