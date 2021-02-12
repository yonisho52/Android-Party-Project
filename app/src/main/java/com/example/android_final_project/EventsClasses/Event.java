package com.example.android_final_project.EventsClasses;

import com.example.android_final_project.UserTypeClasses.PlaceOwner;
import com.example.android_final_project.UserTypeClasses.UserDJ;

import java.util.Date;
import java.util.Random;

public class Event
{
    private static int statEventId=1000;
    private int eventCode;
    private String eventDate;
    private String eventName;
    private PlaceOwner eventPlace;
    private String placeName;
    //private UserDJ playingDj;
    private String playingDjName;
    private double placeRating;
    private double djRating;
    private double eventRating;
    private static int numOfRates=0;
    private String startHour;
    private String endHour;

    public Event(String eventDate, PlaceOwner eventPlace, UserDJ playingDj)
    {
        this.eventCode=statEventId++;
        //Check for last eventID value and add +1 to it
        this.eventDate = eventDate;
        this.eventPlace = eventPlace;
        this.placeName=eventPlace.getPlaceName();
       // this.playingDj = playingDj;
        this.playingDjName=playingDj.getStageName();
        this.placeRating = eventPlace.getPlaceRating();
        this.djRating = playingDj.getRating();
        this.eventRating=0;
    }
    public Event(String eventDate, String eventName,String placeOwner, String playingDj, String startTime, String endTime)
    {
        this.eventCode=statEventId++;
        this.eventDate=eventDate;
        this.placeName=placeOwner;
        this.eventName=eventName;
        this.playingDjName=playingDj;
        this.startHour=startTime;
        this.endHour=endTime;

        //Need to add to DB
        this.eventRating=0;
        this.numOfRates=0;
    }

    public String getEventID() { return Integer.toString(eventCode); }

    public String getEventName(){return eventName;}

    public String getStartHour(){return startHour;}
    public String getEndHour(){return endHour;}

    public String getEventDate() { return eventDate; }

    public PlaceOwner getEventPlace() { return eventPlace; }

    public String getPlaceName() { return placeName; }

   // public UserDJ getPlayingDj() { return playingDjNa; }

    public String getPlayingDjName() { return playingDjName; }

    public double getPlaceRating() { return placeRating; }

    public double getDjRating() { return djRating; }

    public double getEventRating() { return eventRating; }

    public void setEventDate(String eventDate) { this.eventDate = eventDate; }

    public void setEventPlace(PlaceOwner eventPlace) { this.eventPlace = eventPlace; }

   // public void setPlayingDj(UserDJ playingDj) { this.playingDj = playingDj; }

    public void addEventRating(double rate)
    {
        this.placeRating = ((this.placeRating * this.numOfRates++)+rate)/(this.numOfRates);
        //this.numOfRates++;
    }
}
