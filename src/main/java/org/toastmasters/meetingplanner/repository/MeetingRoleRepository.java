package org.toastmasters.meetingplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.toastmasters.meetingplanner.dto.agenda.MeetingRole;

import java.util.Optional;

public interface MeetingRoleRepository extends JpaRepository<MeetingRole, Long> {

    Optional<MeetingRole> findByMeetingIdAndRoleId(Long meetingId, Long roleId);
}
