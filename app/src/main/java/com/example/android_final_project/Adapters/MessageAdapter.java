package com.example.android_final_project.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android_final_project.ObjectsClasses.Message;
import com.example.android_final_project.R;

public class MessageAdapter extends BaseAdapter {

    //Activity mActivity;
    MyAds myAds;
    Context mContext;

    public MessageAdapter(Context mContext, MyAds myAds) {
        this.mContext = mContext;
        this.myAds = myAds;
    }

    @Override
    public int getCount() {
        return myAds.getMyAdsList().size();
    }

    @Override
    public Message getItem(int position) {
        return myAds.getMyAdsList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View oneAdsLine;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        oneAdsLine = inflater.inflate(R.layout.one_line_ads, parent,false);

        TextView textViewCreatedBy = oneAdsLine.findViewById(R.id.textViewcreatedBy1);
        TextView textViewText = oneAdsLine.findViewById(R.id.textViewText1);
        TextView textViewDate = oneAdsLine.findViewById(R.id.textViewDate);
        TextView textViewTime = oneAdsLine.findViewById(R.id.textViewTime);


        Message message = this.getItem(position);

        textViewCreatedBy.setText(message.getCreatedBy());
        textViewText.setText(message.getText());
        textViewDate.setText(message.getDate());
        textViewTime.setText(message.getTime());

        return oneAdsLine;
    }
}












