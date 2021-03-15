package com.example.android_final_project.FragmentsMain;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android_final_project.Activities.MainActivity;
import com.example.android_final_project.Adapters.EventAdapter;
import com.example.android_final_project.ObjectsClasses.Event;
import com.example.android_final_project.Adapters.MyEvents;
import com.example.android_final_project.R;
import com.example.android_final_project.RetrofitInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavedEventsMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavedEventsMainFragment extends Fragment {

    private boolean flag;
    private ListView listView;
    private EventAdapter adapter;
    private MyEvents myEvents;
    private HashMap<String,String> map;
    private String userEmail;

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASEURL="http://10.0.2.2:3000";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SavedEventsMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SavedEventsMainFragment newInstance(String param1, String param2) {
        SavedEventsMainFragment fragment = new SavedEventsMainFragment();
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
        View view = inflater.inflate(R.layout.fragment_saved_events_main, container, false);

        flag = false;

        Context currContext = container.getContext();
        listView = view.findViewById(R.id.listViewSavedEvents);

        retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        MainActivity mainActivity = (MainActivity) getActivity();
        userEmail = mainActivity.getEmail();
        String type = mainActivity.getType();


        getList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),myEvents.getMyEventsList().get(position).getCreatedBy(), Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    public void getList()
    {
        map = new HashMap<String, String>();
        map.put("email",userEmail);

        //TextView allEventsByEmail = view.findViewById(R.id.textViewAllEvents);

        Call<List<Event>> call = retrofitInterface.getSavedEvents(map);
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    HashMap<String, String> map;
                    List<Event> events = response.body();

                    myEvents = new MyEvents(events);

                    adapter = new EventAdapter(myEvents,getContext(),"1","savedEvent");

                    listView.setAdapter(adapter);
                    flag=true;
                }

            }
            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        HashMap<String,String[]> map = new HashMap<>();
        List<String> cancelingList = new ArrayList<>();
        if(adapter!=null)
        {
            cancelingList = adapter.getCancelSaving();


            MainActivity mainActivity = (MainActivity) getActivity();
            String[] arr = {mainActivity.getEmail()};
            map.put("email", arr);
            map.put("partyCode", cancelingList.toArray(new String[0]));

            Log.d("mapping", map.toString());

            Call<Void> call = retrofitInterface.removeSavedEvents(map);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 200) {
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
            mainActivity.getType();

            adapter.setCancelSaving();
        }
    }
}