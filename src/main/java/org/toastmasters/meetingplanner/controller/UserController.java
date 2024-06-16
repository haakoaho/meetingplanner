package org.toastmasters.meetingplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.toastmasters.meetingplanner.dto.user.RegisterUser;
import org.toastmasters.meetingplanner.dto.user.User;
import org.toastmasters.meetingplanner.dto.user.UserResponse;
import org.toastmasters.meetingplanner.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUser user) {
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/userInfo")
    public ResponseEntity<UserResponse> getUser() {
        Optional<User> user = userService.getUserBySecurityConfig();
        return user.map(UserResponse::fromUser).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
