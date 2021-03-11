package com.example.android_final_project.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android_final_project.ObjectsClasses.LoginResult;
import com.example.android_final_project.R;
import com.example.android_final_project.RetrofitInterface;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    NavController navController;
    private FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public SharedPreferences sharedPreferences;
    private EditText editTextEmail;
    private EditText editTextPassword;

    private String[] profile = new String[4];

    private HashMap<String,String> map;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASEURL="http://10.0.2.2:3000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        editTextEmail = findViewById(R.id.editTextLoginEmailNew);
        editTextPassword = findViewById(R.id.editTextLoginPasswordNew);


        if(sharedPreferences.getString("keyUser", null) != null
                && sharedPreferences.getString("keyPass", null) != null )
        {
            // Move To MainActivity
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);

            //write on the login page the details of the user
            editTextEmail.setText(sharedPreferences.getString("keyUser",null));
            editTextPassword.setText(sharedPreferences.getString("keyPass",null));

        }

        editTextEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus) {
                editTextEmail.getText().toString();
                if(isValidEmail(editTextEmail.toString()))
                {
                        editTextEmail.setError("Invalid email address");
                        editTextEmail.setTextColor(Color.RED);

                } else
                    {
                    editTextEmail.setError(null);
                    editTextEmail.setTextColor(Color.BLACK);

                    editTextEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_spellcheck_24px,0);
                }
            }
        });
    }

    public void buttonLogin(View view) {

        String email = editTextEmail.getText().toString();
        String pass = editTextPassword.getText().toString();

        HashMap<String,String> map = new HashMap<>();
        map.put("email",email);
        map.put("password",pass);

        Call<LoginResult> call = retrofitInterface.executeLogin(map);
        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if(response.code()==200)
                {
                    Toast.makeText(LoginActivity.this,"Welcome "+email.toString(),Toast.LENGTH_LONG).show();
                    LoginResult loginResult = response.body();

                    // shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("keyUser", email);
                    editor.putString("keyPass", pass);
                    editor.putString("type", loginResult.getType());
                    if(loginResult.getType().equals("DJ"))
                    {
                        editor.putString("name", loginResult.getStageName());
                    }
                    else if(loginResult.getType().equals("PLACE-OWNER"))
                    {
                        editor.putString("name", loginResult.getPlaceName());
                    }
                    editor.apply();
                    /// end preferences

                    setContentView(R.layout.fragment_splash);
                    //Intent to MainActivity ***

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }, 2000);

                    // ***
                }
                else if(response.code()==400)
                {
                    new AlertDialog.Builder(LoginActivity.this).setTitle("Login Error!").setMessage("One or more of the login credentials you entered are incorrect").show();
                }
            }
            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Toast.makeText(LoginActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });



    }

    public void buttonRegister(View view) {

        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    public boolean isValidEmail(String email)
    {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
}