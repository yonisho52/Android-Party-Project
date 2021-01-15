package com.example.android_final_project.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.android_final_project.FragmentsRegLog.LoginFragment;
import com.example.android_final_project.FragmentsRegLog.RegisterFragment;
import com.example.android_final_project.R;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

public class LoginActivity extends AppCompatActivity
{
    NavController navController;
    private FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        fragmentManager = getSupportFragmentManager();
//
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.logRegFrame, new LoginFragment()).commit();

        //navController = Navigation.findNavController(findViewById(R.id.logRegFrame));
        getSupportActionBar().hide();
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
