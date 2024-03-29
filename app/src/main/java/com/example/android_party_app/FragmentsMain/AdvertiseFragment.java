package com.example.android_party_app.FragmentsMain;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android_party_app.Activities.MainActivity;
import com.example.android_party_app.Adapters.MessageAdapterRecycler;
import com.example.android_party_app.Model.ObjectsClasses.Message;
import com.example.android_party_app.Adapters.MyAds;
import com.example.android_party_app.R;
import com.example.android_party_app.Service.RetrofitInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdvertiseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdvertiseFragment extends Fragment {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASEURL="http://10.0.2.2:3000";

    private String name;

    private ListView listView;
//    MessageAdapter adapter;
    private MessageAdapterRecycler adapter;
    private RecyclerView recyclerView;
    private MyAds myAds;

    private static boolean flag=true;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdvertiseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThirdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdvertiseFragment newInstance(String param1, String param2) {
        AdvertiseFragment fragment = new AdvertiseFragment();
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
        View view = inflater.inflate(R.layout.fragment_advertise_main, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewMessages);

//        listView = view.findViewById(R.id.listViewMessages);

        Context currContext = container.getContext();
        retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        MainActivity mainActivity = (MainActivity) getActivity();
        String userType = mainActivity.getType();
        String name = mainActivity.getName();

        Button publishAds = view.findViewById(R.id.buttonAddAds);
        EditText editTextMessage = view.findViewById(R.id.textViewTextAds);

        if(userType.equals("REGULAR"))
        {
            publishAds.setVisibility(view.INVISIBLE);
            editTextMessage.setVisibility(view.INVISIBLE);
        }

        Call<List<Message>> call = retrofitInterface.getAllMessages();
        call.enqueue(new Callback<List<Message>>() {
                         @Override
                         public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                            if(response.code()==200)
                            {

                                List<Message> messages = response.body();

                                myAds = new MyAds(messages);

                                adapter = new MessageAdapterRecycler(myAds,getContext());

                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(currContext));
                            }
                            else
                            {
                                Toast.makeText(currContext,"Code: "+ response.code(),Toast.LENGTH_LONG).show();
                            }
                         }
                         @Override
                         public void onFailure(Call<List<Message>> call, Throwable t) {
                             Toast.makeText(currContext,"Code: "+ t.getMessage(),Toast.LENGTH_LONG).show();
                         }
                     });

        publishAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time = format.format(calendar.getTime());

                SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
                String date = format2.format(calendar.getTime());
                HashMap<String, String> map = new HashMap<String, String>();
                if(userType.equals("DJ")) { map.put("createdBy", ("DJ: " + name)); }
                else  { map.put("createdBy", ("PLACE: " + name)); }
                map.put("date", date);
                map.put("time", time);
                map.put("text", editTextMessage.getText().toString());


                Call<Void> call = retrofitInterface.addMessageToAds(map);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200) {
                            Toast.makeText(currContext, "Your Ads Added Successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(currContext, "Something went wrong, try another time", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(currContext, "Connection Failure! ", Toast.LENGTH_LONG).show();
                    }
                });
                editTextMessage.setText("");
            }
        });

        return view;
    }
}