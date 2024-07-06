package org.toastmasters.meetingplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.toastmasters.meetingplanner.dto.Speech;

import java.util.List;

public interface SpeechRepository extends JpaRepository<Speech, Long> {

    List<Speech> findByMeetingId(Long meetingId);
}
