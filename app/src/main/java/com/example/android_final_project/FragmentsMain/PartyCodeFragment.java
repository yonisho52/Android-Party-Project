package com.example.android_final_project.FragmentsMain;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android_final_project.Activities.MainActivity;
import com.example.android_final_project.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PartyCodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartyCodeFragment extends Fragment {

    public SharedPreferences sharedPreferencesParty;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PartyCodeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PartyCodeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PartyCodeFragment newInstance(String param1, String param2) {
        PartyCodeFragment fragment = new PartyCodeFragment();
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
        View view = inflater.inflate(R.layout.fragment_party_code_main, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();

        sharedPreferencesParty = getActivity().getSharedPreferences("Party", Context.MODE_PRIVATE);

        if(sharedPreferencesParty.getString("partyCode", null) != null) // && Valid start time && valid end time
        {
            mainActivity.joinParty();
            return null;
        }


        Button joinParty = view.findViewById(R.id.buttonJoinParty);
        joinParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if the code is right
                // check if the party happing now
                // if not send error
            }
        });


        return view;
    }
}