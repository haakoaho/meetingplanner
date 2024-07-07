package org.toastmasters.meetingplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.toastmasters.meetingplanner.dto.agenda.MeetingConfig;

public interface MeetingConfigRepository extends JpaRepository<MeetingConfig, Long> {
}
