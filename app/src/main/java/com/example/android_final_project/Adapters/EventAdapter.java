package com.example.android_final_project.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_final_project.Activities.MainActivity;
import com.example.android_final_project.ObjectsClasses.Event;
import com.example.android_final_project.R;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends BaseAdapter {

    MyEvents myEvents;
    Context mContext;
    String type;
    String fromPage;
    static List<String> cancelSaving = new ArrayList<String>();
    Boolean update;

    public EventAdapter(MyEvents myEvents, Context mContext) {
        this.myEvents = myEvents;
        this.mContext = mContext;
    }

    public EventAdapter(MyEvents myEvents, Context mContext, String type, String fromPage) {
        this.myEvents = myEvents;
        this.mContext = mContext;
        this.type = type;
        this.fromPage = fromPage;
        this.update = false;
        Log.d("arrrayyss", cancelSaving.toString());
    }

    @Override
    public int getCount() {
        return myEvents.getMyEventsList().size();
    }

    @Override
    public Event getItem(int position) {
        return myEvents.getMyEventsList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public Boolean getUpdate() {
        return update;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View oneAdsLine;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        oneAdsLine = inflater.inflate(R.layout.one_line_event, parent,false);

        TextView textViewName = oneAdsLine.findViewById(R.id.textViewEventName);
        TextView textViewDate = oneAdsLine.findViewById(R.id.textViewEventDate);
        TextView textViewDj = oneAdsLine.findViewById(R.id.textViewEventDj);
        TextView textViewStart = oneAdsLine.findViewById(R.id.textViewEventStart);
        TextView textViewEnd = oneAdsLine.findViewById(R.id.textViewEventEnd);
        TextView textViewRating = oneAdsLine.findViewById(R.id.textViewEventRating);
        TextView textViewPartyCode = oneAdsLine.findViewById(R.id.textViewEventPartyCode);
        Button buttonSavedEvent = oneAdsLine.findViewById(R.id.buttonSavedEvent);


        Event event = this.getItem(position);

        textViewName.append(event.getEventName());
        textViewDate.append(event.getEventDate());
        textViewDj.append(event.getWhosPlayingName());
        textViewStart.append(event.getStartTime());
        textViewEnd.append(event.getEndTime());
        textViewRating.append(Double.toString(event.getEventRating()));
        textViewPartyCode.append(event.getPartyCode());

        MainActivity mainActivity = new MainActivity();

        if(fromPage.equals("calendar")) {
            buttonSavedEvent.setVisibility(View.INVISIBLE);
            setUpdate(true);
        }



        if(type!=null) //regular user
        {
            textViewPartyCode.setVisibility(View.GONE);
            textViewRating.setVisibility(View.GONE);
            buttonSavedEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Drawable s = buttonSavedEvent.getBackground();

                    if(buttonSavedEvent.getTag().equals("true"))
                    {
                        buttonSavedEvent.setBackgroundResource(R.drawable.thumb_down_alt_24);
                        buttonSavedEvent.setTag("false");
                        addToCancelSavingList(event.getPartyCode());
                        Toast.makeText(mContext,"Deleted event from your saved list", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        buttonSavedEvent.setBackgroundResource(R.drawable.thumb_up_alt_24);
                        buttonSavedEvent.setTag("true");
                        removeFromCancelSavigList(event.getPartyCode());
                        Toast.makeText(mContext,"Added event to your saved list", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

        else // dj or owner user
        {
            buttonSavedEvent.setVisibility(View.INVISIBLE);
        }

        return oneAdsLine;
    }


    public void addToCancelSavingList(String partyCode)
    {
        cancelSaving.add(partyCode);
    }

    public void removeFromCancelSavigList(String partyCode)
    {
        cancelSaving.remove(partyCode);
    }


    public List<String> getCancelSaving() {

        return cancelSaving;
    }

    public void setCancelSaving() {
        EventAdapter.cancelSaving.clear();
    }



}
