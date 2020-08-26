package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class Alarm extends AppCompatActivity {

    Ringtone ringtone;

    Handler h;
    Runnable r;

    Button[] disBt;
    int count=0;

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        startRingtone();
        h=new Handler();

        Toast.makeText(getApplicationContext(),"Click on dismiss 5 times to dismiss alarm",Toast.LENGTH_LONG).show();

        h=new Handler();
        disBt=new Button[5];
        disBt[0]=findViewById(R.id.dismiss1);
        disBt[1]=findViewById(R.id.dismiss2);
        disBt[2]=findViewById(R.id.dismiss3);
        disBt[3]=findViewById(R.id.dismiss4);
        disBt[4]=findViewById(R.id.dismiss5);

        disappear();

        r=new Runnable() {
            @Override
            public void run() {
                disappear();
                disBt[(int)(Math.random()*5)].setVisibility(View.VISIBLE);
                h.postDelayed(r,1500);
            }
        };

       r.run();
    }

    private void disappear() {
        for(int i=0;i<5;i++){
            disBt[i].setVisibility(View.INVISIBLE);
        }
    }

    public void dismiss(View view) {
        count++;
        if(count>=5) {
            h.removeCallbacks(r);
            stopRingtone();
            Global.setAlarmDone(true);
            onBackPressed();
        }
    }

    public void startRingtone(){
        if(mp==null){
            mp=MediaPlayer.create(getApplicationContext(),Global.songRes);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stop();
                }
            });
        }

    }
    public void stopRingtone(){
        stop();
        onBackPressed();
    }
    public void stop(){
        if(mp!=null){
            mp.release();
            mp=null;
        }
    }
}