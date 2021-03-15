package com.example.android_final_project.FragmentsRegLog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android_final_project.Activities.RegisterActivity;
import com.example.android_final_project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegularRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegularRegisterFragment extends Fragment {

    private int age;
    private List<String> favouriteGenres = new ArrayList<String>();;

    private TextView regularUserDOB;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegularRegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegularRegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegularRegisterFragment newInstance(String param1, String param2) {
        RegularRegisterFragment fragment = new RegularRegisterFragment();
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
            regularUserDOB.setText(format);
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
        View view = inflater.inflate(R.layout.fragment_regular_register, container, false);

        RegisterActivity registerActivity = (RegisterActivity) getActivity();

        CheckBox checkGenreHipHop = view.findViewById(R.id.checkGenreHipHop);
        CheckBox checkBoxElectronic = view.findViewById(R.id.checkBoxElectronic);
        CheckBox checkBoxTechno = view.findViewById(R.id.checkBoxTechno);
        CheckBox checkBoxTrance = view.findViewById(R.id.checkBoxTrance);
        CheckBox checkGenreChill = view.findViewById(R.id.checkGenreChill);
        CheckBox checkGenreDance = view.findViewById(R.id.checkGenreDance);


        Button register = view.findViewById(R.id.buttonRegRegular);
        register.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                if(checkGenreHipHop.isChecked()) { favouriteGenres.add(checkGenreHipHop.getText().toString()); }
                if(checkBoxElectronic.isChecked()) {favouriteGenres.add(checkBoxElectronic.getText().toString()); }
                if(checkBoxTechno.isChecked()) {favouriteGenres.add(checkBoxTechno.getText().toString()); }
                if(checkBoxTrance.isChecked()) {favouriteGenres.add(checkBoxTrance.getText().toString()); }
                if(checkGenreChill.isChecked()) {favouriteGenres.add(checkGenreChill.getText().toString()); }
                if(checkGenreDance.isChecked()) {favouriteGenres.add(checkGenreDance.getText().toString()); }

                registerActivity.registerRegularUser(favouriteGenres,age);
            }
        });



        regularUserDOB = view.findViewById(R.id.editTextRegularUserDOB);
        regularUserDOB.setOnClickListener(new View.OnClickListener() {
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

        return view;
    }

}