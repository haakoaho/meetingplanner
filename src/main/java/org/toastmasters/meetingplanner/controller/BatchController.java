package org.toastmasters.meetingplanner.controller;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.toastmasters.meetingplanner.service.MeetingService;

@RestController
@RequestMapping("api/batch")
@RolesAllowed({"ADMIN","GITHUB_ACTIONS"})
public class BatchController {

    private final MeetingService meetingService;

    public BatchController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PostMapping("archiveMeeting")
    public void recordMeetingBatch() {
        meetingService.archiveMeeting();
        meetingService.scheduleNewMeeting();
    }
}
