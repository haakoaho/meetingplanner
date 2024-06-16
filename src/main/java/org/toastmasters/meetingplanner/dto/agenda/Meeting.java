package org.toastmasters.meetingplanner.dto.agenda;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "meeting")
public class Meeting {

    @Id
    private LocalDateTime startDateTime;
    private String wordOfTheDay;
    private String theme;
    private Integer speakers;

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

    public Integer getSpeakers() {
        return speakers;
    }

    public void setSpeakers(Integer speakers) {
        this.speakers = speakers;
    }
}
