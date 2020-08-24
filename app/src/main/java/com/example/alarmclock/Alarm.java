package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class Alarm extends AppCompatActivity {

    Ringtone ringtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
         ringtone=RingtoneManager.getRingtone(getApplicationContext(),notification);
        ringtone.play();

    }

    public void dismiss(View view) {
        ringtone.stop();
        Global.setAlarmDone(true);
        onBackPressed();
    }
}