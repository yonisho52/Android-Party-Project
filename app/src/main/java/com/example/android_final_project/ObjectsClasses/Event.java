package com.example.android_final_project.ObjectsClasses;

public class Event
{
//    private static int statEventId=1000;
//    private int eventCode;
    private String eventDate;
    private String eventName;
//    private PlaceOwner eventPlace;
    private String placeName;
    //private UserDJ playingDj;
    private String whosPlaying;
    private String whosPlayingName;
//    private double placeRating;
//    private double djRating;
    private double eventRating;
    private int numOfRates;
    private String startTime;
    private String endTime;
    private String createdBy;
    private String partyCode;

//    private Boolean thumb = false;
//
//    public Boolean getThumb() {
//        return thumb;
//    }
//
//    public void setThumb(Boolean thumb) {
//        this.thumb = thumb;
//    }

    public String getPartyCode() {
        return partyCode;
    }


//    public void setWhosPlaying(String whosPlaying) {
//        this.whosPlaying = whosPlaying;
//    }

    public String getWhosPlayingName() {
        return whosPlayingName;
    }

    public void setWhosPlayingName(String whosPlayingName) {
        this.whosPlayingName = whosPlayingName;
    }

//    public Event(String eventDate, PlaceOwner eventPlace, UserDJ playingDj)
//    {
//        this.eventCode=statEventId++;
//        //Check for last eventID value and add +1 to it
//        this.eventDate = eventDate;
//        this.eventPlace = eventPlace;
//        this.placeName=eventPlace.getPlaceName();
//       // this.playingDj = playingDj;
//        this.whosPlaying =playingDj.getStageName();
//        this.placeRating = eventPlace.getPlaceRating();
//        this.djRating = playingDj.getRating();
//        this.eventRating=0;
//    }

    public String getCreatedBy() {
        return createdBy;
    }

//    public static int getStatEventId() {
//        return statEventId;
//    }

//    public int getEventCode() {
//        return eventCode;
//    }

    public int getNumOfRates() {
        return numOfRates;
    }

    public Event(String eventDate, String eventName, String placeOwner, String playingDjEmail, String startTime, String endTime,String playingDjName, String placeName, String createdBy )
    {
//        this.eventCode=statEventId++;
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

//    public String getEventID() { return Integer.toString(eventCode); }

    public String getEventName(){return eventName;}

    public String getStartTime(){return startTime;}
    public String getEndTime(){return endTime;}

    public String getEventDate() { return eventDate; }

//    public PlaceOwner getEventPlace() { return eventPlace; }

    public String getPlaceName() { return placeName; }

   // public UserDJ getPlayingDj() { return playingDjNa; }

    public String getWhosPlaying() { return whosPlaying; }

//    public double getPlaceRating() { return placeRating; }
//
//    public double getDjRating() { return djRating; }

    public double getEventRating() { return eventRating; }

//    public void setEventDate(String eventDate) { this.eventDate = eventDate; }
//
//    public void setEventPlace(PlaceOwner eventPlace) { this.eventPlace = eventPlace; }

}
