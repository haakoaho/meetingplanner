package org.toastmasters.meetingplanner.dto;

import org.toastmasters.meetingplanner.dto.agenda.AgendaSpeech;

public record RecordSpeech(String title, String pathway) {
    public static RecordSpeech fromAgendaSpeech(AgendaSpeech agendaSpeech) {
        return new RecordSpeech(agendaSpeech.getTitle(), agendaSpeech.getPathway());
    }
}
