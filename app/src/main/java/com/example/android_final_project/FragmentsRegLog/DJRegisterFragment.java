package com.example.android_final_project.FragmentsRegLog;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.android_final_project.Activities.LoginActivity;
import com.example.android_final_project.R;
import com.example.android_final_project.UserTypeClasses.RegularUser;
import com.example.android_final_project.UserTypeClasses.UserDJ;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DJRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DJRegisterFragment extends Fragment {

    private String[] profile;
    private int dateOfBirth;
    private List<String> placesCanBeFound = new ArrayList<String>();;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public DJRegisterFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DJRegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DJRegisterFragment newInstance(String param1, String param2) {
        DJRegisterFragment fragment = new DJRegisterFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_d_j_register, container, false);

        //Filling genreSpinner in frament_d_j_register fragment
        Context currContext = container.getContext(); //Get current context
        Spinner genreSpinner = (Spinner) view.findViewById(R.id.regDjPlayingGenreList);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(currContext, R.array.djGenres, android.R.layout.simple_spinner_item);

        // Set layout to use for the list
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        genreSpinner.setAdapter(staticAdapter);

        LoginActivity loginActivity = (LoginActivity) getActivity();

        EditText editTextStageName = view.findViewById(R.id.editTextRegDjStagename);
        EditText editTextSpotify = view.findViewById(R.id.editTextRegDjSpotifyLink);
        EditText editTextApple = view.findViewById(R.id.editTextRegDjAppleMusicLink);
        EditText editTextYouTube = view.findViewById(R.id.editTextRegDjYoutubeLink);
        Spinner  spinnerPlayingGenreList = view.findViewById(R.id.regDjPlayingGenreList);

        dateOfBirth = Integer.parseInt("30");

        placesCanBeFound.add("need to added places First");

        Button register = view.findViewById(R.id.buttonRegDj);
        register.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                profile = loginActivity.getRegisterProfile();

                UserDJ userDJ = new UserDJ(profile[0],profile[1],profile[2],profile[3],editTextStageName.getText().toString(),
                        spinnerPlayingGenreList.getSelectedItem().toString(),editTextYouTube.getText().toString(),
                        editTextSpotify.getText().toString(), editTextApple.getText().toString(), dateOfBirth, placesCanBeFound);

                loginActivity.registerDjUser(userDJ);
            }
        });

        return view;
    }

}