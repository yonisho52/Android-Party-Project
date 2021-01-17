package com.example.android_final_project;

import com.example.android_final_project.Events.EventResult;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterace {

    //Login request, receives response with the logged user details
    @POST("/login")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    //Registration request, no data received except result code
    @POST("/registerRegularUser")
    Call<Void> executeRegUser(@Body HashMap<String, String> map);

    //Add event request, no data received except result code
    @POST("/addEvent")
    Call<Void> executeAddEvent(@Body HashMap<String, String> map);

    //Get events request, receives existing event data
    @POST("/getEvents")
    Call<EventResult> executeGetEvents(@Body HashMap<String, String> map);

}
