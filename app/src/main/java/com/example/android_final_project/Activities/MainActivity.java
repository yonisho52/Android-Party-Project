package com.example.android_final_project.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.android_final_project.FragmentsMain.NowEventMainFragment;
import com.example.android_final_project.LoginResult;
import com.example.android_final_project.R;
import com.example.android_final_project.RetrofitInterace;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

public class MainActivity extends AppCompatActivity {

    NavController navController;
    BottomNavigationView bottomNavigationView;
    public SharedPreferences sharedPreferencesLogin;
    public SharedPreferences sharedPreferencesParty;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    String type;
    String email;
    Boolean flag;

    private Retrofit retrofit;
    private RetrofitInterace retrofitInterace;
    private String BASEURL="http://10.0.2.2:3000";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.fragment_calendar);


        retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterace = retrofit.create(RetrofitInterace.class);
        generateCode();

        sharedPreferencesLogin = getSharedPreferences("Login", MODE_PRIVATE);
        type = sharedPreferencesLogin.getString("type","REGULAR");
        email = sharedPreferencesLogin.getString("keyUser",null);

        sharedPreferencesParty = getSharedPreferences("Party", MODE_PRIVATE);

//        *****START - MAIN MENU VIEW*****
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

//        USER TYPE MENU VIEW
        if(type.equals("REGULAR"))
            bottomNavigationView.inflateMenu(R.menu.regular_user_main_menu);
        else
            bottomNavigationView.inflateMenu(R.menu.dj_user_main_menu);


        navController = Navigation.findNavController(findViewById(R.id.fragment));

        //Titles for each page
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeMainFragment,R.id.savedEventsMainFragment,R.id.advertiseMainFragment,
                R.id.editProfileMainFragment,R.id.eventSummaryMainFragment,R.id.nowEventMainFragment,R.id.partyCodeMainFragment).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        //NAVIGATION CLICK
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());
//        *****END - MAIN MENU VIEW*****

    }

    public void joinParty()
    {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, new NowEventMainFragment()).commit();
    }

    public void insertParty(String code)
    {
        SharedPreferences.Editor editor = sharedPreferencesParty.edit();
        editor.putString("partyCode", code);
        //editor.putString("startTime", startTime);
        //editor.putString("EndTime", EndTime);
        editor.apply();
    }

    public String getType() {return type;}
    public String getEmail() { return  email;}




    // GENERATE CODE START
    public String generateCode()
    {
        Random r = new Random();
        String randomNumber = String.format("%05d", (Object) Integer.valueOf(r.nextInt(10001)));
        HashMap<String,String> map = new HashMap<String, String>();
        map.put("partyCode",randomNumber);

        while (!checkCode(map))
        {
            randomNumber = String.format("%05d", (Object) Integer.valueOf(r.nextInt(10001)));
            map = new HashMap<String, String>();
            map.put("partyCode",randomNumber);
        }
        return randomNumber;
    }

    public boolean checkCode(HashMap<String,String> map)
    {
        flag = false;
        Call<Void> call = retrofitInterace.checkValidPartyCode(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code()==200)
                {
                    flag = true;
                }
                else
                    flag = false;
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                flag = false;
            }
        });
        return flag;
    }
    /// GENERATE CODE END

}