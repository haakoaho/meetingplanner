package org.toastmasters.meetingplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.toastmasters.meetingplanner.dto.Speech;

import java.util.List;
import java.util.Map;

public interface SpeechRepository extends JpaRepository<Speech, Long> {

    @Query(value = "select s.id, s.title, s.pathway, s.speaker_id, s.evaluator_id, sp.name as speaker_name, ev.name as evaluator_name from speech s inner join users sp on \n" +
            "s.speaker_id = sp.id " +
            "left join users ev " +
            "on s.evaluator_id = ev.id " +
            "where s.meeting_id = :meetingId", nativeQuery = true)
    List<Map<String,Object>> findSpeechInAgendaByMeetingId(@Param("meetingId") Long meetingId);
}
