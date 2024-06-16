package org.toastmasters.meetingplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.toastmasters.meetingplanner.dto.agenda.Meeting;

import java.time.LocalDateTime;

public interface MeetingRepository extends JpaRepository<Meeting, LocalDateTime> {
}
