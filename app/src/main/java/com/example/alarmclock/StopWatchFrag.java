package com.example.alarmclock;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Time;
import java.util.Calendar;

public class StopWatchFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public StopWatchFrag() {
        // Required empty public constructor
    }

//    Calendar t;

    long time;

    Button startBt;
    Button resetBt;

    Handler h;
    Runnable runnable;

    boolean started=false;

    int min;
    int sec;
    int millisec;

    TextView minTv;
    TextView secTv;
    TextView milliSecTv;

    Calendar start;
    Calendar run;

    // TODO: Rename and change types and number of parameters
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stop_watch, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startBt=getView().findViewById(R.id.sp_start);
        resetBt=getView().findViewById(R.id.sp_reset);
        minTv=getView().findViewById(R.id.sp_min);
        secTv=getView().findViewById(R.id.sp_sec);
        milliSecTv=getView().findViewById(R.id.sp_milli_sec);
        h=new Handler();

        runnable= new Runnable() {
            @Override
            public void run() {

                run=Calendar.getInstance();
                time=(run.getTimeInMillis()-start.getTimeInMillis());

//                min=(int) (time/60000);
                sec=(int) (time/1000)%60;
                millisec=(int) (time%1000);

                String secFormatted=String.format("%02d",sec);
                String millisecFormatted=String.format("%03d",millisec);


                minTv.setText(""+(time/60000));
                secTv.setText(secFormatted);
//                if((time%1000)<10){
//                    milliSecTv.setText("00"+time%1000);
//                }
//                else if((time%1000)<100){
//                    milliSecTv.setText("0"+time%1000);
//                }
//                else{
//                    milliSecTv.setText(""+time%1000);
//                }
                milliSecTv.setText(millisecFormatted);

                h.postDelayed(runnable,1);
            }
        };


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
                    h.removeCallbacks(runnable);
                }
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
                h.removeCallbacks(runnable);
            }
        });
    }

    public long getMin(){
        return (time/1000)/60;
    }

    public long getSec(){
        return (time/1000)%60;
    }
    public long getMilliSec(){
        return time%1000;
    }

}