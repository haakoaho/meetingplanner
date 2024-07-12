package org.toastmasters.meetingplanner.dto.agenda;

import java.time.LocalDateTime;
import java.util.List;

public record Agenda(List<AgendaRole> roles, List<AgendaSpeech> speeches, LocalDateTime startDateTime, String wordOfTheDay, String theme, Integer speakers) {
}
