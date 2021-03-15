package com.example.android_final_project.Utilities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android_final_project.Activities.RegisterActivity;
import com.example.android_final_project.R;
import com.example.android_final_project.RetrofitInterface;
import com.example.android_final_project.ObjectsClasses.StageName;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateEventDialog extends Dialog
{
    private String message;
    private String title;
    private String btYesText;
    private String btNoText;

    private TextView editTextStartTime;
    private TextView editTextEndTime;
    private EditText eventName;
    private boolean boolEventName=false;
    private Spinner playingDj;
    private int hour, min;
    private String djEmail;
    private TextView editTextCreateDate;

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASEURL="http://10.0.2.2:3000";

    private HashMap<String,String> djMaps;

    private int icon=0;

    private View.OnClickListener bttnCreateEventListener=null;
    private View.OnClickListener bttnCancelListener=null;


    public CreateEventDialog(Context context) {
        super(context);
    }
    public CreateEventDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CreateEventDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            String format = new SimpleDateFormat("dd/M/YYYY").format(c.getTime());
            editTextCreateDate.setText(format);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_create_event);
        TextView tv = (TextView) findViewById(R.id.malertTitle);

        retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        tv.setCompoundDrawablesWithIntrinsicBounds(icon,0,0,0);
        tv.setText(getTitle());
        TextView tvmessage = (TextView) findViewById(R.id.aleartMessage);
        tvmessage.setText(getMessage());

        Button btYes = (Button) findViewById(R.id.aleartYes);
        Button btNo = (Button) findViewById(R.id.aleartNo);
        btYes.setText(btYesText);
        btNo.setText(btNoText);
        btYes.setOnClickListener(bttnCreateEventListener);
        btNo.setOnClickListener(bttnCancelListener);

        editTextStartTime = findViewById(R.id.editTextStartAt);
        editTextEndTime = findViewById(R.id.editTextEndAt);
        eventName = findViewById(R.id.editTextNewEventName);
        playingDj = findViewById(R.id.spinner_Dj);

        editTextStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //Do nothing for now
                        hour=hourOfDay;
                        min=minute;
                        if(hour<10 && min<10)
                            editTextStartTime.setText("0"+hour+":"+"0"+min);
                        else if(hour<10 && min>=10)
                            editTextStartTime.setText("0"+hour+":"+min);
                        else if(hour>=10 && min<10)
                            editTextStartTime.setText(hour+":"+"0"+min);
                        else editTextStartTime.setText(hour+":"+min);
                    }
                }, 21,0,true);
                //timePickerDialog.updateTime(hour,min);
                timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                timePickerDialog.show();
            }
        });
        editTextEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //Do nothing for now
                        hour=hourOfDay;
                        min=minute;
                        if(hour<10 && min<10)
                            editTextEndTime.setText("0"+hour+":"+"0"+min);
                        else if(hour<10 && min>=10)
                            editTextEndTime.setText("0"+hour+":"+min);
                        else if(hour>=10 && min<10)
                            editTextEndTime.setText(hour+":"+"0"+min);
                        else editTextEndTime.setText(hour+":"+min);
                    }
                }, 21,0,true);
                //timePickerDialog.updateTime(hour,min);
                timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                timePickerDialog.show();
            }
        });

        editTextCreateDate = findViewById(R.id.editTextCreateDate);
        editTextCreateDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dateDialog = new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT
                        , datePickerListener, mYear, mMonth, mDay);
                dateDialog.getDatePicker().setMinDate(new Date(c.getTimeInMillis()).getTime());
                dateDialog.show();
            }
        });

        eventName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    if(eventName.getText().toString().isEmpty())
                    {
                        eventName.setError("Event name cannot be empty");
                        boolEventName=false;
                    }
                    else
                    {
                        eventName.setError(null);
                        boolEventName=true;
                    }
                }
            }
        });

        Spinner spinner = findViewById(R.id.spinner_Dj);

        Call<List<StageName>> call = retrofitInterface.getStageNames();
        call.enqueue(new Callback<List<StageName>>() {
            @Override
            public void onResponse(Call<List<StageName>> call, Response<List<StageName>> response) {
                if(!response.isSuccessful())
                {
                    return;
                }

                List<StageName> posts = response.body();
                djMaps = new HashMap<String, String>();

                List<String> categories = new ArrayList<String>();
                for(StageName post : posts) {
                    djMaps.put(post.getStageName(),post.getEmail());
                    categories.add(post.getStageName());
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_djs, categories);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(dataAdapter);
            }
            @Override
            public void onFailure(Call<List<StageName>> call, Throwable t) {
            }
        });
    }

    public String getTitle() {
        return title;
    }
    public String getEventName()
    {
        return eventName.getText().toString();
    }
    public String getWhosPlayingEmail()
    {
        String djMail;
        djMail = djMaps.get(playingDj.getSelectedItem().toString());
        return djMail;
    }
    public String getWhosPlayingName()
    {
        return playingDj.getSelectedItem().toString();
    }
    public String getStartTime() {return editTextStartTime.getText().toString();}
    public String getEndTime() {return editTextEndTime.getText().toString();}

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public void setPositveButton(String yes, View.OnClickListener onClickListener) {
        dismiss();
        this.btYesText = yes;
        this.bttnCreateEventListener = onClickListener;
    }
    public void setNegativeButton(String no, View.OnClickListener onClickListener) {
        dismiss();
        this.btNoText = no;
        this.bttnCancelListener = onClickListener;
    }

    public String getDate() { return editTextCreateDate.getText().toString(); }

    public void enableCreateEventButton()
    {
        Button btYes = (Button) findViewById(R.id.aleartYes);
        if(boolEventName==true)
        {
            btYes.setEnabled(true);
        }
        else
        {
            btYes.setEnabled(false);
        }
    }
}
