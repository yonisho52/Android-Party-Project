package com.example.android_final_project.EventsClasses;

import com.example.android_final_project.UserTypeClasses.PlaceOwner;
import com.example.android_final_project.UserTypeClasses.UserDJ;

import java.util.Random;

public class Commercial
{
    private String commercialID;
    private PlaceOwner placeOwner;
    private String placeName;
    private UserDJ userDJ;
    private String djName;
    private String promoContent;

    public Commercial(PlaceOwner placeOwner, UserDJ userDJ, String content)
    {
        Random randObj = new Random();
        this.commercialID = Integer.toString(randObj.nextInt(100000));
        this.placeOwner = placeOwner;
        this.placeName=placeOwner.getPlaceName();
        this.userDJ=userDJ;
        this.djName=userDJ.getStageName();
        this.promoContent=content;
    }
}
