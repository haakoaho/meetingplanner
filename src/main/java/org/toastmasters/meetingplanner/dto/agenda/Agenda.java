package org.toastmasters.meetingplanner.dto.agenda;

import org.toastmasters.meetingplanner.dto.Speech;

import java.time.LocalDateTime;
import java.util.List;

public record Agenda(List<AgendaRole> roles, List<Speech> speeches, LocalDateTime startDateTime, String wordOfTheDay, String theme, Integer speakers) {
}
