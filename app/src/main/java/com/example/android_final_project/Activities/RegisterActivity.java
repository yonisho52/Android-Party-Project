package com.example.android_final_project.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android_final_project.R;
import com.example.android_final_project.RetrofitInterface;
import com.example.android_final_project.UserTypeClasses.PlaceOwner;
import com.example.android_final_project.UserTypeClasses.RegularUser;
import com.example.android_final_project.UserTypeClasses.UserDJ;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    private String email = null;
    private String firstName = null;
    private String lastName = null;
    private String password = null;
    private String confirmPassword = null;

    private Boolean boolEmail = false;
    private Boolean boolFirstName = false;
    private Boolean boolLastName = false;
    private Boolean boolPassword = false;
    private Boolean boolConfirmPassword = false;

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASEURL="http://10.0.2.2:3000";

    private Boolean djStageNameFree = false;
    private Boolean placeNameFree = false;

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
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        //NAVIGATION CLICK
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.registerTypeFragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());

        EditText editTextEmail = findViewById(R.id.editTextRegEmail);
        EditText editTextFirstName = findViewById(R.id.editTextRegFirstName);
        EditText editTextLastName = findViewById(R.id.editTextRegLastName);
        EditText editTextPassword = findViewById(R.id.editTextRegPW);
        EditText editTextConfirmPassword = findViewById(R.id.editTextRegConfirmPW);


        editTextEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus) {
                email = editTextEmail.getText().toString();
                setEmail(email);
                 if(!isValidEmail(email))
                {
                    if(email.isEmpty())
                    {
                        editTextEmail.setError("Field cannot be empty");
                        editTextEmail.setHintTextColor(Color.RED);
                    }
                    else
                    {
                        editTextEmail.setError("Invalid email address");
                        editTextEmail.setTextColor(Color.RED);
                    }
                    boolEmail = false;
                } else {
                    boolEmail = true;
                    editTextEmail.setError(null);
                    editTextEmail.setTextColor(Color.BLACK);

                    editTextEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_spellcheck_24px,0);

                }
            }
        });

        editTextFirstName.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus) {
                firstName = editTextFirstName.getText().toString();
                setFirstName(firstName);
                if (firstName.isEmpty())
                {
                    editTextFirstName.setHintTextColor(Color.RED);
                    editTextFirstName.setError("Missing first name!");
                    boolFirstName = false;
                }
                else if(!isValidName(firstName))
                {
                    editTextFirstName.setTextColor(Color.RED);
                    editTextFirstName.setError("Name must start with a capital letter and contain letters only");
                }
                else {
                    editTextFirstName.setError(null);
                    editTextFirstName.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_spellcheck_24px,0);
                    editTextFirstName.setTextColor(Color.BLACK);
                    boolFirstName = true;
                }
            }
        });

        editTextLastName.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus) {
                lastName = editTextLastName.getText().toString();
                setLastName(lastName);
                if (lastName.isEmpty())
                {
                    editTextLastName.setHintTextColor(Color.RED);
                    editTextLastName.setError("Missing last name!");
                    boolLastName = false;
                }
                else if(!isValidName(lastName))
                {
                    editTextLastName.setTextColor(Color.RED);
                    editTextLastName.setError("Last name must start with a capital letter and contain letters only");
                }
                else
                    {
                        editTextLastName.setError(null);
                        editTextLastName.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_spellcheck_24px,0);
                        editTextLastName.setTextColor(Color.BLACK);
                        boolLastName = true;
                    }
            }
        });

        editTextPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus) {
                password = editTextPassword.getText().toString();
                setPass(password);
                if (password.isEmpty())
                {
                    editTextPassword.setHintTextColor(Color.RED);
                    editTextPassword.setError("Missing password");
                    boolPassword = false;
                }
                else if(!isValidPassword(password))
                    {
                       // editTextPassword.setError("Password must contain at least the following: \nOne capital letter A-Z\nOne number 0-9\nOne symbol: !@#$%^&+=\nAt least 8 characters long");
                        editTextPassword.setError("Password must contain the following:\n" +
                                "At least 1 digit\n" +
                                "At least 1 upper case letter\n" +
                                "At least 1 lower case letter'\n" +
                                "At least 1 symbol: !@#$%^&*+=\n" +
                                "No white spaces\n" +
                                "Length must be 6-14 characters");
                    }
                else
                    {
                        boolPassword = true;
                        editTextPassword.setError(null);
                        editTextPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_spellcheck_24px,0);
                    }
            }
        });

        editTextConfirmPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus) {
                confirmPassword = editTextConfirmPassword.getText().toString();
                if (password.isEmpty())
                {
                    editTextConfirmPassword.setHintTextColor(Color.RED);
                    editTextConfirmPassword.setError("Confirm password");
                    boolConfirmPassword=false;
                }
                else if(!confirmPassword(password,confirmPassword ))
                {
                    editTextConfirmPassword.setError("Password does not match");
                    boolConfirmPassword=false;
                }
                else
                {
                    editTextConfirmPassword.setError(null);
                    editTextConfirmPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_spellcheck_24px,0);
                    boolConfirmPassword=true;
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void registerRegularUser(List favouriteGenres, int age)
    {
        RegularUser regularUser = new RegularUser(profile[0],profile[1],profile[2],profile[3],favouriteGenres,age);

        if(isRegDetailsValid()==true) {


            map = new HashMap<>();
            map.put("type", "REGULAR");
            map.put("email", regularUser.getEmail());
            map.put("firstName", regularUser.getFirstName());
            map.put("lastName", regularUser.getLastName());
            map.put("password", regularUser.getPassword());
            map.put("favouriteGenres", regularUser.getFavouriteGenres().stream().map(n -> String.valueOf(n)).collect(Collectors.joining(", ", "", "")).toString());
            map.put("age", String.valueOf(regularUser.getAge()));

            intoDataBase(map, "executeRegUser");
        }
        else
        {
            Toast.makeText(RegisterActivity.this,"Please verify registration form" ,Toast.LENGTH_LONG).show();
        }
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
        map.put("numOfRates",String.valueOf(userDJ.getNumOfRates()));

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
        map.put("numOfRates",String.valueOf(placeOwner.getNumOfRates()));

        intoDataBase(map,"executeOwnerUser");
    }

    public void intoDataBase(HashMap<String,String> map, String type)
    {
        Call<Void> call = null;

        if(type.equals("executeRegUser")) { call = retrofitInterface.executeRegUser(map); }
        else if(type.equals("executeDjUser")) { call = retrofitInterface.executeDjUser(map); }
        else if(type.equals("executeOwnerUser")) { call = retrofitInterface.executeOwnerUser(map); }
        else { Log.d("1","intoDatabase function problem"); }

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==200)
                {
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(RegisterActivity.this,"Registered successfully",Toast.LENGTH_LONG).show();
                }
                else if(response.code()==400)
                {
                    Toast.makeText(RegisterActivity.this,"User with the same email already exists",Toast.LENGTH_LONG).show();
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
        Call<Void> call = retrofitInterface.executeCheckDjName(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==200)
                {
                    //Toast.makeText(RegisterActivity.this,"Dj Name Free",Toast.LENGTH_LONG).show();
                    djStageNameFree = true;
                }
                else if(response.code()==400)
                {
                    Toast.makeText(RegisterActivity.this,"DJ Name is already taken",Toast.LENGTH_LONG).show();
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
        Call<Void> call = retrofitInterface.executeCheckPlaceName(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==200)
                {
                    //Toast.makeText(RegisterActivity.this,"Place Name Free",Toast.LENGTH_LONG).show();
                   placeNameFree = true;
                }
                else if(response.code()==400)
                {
                    Toast.makeText(RegisterActivity.this,"Place name is already taken",Toast.LENGTH_LONG).show();
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

    public boolean isValidEmail(String email)
    {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public boolean isValidPassword(String pw)
    {
        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^" +
                "(?=.*[0-9])" +                 //at least 1 digit
                "(?=.*[a-z])" +                 //at least 1 lower case letter
                "(?=.*[A-Z])" +                 //at least 1 upper case letter
                "(?=.*[!@#$%^&*+=.])"+          //at least 1 special character
                "(?=\\S+$)" +                   //no white spaces
                ".{6,14}" +                     //length between 6-14
                "$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(pw);

        return matcher.matches();
    }

    public boolean isValidName(String name)
    {
        Pattern pattern;
        Matcher matcher;

        final String NAME_PATTERN = ("[A-Z][a-z]*");

        pattern = Pattern.compile(NAME_PATTERN);
        matcher = pattern.matcher(name);

        return matcher.matches();
    }

    public boolean confirmPassword(String password, String confirmation)
    {
        if(password.equals(confirmation))
            return true;
        else return false;
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

    public boolean isRegDetailsValid()
    {
        if(boolEmail && boolFirstName && boolLastName && boolPassword && boolConfirmPassword)
            return true;
        else return false;
    }

    public boolean  isDjRegDetailsValid()
    {
        return false;
    }

    public void enableDjRegisterButton()
    {
        Button regDjButton = findViewById(R.id.buttonRegDj);
    }

}