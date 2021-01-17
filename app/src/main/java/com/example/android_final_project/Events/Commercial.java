package com.example.android_final_project.Events;

import com.example.android_final_project.UserTypeClasses.placeOwner;
import com.example.android_final_project.UserTypeClasses.userDJ;

import java.util.Random;

public class Commercial
{
    private String commercialID;
    private placeOwner place;
    private String placeName;
    private userDJ dj;
    private String djName;
    private String promoContent;

    public Commercial(placeOwner place, userDJ dj, String content)
    {
        Random randObj = new Random();
        this.commercialID = Integer.toString(randObj.nextInt(100000));
        this.place = place;
        this.placeName=place.getPlaceName();
        this.dj=dj;
        this.djName=dj.getStageName();
        this.promoContent=content;
    }
}

