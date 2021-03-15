package com.example.android_final_project.FragmentsMain;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android_final_project.Activities.MainActivity;
import com.example.android_final_project.ObjectsClasses.Event;
import com.example.android_final_project.R;
import com.example.android_final_project.RetrofitInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PartyCodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartyCodeFragment extends Fragment {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASEURL="http://10.0.2.2:3000";

    public SharedPreferences sharedPreferencesParty;

    private MainActivity mainActivity;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PartyCodeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PartyCodeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PartyCodeFragment newInstance(String param1, String param2) {
        PartyCodeFragment fragment = new PartyCodeFragment();
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
        View view = inflater.inflate(R.layout.fragment_party_code_main, container, false);

        mainActivity = (MainActivity) getActivity();

        retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        EditText partyCode = view.findViewById(R.id.editTextPartyCode);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy");
        String date = format.format(calendar.getTime());

        sharedPreferencesParty = getActivity().getSharedPreferences("Party", Context.MODE_PRIVATE);

        if(sharedPreferencesParty.getString("partyCode", null) != null) // && Valid start time && valid end time
        {
            checkExistParty(date,sharedPreferencesParty.getString("partyCode", null));
        }

        Button joinParty = view.findViewById(R.id.buttonJoinParty);
        joinParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkExistParty(date,partyCode.getText().toString());
            }
        });

        return view;
    }

    public void checkExistParty(String date,String partyCode){
        HashMap<String,String> map = new HashMap<>();
        map.put("partyCode", partyCode);
        map.put("eventDate", date);

        Call<Event> call = retrofitInterface.checkPartyNowByCodeAndDate(map);
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                if(response.code()==200)
                {
                    Toast.makeText(getContext(),"Getting In !",Toast.LENGTH_LONG).show();
                    mainActivity.joinParty(response.body());
                }
                else
                {
                    Toast.makeText(getContext(),"Not Valid !",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Toast.makeText(getContext(),"something went wrong ):",Toast.LENGTH_LONG).show();
            }
        });
    }
}