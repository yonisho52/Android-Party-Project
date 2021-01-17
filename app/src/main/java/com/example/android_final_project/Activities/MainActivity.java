package com.example.android_final_project.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.android_final_project.R;
import com.example.android_final_project.UserTypeClasses.userDJ;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    NavController navController;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.fragment_calendar);


//        *****START - MAIN MENU VIEW*****
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

//        USER TYPE MENU VIEW
//        if(userType == regular user )
        bottomNavigationView.inflateMenu(R.menu.regular_user_main_menu);
//        else
//        bottomNavigationView.inflateMenu(R.menu.dj_user_main_menu);


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
}