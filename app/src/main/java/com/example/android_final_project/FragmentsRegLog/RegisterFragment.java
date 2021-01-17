package com.example.android_final_project.FragmentsRegLog;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.android_final_project.Activities.LoginActivity;
import com.example.android_final_project.R;
import com.example.android_final_project.RetrofitInterace;
import com.example.android_final_project.UserTypeClasses.GeneralUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

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

        LoginActivity loginActivity = (LoginActivity) getActivity();


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


        editTextEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    email = editTextEmail.getText().toString();
                    loginActivity.setEmail(email);
                    if(email.equals(""))
                    {
                        editTextEmail.setHintTextColor(R.color.red);
                        boolEmail=false;
                    }
                    else
                    {
                        boolEmail=true;
                    }
                }
            }
        });

        editTextFirstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    firstName = editTextFirstName.getText().toString();
                    loginActivity.setFirstName(firstName);
                    if(firstName.equals(""))
                    {
                        editTextFirstName.setHintTextColor(R.color.red);
                        boolFirstName=false;
                    }
                    else
                    {
                        boolFirstName=true;
                    }
                }
            }
        });

        editTextLastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    lastName = editTextLastName.getText().toString();
                    loginActivity.setLastName(lastName);
                    if(lastName.equals(""))
                    {
                        editTextLastName.setHintTextColor(R.color.red);
                        boolLastName=false;
                    }
                    else
                    {
                        boolLastName=true;
                    }
                }
            }
        });

        editTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    password = editTextPassword.getText().toString();
                    loginActivity.setPass(password);
                    if(password.equals(""))
                    {
                        editTextPassword.setHintTextColor(R.color.red);
                        boolPassword=false;
                    }
                    else
                    {
                        boolPassword=true;
                    }
                }
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        return view;
    }

}