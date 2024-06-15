package org.toastmasters.meetingplanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.toastmasters.meetingplanner.dto.Role;
import org.toastmasters.meetingplanner.dto.User;
import org.toastmasters.meetingplanner.repository.RoleRepository;
import org.toastmasters.meetingplanner.repository.UserRepository;

@Service
public class MeetingService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public MeetingService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public void reserveRole(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        long userId = userRepository.findByUsername(principal.getUsername()).map(User::getId).orElseThrow();
        Role role = roleRepository.findById(id).orElseThrow();
        role.setUserId(userId);
        roleRepository.save(role);
    }

}
