package org.toastmasters.meetingplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @PatchMapping("reserveRole/{id}")
    public Agenda reserveRole(@PathVariable Long id){
        meetingService.reserveRole(id);
        return meetingService.getAgenda();
    }

    @PostMapping("reserveSpeech")
    public Agenda reserveSpeech(@RequestBody Object speech){
        return meetingService.getAgenda();
    }

    @GetMapping("agenda")
    public Agenda getAgenda(){
        return meetingService.getAgenda();
    }
}
