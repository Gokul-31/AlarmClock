package com.example.alarmclock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TimePicker;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    Fragment f1;
    Fragment f2;
    Fragment f3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=findViewById(R.id.bottom_nav);

        f1=new AlarmFrag();
        f2=new TimerFrag();
        f3=new StopWatchFrag();

        getSupportFragmentManager().beginTransaction().replace(R.id.frag_tag,f1).commit();
        bottomNavigationView.setSelectedItemId(R.id.Alarm);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment frag=null;
                switch (item.getItemId()){
                    case R.id.Alarm:
                        frag=f1;
                        break;
                    case R.id.Timer:
                        frag=f2;
                        break;
                    case R.id.StopWatch:
                        frag=f3;
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_tag,frag).commit();
                return true;  //to select the item
            }
        });
    }
}