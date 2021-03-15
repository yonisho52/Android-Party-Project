package com.example.android_final_project.FragmentsMain;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_final_project.Adapters.EventAdapter;
import com.example.android_final_project.Activities.MainActivity;
import com.example.android_final_project.ObjectsClasses.Event;
import com.example.android_final_project.Adapters.MyEvents;
import com.example.android_final_project.R;
import com.example.android_final_project.RetrofitInterface;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventSummaryMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventSummaryMainFragment extends Fragment {

    private ListView listView;
    private EventAdapter adapter;
    private MyEvents myEvents;


    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASEURL="http://10.0.2.2:3000";

    private HashMap<String,String> map;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EventSummaryMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventSummaryMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventSummaryMainFragment newInstance(String param1, String param2) {
        EventSummaryMainFragment fragment = new EventSummaryMainFragment();
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
        View view = inflater.inflate(R.layout.fragment_event_summary_main, container, false);

        Context currContext = container.getContext();
        listView = view.findViewById(R.id.listViewEvents);

        retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        MainActivity mainActivity = (MainActivity) getActivity();
        String userEmail = mainActivity.getEmail();
        String type = mainActivity.getType();

        TextView title = view.findViewById(R.id.textViewTitle);

        if(type.equals("DJ"))
        {
            title.append(" - Your Playing");
        }
        else
        {
            title.append(" - In Your Place");
        }

        map = new HashMap<String, String>();
        map.put("email",userEmail);

        //TextView allEventsByEmail = view.findViewById(R.id.textViewAllEvents);

        Call<List<Event>> call = retrofitInterface.executeGetEventsByEmail(map);
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    HashMap<String, String> map;
                    List<Event> events = response.body();

                    myEvents = new MyEvents(events);

                    adapter = new EventAdapter(myEvents,currContext,null,"summary");

                    listView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event ev = myEvents.getMyEventsList().get(position);
                Dialog dialog = new Dialog(currContext);
                dialog.setContentView(R.layout.dialog_event_summary);
                setEventDetails(dialog,ev);
                Button closeDialog = dialog.findViewById(R.id.buttonSummaryDismiss);
                closeDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Button deleteEvent = dialog.findViewById((R.id.buttonSummaryDialogDeleteEvent));
                deleteEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteEvent(ev.getPartyCode());
                        dialog.dismiss();
                        myEvents.getMyEventsList().remove(position);
                        adapter = new EventAdapter(myEvents,currContext,null,"summary");
                        listView.refreshDrawableState();
                        listView.setAdapter(adapter);
                    }
                });

                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });

        return view;
    }

    public void deleteEvent(String partyCode)
    {
        map = new HashMap<>();
        map.put("partyCode", partyCode);

        Call<Void> call = retrofitInterface.deleteEvent(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful())
                    Toast.makeText(getContext(),"Event Deleted!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(),"SomeThing Went Wrong", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(),"error: "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setEventDetails(Dialog dialog, Event ev)
    {
        TextView textViewSummaryDialogPartyCode = dialog.findViewById(R.id.textViewSummaryDialogPartyCode);
        TextView textViewSummaryDialogCreatedBy = dialog.findViewById(R.id.textViewSummaryDialogCreatedBy);
        TextView textViewSummaryDialogPlaceName = dialog.findViewById(R.id.textViewSummaryDialogPlaceName);
        TextView textViewSummaryDialogDjName = dialog.findViewById(R.id.textViewSummaryDialogDjName);
        TextView textViewSummaryDialogWhoIsPlaying = dialog.findViewById(R.id.textViewSummaryDialogWhoIsPlaying);
        TextView textViewSummaryDialogEndTime = dialog.findViewById(R.id.textViewSummaryDialogEndTime);
        TextView textViewSummaryDialogStartTime = dialog.findViewById(R.id.textViewSummaryDialogStartTime);
        TextView textViewSummaryDialogNumOfRating = dialog.findViewById(R.id.textViewSummaryDialogNumOfRating);
        TextView textViewSummaryDialogEventRating = dialog.findViewById(R.id.textViewSummaryDialogEventRating);
        TextView textViewSummaryDialogEventName = dialog.findViewById(R.id.textViewSummaryDialogEventName);
        TextView textViewSummaryDialogEventDate = dialog.findViewById(R.id.textViewSummaryDialogEventDate);

        textViewSummaryDialogPartyCode.setText("Party Code: " + ev.getPartyCode());
        textViewSummaryDialogCreatedBy.setText("Place Owner Email: " + ev.getCreatedBy());
        textViewSummaryDialogPlaceName.setText("Place Name: " + ev.getPlaceName());
        textViewSummaryDialogDjName.setText("Dj Name: " + ev.getWhosPlayingName());
        textViewSummaryDialogWhoIsPlaying.setText("Dj Email: " + ev.getWhosPlaying());
        textViewSummaryDialogEndTime.setText("End Time: " + ev.getEndTime());
        textViewSummaryDialogStartTime.setText("Start Time: " + ev.getStartTime());
        textViewSummaryDialogNumOfRating.setText("Number Of Rates: " + ev.getNumOfRates());
        textViewSummaryDialogEventRating.setText("Event Rating: " + ev.getEventRating());
        textViewSummaryDialogEventName.setText("Name Of The Party: " + ev.getEventName());
        textViewSummaryDialogEventDate.setText("Date: " + ev.getEventDate());
    }
}