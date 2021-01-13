package com.example.android_final_project.FragmentsMain;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_final_project.Activities.LoginActivity;
import com.example.android_final_project.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    BottomNavigationView bottomNavigationView;
    NavController navController;
    FragmentManager fragmentManager;


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

        bottomNavigationView = view.findViewById(R.id.bottomNavigationView1);

        navController = Navigation.findNavController(view.findViewById(R.id.fragment3));

        //Titles for each page
        //AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.regularRegFragment,R.id.djRegFragment,R.id.placeOwnerRegFragment).build();
        //NavigationUI.setupActionBarWithNavController(, navController, appBarConfiguration);

//        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        //NAVIGATION CLICK
        NavHostFragment navHostFragment = (NavHostFragment)getChildFragmentManager().findFragmentById(R.id.fragment3);
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());

        // Inflate the layout for this fragment
        return view;
    }
}