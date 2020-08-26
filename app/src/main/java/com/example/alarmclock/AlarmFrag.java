package com.example.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmFrag extends Fragment implements TimePicker.OnTimeChangedListener{


    public AlarmFrag() {
        // Required empty public constructor
    }
    String TAG="log";

    TimePicker tp;
    Button cancelBt;
    Calendar alarmTime;
    Button addBt;
    TextView alarmTimeShow;
    boolean first=true;

    Button mBt;
    Button tBt;
    Button wBt;
    Button thBt;
    Button fBt;
    Button sBt;
    Button suBt;

    Spinner spinner;

    View.OnClickListener day_click;

    long WEEK_IN_MS;

    int hour;
    int min;

    public static AlarmFrag newInstance(String param1, String param2) {
        AlarmFrag fragment = new AlarmFrag();
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
        return inflater.inflate(R.layout.fragment_alarm, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tp=getView().findViewById(R.id.Al_timePicker);
        addBt = getView().findViewById(R.id.al_add_btn2);
        cancelBt = getView().findViewById(R.id.al_cancel_btn);

        mBt=getView().findViewById(R.id.al_mBt);
        tBt=getView().findViewById(R.id.al_tuBt);
        wBt=getView().findViewById(R.id.al_wBt);
        thBt=getView().findViewById(R.id.al_thBt);
        fBt=getView().findViewById(R.id.al_fBt);
        sBt=getView().findViewById(R.id.al_saBt);
        suBt=getView().findViewById(R.id.al_suBt);

        spinner=getView().findViewById(R.id.al_spinner);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(getContext(),R.array.songs,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Global.songRes=R.raw.alarm_rooster;
                        break;
                    case 1:
                        Global.songRes=R.raw.alarm_clock;
                        break;
                    case 2:
                        Global.songRes=R.raw.alarm_clock_2015;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Global.songRes=R.raw.alarm_rooster;
            }
        });

        WEEK_IN_MS =7*24*3600*1000;

        alarmTimeShow = getView().findViewById(R.id.Al_time);
        alarmTime = Calendar.getInstance();

        setDayListener();

        if(Global.alarmDone){
            alarmTimeShow.setText(R.string.no_alarm_set);
            addBt.setText(R.string.add_alarm);
        }

        if(Global.getAlarm()!=null){
            alarmTime=Global.getAlarm();
            Date d=alarmTime.getTime();
            alarmTimeShow.setText("Alarm time: "+new SimpleDateFormat("h:mm a").format(d));
        }

        addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(first){
                    addBt.setText("Change");
                }

                Date d=alarmTime.getTime();
                alarmTimeShow.setText("Alarm time: "+new SimpleDateFormat("h:mm a").format(d));

                Global.setAlarm(alarmTime);

                addAlarm(alarmTime);
            }
        });

        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Cancelled Alarm",Toast.LENGTH_SHORT);
                AlarmManager alarmManager=(AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                Intent intent =new Intent (getContext(),AlertReciever.class);
                PendingIntent pendingIntent=PendingIntent.getBroadcast(getContext(),1,intent,0);
                alarmManager.cancel(pendingIntent);


//                for(int i=0;i<7;i++) {
//                    PendingIntent pendingIntent=PendingIntent.getBroadcast(getContext(),i+1,intent,0);
//                    alarmManager.cancel(pendingIntent);
//                }

                Global.setAlarmDone(true);
                alarmTimeShow.setText(R.string.no_alarm_set);
                addBt.setText(R.string.add_alarm);
            }
        });

        tp.setOnTimeChangedListener(this);
    }

    private void setDayListener() {
        day_click=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.al_mBt:
                        if(!Global.days[0]){
                            view.setBackground(getResources().getDrawable(R.drawable.day_button_selected));
                            Global.days[0]=true;
                        }
                        else{
                            view.setBackground(getResources().getDrawable(R.drawable.day_button));
                            Global.days[0]=false;
                        }
                        break;
                    case R.id.al_tuBt:
                        if(!Global.days[1]){
                            view.setBackground(getResources().getDrawable(R.drawable.day_button_selected));
                            Global.days[1]=true;
                        }
                        else{
                            view.setBackground(getResources().getDrawable(R.drawable.day_button));
                            Global.days[1]=false;
                        }
                        break;
                    case R.id.al_wBt:
                        if(!Global.days[2]){
                            view.setBackground(getResources().getDrawable(R.drawable.day_button_selected));
                            Global.days[2]=true;
                        }
                        else{
                            view.setBackground(getResources().getDrawable(R.drawable.day_button));
                            Global.days[2]=false;
                        }
                        break;
                    case R.id.al_thBt:
                        if(!Global.days[3]){
                            view.setBackground(getResources().getDrawable(R.drawable.day_button_selected));
                            Global.days[3]=true;
                        }
                        else{
                            view.setBackground(getResources().getDrawable(R.drawable.day_button));
                            Global.days[3]=false;
                        }
                        break;
                    case R.id.al_fBt:
                        if(!Global.days[4]){
                            view.setBackground(getResources().getDrawable(R.drawable.day_button_selected));
                            Global.days[4]=true;
                        }
                        else{
                            view.setBackground(getResources().getDrawable(R.drawable.day_button));
                            Global.days[4]=false;
                        }
                        break;
                    case R.id.al_saBt:
                        if(!Global.days[5]){
                            view.setBackground(getResources().getDrawable(R.drawable.day_button_selected));
                            Global.days[5]=true;
                        }
                        else{
                            view.setBackground(getResources().getDrawable(R.drawable.day_button));
                            Global.days[5]=false;
                        }
                        break;
                    case R.id.al_suBt:
                        if(!Global.days[6]){
                            view.setBackground(getResources().getDrawable(R.drawable.day_button_selected));
                            Global.days[6]=true;
                        }
                        else{
                            view.setBackground(getResources().getDrawable(R.drawable.day_button));
                            Global.days[6]=false;
                        }
                        break;
                }
            }
        };
        mBt.setOnClickListener(day_click);
        tBt.setOnClickListener(day_click);
        wBt.setOnClickListener(day_click);
        thBt.setOnClickListener(day_click);
        fBt.setOnClickListener(day_click);
        sBt.setOnClickListener(day_click);
        suBt.setOnClickListener(day_click);
    }

    @Override
    public void onTimeChanged(TimePicker timePicker, int i, int i1) {
        alarmTime.set(Calendar.HOUR_OF_DAY, i);
        alarmTime.set(Calendar.MINUTE, i1);
        alarmTime.set(Calendar.SECOND, 0);
    }


    private void addAlarm(Calendar c) {

        AlarmManager alarmManager=(AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent =new Intent (getContext(),AlertReciever.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(getContext(),1,intent,0);
        alarmManager.cancel(pendingIntent);

//        if(c.before(Calendar.getInstance())){
//            c.add(Calendar.DATE,1);
//        }
//
//        for(int i=0;i<7;i++) {
//            PendingIntent pendingIntent=PendingIntent.getBroadcast(getContext(),i+1,intent,0);
//            alarmManager.cancel(pendingIntent);
//        }
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP,alarmTime.getTimeInMillis(),pendingIntent);

        for(int i=0;i<7;i++) {
            if(Global.days[i]) {
                c.set(Calendar.DAY_OF_WEEK,i+1);
                c.set(Calendar.SECOND,0);
                Log.i(TAG, "addAlarm: day:  "+(i+1));
                Log.i(TAG, "ms: "+c.getTimeInMillis());
//                PendingIntent pendingIntent=PendingIntent.getBroadcast(getContext(),i+1,intent,0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), WEEK_IN_MS  , pendingIntent);
            }
        }
    }

}