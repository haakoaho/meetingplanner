package org.toastmasters.meetingplanner.dto.agenda;

import jakarta.persistence.*;

@Entity
@Table(name = "meeting_config")
public class MeetingConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    Integer speakers;
    Integer scheduleDays;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSpeakers() {
        return speakers;
    }

    public void setSpeakers(Integer speakers) {
        this.speakers = speakers;
    }

    public Integer getScheduleDays() {
        return scheduleDays;
    }

    public void setScheduleDays(Integer scheduleDays) {
        this.scheduleDays = scheduleDays;
    }
}
