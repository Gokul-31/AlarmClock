package com.example.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class AlertReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent Alarm=new Intent(context, com.example.alarmclock.Alarm.class);
        context.startActivity(Alarm);
    }
}
