package com.example.android_final_project.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_final_project.R;

public class MessageAdapterRecycler extends RecyclerView.Adapter<MessageAdapterRecycler.MyViewHolder> {

    private MyAds myAds;
    private Context mContext;

    public MessageAdapterRecycler(MyAds myAds, Context mContext) {
        this.myAds = myAds;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MessageAdapterRecycler.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.one_line_ads, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapterRecycler.MyViewHolder holder, int position) {
        holder.textViewCreatedBy.setText(myAds.getMyAdsList().get(position).getCreatedBy());
        holder.textViewText.setText(myAds.getMyAdsList().get(position).getText());
        holder.textViewDate.setText(myAds.getMyAdsList().get(position).getDate());
        holder.textViewTime.setText(myAds.getMyAdsList().get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return myAds.getMyAdsList().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCreatedBy, textViewText, textViewDate, textViewTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCreatedBy = itemView.findViewById(R.id.textViewcreatedBy1);
            textViewText = itemView.findViewById(R.id.textViewText1);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTime = itemView.findViewById(R.id.textViewTime);
        }
    }
}
