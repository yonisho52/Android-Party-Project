package com.example.android_final_project.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.android_final_project.ObjectsClasses.Event;
import com.example.android_final_project.FragmentsMain.NoEventTodayFragment;
import com.example.android_final_project.FragmentsMain.NowEventMainFragment;
import com.example.android_final_project.FragmentsMain.PartyCodeFragment;
import com.example.android_final_project.ObjectsClasses.Message;
import com.example.android_final_project.R;
import com.example.android_final_project.RetrofitInterface;
import com.example.android_final_project.ObjectsClasses.Survey;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private BottomNavigationView bottomNavigationView;
    private SharedPreferences sharedPreferencesLogin;
    private SharedPreferences sharedPreferencesParty;
    private String type;
    private String email;
    private Boolean flag;
    private String name;

    private Event nowEvent;
    private Survey survey;

    private List<Message> messages;

    private NavHostFragment navHostFragment;


    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASEURL="http://10.0.2.2:3000";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        sharedPreferencesLogin = getSharedPreferences("Login", MODE_PRIVATE);
        type = sharedPreferencesLogin.getString("type","REGULAR");
        email = sharedPreferencesLogin.getString("keyUser",null);
        name = sharedPreferencesLogin.getString("name",null);

        sharedPreferencesParty = getSharedPreferences("Party", MODE_PRIVATE);

//        *****START - MAIN MENU VIEW*****
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

//        USER TYPE MENU VIEW
        if(type.equals("REGULAR"))
            bottomNavigationView.inflateMenu(R.menu.regular_user_main_menu);
        else {
            bottomNavigationView.inflateMenu(R.menu.dj_owner_main_menu);
        }

        navController = Navigation.findNavController(findViewById(R.id.fragment));

        //Titles for each page
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.calendarFragment,R.id.savedEventsMainFragment,R.id.advertiseMainFragment,
                R.id.editProfileMainFragment,R.id.eventSummaryMainFragment,R.id.nowEventMainFragment,R.id.partyCodeMainFragment).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        //NAVIGATION CLICK
        navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());
//        *****END - MAIN MENU VIEW*****


    }

    public boolean voted()
    {
        if(sharedPreferencesParty.getString("votedSurvey", null) != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void votedAddSharedPref()
    {
        SharedPreferences.Editor editor = sharedPreferencesParty.edit();
        editor.putString("votedSurvey", "true");
        editor.apply();
    }

    public boolean djRated()
    {
        if(sharedPreferencesParty.getString("djRated", null) != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public void djRatedAddSharedPref()
    {
        SharedPreferences.Editor editor = sharedPreferencesParty.edit();
        editor.putString("djRated", "true");
        editor.apply();
    }

    public void onwerRatedAddSharedPref()
    {
        SharedPreferences.Editor editor = sharedPreferencesParty.edit();
        editor.putString("ownerRated", "true");
        editor.apply();
    }

    public void eventRatedAddSharedPref()
    {
        SharedPreferences.Editor editor = sharedPreferencesParty.edit();
        editor.putString("eventRated", "true");
        editor.apply();
    }

    public boolean ownerRated()
    {
        if(sharedPreferencesParty.getString("ownerRated", null) != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean eventRated()
    {
        if(sharedPreferencesParty.getString("eventRated", null) != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void joinParty(Event event)
    {
        nowEvent = event;
        navHostFragment.getChildFragmentManager().beginTransaction().replace(R.id.fragment ,new NowEventMainFragment()).commit();
    }

    public void noEventsToday()
    {
        navHostFragment.getChildFragmentManager().beginTransaction().replace(R.id.fragment ,new NoEventTodayFragment()).commit();
    }

    public Event getNowEvent()
    {
        return nowEvent;
    }


    public void insertParty(String code)
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy");
        String date = format.format(calendar.getTime());
        SharedPreferences.Editor editor = sharedPreferencesParty.edit();
        editor.putString("partyCode", code);
        editor.putString("Date", date);
        //editor.putString("EndTime", EndTime);
        editor.apply();
    }

    //True == Also kick
    public void kickOfParty(Boolean flag)
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy");
        String date = format.format(calendar.getTime());

        if(sharedPreferencesParty.getString("Date", null)!=null) {
            String s = sharedPreferencesParty.getString("Date", null);
            if (s.compareTo(date) != 0 || flag) {
                sharedPreferencesParty = getSharedPreferences("Party", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferencesParty.edit();
                editor.clear();
                editor.commit();

                if (getType().equals("REGULAR")) {
                    navHostFragment.getChildFragmentManager().beginTransaction().replace(R.id.fragment, new PartyCodeFragment()).commit();
                } else {
                    navHostFragment.getChildFragmentManager().beginTransaction().replace(R.id.fragment, new NoEventTodayFragment()).commit();
                }
            }
        }
    }



    public String getType() {return type;}
    public String getEmail() { return  email;}
    public String getName() { return  name;}


    //Add event to DB function
    public void createEvent(Event event)
    {
        HashMap<String,String> map = new HashMap<>();
        map.put("partyCode",generateCode());
        map.put("createdBy",event.getCreatedBy());
        map.put("eventName",event.getEventName());
        map.put("eventDate",event.getEventDate());
        map.put("placeName",event.getPlaceName());
        map.put("whosPlayingName",event.getWhosPlayingName());
        map.put("whosPlaying",event.getWhosPlaying());
        map.put("startTime",event.getStartTime());
        map.put("endTime",event.getEndTime());
        map.put("eventRating","0");
        map.put("numOfRates","0");

        Call<Void> call = retrofitInterface.executeAddEvent(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==200)
                {
                    //Toast.makeText(currContext,"Successfully created new event",Toast.LENGTH_LONG).show();
                    //eventID++;
                }
                else if(response.code()==400)
                {
                    //Toast.makeText(currContext,"Cannot create another event on the same date",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                //Toast.makeText(currContext,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    // GENERATE CODE START
    public String generateCode()
    {
        Random r = new Random();
        String randomNumber = String.format("%05d", (Object) Integer.valueOf(r.nextInt(10001)));
        HashMap<String,String> map = new HashMap<String, String>();
        map.put("partyCode",randomNumber);

        while (checkCode(map))
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
        Call<Void> call = retrofitInterface.checkValidPartyCode(map);
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
}