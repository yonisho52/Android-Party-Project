package com.example.android_final_project.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.android_final_project.FragmentsRegLog.LoginFragment;
import com.example.android_final_project.FragmentsRegLog.RegisterFragment;
import com.example.android_final_project.R;
import com.example.android_final_project.RetrofitInterace;
import com.example.android_final_project.UserTypeClasses.PlaceOwner;
import com.example.android_final_project.UserTypeClasses.RegularUser;
import com.example.android_final_project.UserTypeClasses.UserDJ;

import java.util.HashMap;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

public class LoginActivity extends AppCompatActivity
{
    NavController navController;
    private FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private String[] profile = new String[4];

    private HashMap<String,String> map;
    private Retrofit retrofit;
    private RetrofitInterace retrofitInterace;
    private String BASEURL="http://10.0.2.2:3000";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        fragmentManager = getSupportFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.logRegFrame, new LoginFragment()).commit();

        //navController = Navigation.findNavController(findViewById(R.id.logRegFrame));
        getSupportActionBar().hide();

        retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterace = retrofit.create(RetrofitInterace.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void registerRegularUser(RegularUser regularUser)
    {
        map = new HashMap<>();
        map.put("type","REGULAR");
        map.put("email",regularUser.getEmail());
        map.put("firstName",regularUser.getFirstName());
        map.put("lastName",regularUser.getLastName());
        map.put("password",regularUser.getPassword());
        map.put("favouriteGenres",regularUser.getFavouriteGenres().stream().map(n -> String.valueOf(n)).collect(Collectors.joining(", ", "", "")).toString());
        map.put("age",String.valueOf(regularUser.getAge()));

        intoDataBase(map,"executeRegUser");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void registerDjUser(UserDJ userDJ)
    {
        map = new HashMap<>();
        map.put("type","DJ");
        map.put("email",userDJ.getEmail());
        map.put("firstName",userDJ.getFirstName());
        map.put("lastName",userDJ.getLastName());
        map.put("password",userDJ.getPassword());
        map.put("stageName",userDJ.getStageName());
        map.put("playingGenre",userDJ.getPlayingGenre());
        map.put("youtubeLink",userDJ.getYoutubeLink().toString());
        map.put("spotifyLink",userDJ.getSpotifyLink().toString());
        map.put("appleMusicLink",userDJ.getAppleMusicLink().toString());
        map.put("age",String.valueOf(userDJ.getAge()));
        map.put("placesCanBeFound",userDJ.getPlacesCanBeFound().stream().map(n -> String.valueOf(n)).collect(Collectors.joining(", ", "", "")).toString());

        intoDataBase(map,"executeDjUser");
    }

    public void registerPlaceOwnerUser(PlaceOwner placeOwner)
    {
        map = new HashMap<>();
        map.put("type","PLACE-OWNER");
        map.put("email",placeOwner.getEmail());
        map.put("firstName",placeOwner.getFirstName());
        map.put("lastName",placeOwner.getLastName());
        map.put("password",placeOwner.getPassword());
        map.put("placeName",placeOwner.getPlaceName());
        map.put("placeType",placeOwner.getPlaceType());
        map.put("placeAddress",placeOwner.getPlaceAddress());

        intoDataBase(map,"executeOwnerUser");
    }

    public void intoDataBase(HashMap<String,String> map, String type)
    {
        Call<Void> call = null;

        if(type.equals("executeRegUser")) { call = retrofitInterace.executeRegUser(map); }
        if(type.equals("executeDjUser")) { call = retrofitInterace.executeDjUser(map); }
        if(type.equals("executeOwnerUser")) { call = retrofitInterace.executeOwnerUser(map); }
        else {
            ///Error SomeHow
        }

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==200)
                {
                    Toast.makeText(LoginActivity.this,"Register secusess",Toast.LENGTH_LONG).show();
                }
                else if(response.code()==400)
                {
                    Toast.makeText(LoginActivity.this,"allready regiester",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(LoginActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }



    public void setEmail(String email){
        profile[2] = email;
    }
    public void setFirstName(String firstName){
        profile[0] = firstName;
    }
    public void setLastName(String lastName){
        profile[1] = lastName;
    }
    public void setPass(String pass){
        profile[3] = pass;
    }

    public String[] getRegisterProfile(){
        return profile;
    }


//    @Override
//    public boolean onSupportNavigateUp() {
//        navController = Navigation.findNavController(findViewById(R.id.logRegFrame));
//        return navController.navigateUp() || super.onSupportNavigateUp();
//    }

//    public void goToRegister(View view)
//    {
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.logRegFrame, new RegisterFragment()).addToBackStack(null).commit();
//    }
}
