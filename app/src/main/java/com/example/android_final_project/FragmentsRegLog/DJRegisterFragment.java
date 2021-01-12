package com.example.android_final_project.FragmentsRegLog;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.android_final_project.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DJRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DJRegisterFragment extends Fragment {

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

        return view;
    }

}