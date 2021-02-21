package com.example.android_final_project.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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
            // write on the login page the details of the user
            editTextEmail.setText(sharedPreferences.getString("keyUser",null));
            editTextPassword.setText(sharedPreferences.getString("keyPass",null));

            //auto log-in
            String email = sharedPreferences.getString("keyUser",null);
            String pass = sharedPreferences.getString("keyPass",null);
        }


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
                    Toast.makeText(LoginActivity.this,"Login secusess",Toast.LENGTH_LONG).show();
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

                    //Intent to MainActivity ***
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    // ***
                }
                else if(response.code()==404)
                {
                    Toast.makeText(LoginActivity.this,"there is a problem",Toast.LENGTH_LONG).show();
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

}