package com.example.android_final_project.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.android_final_project.FragmentsMain.LoginFragment;
import com.example.android_final_project.FragmentsMain.RegisterFragment;
import com.example.android_final_project.R;

public class LoginActivity extends AppCompatActivity
{
    private FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //setContentView(R.layout.fragment_calendar); //Delete later, only for tests

        fragmentManager = getSupportFragmentManager();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.logRegFrame, new LoginFragment()).commit();



    }


    public void goToRegister(View view)
    {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.logRegFrame, new RegisterFragment()).addToBackStack(null).commit();
    }
}