package com.example.android_final_project.ObjectsClasses;

public class Message {

    private String createdBy;
    private String date;
    private String time;
    private String text;
    private String name;

    public Message(String createdBy, String date, String time, String text) {
        this.createdBy = createdBy;
        this.date = date;
        this.time = time;
        this.text = text;
    }


    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getText() {
        return text;
    }
}
