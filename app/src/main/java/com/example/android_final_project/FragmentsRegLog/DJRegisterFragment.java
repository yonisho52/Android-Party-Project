package com.example.android_final_project.FragmentsRegLog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_final_project.Activities.RegisterActivity;
import com.example.android_final_project.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DJRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DJRegisterFragment extends Fragment {

    private String stageName;
    private String spotify;
    private String apple;
    private String youtube;
    private String playingGenre;
    private List<String> placesCanBeFound = new ArrayList<String>();
    private RegisterActivity registerActivity;

    private String djDOB = null;
    private String djGenre = null;
    private Boolean boolDjName = false;

    private TextView regDjDOB;
    private int age;

    private HashMap<String,String> map;

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

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            String format = new SimpleDateFormat("dd MMM YYYY").format(c.getTime());
            regDjDOB.setText(format);
            age = calculateAge(c.getTimeInMillis());
            //tvAge.setText(Integer.toString(calculateAge(c.getTimeInMillis())));
        }
    };
    int calculateAge(long date){
        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(date);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if(today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)){
            age--;
        }
        return age;
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

        registerActivity = (RegisterActivity) getActivity();

        EditText editTextStageName = view.findViewById(R.id.editTextRegDjStagename);
        EditText editTextSpotify = view.findViewById(R.id.editTextRegDjSpotifyLink);
        EditText editTextApple = view.findViewById(R.id.editTextRegDjAppleMusicLink);
        EditText editTextYouTube = view.findViewById(R.id.editTextRegDjYoutubeLink);
        Spinner  spinnerPlayingGenreList = view.findViewById(R.id.regDjPlayingGenreList);

        placesCanBeFound.add(null);

        Button register = view.findViewById(R.id.buttonRegDj);
        register.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                if(isDjRegDetailsValid()==true)
                {
                    stageName = editTextStageName.getText().toString();
                    playingGenre = spinnerPlayingGenreList.getSelectedItem().toString();
                    youtube = editTextYouTube.getText().toString();
                    spotify = editTextSpotify.getText().toString();
                    apple = editTextApple.getText().toString();

                    registerActivity.registerDjUser(stageName,playingGenre,youtube,spotify,apple,age,placesCanBeFound);
                }
                else
                {
                    Toast.makeText(getContext(),"Please verify registration form",Toast.LENGTH_LONG).show();
                }
            }
        });

        regDjDOB = view.findViewById(R.id.editTextRegDjDOB);
        regDjDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dateDialog = new DatePickerDialog(view.getContext(), AlertDialog.THEME_HOLO_LIGHT
                        , datePickerListener, mYear, mMonth, mDay);
                dateDialog.getDatePicker().setMaxDate(new Date().getTime());
                dateDialog.show();
            }
        });


        editTextStageName.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus) {
                stageName = editTextStageName.getText().toString();
                map = new HashMap<>();
                map.put("stageName",stageName);

                if(!(registerActivity.checkFreeDjStageName(map)))
                {
                    editTextStageName.setTextColor(Color.RED);
                    editTextStageName.setError("Stage name already taken");
                    boolDjName = false;
                }
                else if(stageName.isEmpty())
                {
                    editTextStageName.setHintTextColor(Color.RED);
                    editTextStageName.setError("Stage name cannot be empty");
                    boolDjName = false;
                }
                else
                {
                    editTextStageName.setTextColor(Color.BLACK);
                    editTextStageName.setHintTextColor(Color.BLACK);
                    editTextStageName.setError(null);
                    boolDjName = true;
                }
            }
        });

        return view;
    }

    public boolean isDjRegDetailsValid()
    {
        if(boolDjName==true && registerActivity.isRegDetailsValid())
        {
            return true;
        }
        return false;
    }

}