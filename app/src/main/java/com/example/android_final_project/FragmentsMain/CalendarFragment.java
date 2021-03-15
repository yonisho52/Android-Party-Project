package com.example.android_final_project.FragmentsMain;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_final_project.Activities.MainActivity;
import com.example.android_final_project.Adapters.EventAdapter;
import com.example.android_final_project.ObjectsClasses.Event;
import com.example.android_final_project.Adapters.MyEvents;
import com.example.android_final_project.R;
import com.example.android_final_project.RetrofitInterface;
import com.example.android_final_project.Utilities.CreateEventDialog;

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sun.bob.mcalendarview.listeners.OnMonthChangeListener;
import sun.bob.mcalendarview.vo.DateData;
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

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASEURL="http://10.0.2.2:3000";
    private String selectedDate;
    private int selectedDay;
    private int selectedMonth;
    private int selectedYear;
    private SimpleDateFormat sdf;
    private String dateToFill;
    private static int eventID = 1000;

    private ListView listView;
    private EventAdapter adapter;
    private MyEvents myEvents;

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
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        listView = view.findViewById(R.id.listViewCalendar);


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sun.bob.mcalendarview.MCalendarView mainCalendar = ((sun.bob.mcalendarview.MCalendarView) view.findViewById(R.id.calendarHomepage));

        Calendar calendar = Calendar.getInstance();

        //Filling dates with events
        YearMonth yearMonthObj = YearMonth.of(calendar.YEAR,calendar.MONTH);
        int daysInMonth = yearMonthObj.lengthOfMonth();

        int dayToFill = calendar.get(Calendar.DAY_OF_MONTH);
        int monthToFill = calendar.get(Calendar.MONTH)+1;
        int yearToFill = calendar.get(Calendar.YEAR);

        for(int i=dayToFill;i<=daysInMonth;i++)
        {
            dateToFill = (i)+"/"+monthToFill+"/"+yearToFill;
            HashMap<String,String> map = new HashMap<>();
            map.put("Date",dateToFill);
            Call<List<Event>> call = retrofitInterface.executeGetEvents(map);

            int finalI = i;
            call.enqueue(new Callback<List<Event>>() {
                @Override
                public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                    if(response.code()==200)
                    {
                        mainCalendar.markDate(new DateData(yearToFill,monthToFill,finalI).setMarkStyle(MarkStyle.DOT,Color.RED));
                    }
                }
                @Override
                public void onFailure(Call<List<Event>> call, Throwable t) {
                    Toast.makeText(currContext,t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }

        //Month pick listener
        mainCalendar.setOnMonthChangeListener(new OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                YearMonth yearMonthObj = YearMonth.of(year,month);
                int daysInMonth = yearMonthObj.lengthOfMonth();
                for(int i=1;i<=daysInMonth;i++)
                {
                    if(i<10)
                    {
                        dateToFill = "0"+(i)+"/"+month+"/"+year;
                    }
                    else
                    {
                        dateToFill = (i)+"/"+month+"/"+year;
                    }

                    HashMap<String,String> map = new HashMap<>();
                    map.put("Date",dateToFill);
                    Call<List<Event>> call = retrofitInterface.executeGetEvents(map);

                    int finalI = i;
                    call.enqueue(new Callback<List<Event>>() {
                        @Override
                        public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                            if(response.code()==200)
                            {
                                mainCalendar.markDate(new DateData(year,month,finalI).setMarkStyle(MarkStyle.DOT,Color.RED));
                            }
                        }
                        @Override
                        public void onFailure(Call<List<Event>> call, Throwable t) {
                            Toast.makeText(currContext,t.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        //Date pick listener
        DateData prevDate = new DateData(2000,1,1);
        mainCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                mainCalendar.unMarkDate(prevDate);
                mainCalendar.markDate(date.setMarkStyle(MarkStyle.BACKGROUND, R.color.very_light_gray));
                prevDate.setDay(date.getDay());
                prevDate.setMonth(date.getMonth());
                prevDate.setYear(date.getYear());

                if(date.getDay()<10)
                {
                    selectedDate = "0"+date.getDay() + "/" + date.getMonth() + "/" + date.getYear();
                }
                else
                {
                    selectedDate = date.getDay() + "/" + date.getMonth() + "/" + date.getYear();
                }
                selectedDay=date.getDay();
                selectedMonth=date.getMonth();
                selectedYear=date.getYear();

                //Displaying events
                HashMap<String,String> map = new HashMap<>();
                map.put("Date",selectedDate);

                Call<List<Event>> call = retrofitInterface.executeGetEvents(map);
                call.enqueue(new Callback<List<Event>>() {
                    @Override
                    public void onResponse(Call<List<Event>> call, Response<List<Event>> response)
                    {
                        if(response.code()==200)
                        {
                            List<Event> events = response.body();

                            myEvents = new MyEvents(events);
                            adapter = new EventAdapter(myEvents,currContext,"1","calendar");
                            listView.setAdapter(adapter);
                        }
                        else if(response.code()==400)
                        {
                            listView.removeAllViewsInLayout();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Event>>call, Throwable t)
                    {
                        Toast.makeText(currContext,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


        MainActivity mainActivity = (MainActivity) getActivity();
        String type = mainActivity.getType();


        if(type.equals("PLACE-OWNER")) {
            Button createEvent = view.findViewById(R.id.buttonAddEvent);
            createEvent.setVisibility(View.VISIBLE);
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

                            if(mDialog.getStartTime().isEmpty() || mDialog.getEndTime().isEmpty() || mDialog.getEventName().isEmpty() || mDialog.getDate().isEmpty())
                            {
                                Toast.makeText(container.getContext(), "One or more of the required details are missing.", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                String whosPlayingEmail = mDialog.getWhosPlayingEmail();
                                String whosPlayingName = mDialog.getWhosPlayingName();
                                String placeName = mainActivity.getName();
                                String createdBy = mainActivity.getEmail();
                                mDialog.dismiss();
                                Event eventToCreate = new Event(mDialog.getDate(), mDialog.getEventName(), mainActivity.getEmail(), whosPlayingEmail, mDialog.getStartTime(), mDialog.getEndTime(), whosPlayingName, placeName, createdBy);
                                mainActivity.createEvent(eventToCreate);
                                mainCalendar.markDate(new DateData(selectedYear, selectedMonth, selectedDay).setMarkStyle(MarkStyle.DOT, Color.RED));
                                Toast.makeText(container.getContext(), "Successfully added event", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    mDialog.setNegativeButton("X", new View.OnClickListener() {
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
        }

        TextView touchToSave = view.findViewById(R.id.textViewTouchToSave);

        if(type.equals("REGULAR"))
        {
            touchToSave.setVisibility(View.VISIBLE);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Event ev = myEvents.getMyEventsList().get(position);
                    addSavedEvent(ev.getPartyCode());
                }
            });
        }
        return view;
    }

    public void addSavedEvent(String code)
    {
        MainActivity mainActivity = (MainActivity) getActivity();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("email",mainActivity.getEmail());
        map.put("partyCode", code);

        Log.d("checkingEvent",map.toString());

        Call<Void> call = retrofitInterface.addSavedEvent(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(),"Saved event to your list",Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    /**
     * A simple {@link Fragment} subclass.
     * Use the {@link splashFragment#newInstance} factory method to
     * create an instance of this fragment.
     */

}