package com.example.android_final_project.Utilities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android_final_project.R;
import com.example.android_final_project.RetrofitInterface;
import com.example.android_final_project.ObjectsClasses.Survey;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SurveyDialog extends Dialog {

    private String chooseWindow;
    private String partyCode;

    private Survey survey;

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASEURL="http://10.0.2.2:3000";

    private HashMap<String,String> map;

    private TextView textViewResultQuestion;
    private TextView textViewResultAns1;
    private TextView textViewResultAns2;
    private TextView textViewResultAns3;
    private ProgressBar progressBar1;
    private ProgressBar progressBar2;
    private ProgressBar progressBar3;

    private TextView textViewVoteQuestion;
    private RadioButton radioButtonAns1;
    private RadioButton radioButtonAns2;
    private RadioButton radioButtonAns3;
    private TextView textViewNumOfVote1;
    private TextView textViewNumOfVote2;
    private TextView textViewNumOfVote3;
    private TextView textViewResultPrecent1;
    private TextView textViewResultPrecent2;
    private TextView textViewResultPrecent3;

    boolean flagSurveyVoted;

    private RadioButton radioAnsButton;


    public SurveyDialog(@NonNull Context context,String chooseWindow,String partyCode) {
        super(context);
        this.chooseWindow = chooseWindow;
        this.partyCode = partyCode;
    }

    public SurveyDialog(@NonNull Context context,String chooseWindow,String partyCode,Survey survey) {
        super(context);
        this.chooseWindow = chooseWindow;
        this.partyCode = partyCode;
        this.survey = survey;
    }

    public SurveyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SurveyDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        map = new HashMap<>();
        flagSurveyVoted = false;

        if(chooseWindow.equals("2"))  // make survey for dj
        {
            setContentView(R.layout.dialog_make_survey);

            EditText sendQuestion = findViewById(R.id.editTextQuestion);
            EditText sendAns1 = findViewById(R.id.editTextAnswerOne);
            EditText sendAns2 = findViewById(R.id.editTextAnswerTwo);
            EditText sendAns3 = findViewById(R.id.editTextAnswerThree);
            Button buttonMakeSurvey = findViewById(R.id.buttonMakeSurvey);


            buttonMakeSurvey.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    survey = new Survey(partyCode,sendQuestion.getText().toString(),sendAns1.getText().toString(),
                            sendAns2.getText().toString(),sendAns3.getText().toString());
                    map = makeMap(survey);
                    Call<Void> call = retrofitInterface.addSurvey(map);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful())
                            {
                                Toast.makeText(getContext(),"Survey Sent !",Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(getContext(),"Survey not Sent ! " + response.errorBody(),Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getContext(),"Survey not Sent ! " + t.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                    dismiss();

                }
            });
        }
        else if(chooseWindow.equals("1"))  // show result for dj / owner
        {
            setContentView(R.layout.dialog_survey_result);

            textViewResultQuestion = findViewById(R.id.textViewResultQuestion);
            textViewResultAns1 = findViewById(R.id.textViewResultOne);
            textViewResultAns2 = findViewById(R.id.textViewResultTwo);
            textViewResultAns3 = findViewById(R.id.textViewResultThree);
            progressBar1 = findViewById(R.id.progressBar1);
            progressBar2 = findViewById(R.id.progressBar2);
            progressBar3 = findViewById(R.id.progressBar3);

            textViewNumOfVote1 = findViewById(R.id.textViewNumOfVote1);
            textViewNumOfVote2 = findViewById(R.id.textViewNumOfVote2);
            textViewNumOfVote3 = findViewById(R.id.textViewNumOfVote3);

            textViewResultPrecent1 = findViewById(R.id.textViewResultPrecent1);
            textViewResultPrecent2 = findViewById(R.id.textViewResultPrecent2);
            textViewResultPrecent3 = findViewById(R.id.textViewResultPrecent3);

            setSurveyResult();

            Button buttonCloseResult = findViewById(R.id.buttonCloseResult);
            buttonCloseResult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });


        }
        else // voting for reguar user chooseWindow = 3
        {
            setContentView(R.layout.dialog_vote_surevy);
            textViewVoteQuestion = findViewById(R.id.textViewVoteQuestion);
            radioButtonAns1 = findViewById(R.id.radioButtonAns1);
            radioButtonAns2 = findViewById(R.id.radioButtonAns2);
            radioButtonAns3 = findViewById(R.id.radioButtonAns3);
            RadioGroup radioGroupVote = findViewById(R.id.radioGroupVote);
            Button buttonSurveyVote = findViewById(R.id.buttonVote);


            loadSurveyForVote();

            buttonSurveyVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int radioId = radioGroupVote.getCheckedRadioButtonId();
                    radioAnsButton = findViewById(radioId);
                    voteSurvey(radioAnsButton.getTag().toString());
                    flagSurveyVoted =true;
                    dismiss();
                }
            });


        }
    }

    public boolean getFlagSurveyVoted()
    {
        return flagSurveyVoted;
    }

    private HashMap<String,String> makeMap(Survey surveyToSend) {
        map = new HashMap<>();
        map.put("partyCode",partyCode);
        map.put("question",surveyToSend.getQuestion());
        map.put("ans1",surveyToSend.getAns1());
        map.put("ans2",surveyToSend.getAns2());
        map.put("ans3",surveyToSend.getAns3());
        map.put("ans1Rate",String.valueOf(surveyToSend.getAns1Rate()));
        map.put("ans1NumOfRate",String.valueOf(surveyToSend.getAns1NumOfRate()));
        map.put("ans2Rate",String.valueOf(surveyToSend.getAns2Rate()));
        map.put("ans2NumOfRate",String.valueOf(surveyToSend.getAns2NumOfRate()));
        map.put("ans3Rate",String.valueOf(surveyToSend.getAns3Rate()));
        map.put("ans3NumOfRate",String.valueOf(surveyToSend.getAns3NumOfRate()));
        return map;
    }


    public void setSurveyResult()
    {
        textViewResultQuestion.setText(survey.getQuestion());
        textViewResultAns1.setText(survey.getAns1());
        textViewResultAns2.setText(survey.getAns2());
        textViewResultAns3.setText(survey.getAns3());

        progressBar1.setProgress((int) survey.getAns1Rate());
        textViewNumOfVote1.append(String.valueOf(survey.getAns1NumOfRate()));
        textViewResultPrecent1.setText((int) survey.getAns1Rate()+"%");

        progressBar2.setProgress((int) survey.getAns2Rate());
        textViewNumOfVote2.append(String.valueOf(survey.getAns2NumOfRate()));
        textViewResultPrecent2.setText((int) survey.getAns2Rate()+"%");

        progressBar3.setProgress((int) survey.getAns3Rate());
        textViewNumOfVote3.append(String.valueOf(survey.getAns3NumOfRate()));
        textViewResultPrecent3.setText((int) survey.getAns3Rate()+"%");

    }

    public void loadSurveyForVote()
    {
        textViewVoteQuestion.setText(survey.getQuestion());
        radioButtonAns1.setText(survey.getAns1());
        radioButtonAns2.setText(survey.getAns2());
        radioButtonAns3.setText(survey.getAns3());
    }

    private void voteSurvey(String vote)
    {
        map = new HashMap<>();
        map.put("partyCode",partyCode);
        map.put("ansNumber", vote);

        Call<Void> call = retrofitInterface.voteForSurvey(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(getContext(),"Voted!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(),"Something Went Wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(),"Something Went Wrong! " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
