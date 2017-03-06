package com.guma.desarrollo.core;

import android.text.format.Time;

/**
 * Created by maryan.espinoza on 06/03/2017.
 */

public class Clock {
    public static String getNow() {
        Time now = new Time();
        now.setToNow();
        String sTime = now.format("%Y-%m-%d-%T");
        return sTime;
    }
    public static String getTimeStamp() {
        Time now = new Time();
        now.setToNow();
        String sTime = now.format("%Y-%m-%d %H:%M:%S");
        return sTime;
    }
}
