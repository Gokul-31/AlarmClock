package com.example.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmFrag extends Fragment implements TimePicker.OnTimeChangedListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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

    int hour;
    int min;

    // TODO: Rename and change types and number of parameters
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
        addBt = getView().findViewById(R.id.Al_set_btn);
        cancelBt = getView().findViewById(R.id.al_cancel_btn);

        alarmTimeShow = getView().findViewById(R.id.Al_time);
        alarmTime = Calendar.getInstance();

        if(Global.alarmDone){
            alarmTimeShow.setText(R.string.no_alarm_set);
            addBt.setText(R.string.add_alarm);
        }

        addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(first){
                    addBt.setText("Change");
                }

                Date d=alarmTime.getTime();
                alarmTimeShow.setText("Alarm time: "+new SimpleDateFormat("h:mm a").format(d));

                if(alarmTime.before(Calendar.getInstance())){
                    alarmTime.add(Calendar.DATE,1);
                }

                addAlarm(alarmTime);
            }
        });
        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmManager alarmManager=(AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                Intent intent =new Intent (getContext(),AlertReciever.class);
                PendingIntent pendingIntent=PendingIntent.getBroadcast(getContext(),1,intent,0);

                alarmManager.cancel(pendingIntent);

                Global.setAlarmDone(true);
                alarmTimeShow.setText(R.string.no_alarm_set);
                addBt.setText(R.string.add_alarm);
            }
        });

        tp.setOnTimeChangedListener(this);
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

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,alarmTime.getTimeInMillis(),pendingIntent);
    }

}