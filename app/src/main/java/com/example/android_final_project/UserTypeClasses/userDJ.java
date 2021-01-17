package com.example.android_final_project.UserTypeClasses;

import java.net.URL;
import java.util.List;

public class userDJ extends GeneralUser
{
    private String stageName;
    private String playingGenre;
    private URL youtubeLink;
    private URL spotifyLink;
    private URL appleMusicLink;
    private int age;
    private List<String> placesCanBeFound;
    private double rating;
    private static int numOfRates=0;

    public userDJ(String firstName, String lastName, String email, String password, String stageName, String playingGenre, URL youtubeLink,
                  URL spotifyLink, URL appleMusicLink, int age, List<String> placesCanBeFound) {
        super(firstName, lastName, email, password);
        this.stageName = stageName;
        this.playingGenre = playingGenre;
        this.youtubeLink = youtubeLink;
        this.spotifyLink = spotifyLink;
        this.appleMusicLink = appleMusicLink;
        this.age = age;
        this.placesCanBeFound = placesCanBeFound;
        this.rating = 0;
    }

    //Getters
    public String getStageName() {
        return stageName;
    }
    public URL getYoutubeLink() {
        return youtubeLink;
    }
    public URL getSpotifyLink() {
        return spotifyLink;
    }
    public URL getAppleMusicLink() {
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
    public void setYoutubeLink(URL youtubeLink) {
        this.youtubeLink = youtubeLink;
    }
    public void setSpotifyLink(URL spotifyLink) {
        this.spotifyLink = spotifyLink;
    }
    public void setAppleMusicLink(URL appleMusicLink) {
        this.appleMusicLink = appleMusicLink;
    }
    public void setPlacesCanBeFound(List<String> placesCanBeFound) {
        this.placesCanBeFound = placesCanBeFound;
    }
    public void addRating(double rate)
    {
        this.rating = ((this.rating * this.numOfRates++)+rate)/(this.numOfRates);
        //this.numOfRates++;
    }
}
