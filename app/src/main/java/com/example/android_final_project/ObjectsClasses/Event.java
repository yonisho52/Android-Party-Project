package com.example.android_final_project.ObjectsClasses;

public class Event
{

    private String eventDate;
    private String eventName;
    private String placeName;
    private String whosPlaying;
    private String whosPlayingName;
    private double eventRating;
    private int numOfRates;
    private String startTime;
    private String endTime;
    private String createdBy;
    private String partyCode;


    public String getPartyCode() {
        return partyCode;
    }

    public String getWhosPlayingName() {
        return whosPlayingName;
    }

    public void setWhosPlayingName(String whosPlayingName) {
        this.whosPlayingName = whosPlayingName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public int getNumOfRates() {
        return numOfRates;
    }

    public Event(String eventDate, String eventName, String placeOwner, String playingDjEmail, String startTime, String endTime,String playingDjName, String placeName, String createdBy )
    {
        this.eventDate=eventDate;
        this.placeName=placeOwner;
        this.eventName=eventName;
        this.whosPlaying =playingDjEmail;
        this.startTime =startTime;
        this.endTime =endTime;
        this.whosPlayingName = playingDjName;
        this.placeName = placeName;
        this.createdBy = createdBy;
        this.eventRating=0;
        this.numOfRates=0;
    }

    public String getEventName(){return eventName;}

    public String getStartTime(){return startTime;}

    public String getEndTime(){return endTime;}

    public String getEventDate() { return eventDate; }

    public String getPlaceName() { return placeName; }

    public String getWhosPlaying() { return whosPlaying; }

    public double getEventRating() { return eventRating; }
}
