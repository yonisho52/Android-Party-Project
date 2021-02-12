package com.example.android_final_project;

import com.example.android_final_project.EventsClasses.EventResult;

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
    @POST("/getEventsByDate")
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
    @POST("/getUserByEmail")
    Call<Details> getUser(@Body HashMap<String, String> map);

    // Check if the party Code is free
    @POST("/checkValidPartyCode")
    Call<Void> checkValidPartyCode(@Body HashMap<String,String> map);

    // Get Email (dj) By sending Stage Name
    @GET("/getDjEmailByStageName")
    Call<Details> getDjEmailByStageName(@Body HashMap<String, String> map);

    @POST("/addMessageToAds")
    Call<Void> addMessageToAds(@Body HashMap<String,String> map);

    @GET("/getAllMessages")
    Call<List<Message>> getAllMessages();

    ///

    @POST("/getPlaceNameOrStageNameByEmail")
    Call<String> getPlaceNameOrStageNameByEmail(@Body HashMap<String,String> map);

    //Testing GET functions

    @GET("/getEvents")
    default Call<Void> executeTest() {
        return null;
    }

}
