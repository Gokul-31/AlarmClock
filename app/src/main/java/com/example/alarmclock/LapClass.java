package com.example.alarmclock;

public class LapClass {
    int min;
    int sec;
    int msec;

    public LapClass(long t){
        min=(int)(t/60000);
        sec=(int)((t/1000)%60);
        msec=(int) t%1000;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getSec() {
        return sec;
    }

    public void setSec(int sec) {
        this.sec = sec;
    }

    public int getMsec() {
        return msec;
    }

    public void setMsec(int msec) {
        this.msec = msec;
    }
}
