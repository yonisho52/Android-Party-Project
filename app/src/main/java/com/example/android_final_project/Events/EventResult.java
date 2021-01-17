package com.example.android_final_project.Events;

import java.util.Date;

public class EventResult
{
    private String createdBy;
    private String eventDate;
    private String eventName;
    private String playingDj;

    public String getCreatedBy() { return this.createdBy; }

    public String getEventDate() { return this.eventDate; }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setPlayingDJ(String playingDJ) {
        this.playingDj = playingDJ;
    }

    public String getEventName() { return eventName; }

    public String getPlayingDJ() { return playingDj; }
}

