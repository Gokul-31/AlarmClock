package com.example.alarmclock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LapAdapter extends RecyclerView.Adapter<LapAdapter.LapViewHolder> {

    private ArrayList<LapClass> mlaps;

    public static class LapViewHolder extends RecyclerView.ViewHolder{
        public TextView lapTv;
        public TextView lapTimeTV;
        public LapViewHolder(@NonNull View itemView) {
            super(itemView);
            lapTv=itemView.findViewById(R.id.spcard_lap);
            lapTimeTV=itemView.findViewById(R.id.spcard_time);
        }
    }

    public LapAdapter(ArrayList<LapClass> laps){
        mlaps=laps;
    }

    public void addLap(LapClass l){
        mlaps.add(l);
        notifyDataSetChanged();
    }

    public void clearAll(){
        mlaps.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lap_card,parent,false);
        LapViewHolder lvh=new LapViewHolder(v);
        return lvh;
    }

    @Override
    public void onBindViewHolder(@NonNull LapViewHolder holder, int position) {
        LapClass l=mlaps.get(position);
        holder.lapTimeTV.setText(String.format("%01d:%02d.%03d",l.getMin(),l.getSec(),l.getMsec()));
        holder.lapTv.setText("Lap: "+(position+1));
    }

    @Override
    public int getItemCount() {
        return mlaps.size();
    }
}
