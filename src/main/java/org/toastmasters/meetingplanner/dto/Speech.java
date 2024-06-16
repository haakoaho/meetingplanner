package org.toastmasters.meetingplanner.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "speech")
public class Speech {
    @Id
    private Long id;
    private String title;
    private String pathway;
    private Integer speakerId;

    private Integer evaluatorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPathway() {
        return pathway;
    }

    public void setPathway(String pathway) {
        this.pathway = pathway;
    }

    public Integer getSpeakerId() {
        return speakerId;
    }

    public void setSpeakerId(Integer speakerId) {
        this.speakerId = speakerId;
    }

    public Integer getEvaluatorId() {
        return evaluatorId;
    }

    public void setEvaluatorId(Integer evaluatorId) {
        this.evaluatorId = evaluatorId;
    }
}