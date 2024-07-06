package org.toastmasters.meetingplanner.dto.agenda;

import java.util.Map;
import java.util.Optional;

public class AgendaSpeech {

    private final Long id;
    private final String title;
    private final String pathway;
    private final Long speakerId;

    private final Long evaluatorId;

    private final String speakerName;

    private final String evaluatorName;

    public AgendaSpeech(Map<String, Object> map) {
        id = ((Number) map.get("id")).longValue();
        title = (String) map.get("title");
        pathway = (String) map.get("pathway");
        speakerId = ((Number) map.get("speaker_id")).longValue();
        evaluatorId = Optional.ofNullable(map.get("evaluator_id")).map(o -> (Number) o).map(Number::longValue).orElse(null);
        speakerName = (String) map.get("speaker_name");
        evaluatorName = (String) map.get("evaluator_name");
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPathway() {
        return pathway;
    }

    public Long getSpeakerId() {
        return speakerId;
    }

    public Long getEvaluatorId() {
        return evaluatorId;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public String getEvaluatorName() {
        return evaluatorName;
    }
}
