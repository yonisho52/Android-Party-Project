package com.example.android_final_project.FragmentsMain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android_final_project.Activities.LoginActivity;
import com.example.android_final_project.Activities.MainActivity;
import com.example.android_final_project.ObjectsClasses.Details;
import com.example.android_final_project.R;
import com.example.android_final_project.RetrofitInterface;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfileMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileMainFragment extends Fragment {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASEURL="http://10.0.2.2:3000";

    private SharedPreferences sharedPreferences;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditProfileMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfileMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileMainFragment newInstance(String param1, String param2) {
        EditProfileMainFragment fragment = new EditProfileMainFragment();
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
        View view = inflater.inflate(R.layout.fragment_edit_profile_main, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();
        String type = mainActivity.getType();

        String email = mainActivity.getEmail();

        retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        HashMap<String,String> map = new HashMap<>();
        map.put("email",email);

        TextView textViewResult = view.findViewById(R.id.text_view_user_details);

        Call<Details> call = retrofitInterface.getUser(map);
        call.enqueue(new Callback<Details>() {
            @Override
            public void onResponse(Call<Details> call, Response<Details> response) {
                //RegularUser regularUser = response.body();
                if(response.code() == 200) {


                    Details details = response.body();

                    String content = "";
                    content += "Email: " + details.getEmail() + "\n";
                    content += "First Name: " + details.getFirstName() + "\n";
                    content += "Last Name: " + details.getLastName() + "\n";
                    content += "TYPE: " + details.getType() + "\n";

                    if(type.equals("REGULAR"))
                    {
                        content += "age: " + details.getAge() + "\n";
                        content += "favourite Genres: " + details.getFavouriteGenres() + "\n";
                    }
                    if(type.equals("DJ"))
                    {
                        content += "stageName: " + details.getStageName() + "\n";
                        content += "playingGenre: " + details.getPlayingGenre() + "\n";
                        content += "youtubeLink: " + details.getYoutubeLink() + "\n";
                        content += "spotifyLink: " + details.getSpotifyLink() + "\n";
                        content += "appleMusicLink: " + details.getAppleMusicLink() + "\n";
                    }
                    if(type.equals("PLACE-OWNER"))
                    {
                        content += "placeName: " + details.getPlaceName() + "\n";
                        content += "placeType: " + details.getPlaceType() + "\n";
                        content += "placeAddress: " + details.getPlaceAddress() + "\n";
                    }
                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<Details> call, Throwable t) {

            }
        });

        if(type=="REGULAR")
        {

        }

        Context context = container.getContext();

        Button logout = (Button) view.findViewById(R.id.buttonLogOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

                SharedPreferences.Editor editorUser = sharedPreferences.edit();
                editorUser.clear();
                editorUser.commit();

                sharedPreferences = getActivity().getSharedPreferences("Party", Context.MODE_PRIVATE);

                SharedPreferences.Editor editorParty = sharedPreferences.edit();
                editorParty.clear();
                editorParty.commit();

                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}