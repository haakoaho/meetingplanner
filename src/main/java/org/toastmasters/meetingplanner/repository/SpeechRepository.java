package org.toastmasters.meetingplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.toastmasters.meetingplanner.dto.Speech;

public interface SpeechRepository extends JpaRepository<Speech, Long> {
}
