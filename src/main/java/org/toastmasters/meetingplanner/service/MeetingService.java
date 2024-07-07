package org.toastmasters.meetingplanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.toastmasters.meetingplanner.dto.Role;
import org.toastmasters.meetingplanner.dto.Speech;
import org.toastmasters.meetingplanner.dto.SpeechRequest;
import org.toastmasters.meetingplanner.dto.agenda.*;
import org.toastmasters.meetingplanner.dto.user.User;
import org.toastmasters.meetingplanner.repository.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class MeetingService {
    private final RoleRepository roleRepository;

    private final MeetingRepository meetingRepository;
    private final SpeechRepository speechRepository;

    private final MeetingRoleRepository meetingRoleRepository;

    private final MeetingConfigRepository meetingConfigRepository;

    private final UserService userService;

    @Autowired
    public MeetingService(RoleRepository roleRepository, MeetingRepository meetingRepository, SpeechRepository speechRepository, MeetingRoleRepository meetingRoleRepository, MeetingConfigRepository meetingConfigRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.meetingRepository = meetingRepository;
        this.speechRepository = speechRepository;
        this.meetingRoleRepository = meetingRoleRepository;
        this.meetingConfigRepository = meetingConfigRepository;
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

    public Agenda getAgendaByMeetingId(Long id) {
        Meeting meeting = meetingRepository.findById(id).orElseThrow();
        List<AgendaRole> roles = meetingRepository.findAgendaRolesByMeetingId(meeting.getId()).stream().map(AgendaRole::new).toList();
        List<AgendaSpeech> speeches = speechRepository.findSpeechInAgendaByMeetingId(meeting.getId()).stream().map(AgendaSpeech::new).toList();
        return new Agenda(roles, speeches, meeting.getStartDateTime(), meeting.getWordOfTheDay(), meeting.getTheme(), meeting.getSpeakers());
    }

    public Agenda getAgendaByMeeting(Meeting meeting) {
        List<AgendaRole> roles = meetingRepository.findAgendaRolesByMeetingId(meeting.getId()).stream().map(AgendaRole::new).toList();
        List<AgendaSpeech> speeches = speechRepository.findSpeechInAgendaByMeetingId(meeting.getId()).stream().map(AgendaSpeech::new).toList();
        return new Agenda(roles, speeches, meeting.getStartDateTime(), meeting.getWordOfTheDay(), meeting.getTheme(), meeting.getSpeakers());
    }

    public Meeting reserveSpeech(SpeechRequest request) {
        Meeting meeting = meetingRepository.findNthRecentMeeting(request.meetingOrder());
        User user = userService.getUserBySecurityConfig().orElseThrow();
        Speech speech = new Speech();
        speech.setSpeakerId(user.getId());
        speech.setTitle(request.title());
        speech.setPathway(request.pathway());
        speech.setMeetingId(meeting.getId());
        speechRepository.save(speech);
        return meeting;
    }

    public Long evaluateSpeech(Long speechId) {
        User user = userService.getUserBySecurityConfig().orElseThrow();
        Speech speech = speechRepository.findById(speechId).orElseThrow();
        speech.setEvaluatorId(user.getId());
        speechRepository.save(speech);
        return speech.getMeetingId();
    }

    public void archiveMeeting() {
        LocalDateTime cutOff = LocalDateTime.now().plusDays(1);
        meetingRepository.findAll().stream().filter(a -> a.getStartDateTime().isBefore(cutOff)).findAny().ifPresent(meeting -> {
            Agenda agenda = getAgendaByMeeting(meeting);
            agenda.speeches().forEach(userService::recordSpeech);
            agenda.roles().forEach(userService::recordRole);
            meetingRepository.delete(meeting);
            scheduleNewMeeting();
        });
    }

    private void scheduleNewMeeting() {
        meetingRepository.findAll().stream()
                .sorted(Comparator.comparing(Meeting::getStartDateTime).reversed())
                .filter(Meeting::getShouldReschedule).findAny().ifPresent(meeting -> {
                    MeetingConfig config = meetingConfigRepository.findAll().stream().findAny().orElseThrow();
                    Meeting scheduledMeeting = new Meeting();
                    scheduledMeeting.setShouldReschedule(true);
                    scheduledMeeting.setSpeakers(config.getSpeakers());
                    scheduledMeeting.setStartDateTime(meeting.getStartDateTime().plusDays(config.getScheduleDays()));
                    final long meetingId = meetingRepository.save(scheduledMeeting).getId();

                    List<MeetingRole> roles = roleRepository.findAll().stream().map(role -> {
                        MeetingRole meetingRole = new MeetingRole();
                        meetingRole.setMeetingId(meetingId);
                        meetingRole.setRoleId(role.getId());
                        return meetingRole;
                    }).toList();
                    meetingRoleRepository.saveAll(roles);
                });
    }


}
