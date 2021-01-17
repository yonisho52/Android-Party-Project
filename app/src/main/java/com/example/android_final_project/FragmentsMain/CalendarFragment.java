package com.example.android_final_project.FragmentsMain;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.EventDay;
import com.example.android_final_project.Events.Event;
import com.example.android_final_project.Events.EventResult;
import com.example.android_final_project.LoginResult;
import com.example.android_final_project.R;
import com.example.android_final_project.RetrofitInterace;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Retrofit retrofit;
    RetrofitInterace retrofitInterace;
    private String BASEURL="http://10.0.2.2:3000";
    String selectedDate;
    SimpleDateFormat sdf;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
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
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        Context currContext = container.getContext();

        retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterace = retrofit.create(RetrofitInterace.class);

        //Set todays date at init for calendar
        CalendarView mainCalendar = view.findViewById(R.id.calendarHomepage);
        Long currentDate = Calendar.getInstance().getTimeInMillis();
        mainCalendar.setDate(currentDate);

        //Setting min date for calendar to support - 01/01/2020
        mainCalendar.setMinDate(1609452000000L);

        //Setting max date for calendar to support - 31/12/2021
        mainCalendar.setMaxDate(1640988000000L);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        selectedDate = sdf.format(new Date(mainCalendar.getDate()));

        mainCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                selectedDate = dayOfMonth + "/" + (month+1) + "/" + year;
            }
        });

        TextView eventDate = view.findViewById(R.id.editTextEventDate);
        TextView createdBy = view.findViewById(R.id.editTextCreatedBy);
        TextView eventName = view.findViewById(R.id.editTextEventName);
        TextView playingDj = view.findViewById(R.id.editTextPlayingDj);


        Button createEvent = view.findViewById(R.id.buttonAddEvent);
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("OnClick","Inside on click event");

                HashMap<String,String> map = new HashMap<>();
                map.put("createdBy","Kiril");
                map.put("eventDate",selectedDate);
                map.put("eventName","La La Land");
                map.put("playingDj","Efi Profus");

                Call<Void> call = retrofitInterace.executeAddEvent(map);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code()==200)
                        {
                            Toast.makeText(currContext,"Successfully created new event",Toast.LENGTH_LONG).show();
                        }
                        else if(response.code()==400)
                        {
                            Toast.makeText(currContext,"Cannot create another event on the same date",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(currContext,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        Button getEvents = view.findViewById(R.id.buttonGetEvents);
        getEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> map = new HashMap<>();
                map.put("eventDate",selectedDate);

                Call<EventResult> call = retrofitInterace.executeGetEvents(map);
                call.enqueue(new Callback<EventResult>() {
                    @Override
                    public void onResponse(Call<EventResult> call, Response<EventResult> response) {
                        if(response.code()==200)
                        {
                                EventResult result = response.body();
                                eventDate.setText("Event Date:" +result.getEventDate());
                                createdBy.setText("Created By:"+result.getCreatedBy());
                                eventName.setText("Event name:"+result.getEventName());
                                playingDj.setText("Whos playing:"+result.getPlayingDJ());
                                //Toast.makeText(currContext,"Inside onResponse of EventResult",Toast.LENGTH_LONG).show();
                        }
                        else if(response.code()==404)
                        {
                            eventDate.setText("No events for this day");
                            createdBy.setText("");
                            eventName.setText("");
                            playingDj.setText("");
                            Toast.makeText(currContext,"No events on this day",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<EventResult> call, Throwable t) {
                        Toast.makeText(currContext,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        return view;
    }
}