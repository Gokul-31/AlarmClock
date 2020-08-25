package com.example.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlertReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent Alarm=new Intent(context, com.example.alarmclock.Alarm.class);
        Alarm.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Alarm);
    }
}
