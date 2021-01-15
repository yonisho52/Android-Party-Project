package com.example.android_final_project;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterace {

    @POST("/login")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    @POST("/registerRegularUser")
    Call<Void> executeRegUser(@Body HashMap<String, String> map);
}
