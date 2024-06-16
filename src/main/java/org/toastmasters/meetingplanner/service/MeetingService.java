package org.toastmasters.meetingplanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.toastmasters.meetingplanner.dto.Role;
import org.toastmasters.meetingplanner.dto.Speech;
import org.toastmasters.meetingplanner.dto.agenda.Agenda;
import org.toastmasters.meetingplanner.dto.agenda.AgendaRole;
import org.toastmasters.meetingplanner.dto.agenda.Meeting;
import org.toastmasters.meetingplanner.dto.user.User;
import org.toastmasters.meetingplanner.dto.user.UserResponse;
import org.toastmasters.meetingplanner.repository.AgendaRoleRepository;
import org.toastmasters.meetingplanner.repository.MeetingRepository;
import org.toastmasters.meetingplanner.repository.RoleRepository;
import org.toastmasters.meetingplanner.repository.SpeechRepository;

import java.util.List;

@Service
public class MeetingService {
    private final RoleRepository roleRepository;

    private final AgendaRoleRepository agendaRoleRepository;

    private final MeetingRepository meetingRepository;
    private final SpeechRepository speechRepository;

    private final UserService userService;

    @Autowired
    public MeetingService(RoleRepository roleRepository, AgendaRoleRepository agendaRoleRepository, MeetingRepository meetingRepository, SpeechRepository speechRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.agendaRoleRepository = agendaRoleRepository;
        this.meetingRepository = meetingRepository;
        this.speechRepository = speechRepository;
        this.userService = userService;
    }

    public UserResponse reserveRole(Long id) {
        User user = userService.getUserBySecurityConfig().orElseThrow();
        Role role = roleRepository.findById(id).orElseThrow();
        role.setUserId(user.getId());
        roleRepository.save(role);
        return UserResponse.fromUser(user);
    }

    public Agenda getAgenda() {
        List<AgendaRole> roles = agendaRoleRepository.findAll();
        Meeting meeting = meetingRepository.findAll().get(0);
        List<Speech> speeches = speechRepository.findAll();
        return new Agenda(roles, speeches, meeting.getStartDateTime(), meeting.getWordOfTheDay(), meeting.getTheme(), meeting.getSpeakers());
    }
}
