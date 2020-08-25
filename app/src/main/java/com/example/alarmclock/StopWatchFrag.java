package com.example.alarmclock;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

public class StopWatchFrag extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public StopWatchFrag() {
        // Required empty public constructor
    }

    long time;
    ArrayList<LapClass> laps;

    Button startBt;
    Button resetBt;
    Button lapBt;

    Handler h;
    Runnable runnable;

    RecyclerView rView;
    LapAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    boolean started=false;
    boolean firstStart=true;

    int min;
    int sec;
    int millisec;

    TextView minTv;
    TextView secTv;
    TextView milliSecTv;

    long PAUSEOFFSET;

    Calendar start;
    Calendar run;

    String secFormatted;
    String millisecFormatted;

    public static StopWatchFrag newInstance(String param1, String param2) {
        StopWatchFrag fragment = new StopWatchFrag();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stop_watch, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startBt=getView().findViewById(R.id.sp_start);
        resetBt=getView().findViewById(R.id.sp_reset);
        lapBt=getView().findViewById(R.id.sp_lap);
        minTv=getView().findViewById(R.id.sp_min);
        secTv=getView().findViewById(R.id.sp_sec);
        milliSecTv=getView().findViewById(R.id.sp_milli_sec);
        rView=getView().findViewById(R.id.sp_recyclerView);
//        laps=new ArrayList<>();
        adapter=new LapAdapter(Global.laps);
        layoutManager=new LinearLayoutManager(getContext());
        rView.setLayoutManager(layoutManager);
        rView.setAdapter(adapter);

        if(started){
            startBt.setText(R.string.pause);
        }
        if(Global.laps.size()>0){
            adapter.notifyDataSetChanged();
        }


        h=new Handler();

        runnable= new Runnable() {
            @Override
            public void run() {

                run=Calendar.getInstance();
                time=(run.getTimeInMillis()-start.getTimeInMillis()+PAUSEOFFSET);

                sec=(int) (time/1000)%60;
                millisec=(int) (time%1000);

                secFormatted=String.format("%02d",sec);
                millisecFormatted=String.format("%03d",millisec);

                minTv.setText(""+(time/60000));
                secTv.setText(secFormatted);
                milliSecTv.setText(millisecFormatted);
                h.postDelayed(runnable,1);
            }
        };

        if(start!=null) {
            minTv.setText(""+(time/60000));
            secTv.setText(secFormatted);
            milliSecTv.setText(millisecFormatted);
        }

        startBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!started){
                    started=true;
                    startBt.setText(R.string.pause);
                    start=Calendar.getInstance();
                    runnable.run();
                }else{
                    started=false;
                    startBt.setText(R.string.start);
                    PAUSEOFFSET+=run.getTimeInMillis()-start.getTimeInMillis();
                    h.removeCallbacks(runnable);
                }
            }
        });

        lapBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LapClass l =new LapClass(time);
                Global.laps.add(l);
                layoutManager.scrollToPosition(adapter.getItemCount()-1);
            }
        });


        resetBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time=0;
                minTv.setText(R.string._0);
                secTv.setText(R.string.zero_xero);
                milliSecTv.setText(R.string.zero_zero_zero);
                startBt.setText(R.string.start);
                started=false;
                firstStart=true;
                h.removeCallbacks(runnable);
                PAUSEOFFSET=0;
                Global.laps.clear();
                adapter.clearAll();
            }
        });
    }

}