package com.example.android_final_project.FragmentsMain;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_final_project.Activities.MainActivity;
import com.example.android_final_project.ObjectsClasses.Event;
import com.example.android_final_project.R;
import com.example.android_final_project.RetrofitInterface;
import com.example.android_final_project.ObjectsClasses.Survey;
import com.example.android_final_project.Utilities.SurveyDialog;

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
 * Use the {@link NowEventMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NowEventMainFragment extends Fragment {


    private View view;
    private MainActivity mainActivity;
    private String userType;

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASEURL="http://10.0.2.2:3000";

    private Event nowEvent;

    private String rateType;
    private Button buttonRateDj;
    private Button buttonRateOwner;

    private Survey survey;
    private Button buttonSurvey;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NowEventMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NowEventMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NowEventMainFragment newInstance(String param1, String param2) {
        NowEventMainFragment fragment = new NowEventMainFragment();
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
        view = inflater.inflate(R.layout.fragment_now_event_main, container, false);

        retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        mainActivity = (MainActivity) getActivity();
        userType = mainActivity.getType();

        String type = mainActivity.getType();
        String email = mainActivity.getEmail();

        Button buttonExitParty = view.findViewById(R.id.buttonExitParty);
        buttonSurvey = view.findViewById(R.id.buttonSurvey);
        buttonRateDj = view.findViewById(R.id.buttonRateDj);
        buttonRateOwner = view.findViewById(R.id.buttonRateOwner);
        Button rateEvent = view.findViewById(R.id.buttonRateEvent);
        buttonRateDj.setEnabled(false);
        rateEvent.setEnabled(false);
        buttonRateOwner.setEnabled(false);
        buttonSurvey.setEnabled(false);


        //
        //Log.d("nowEvent",nowEvent.getPartyCode());


        if(type.equals("REGULAR"))
        {
            nowEvent = mainActivity.getNowEvent();
            buttonExitParty.setVisibility(View.VISIBLE);
            suggestPageDetails();

            checkExistSurvey(nowEvent.getPartyCode());
//            if(survey==null)
//                buttonSurvey.setEnabled(false);
        }

        else
        {
            buttonExitParty.setVisibility(View.INVISIBLE);
            buttonRateDj.setVisibility(View.INVISIBLE);
            buttonRateOwner.setVisibility(View.INVISIBLE);
            rateEvent.setVisibility(View.INVISIBLE);

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy");
            String date = format.format(calendar.getTime());

            HashMap<String,String> map = new HashMap<String, String>();
            map.put("email", email);
            map.put("date", date);
            Log.d("auth", map.toString());
            Call<Event> call = retrofitInterface.getEventByDateAndEmail(map);
            call.enqueue(new Callback<Event>() {
                @Override
                public void onResponse(Call<Event> call, Response<Event> response) {
                    if(response.code()==200)
                    {
                        nowEvent = response.body();
                        suggestPageDetails();
                        checkExistSurvey(nowEvent.getPartyCode());
                    }
                    else
                    {
                        mainActivity.noEventsToday();
                        return;
                    }
                }

                @Override
                public void onFailure(Call<Event> call, Throwable t) {

                }
            });
        }

        mainActivity.kickOfParty(false);


        buttonExitParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.kickOfParty(true);
            }
        });



        if(!(mainActivity.voted()))
        {
            buttonSurvey.setEnabled(true);
        }
        buttonSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // chooseWindow = 1->showSurveyResult, 2->makeSurvey, 3->getSurvey and vote

                if(type.equals("REGULAR"))
                {
                    final SurveyDialog mDialog = new SurveyDialog(getContext(),"3",nowEvent.getPartyCode(),survey);
                    mDialog.setTitle("Create a new event");
                    mDialog.show();

                    mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            if(mDialog.getFlagSurveyVoted()) {
                                buttonSurvey.setEnabled(false);
                                buttonSurvey.setText("Voted!");
                                mainActivity.votedAddSharedPref();
                            }
                        }
                    });

                }
                else if(survey!=null)
                    {
                        final SurveyDialog mDialog = new SurveyDialog(getContext(),"1",nowEvent.getPartyCode(),survey);
                        mDialog.setTitle("Create a new event");
                        mDialog.show();
                        Window window = mDialog.getWindow();
                        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    }
                else if(type.equals("DJ")) // no survey
                {
                    final SurveyDialog mDialog = new SurveyDialog(getContext(),"2",nowEvent.getPartyCode(),survey);
                    mDialog.setTitle("Create a new event");
                    mDialog.show();

                    mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            mainActivity.joinParty(nowEvent);
                        }
                    });
                }
                else
                {
                    final SurveyDialog mDialog = new SurveyDialog(getContext(),"1",nowEvent.getPartyCode(),survey);
                    mDialog.setTitle("Create a new event");
                    mDialog.show();
                }

            }

        });



        if(!(mainActivity.djRated()))
        {
            buttonRateDj.setEnabled(true);
        }
        buttonRateDj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //open dialog to rate the dj !
                    String djEmail = nowEvent.getWhosPlaying();
                    rateType = "DJ";
                    rateDjOrOwner(djEmail, rateType);
//                    mainActivity.djRatedAddSharedPref();
                }
            });


        if(!(mainActivity.ownerRated()))
        {
            buttonRateOwner.setEnabled(true);
        }
        buttonRateOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ownerEmail = nowEvent.getCreatedBy();
                rateType = "Place";
                rateDjOrOwner(ownerEmail, rateType);
//                mainActivity.onwerRatedAddSharedPref();
            }
        });

        if(!(mainActivity.eventRated()))
        {
            rateEvent.setEnabled(true);
        }
        rateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String partyCode = nowEvent.getPartyCode();
                HashMap<String,String> map = new HashMap<>();
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.dialog_rate_dj_event_place, null);

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

//                mainActivity.eventRatedAddSharedPref();

                RatingBar ratingBar = mView.findViewById(R.id.ratingBarRatingDialog);
                TextView textViewRating = mView.findViewById(R.id.textViewRating);
                TextView textViewTitle = mView.findViewById(R.id.textViewratingDialogTitle);
                textViewTitle.append("Event");
                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        textViewRating.setText(String.valueOf(ratingBar.getRating()));
                    }
                });
                Button buttonSendRate = mView.findViewById(R.id.buttonSendRatingDialog);
                buttonSendRate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        rateEvent.setText("Voted!");
                        mainActivity.eventRatedAddSharedPref();
                        rateEvent.setEnabled(false);
                        Log.d("rating",String.valueOf(ratingBar.getRating()));
                        map.put("partyCode",partyCode);
                        map.put("rating",String.valueOf(ratingBar.getRating()));
                        Call<Void> call = retrofitInterface.rateEventByPartyCode(map);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.isSuccessful())
                                {
                                    Toast.makeText(getContext(),"dj rated !", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                        dialog.cancel();
                    }
                });
            }
        });
        return view;
    }

    public void rateDjOrOwner(String email,String type)
    {
        HashMap<String,String> map = new HashMap<>();
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.dialog_rate_dj_event_place, null);

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

        RatingBar ratingBar = mView.findViewById(R.id.ratingBarRatingDialog);
        TextView textViewRating = mView.findViewById(R.id.textViewRating);
        TextView textViewTitle = mView.findViewById(R.id.textViewratingDialogTitle);
        textViewTitle.append(type);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                textViewRating.setText(String.valueOf(ratingBar.getRating()));
            }
        });


        Button buttonSendRate = mView.findViewById(R.id.buttonSendRatingDialog);
        buttonSendRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rateType.equals("DJ"))
                {
                    buttonRateDj.setEnabled(false);
                    buttonRateDj.setText("Voted!");
                    mainActivity.djRatedAddSharedPref();
                }
                else
                {
                    buttonRateOwner.setEnabled(false);
                    buttonRateOwner.setText("Voted!");
                    mainActivity.onwerRatedAddSharedPref();
                }
//                Log.d("rating",String.valueOf(ratingBar.getRating()));
                map.put("email",email);
                map.put("rating",String.valueOf(ratingBar.getRating()));


                Call<Void> call = retrofitInterface.rateDjOrPlaceByEmail(map);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful())
                        {
                            Toast.makeText(getContext(),"rated !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

                dialog.cancel();
            }
        });
    }

    public void suggestPageDetails()
    {
        mainActivity.insertParty(nowEvent.getPartyCode());


        TextView code = view.findViewById(R.id.textViewNowEventCode);
        TextView placeName = view.findViewById(R.id.textViewNowEventPlaceName);
        TextView startTime = view.findViewById(R.id.textViewNowEventStartTime);
        TextView endTime = view.findViewById(R.id.textViewNowEventEndTime);
        TextView playingDj = view.findViewById(R.id.textViewNowEventPlayingDj);
        TextView EventRating = view.findViewById(R.id.textViewNowEventRatingEvent);

        code.append(nowEvent.getPartyCode());
        placeName.append(nowEvent.getPlaceName());
        startTime.append(nowEvent.getStartTime());
        endTime.append(nowEvent.getEndTime());
        playingDj.append(nowEvent.getWhosPlayingName());
        EventRating.append(Double.toString(nowEvent.getEventRating()));
    }

    public void checkExistSurvey(String partyCode)
    {
        HashMap<String,String> map = new HashMap<>();
        map.put("partyCode",partyCode);
        Call<Survey> call = retrofitInterface.getSurveyByPartyCode(map);
        call.enqueue(new Callback<Survey>() {
            @Override
            public void onResponse(Call<Survey> call, Response<Survey> response) {
                if(response.isSuccessful())
                {
                    survey = response.body();
                }
                else
                    if(!userType.equals("DJ")) {
                        buttonSurvey.setEnabled(false);
                        buttonSurvey.setText("No Survey Yet");
                    }

            }

            @Override
            public void onFailure(Call<Survey> call, Throwable t) {
                if(!userType.equals("DJ")) {
                    buttonSurvey.setEnabled(false);
                    buttonSurvey.setText("No Survey Yet");
                }
            }
        });
    }
}