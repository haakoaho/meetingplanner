package org.toastmasters.meetingplanner.dto.agenda;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "meeting")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private LocalDateTime startDateTime;
    private String wordOfTheDay;
    private String theme;

    private Integer speakers;

    private Boolean shouldReschedule;

    public Boolean getShouldReschedule() {
        return shouldReschedule;
    }

    public void setShouldReschedule(Boolean shouldReschedule) {
        this.shouldReschedule = shouldReschedule;
    }

    public Integer getSpeakers() {
        return speakers;
    }

    public void setSpeakers(Integer speakers) {
        this.speakers = speakers;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getWordOfTheDay() {
        return wordOfTheDay;
    }

    public void setWordOfTheDay(String wordOfTheDay) {
        this.wordOfTheDay = wordOfTheDay;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

}
