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

    @Scheduled(cron = "0 1 1 * * ?")
    public void runJob() {
        meetingService.archiveMeeting();
    }

    @Scheduled(cron = "0 49 18 * * ?") // Runs every minute for testing
    public void testJob() {
        meetingService.archiveMeeting();
    }

}
