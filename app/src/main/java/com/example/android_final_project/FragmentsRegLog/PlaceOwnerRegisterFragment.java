package com.example.android_final_project.FragmentsRegLog;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android_final_project.Activities.RegisterActivity;
import com.example.android_final_project.R;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlaceOwnerRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaceOwnerRegisterFragment extends Fragment {

    private String placeName;
    private String placeAddress;
    private String placeType;
    private RegisterActivity registerActivity;

    private boolean boolPlaceName = false;
    private boolean boolPlaceAddress = false;
    private HashMap<String,String> map;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlaceOwnerRegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlaceOwnerRegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlaceOwnerRegisterFragment newInstance(String param1, String param2) {
        PlaceOwnerRegisterFragment fragment = new PlaceOwnerRegisterFragment();
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
        View view = inflater.inflate(R.layout.fragment_place_owner_register, container, false);

        //Filling genreSpinner in frament_d_j_register fragment
        Context currContext = container.getContext(); //Get current context
        Spinner placeTypeSpinner = (Spinner) view.findViewById(R.id.regPlaceType);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(currContext, R.array.placeTypes, android.R.layout.simple_spinner_item);

        // Set layout to use for the list
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        placeTypeSpinner.setAdapter(staticAdapter);

        registerActivity = (RegisterActivity) getActivity();

        EditText editTextTextRegPlacePlaceName = view.findViewById(R.id.editTextTextRegPlacePlaceName);
        EditText editTextTextRegPlacePlaceAddress = view.findViewById(R.id.editTextTextRegPlacePlaceAddress);
        Spinner spinnerRegPlaceType = view.findViewById(R.id.regPlaceType);

        Button buttonRegOwner = view.findViewById(R.id.buttonRegOwner);
        buttonRegOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isPlaceOwnerRegDetailsValid()==true)
                {
                    placeName = editTextTextRegPlacePlaceName.getText().toString();
                    placeType = spinnerRegPlaceType.getSelectedItem().toString();
                    placeAddress = editTextTextRegPlacePlaceAddress.getText().toString();

                    registerActivity.registerPlaceOwnerUser(placeName,placeType,placeAddress);
                }
                else
                {
                    Toast.makeText(getContext(),"Please verify registration form",Toast.LENGTH_LONG).show();
                }
            }
        });

        editTextTextRegPlacePlaceName.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus) {
                placeName = editTextTextRegPlacePlaceName.getText().toString();
                map = new HashMap<>();
                map.put("placeName",placeName);


                if(placeName.isEmpty())
                {
                    editTextTextRegPlacePlaceName.setTextColor(Color.BLACK);
                    editTextTextRegPlacePlaceName.setHintTextColor(Color.RED);
                    editTextTextRegPlacePlaceName.setError("Missing place name!");
                    boolPlaceName = false;
                }
                else if((registerActivity.checkFreePlaceName(map)))
                {
                    editTextTextRegPlacePlaceName.setHintTextColor(Color.BLACK);
                    editTextTextRegPlacePlaceName.setTextColor(Color.RED);
                    editTextTextRegPlacePlaceName.setError("Name already taken!");
                    boolPlaceName = false;
                }
                else
                {
                    editTextTextRegPlacePlaceName.setTextColor(Color.BLACK);
                    editTextTextRegPlacePlaceName.setHintTextColor(Color.BLACK);
                    boolPlaceName = true;
                    editTextTextRegPlacePlaceName.setError(null);
                }
            }
        });

        editTextTextRegPlacePlaceAddress.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus) {
                placeAddress = editTextTextRegPlacePlaceAddress.getText().toString();

                if (placeAddress.isEmpty())
                {
                    editTextTextRegPlacePlaceAddress.setHintTextColor(Color.RED);
                    editTextTextRegPlacePlaceAddress.setError("Missing place address!");
                    boolPlaceAddress = false;
                }
                else
                {
                    editTextTextRegPlacePlaceAddress.setError(null);
                    boolPlaceAddress = true;
                }
            }
        });

        return view;
    }
    public boolean isPlaceOwnerRegDetailsValid()
    {
        if(boolPlaceAddress==true && boolPlaceName==true && registerActivity.isRegDetailsValid()==true)
        {
            return true;
        }
        return false;
    }
}