package com.example.android_final_project.FragmentsMain;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_final_project.EventsClasses.EventResult;
import com.example.android_final_project.R;
import com.example.android_final_project.RetrofitInterace;
import com.example.android_final_project.Utilities.CreateEventDialog;

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sun.bob.mcalendarview.vo.DateData;
import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnDateClickListener;

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
    String dateToFill;
    private static int eventID = 1000;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        Context currContext = container.getContext();

        retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterace = retrofit.create(RetrofitInterace.class);

        //Set todays date at init for calendar
        //CalendarView mainCalendar = view.findViewById(R.id.calendarHomepage);
        //Long currentDate = Calendar.getInstance().getTimeInMillis();
        //mainCalendar.setDate(currentDate);

        //Displaying output from DB of an event


        //Setting min date for calendar to support - 01/01/2020
        //mainCalendar.setMinDate(1609452000000L);

        //Setting max date for calendar to support - 31/12/2021
        //mainCalendar.setMaxDate(1640988000000L);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //selectedDate = sdf.format(new Date(mainCalendar.getDate()));
        //CalendarView mainCalendar = view.findViewById(R.id.calendarHomepage);
        sun.bob.mcalendarview.MCalendarView mainCalendar = ((sun.bob.mcalendarview.MCalendarView) view.findViewById(R.id.calendarHomepage));

        //Adding layout and textviews for more than 1 event in the page
        LinearLayout moreEvents = view.findViewById(R.id.LinearScrollLayout);

        //Testing mCalendarView - Date highlight
        Calendar calendar = Calendar.getInstance();

        //Filling dates with events
        YearMonth yearMonthObj = YearMonth.of(calendar.YEAR,calendar.MONTH);
        int daysInMonth = yearMonthObj.lengthOfMonth();
        ArrayList<DateData> markEvents = new ArrayList<>();

        int monthToFill = calendar.get(Calendar.MONTH)+1;

        int yearToFill = calendar.get(Calendar.YEAR);

        for(int i=0;i<10;i++)
        {
            dateToFill = (i+1)+"/"+monthToFill+"/"+yearToFill;
            HashMap<String,String> map = new HashMap<>();
            map.put("eventDate",dateToFill);
            Call<EventResult> call = retrofitInterace.executeGetEvents(map);

            int finalI = i;
            call.enqueue(new Callback<EventResult>() {
                @Override
                public void onResponse(Call<EventResult> call, Response<EventResult> response) {
                    if(response.code()==200)
                    {
                        mainCalendar.markDate(new DateData(yearToFill,monthToFill,finalI+1).setMarkStyle(MarkStyle.DOT,Color.RED));
                    }
                }
                @Override
                public void onFailure(Call<EventResult> call, Throwable t) {
                    Toast.makeText(currContext,t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }

        //Date pick listener
        DateData prevDate = new DateData(2000,1,1);
        mainCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                mainCalendar.unMarkDate(prevDate);
                mainCalendar.markDate(date.setMarkStyle(MarkStyle.DOT, Color.RED));
                prevDate.setDay(date.getDay());
                prevDate.setMonth(date.getMonth());
                prevDate.setYear(date.getYear());
                selectedDate = date.getDay() + "/" + date.getMonth() + "/" + date.getYear();

                //Displaying events
                HashMap<String,String> map = new HashMap<>();
                map.put("eventDate",selectedDate);

                Call<EventResult> call = retrofitInterace.executeGetEvents(map);
                call.enqueue(new Callback<EventResult>() {
                    @Override
                    public void onResponse(Call<EventResult> call, Response<EventResult> response)
                    {
                        if(response.code()==200)
                        {
                            EventResult result = response.body();
                            //Clean moreEvents
                            moreEvents.removeAllViews();

                            for(int i=0;i<5;i++)
                            {
                                TextView eventCount = new TextView(currContext);
                                eventCount.setText("Event No. "+(i+1));
                                moreEvents.addView(eventCount);

                                //Event date
                                TextView textViewDate = new TextView(currContext);
                                //Event No and date
                                textViewDate.setText("Event Date:" +result.getEventDate());
                                moreEvents.addView(textViewDate);

                                //Created by
                                TextView textViewCreatedBy = new TextView(currContext);
                                textViewCreatedBy.setText("Created By" +result.getCreatedBy());
                                moreEvents.addView(textViewCreatedBy);

                                //Event name
                                TextView textViewEventName = new TextView(currContext);
                                textViewEventName.setText("Event Name" +result.getEventName());
                                moreEvents.addView(textViewEventName);

                                //Whos Playing
                                TextView textViewWhosPlaying = new TextView(currContext);
                                textViewWhosPlaying.setText("Whos playing:" +result.getPlayingDj());
                                moreEvents.addView(textViewWhosPlaying);
                            }
                        }
                        else if(response.code()==404)
                        {
                            Toast.makeText(currContext,"No events on this day",Toast.LENGTH_LONG).show();

                            //Clean moreEvents
                            moreEvents.removeAllViews();
                        }
                    }
                    @Override
                    public void onFailure(Call<EventResult> call, Throwable t)
                    {
                        Toast.makeText(currContext,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


        Button createEvent = view.findViewById(R.id.buttonAddEvent);
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CreateEventDialog mDialog = new CreateEventDialog(currContext);
                mDialog.setTitle("Create a new event");
                mDialog.setIcon(android.R.drawable.ic_input_add);
                mDialog.setMessage("Please provide the following information about the event:");

                //Positive and negative answers
                mDialog.setPositveButton("Create event", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();

                        //Event insertion to DB here
                        HashMap<String,String> map = new HashMap<>();
                        map.put("createdBy","Kiril");
                        //map.put("createdBy", getCurrentUser);

                        map.put("eventDate",selectedDate);

                        //map.put("eventName","La La Land");
                        map.put("eventName",mDialog.getEventName());

                       // map.put("playingDj","Efi Profus");
                        map.put("playingDj",mDialog.getWhosPlaying());

                        Call<Void> call = retrofitInterace.executeAddEvent(map);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.code()==200)
                                {
                                    Toast.makeText(currContext,"Successfully created new event",Toast.LENGTH_LONG).show();
                                    eventID++;
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
                mDialog.setNegativeButton("Cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                        //Closure if the window without adding the event
                    }
                });
                mDialog.show();
                Window window = mDialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });

        return view;
    }
}