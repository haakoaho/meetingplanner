package org.toastmasters.meetingplanner.controller;

import jakarta.annotation.security.RolesAllowed;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.toastmasters.meetingplanner.dto.user.*;
import org.toastmasters.meetingplanner.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RolesAllowed("ROLE_ANONYMOUS")
    @PostMapping("register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUser user) {
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @RolesAllowed("USER")
    @GetMapping("userInfo")
    public ResponseEntity<UserResponse> getUser() {
        Optional<User> user = userService.getUserBySecurityConfig();
        return user.map(UserResponse::fromUser).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RolesAllowed("USER")
    @GetMapping("allUsers")
    public List<UserResponse> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return users.stream().map(UserResponse::fromUser).toList();
    }

    @RolesAllowed("USER")
    @DeleteMapping
    public void deleteUser() {
        userService.deleteUserBySecurityConfig();
    }

    @RolesAllowed("USER")
    @PutMapping
    public void updateUser(@RequestBody UpdateUser updateUser) {
        userService.updateUserBySecurityConfig(updateUser);
    }

    @RolesAllowed("USER")
    @PutMapping("changePassword")
    public void updatePassword(@RequestBody UpdatePassword updatePassword) throws AuthenticationException {
        userService.changePasswordBySecurityConfig(updatePassword);
    }
}
