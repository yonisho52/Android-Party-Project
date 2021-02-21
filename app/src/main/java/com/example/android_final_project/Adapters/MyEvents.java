package com.example.android_final_project.Adapters;

import com.example.android_final_project.ObjectsClasses.Event;

import java.util.List;

public class MyEvents {

    List<Event> myEventsList;

    public List<Event> getMyEventsList() {
        return myEventsList;
    }

    public void setMyEventsList(List<Event> myEventsList) {
        this.myEventsList = myEventsList;
    }

    public MyEvents(List<Event> myEventsList) {
        this.myEventsList = myEventsList;
    }
}
