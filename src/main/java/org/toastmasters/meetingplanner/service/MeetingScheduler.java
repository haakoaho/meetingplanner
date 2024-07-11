package org.toastmasters.meetingplanner.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class MeetingScheduler {

    private final MeetingService meetingService;

    public MeetingScheduler(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @Scheduled(cron = "0 0 2 1/1 * *")
    public void runJob() throws Exception {
        meetingService.archiveMeeting();
    }

}
