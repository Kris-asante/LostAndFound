package com.example.krisperezcyrus.lostfound;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Util_time {

    public static String getPostTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.UK);
        sdf.setTimeZone(TimeZone.getTimeZone("Africa/Accra"));
        return sdf.format(new Date());
    }

    public static String getTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd''HH:mm:ss''", Locale.UK);
        sdf.setTimeZone(TimeZone.getTimeZone("Africa/Accra"));
        return sdf.format(new Date());
    }

    public static String getTimestampDifference(String time_posted){
        Log.d("TIME UTIL", "getTimestampDifference: getting timestamp difference.");

        String difference = "";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd''HH:mm:ss''", Locale.UK);
        sdf.setTimeZone(TimeZone.getTimeZone("Africa/Accra"));//google 'android list of timezones'
        Date today = c.getTime();
        sdf.format(today);
        Date timestamp;
        //final String photoTimestamp = time_posted;
        try{
            timestamp = sdf.parse(time_posted);
            difference = String.valueOf(Math.round(((today.getTime() - timestamp.getTime()) / 1000 / 60 / 60 / 24 )));
        }catch (ParseException e){
            Log.e("TIME UTIL", "getTimestampDifference: ParseException: " + e.getMessage() );
            difference = "0";
        }
        return difference;
    }

}
