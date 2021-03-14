package com.example.android_final_project;

import com.example.android_final_project.ObjectsClasses.StageName;
import com.example.android_final_project.ObjectsClasses.Details;
import com.example.android_final_project.ObjectsClasses.Event;
import com.example.android_final_project.ObjectsClasses.LoginResult;
import com.example.android_final_project.ObjectsClasses.Message;
import com.example.android_final_project.ObjectsClasses.Survey;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitInterface {

    // **** LOGIN REGISTER START

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

    // Check if Dj Name is free
    @POST("/checkDjName")
    Call<Void> executeCheckDjName(@Body HashMap<String, String> map);

    // Check if place Name is free
    @POST("/checkPlaceName")
    Call<Void> executeCheckPlaceName(@Body HashMap<String, String> map);

    // Get all Djs Stage Names
    @GET("/getAllDjStageName")
    Call<List<StageName>> getStageNames();

    // Get all User details by Email
    @POST("/getUserByEmail")
    Call<Details> getUser(@Body HashMap<String, String> map);

    // Get Email (dj) By sending Stage Name
    @POST("/getDjEmailByStageName")
    Call<String> getDjEmailByStageName(@Body HashMap<String, String> map);

    @POST("/getPlaceNameOrStageNameByEmail")
    Call<Void> getPlaceNameOrStageNameByEmail(@Body HashMap<String,String> map);

    // **** LOGIN REGISTER END


    // **** EVENT START

    //Add event request, no data received except result code
    @POST("/addEvent")
    Call<Void> executeAddEvent(@Body HashMap<String, String> map);

    //Get events request, receives existing event data
    @POST("/getEventsByDate")
    Call<List<Event>> executeGetEvents(@Body HashMap<String, String> map);

    //Get Events By Email (for place owner and dj users)
    @POST("/getEventsByEmail")
    Call<List<Event>> executeGetEventsByEmail(@Body HashMap<String, String> map);

    // Check if the party Code is free
    @POST("/checkValidPartyCode")
    Call<Void> checkValidPartyCode(@Body HashMap<String,String> map);

    // For Dj/Owner user ONLY!
    @POST("/getEventByDateAndEmail")
    Call<Event> getEventByDateAndEmail(@Body HashMap<String, String> map);

    //For regular user ONLY!
    @POST("/checkPartyNowByCodeAndDate")
    Call<Event> checkPartyNowByCodeAndDate(@Body HashMap<String, String> map);

    // by Regular user EMAIL
    @POST("/getSavedEvents")
    Call<List<Event>> getSavedEvents(@Body HashMap<String, String> map);

    @POST("/removeSavedEvents")
    Call<Void> removeSavedEvents(@Body HashMap<String, String[]> map);

    @POST("/checkIfEventSaved")
    Call<Void> checkIfEventSaved(@Body HashMap<String, String> map);

    @POST("/addSavedEvent")
    Call<Void> addSavedEvent(@Body HashMap<String, String> map);

    @POST("/deleteEvent")
    Call<Void> deleteEvent(@Body HashMap<String,String> map); // map=> partyCode

    // **** EVENT END


    // **** MESSAGES AND SURVEY START

    @POST("/addMessageToAds")
    Call<Void> addMessageToAds(@Body HashMap<String,String> map);

    @GET("/getAllMessages")
    Call<List<Message>> getAllMessages();

    @POST("/rateDjOrPlaceByEmail")
    Call<Void> rateDjOrPlaceByEmail(@Body HashMap<String, String> map); // map=> email+rating

    @POST("/rateEventByPartyCode")
    Call<Void> rateEventByPartyCode(@Body HashMap<String,String> map); // map=> partyCode+rating

    @POST("/addSurvey")
    Call<Void> addSurvey(@Body HashMap<String,String> map); // map=> survey object

    @POST("/getSurveyByPartyCode")
    Call<Survey> getSurveyByPartyCode(@Body HashMap<String,String> map); // map => partyCode

    @POST("/voteForSurvey")
    Call<Void> voteForSurvey(@Body HashMap<String,String> map); // map => partyCode + ansNumber (***ansNumber = ans1/ans2/ans3***)

    // **** MESSAGES AND SURVEY END


    //Testing GET functions

    @GET("/getEvents")
    default Call<Void> executeTest() {
        return null;
    }

}
