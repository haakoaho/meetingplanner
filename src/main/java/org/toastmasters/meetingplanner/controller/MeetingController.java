package org.toastmasters.meetingplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public  void reserveRole(@PathVariable Long id){
        meetingService.reserveRole(id);
    }
}
