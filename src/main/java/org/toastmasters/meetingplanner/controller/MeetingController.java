package org.toastmasters.meetingplanner.controller;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.toastmasters.meetingplanner.dto.SpeechRequest;
import org.toastmasters.meetingplanner.dto.agenda.Agenda;
import org.toastmasters.meetingplanner.dto.agenda.Meeting;
import org.toastmasters.meetingplanner.service.MeetingService;

@RestController
@RequestMapping("api/currentMeeting")
@RolesAllowed("USER")
public class MeetingController {

    private final MeetingService meetingService;

    @Autowired
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PatchMapping("reserveRole/{meetingOrder}/{id}")
    public Agenda reserveRole(@PathVariable Long id, @PathVariable int meetingOrder, @RequestParam Long userId) {
        meetingService.reserveRole(id, meetingOrder, false, userId);
        return meetingService.getAgenda(meetingOrder);
    }

    @PatchMapping("reserveRole/{meetingOrder}/{id}/force")
    public Agenda forceReserveRole(@PathVariable Long id, @PathVariable int meetingOrder, @RequestParam Long userId) {
        meetingService.reserveRole(id, meetingOrder, true, userId);
        return meetingService.getAgenda(meetingOrder);
    }

    @PatchMapping("cancelRole/{meetingOrder}/{id}")
    public Agenda cancelRole(@PathVariable Long id, @PathVariable int meetingOrder) {
        meetingService.cancelRole(id, meetingOrder);
        return meetingService.getAgenda(meetingOrder);
    }

    @PostMapping("reserveSpeech")
    public Agenda reserveSpeech(@RequestBody SpeechRequest request) {
        Meeting meeting = meetingService.reserveSpeech(request);
        return meetingService.getAgendaByMeeting(meeting);
    }

    @PatchMapping("evaluateSpeech/{id}")
    public Agenda evaluateSpeech(@PathVariable Long id) {
        Long meetingId = meetingService.evaluateSpeech(id, false);
        return meetingService.getAgendaByMeetingId(meetingId);
    }

    @PatchMapping("evaluateSpeech/{id}/force")
    public Agenda forceEvaluate(@PathVariable Long id) {
        Long meetingId = meetingService.evaluateSpeech(id, true);
        return meetingService.getAgendaByMeetingId(meetingId);
    }

    @PatchMapping("cancelEvaluation/{id}")
    public Agenda cancelEvaluation(@PathVariable Long id) {
        Long meetingId = meetingService.cancelEvaluation(id);
        return meetingService.getAgendaByMeetingId(meetingId);
    }


    @GetMapping("agenda/{meetingOrder}")
    public Agenda getAgenda(@PathVariable int meetingOrder) {
        return meetingService.getAgenda(meetingOrder);
    }

    @PatchMapping("wordOfTheDay")
    public void setWordOfTheDay(@RequestParam String word) {
        meetingService.setWordOfTheDay(word);
    }

    @PatchMapping("theme")
    public void setTheme(@RequestParam String theme) {
        meetingService.setTheme(theme);
    }
}
