package org.toastmasters.meetingplanner.dto;

import org.toastmasters.meetingplanner.dto.agenda.AgendaSpeech;

import java.util.Map;

public record RecordSpeech(String title, String pathway) {
    public static RecordSpeech fromAgendaSpeech(AgendaSpeech agendaSpeech) {
        return new RecordSpeech(agendaSpeech.getTitle(), agendaSpeech.getPathway());
    }

    public static RecordSpeech fromDatabaseJson(Map<String, Object> map) {
        return new RecordSpeech((String) map.get("title"), (String) map.get("pathway"));
    }
}
