package org.toastmasters.meetingplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.toastmasters.meetingplanner.dto.agenda.Meeting;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    @Query(value = "SELECT * FROM Meeting ORDER BY start_date_time LIMIT 1 OFFSET :offset", nativeQuery = true)
    Meeting findNthRecentMeeting(@Param("offset") int offset);

    @Query(value = " SELECT u.id AS user_id," +
            "r.id AS role_id," +
            "r.name AS role_name," +
            "u.name AS user_name " +
            "FROM meeting_roles mr " +
            "JOIN roles r ON mr.role_id = r.id " +
            "LEFT JOIN users u ON mr.user_id = u.id " +
            "where mr.meeting_id = :meetingId;", nativeQuery = true)
    List<Map<String, Object>> findAgendaRolesByMeetingId(@Param("meetingId") Long meetingId);

}
