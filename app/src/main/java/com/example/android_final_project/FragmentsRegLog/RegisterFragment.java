package com.example.android_final_project.FragmentsRegLog;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android_final_project.Activities.LoginActivity;
import com.example.android_final_project.R;
import com.example.android_final_project.RetrofitInterace;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    BottomNavigationView bottomNavigationView;
    NavController navController;
    FragmentManager fragmentManager;

    private Retrofit retrofit;
    private RetrofitInterace retrofitInterace;
    private String BASEURL="http://10.0.2.2:3000";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        bottomNavigationView = view.findViewById(R.id.bottomNavigationViewRegister);
        navController = Navigation.findNavController(view.findViewById(R.id.registerTypeFragment));

        //new new
        retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterace = retrofit.create(RetrofitInterace.class);
        Context currContext = container.getContext();
        //new new

        //NAVIGATION CLICK
        NavHostFragment navHostFragment = (NavHostFragment)getChildFragmentManager().findFragmentById(R.id.registerTypeFragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());

        // Inflate the layout for this fragment

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
            }
        };

        ///new
        EditText editTextEmail = view.findViewById(R.id.editTextRegEmail);
        EditText editTextFirstName = view.findViewById(R.id.editTextRegFirstName);
        EditText editTextLastName = view.findViewById(R.id.editTextRegLastName);
        EditText editTextPassword = view.findViewById(R.id.editTextRegPW);


        Button register = view.findViewById(R.id.buttonRegRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> map = new HashMap<>();
                map.put("email",editTextEmail.getText().toString());
                map.put("firstName",editTextFirstName.getText().toString());
                map.put("lastName",editTextLastName.getText().toString());
                map.put("password",editTextPassword.getText().toString());



                Call<Void> call = retrofitInterace.executeRegUser(map);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code()==200)
                        {
                            Toast.makeText(currContext,"Register secusess",Toast.LENGTH_LONG).show();
                        }
                        else if(response.code()==400)
                        {
                            Toast.makeText(currContext,"allready regiester",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(currContext,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        /// new

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        return view;
    }

}