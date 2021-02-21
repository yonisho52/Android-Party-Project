package com.example.android_final_project.Adapters;

import com.example.android_final_project.ObjectsClasses.Message;

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
