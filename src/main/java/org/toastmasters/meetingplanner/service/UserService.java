package org.toastmasters.meetingplanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.toastmasters.meetingplanner.dto.user.RegisterUser;
import org.toastmasters.meetingplanner.dto.user.User;
import org.toastmasters.meetingplanner.repository.UserRepository;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(12, new SecureRandom());
    }

    public void registerUser(RegisterUser registerUser) {
        if (userRepository.findByUsername(registerUser.username()).isPresent()) {
            throw new RuntimeException("user already exist");
        }
        String salt = generateSalt();
        String hashedPassword = hashPassword(registerUser.password(), salt);

        User user = new User(registerUser.username(), registerUser.name(),
                registerUser.email(), hashedPassword, salt, registerUser.phoneNumber());

        userRepository.save(user);
    }

    public Optional<User> getUserBySecurityConfig() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return getUserByUserName(principal.getUsername());
    }

    public Optional<User> getUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    public String hashPassword(String password, String salt) {
        return passwordEncoder.encode(password + salt);
    }

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return new String(saltBytes);
    }

    public boolean validatePassword(String rawPassword, User user) {
        String saltedPassword = rawPassword + user.getSalt();
        return passwordEncoder.matches(saltedPassword, user.getHashedPassword());
    }

}