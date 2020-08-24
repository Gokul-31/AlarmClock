package com.example.alarmclock;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class TimerFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public TimerFrag() {
        // Required empty public constructor
    }

    private static long TIME_START=60000;

    private TextView timerTv;
    private Button startPauseBt;
    private Button resetBt;
    Button setBt;

    NumberPicker minP;
    NumberPicker secP;

    CountDownTimer ct;
    boolean timerRun=false;
    long TIME_LEFT=TIME_START;

    // TODO: Rename and change types and number of parameters
    public static TimerFrag newInstance(String param1, String param2) {
        TimerFrag fragment = new TimerFrag();
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
        return inflater.inflate(R.layout.fragment_timer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        startPauseBt=getView().findViewById(R.id.tm_start);
        resetBt=getView().findViewById(R.id.tm_reset);
        timerTv=getView().findViewById(R.id.tm_timer);
        setBt=getView().findViewById(R.id.tm_set);

        minP=getView().findViewById(R.id.tm_picker_min);
        secP=getView().findViewById(R.id.tm_picker_sec);

        //setPickers
        minP.setMinValue(0);
        minP.setMaxValue(59);
        minP.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d",i);
            }
        });
        Log.i("Main", "onViewCreated: "+minP.getValue());

        secP.setMinValue(0);
        secP.setMaxValue(59);
        secP.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d",i);
            }
        });
        Log.i("Main", "onViewCreated: "+secP.getValue());

        setBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(minP.getValue()==0&&secP.getValue()==0){
                    Toast.makeText(getContext(),"Cant be set to 0",Toast.LENGTH_SHORT).show();
                }
                else {
                    TIME_START = ((minP.getValue() * 60) + secP.getValue()) * 1000;
                    TIME_LEFT = TIME_START;
                    updateText();
                }
            }
        });

        //set listeners
        startPauseBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!timerRun) {
                    //start functions
                    startPauseBt.setText(R.string.pause);
                    timerRun=true;
                    resetBt.setVisibility(View.VISIBLE);
                    setBt.setVisibility(View.INVISIBLE);
                    ct=new CountDownTimer(TIME_LEFT,1000) {
                        @Override
                        public void onTick(long l) {
                            TIME_LEFT=l;
                            updateText();
                        }

                        @Override
                        public void onFinish() {
                            Vibrator v= (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                            v.vibrate(2000);
                            AlertDialog.Builder a = new AlertDialog.Builder(getContext())
                                    .setTitle("Timer")
                                    .setMessage("Time OVER!!!")
                                    .setPositiveButton("Ok",null);
                            a.create().show();
                            resetBt.performClick();
                        }
                    }.start();
                }
                else{
                    //pause functions
                    startPauseBt.setText(R.string.start);
                    timerRun=false;
                    resetBt.setVisibility(View.VISIBLE);
                    setBt.setVisibility(View.VISIBLE);
                    ct.cancel();
                }
            }
        });

        resetBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //reset functions
                TIME_LEFT=TIME_START;
                updateText();
                ct.cancel();
                startPauseBt.setText(R.string.start);
                timerRun=false;
                resetBt.setVisibility(View.INVISIBLE);
                setBt.setVisibility(View.VISIBLE);
            }
        });

    }

    private void updateText() {
        int min= (int) (TIME_LEFT/60000);
        int sec=(int) ((TIME_LEFT)/1000)%60;

        String formatted=String.format(Locale.getDefault(),"%02d:%02d",min,sec);
        timerTv.setText(formatted);

    }
}