package org.toastmasters.meetingplanner.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.toastmasters.meetingplanner.dto.RecordSpeech;
import org.toastmasters.meetingplanner.dto.agenda.AgendaRole;
import org.toastmasters.meetingplanner.dto.agenda.AgendaSpeech;
import org.toastmasters.meetingplanner.dto.user.RegisterUser;
import org.toastmasters.meetingplanner.dto.user.UpdateUser;
import org.toastmasters.meetingplanner.dto.user.User;
import org.toastmasters.meetingplanner.repository.UserRepository;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(12, new SecureRandom());
    }

    public void registerUser(RegisterUser registerUser) {
        if (userRepository.findByEmail(registerUser.email()).isPresent()) {
            throw new RuntimeException("user already exist");
        }
        String salt = generateSalt();
        String hashedPassword = hashPassword(registerUser.password(), salt);

        User user = new User(registerUser.name(),
                registerUser.email(), hashedPassword, salt, registerUser.phoneNumber(), registerUser.photoConsent());

        userRepository.save(user);
    }

    public Optional<User> getUserBySecurityConfig() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return getUserByEmail(principal.getUsername());
    }

    public Optional<User> getUserByEmail(String username) {
        return userRepository.findByEmail(username);
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

    public void recordSpeech(AgendaSpeech speech) {
        User speaker = userRepository.findById(speech.getSpeakerId()).orElseThrow();
        var speeches = speaker.getSpeechHistory().stream().map(LinkedHashMap.class::cast).map(RecordSpeech::fromDatabaseJson).toList();
        var a = new ArrayList<>(speeches);
        a.add(RecordSpeech.fromAgendaSpeech(speech));
        speaker.setSpeechHistory(a.stream().map(Object.class::cast).toList());
        userRepository.save(speaker);

        if (speech.getEvaluatorId() == null) return;

        User evaluator = userRepository.findById(speech.getSpeakerId()).orElseThrow();
        var roles = evaluator.getRoleHistory();
        int times = roles.getOrDefault("evaluator", 0) + 1;
        roles.put("evaluator", times);
        userRepository.save(evaluator);
    }

    public void recordRole(AgendaRole role) {
        if (role.getUserId() == null) return;
        User user = userRepository.findById(role.getUserId()).orElseThrow();
        var roles = user.getRoleHistory();
        int times = roles.getOrDefault(role.getRoleName(), 0) + 1;
        roles.put(role.getRoleName(), times);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll().stream().filter(user -> user.getRoles().contains("USER")).toList();
    }

    public void deleteUserBySecurityConfig() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        var user = getUserByEmail(principal.getUsername()).orElseThrow();
        userRepository.delete(user);
    }

    public void updateUserBySecurityConfig(UpdateUser updateUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        var user = getUserByEmail(principal.getUsername()).orElseThrow();
        user.setEmail(updateUser.email());
        user.setName(updateUser.name());
        user.setPhoneNumber(updateUser.phoneNumber());
        user.setRoleHistory(updateUser.roleHistory());
        user.setSpeechHistory(updateUser.speechHistory().stream().map(Object.class::cast).toList());
        user.setPhotoConsent(updateUser.photoConsent());
        userRepository.save(user);
    }
}