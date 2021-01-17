package com.example.android_final_project.Events;

import com.example.android_final_project.UserTypeClasses.placeOwner;
import com.example.android_final_project.UserTypeClasses.userDJ;

import java.util.Date;
import java.util.Random;

public class Event
{
    private String eventID;
    private Date eventDate;
    private placeOwner eventPlace;
    private String placeName;
    private userDJ playingDj;
    private String playingDjName;
    private double placeRating;
    private double djRating;
    private double eventRating;
    private static int numOfRates=0;

    public Event(Date eventDate, placeOwner eventPlace, userDJ playingDj) {
        Random randObj = new Random();
        this.eventID = Integer.toString(randObj.nextInt(100000));
        //Check for last eventID value and add +1 to it
        this.eventDate = eventDate;
        this.eventPlace = eventPlace;
        this.placeName=eventPlace.getPlaceName();
        this.playingDj = playingDj;
        this.playingDjName=playingDj.getStageName();
        this.placeRating = eventPlace.getPlaceRating();
        this.djRating = playingDj.getRating();
        this.eventRating=0;
    }

    public String getEventID() { return eventID; }

    public Date getEventDate() { return eventDate; }

    public placeOwner getEventPlace() { return eventPlace; }

    public String getPlaceName() { return placeName; }

    public userDJ getPlayingDj() { return playingDj; }

    public String getPlayingDjName() { return playingDjName; }

    public double getPlaceRating() { return placeRating; }

    public double getDjRating() { return djRating; }

    public double getEventRating() { return eventRating; }

    public void setEventDate(Date eventDate) { this.eventDate = eventDate; }

    public void setEventPlace(placeOwner eventPlace) { this.eventPlace = eventPlace; }

    public void setPlayingDj(userDJ playingDj) { this.playingDj = playingDj; }

    public void addEventRating(double rate)
    {
        this.placeRating = ((this.placeRating * this.numOfRates++)+rate)/(this.numOfRates);
        //this.numOfRates++;
    }
}
