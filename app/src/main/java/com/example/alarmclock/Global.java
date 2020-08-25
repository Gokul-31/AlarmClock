package com.example.alarmclock;

import java.util.ArrayList;
import java.util.Calendar;

public class Global {
    static boolean alarmDone=false;

    static boolean[] days=new boolean[7];

    static Calendar alarm;

    static long TIMER_Time=60000;

    static ArrayList<LapClass> laps=new ArrayList<>();

    static int songRes;

    public static void setAlarmDone(boolean alarmDone) {
        Global.alarmDone = alarmDone;
    }

    public static void setAlarm(Calendar alarm) {
        Global.alarm = alarm;
    }

    public static Calendar getAlarm() {
        return alarm;
    }

    public static long getTIMER_Time() {
        return TIMER_Time;
    }

    public static void setTIMER_Time(long TIMER_Time) {
        Global.TIMER_Time = TIMER_Time;
    }
}
