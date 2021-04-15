package com.example.android_party_app.Adapters;

import com.example.android_party_app.ObjectsClasses.Message;

import java.util.List;

public class MyAds {

    List<Message> myAdsList;

    public MyAds(List<Message> myAdsList) {
        this.myAdsList = myAdsList;
    }

    public List<Message> getMyAdsList() {
        return myAdsList;
    }

    public void setMyAdsList(List<Message> myAdsList) {
        this.myAdsList = myAdsList;
    }
}
