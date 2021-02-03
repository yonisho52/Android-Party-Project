package com.example.android_final_project.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android_final_project.R;
import com.example.android_final_project.RetrofitInterace;
import com.example.android_final_project.UserTypeClasses.PlaceOwner;
import com.example.android_final_project.UserTypeClasses.RegularUser;
import com.example.android_final_project.UserTypeClasses.UserDJ;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private FragmentManager fragmentManager;

    private String email = null;
    private String firstName = null;
    private String lastName = null;
    private String password = null;

    String[] generalProfile;

    private Boolean boolEmail = false;
    private Boolean boolFirstName = false;
    private Boolean boolLastName = false;
    private Boolean boolPassword = false;

    private Retrofit retrofit;
    private RetrofitInterace retrofitInterace;
    private String BASEURL="http://10.0.2.2:3000";

    Boolean djStageNameFree = false;
    Boolean placeNameFree = false;


    FragmentTransaction fragmentTransaction;

    private String[] profile = new String[4];

    private HashMap<String,String> map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        bottomNavigationView = findViewById(R.id.bottomNavigationViewRegister);
        navController = Navigation.findNavController(findViewById(R.id.registerTypeFragment));

        retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterace = retrofit.create(RetrofitInterace.class);

        //NAVIGATION CLICK
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.registerTypeFragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());

        EditText editTextEmail = findViewById(R.id.editTextRegEmail);
        EditText editTextFirstName = findViewById(R.id.editTextRegFirstName);
        EditText editTextLastName = findViewById(R.id.editTextRegLastName);
        EditText editTextPassword = findViewById(R.id.editTextRegPW);

        editTextEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus) {
                email = editTextEmail.getText().toString();
                setEmail(email);
                if (email.equals("")) {
                    editTextEmail.setHintTextColor(R.color.red);
                    boolEmail = false;
                } else {
                    boolEmail = true;
                }
            }
        });

        editTextFirstName.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus) {
                firstName = editTextFirstName.getText().toString();
                setFirstName(firstName);
                if (firstName.equals("")) {
                    editTextFirstName.setHintTextColor(R.color.red);
                    boolFirstName = false;
                } else {
                    boolFirstName = true;
                }
            }
        });

        editTextLastName.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus) {
                lastName = editTextLastName.getText().toString();
                setLastName(lastName);
                if (lastName.equals("")) {
                    editTextLastName.setHintTextColor(R.color.red);
                    boolLastName = false;
                } else {
                    boolLastName = true;
                }
            }
        });

        editTextPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus) {
                password = editTextPassword.getText().toString();
                setPass(password);
                if (password.equals("")) {
                    editTextPassword.setHintTextColor(R.color.red);
                    boolPassword = false;
                } else {
                    boolPassword = true;
                }
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void registerRegularUser(List favouriteGenres, int age)
    {
        RegularUser regularUser = new RegularUser(profile[0],profile[1],profile[2],profile[3],favouriteGenres,age);

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
    public void registerDjUser(String stageName, String playingGenre, String youtube, String spotify, String apple,
                               int dateOfBirth, List placesCanBeFound)
    {
        UserDJ userDJ = new UserDJ(profile[0],profile[1],profile[2],profile[3],stageName,
                playingGenre,youtube, spotify, apple, dateOfBirth, placesCanBeFound);

        map = new HashMap<>();
        map.put("type","DJ");
        map.put("email",userDJ.getEmail());
        map.put("firstName",userDJ.getFirstName());
        map.put("lastName",userDJ.getLastName());
        map.put("password",userDJ.getPassword());
        map.put("stageName",userDJ.getStageName());
        map.put("playingGenre",userDJ.getPlayingGenre());
        map.put("youtubeLink",userDJ.getYoutubeLink());
        map.put("spotifyLink",userDJ.getSpotifyLink());
        map.put("appleMusicLink",userDJ.getAppleMusicLink());
        map.put("age",String.valueOf(userDJ.getAge()));
        map.put("placesCanBeFound",userDJ.getPlacesCanBeFound().stream().map(n -> String.valueOf(n)).collect(Collectors.joining(", ", "", "")).toString());
        map.put("rating",String.valueOf(userDJ.getRating()));

        intoDataBase(map,"executeDjUser");
    }

    public void registerPlaceOwnerUser(String placeName, String placeType, String placeAddress)
    {
        PlaceOwner placeOwner = new PlaceOwner(profile[0], profile[1], profile[2], profile[3], placeName, placeType, placeAddress);

        map = new HashMap<>();
        map.put("type","PLACE-OWNER");
        map.put("email",placeOwner.getEmail());
        map.put("firstName",placeOwner.getFirstName());
        map.put("lastName",placeOwner.getLastName());
        map.put("password",placeOwner.getPassword());
        map.put("placeName",placeOwner.getPlaceName());
        map.put("placeType",placeOwner.getPlaceType());
        map.put("placeAddress",placeOwner.getPlaceAddress());
        map.put("rating",String.valueOf(placeOwner.getPlaceRating()));

        intoDataBase(map,"executeOwnerUser");
    }

    public void intoDataBase(HashMap<String,String> map, String type)
    {
        Call<Void> call = null;

        if(type.equals("executeRegUser")) { call = retrofitInterace.executeRegUser(map); }
        else if(type.equals("executeDjUser")) { call = retrofitInterace.executeDjUser(map); }
        else if(type.equals("executeOwnerUser")) { call = retrofitInterace.executeOwnerUser(map); }
        else { Log.d("1","intoDatabase function problem"); }

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==200)
                {
                    Toast.makeText(RegisterActivity.this,"Register secusess",Toast.LENGTH_LONG).show();
                }
                else if(response.code()==400)
                {
                    Toast.makeText(RegisterActivity.this,"allready regiester",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean checkFreeDjStageName(HashMap<String,String> map)
    {
        Call<Void> call = retrofitInterace.executeCheckDjName(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==200)
                {
                    Toast.makeText(RegisterActivity.this,"Dj Name Free",Toast.LENGTH_LONG).show();
                    djStageNameFree = true;
                }
                else if(response.code()==400)
                {
                    Toast.makeText(RegisterActivity.this,"Dj Name notfree",Toast.LENGTH_LONG).show();
                    djStageNameFree = false;
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                djStageNameFree = false;
            }
        });
        return djStageNameFree;
    }

    public boolean checkFreePlaceName(HashMap<String,String> map)
    {
        Call<Void> call = retrofitInterace.executeCheckPlaceName(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==200)
                {
                    Toast.makeText(RegisterActivity.this,"Place Name Free",Toast.LENGTH_LONG).show();
                   placeNameFree = true;
                }
                else if(response.code()==400)
                {
                    Toast.makeText(RegisterActivity.this,"Place Name notfree",Toast.LENGTH_LONG).show();
                    placeNameFree = false;
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                placeNameFree = false;
            }
        });
        return placeNameFree;
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

}