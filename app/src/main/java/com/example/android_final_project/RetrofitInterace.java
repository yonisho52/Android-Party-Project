package com.example.android_final_project;

import com.example.android_final_project.EventsClasses.EventResult;
import com.example.android_final_project.UserTypeClasses.RegularUser;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitInterace {

    //Login request, receives response with the logged user details
    @POST("/login")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    //Registration request, no data received except result code
    @POST("/registerRegularUser")
    Call<Void> executeRegUser(@Body HashMap<String, String> map);

    //Registration request, no data received except result code
    @POST("/registerDjUser")
    Call<Void> executeDjUser(@Body HashMap<String, String> map);

    //Registration request, no data received except result code
    @POST("/registerPlaceOwnerUser")
    Call<Void> executeOwnerUser(@Body HashMap<String, String> map);

    //Add event request, no data received except result code
    @POST("/addEvent")
    Call<Void> executeAddEvent(@Body HashMap<String, String> map);

    //Get events request, receives existing event data
    @GET("/getEventsByDate")
    Call<List<EventResult>> executeGetEvents(@Body HashMap<String, String> map);

    //Get Events By Email (for place owner and dj users)
    @GET("/getEventsByEmail")
    Call<List<EventResult>> executeGetEventsByEmail(@Body HashMap<String, String> map);

    // Check if Dj Name is free
    @POST("/checkDjName")
    Call<Void> executeCheckDjName(@Body HashMap<String, String> map);

    // Check if place Name is free
    @POST("/checkPlaceName")
    Call<Void> executeCheckPlaceName(@Body HashMap<String, String> map);

    // Get all Djs Stage Names
    @GET("/getAllDjStageName")
    Call<List<StageName>> getPosts();

    // Get all User details by Email
    @GET("/getUserByEmail")
    Call<Details> getUser(@Body HashMap<String, String> map);

    // Check if the party Code is free
    @POST("/checkValidPartyCode")
    Call<Void> checkValidPartyCode(@Body HashMap<String,String> map);

    // Get Email (dj) By sending Stage Name
    @GET("/getDjEmailByStageName")
    Call<Details> getDjEmailByStageName(@Body HashMap<String, String> map);

}
