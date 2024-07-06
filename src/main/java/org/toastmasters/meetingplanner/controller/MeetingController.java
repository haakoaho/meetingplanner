package org.toastmasters.meetingplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.toastmasters.meetingplanner.dto.SpeechRequest;
import org.toastmasters.meetingplanner.dto.agenda.Agenda;
import org.toastmasters.meetingplanner.service.MeetingService;

@RestController
@RequestMapping("api/currentMeeting")
public class MeetingController {

    private final MeetingService meetingService;
    @Autowired
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PatchMapping("reserveRole/{meetingOrder}/{id}")
    public Agenda reserveRole(@PathVariable Long id, @PathVariable int meetingOrder){
        meetingService.reserveRole(id, meetingOrder);
        return meetingService.getAgenda(meetingOrder);
    }

    @PostMapping("reserveSpeech")
    public Agenda reserveSpeech(@RequestBody SpeechRequest request){
        meetingService.reserveSpeech(request);
        return meetingService.getAgenda(request.meetingOrder());
    }

    @PatchMapping("evaluateSpeech/{id}")
    public Agenda evaluateSpeech(@PathVariable Long id){
        meetingService.evaluateSpeech(id);
        return meetingService.getAgenda(0);
    }

    @GetMapping("agenda/{meetingOrder}")
    public Agenda getAgenda(@PathVariable int meetingOrder){
        return meetingService.getAgenda(meetingOrder);
    }
}
