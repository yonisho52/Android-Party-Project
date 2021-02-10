package com.example.android_final_project.FragmentsMain;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android_final_project.StageName;
import com.example.android_final_project.R;
import com.example.android_final_project.RetrofitInterace;

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
 * Use the {@link HomeMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeMainFragment extends Fragment {

    private TextView textViewResult;
    private HashMap<String,String> map;
    private Retrofit retrofit;
    private RetrofitInterace retrofitInterace;
    private String BASEURL="http://10.0.2.2:3000";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeMainFragment newInstance(String param1, String param2) {
        HomeMainFragment fragment = new HomeMainFragment();
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
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_main, container, false);

        Context currContext = container.getContext();

        textViewResult = view.findViewById(R.id.text_view_result);
        Spinner spinner = view.findViewById(R.id.spinner_Dj);


        retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterace = retrofit.create(RetrofitInterace.class);


        Call<List<StageName>> call = retrofitInterace.getPosts();
        call.enqueue(new Callback<List<StageName>>() {
            @Override
            public void onResponse(Call<List<StageName>> call, Response<List<StageName>> response) {
                if(!response.isSuccessful())
                {
                    textViewResult.setText("Code: "+ response.code());
                    return;
                }

                List<StageName> posts = response.body();

                List<String> categories = new ArrayList<String>();
                for(StageName post : posts) {
                    categories.add(post.getStageName());
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(currContext, android.R.layout.simple_spinner_item, categories);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(dataAdapter);

//                for(Post post : posts)
//                {
//                    String content= "";
//                   // content += "Email:" + post.getEmail() + "\n";
//                    //content += "Password: " + post.getPassword() + "\n";
//                    content += "Stage Name: " + post.getStageName() + "\n\n";
////
//                    textViewResult.append(content);
//                }
            }

            @Override
            public void onFailure(Call<List<StageName>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });


        return view;
    }
}