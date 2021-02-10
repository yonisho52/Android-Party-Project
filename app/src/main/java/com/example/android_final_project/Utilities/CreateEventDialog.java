package com.example.android_final_project.Utilities;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.android_final_project.R;

import org.w3c.dom.Text;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateEventDialog extends Dialog
{
    private String message;
    private String title;
    private String btYesText;
    private String btNoText;
    private String selectedTime;

    private TextView textViewStartTime;
    private Text textViewEndTime;


    private TextView editTextStartTime;
    private TextView editTextEndTime;
    private TextView eventName;
    private TextView playingDj;
    int hour, min;


    private int icon=0;


    private View.OnClickListener bttnCreateEventListener=null;
    private View.OnClickListener bttnCancelListener=null;
    //private View.OnClickListener txtStartTime=null;


    public CreateEventDialog(Context context) {
        super(context);
    }
    public CreateEventDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CreateEventDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.create_event_dialog);
        TextView tv = (TextView) findViewById(R.id.malertTitle);

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
        playingDj = findViewById(R.id.editTextNewWhosPlaying);

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
    }

    public String getTitle() {
        return title;
    }
    public String getEventName()
    {
        return eventName.getText().toString();
    }
    public String getWhosPlaying()
    {
        return playingDj.getText().toString();
    }

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
}
