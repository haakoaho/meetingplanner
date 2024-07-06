package org.toastmasters.meetingplanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.toastmasters.meetingplanner.dto.Speech;
import org.toastmasters.meetingplanner.dto.SpeechRequest;
import org.toastmasters.meetingplanner.dto.agenda.*;
import org.toastmasters.meetingplanner.dto.user.User;
import org.toastmasters.meetingplanner.dto.user.UserResponse;
import org.toastmasters.meetingplanner.repository.*;

import java.util.List;

@Service
public class MeetingService {
    private final RoleRepository roleRepository;

    private final MeetingRepository meetingRepository;
    private final SpeechRepository speechRepository;

    private final MeetingRoleRepository meetingRoleRepository;

    private final UserService userService;

    @Autowired
    public MeetingService(RoleRepository roleRepository, MeetingRepository meetingRepository, SpeechRepository speechRepository, MeetingRoleRepository meetingRoleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.meetingRepository = meetingRepository;
        this.speechRepository = speechRepository;
        this.meetingRoleRepository = meetingRoleRepository;
        this.userService = userService;
    }

    public void reserveRole(Long id, int meetingOrder) {
        User user = userService.getUserBySecurityConfig().orElseThrow();
        Meeting meeting = meetingRepository.findNthRecentMeeting(meetingOrder);

        MeetingRole meetingRole = meetingRoleRepository.findByMeetingIdAndRoleId(meeting.getId(), id).orElseThrow();
        meetingRole.setUserId(user.getId());
        meetingRoleRepository.save(meetingRole);
    }

    public Agenda getAgenda(int meetingOrder) {
        Meeting meeting = meetingRepository.findNthRecentMeeting(meetingOrder);
        List<AgendaRole> roles = meetingRepository.findAgendaRolesByMeetingId(meeting.getId()).stream().map(AgendaRole::new).toList();
        List<AgendaSpeech> speeches = speechRepository.findSpeechInAgendaByMeetingId(meeting.getId()).stream().map(AgendaSpeech::new).toList();
        return new Agenda(roles, speeches, meeting.getStartDateTime(), meeting.getWordOfTheDay(), meeting.getTheme(), meeting.getSpeakers());
    }

    public void reserveSpeech(SpeechRequest request) {
        Meeting meeting = meetingRepository.findNthRecentMeeting(request.meetingOrder());
        User user = userService.getUserBySecurityConfig().orElseThrow();
        Speech speech = new Speech();
        speech.setSpeakerId(user.getId());
        speech.setTitle(request.title());
        speech.setPathway(request.pathway());
        speech.setMeetingId(meeting.getId());
        speechRepository.save(speech);
    }

    public void evaluateSpeech(Long speechId) {
        User user = userService.getUserBySecurityConfig().orElseThrow();
        Speech speech = speechRepository.findById(speechId).orElseThrow();
        speech.setEvaluatorId(user.getId());
        speechRepository.save(speech);
    }
}
