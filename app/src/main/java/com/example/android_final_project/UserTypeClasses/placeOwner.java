package com.example.android_final_project.UserTypeClasses;

public class placeOwner extends GeneralUser
{
    private String placeName;
    private String placeType;
    private String placeAddress;
    private double placeRating;

    public placeOwner(String firstName, String lastName, String email, String password, String placeName, String placeType, String placeAddress) {
        super(firstName, lastName, email, password);
        this.placeName = placeName;
        this.placeType = placeType;
        this.placeAddress = placeAddress;
        this.placeRating = 0;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getPlaceType() {
        return placeType;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }
}
