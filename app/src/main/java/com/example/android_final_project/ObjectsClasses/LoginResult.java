package com.example.android_final_project.ObjectsClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResult {
    private String stageName;
    private String placeName;
    private String email;
    private String type;

    public String getType() { return type; }

    public String getStageName() {
        return stageName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getEmail() {
        return email;
    }


}

