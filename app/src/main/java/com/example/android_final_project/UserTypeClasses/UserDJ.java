package com.example.android_final_project.UserTypeClasses;

import java.net.URL;
import java.util.List;

public class UserDJ extends GeneralUser
{
    private String stageName;
    private String playingGenre;
    private String youtubeLink; // change from url
    private String spotifyLink; // change from url
    private String appleMusicLink; // change from url
    private int age;
    private List<String> placesCanBeFound;
    private double rating;
    private int numOfRates;

    public int getNumOfRates() {
        return numOfRates;
    }

    public UserDJ(String firstName, String lastName, String email, String password, String stageName, String playingGenre, String youtubeLink,
                  String spotifyLink, String appleMusicLink, int age, List<String> placesCanBeFound) {
        super(firstName, lastName, email, password);
        this.stageName = stageName;
        this.playingGenre = playingGenre;
        this.youtubeLink = youtubeLink;
        this.spotifyLink = spotifyLink;
        this.appleMusicLink = appleMusicLink;
        this.age = age;
        this.placesCanBeFound = placesCanBeFound;
        this.rating = 0;
        this.numOfRates = 0;
    }

    //Getters
    public String getStageName() {
        return stageName;
    }
    public String getPlayingGenre() { return playingGenre; }
    public String getYoutubeLink() {
        return youtubeLink;
    }
    public String getSpotifyLink() {
        return spotifyLink;
    }
    public String getAppleMusicLink() {
        return appleMusicLink;
    }
    public int getAge() {
        return age;
    }
    public List<String> getPlacesCanBeFound() {
        return placesCanBeFound;
    }
    public double getRating() {
        return rating;
    }

    //Setters
    public void setStageName(String stageName) {
        this.stageName = stageName;
    }
    public void setPlayingGenre(String playingGenre) {
        this.playingGenre = playingGenre;
    }
    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }
    public void setSpotifyLink(String spotifyLink) {
        this.spotifyLink = spotifyLink;
    }
    public void setAppleMusicLink(String appleMusicLink) {
        this.appleMusicLink = appleMusicLink;
    }
    public void setPlacesCanBeFound(List<String> placesCanBeFound) {
        this.placesCanBeFound = placesCanBeFound;
    }

}
