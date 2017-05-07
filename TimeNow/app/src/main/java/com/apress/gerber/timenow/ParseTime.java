package com.apress.gerber.timenow;

import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.HashMap;

/**
 * Created by hriso on 5/3/2017.
 */

public class ParseTime {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String[] parse(String[] cityTimesInArray){
        TimeZone tz = TimeZone.getTimeZone("GMT+0");
        Calendar c = Calendar.getInstance(tz);
        HashMap nameTimeNow = new HashMap();
        for(int i=0;i<cityTimesInArray.length;i++){
            String[] city_time = cityTimesInArray[i].split("=");
            String city=city_time[0];
            String offSet=city_time[1];
            int currentHour;
            int minute=0;
            int minutes;
            int hour;
            if(offSet.contains(":")){
                String[] utcArr = offSet.split(":");
                hour=Integer.parseInt(utcArr[0]);
                minute=Integer.parseInt(utcArr[1]);
                if((currentHour=c.get(Calendar.HOUR_OF_DAY)+hour)>=24){
                    currentHour=currentHour-24;
                }else {
                    if ((currentHour = c.get(Calendar.HOUR_OF_DAY) + hour) <= 0) {
                        currentHour = 24 + currentHour;
                    }
                }
                if(hour<0){
                    minutes = c.get(Calendar.MINUTE)-minute;
                    if(minutes<0){
                        minutes=60-minute;
                        hour-=1;
                    }
                }
            }else {
                hour = Integer.parseInt(offSet);
                if((currentHour=c.get(Calendar.HOUR_OF_DAY)+hour)>=24){
                    currentHour=currentHour-24;
                }else {
                    if ((currentHour = c.get(Calendar.HOUR_OF_DAY) + hour) <= 0) {
                        currentHour = 24 + currentHour;
                    }
                }
            }
            minutes=c.get(Calendar.MINUTE)+minute;
            if(minutes>=60){
                minutes=minutes-60;
                currentHour+=1;
            }
            nameTimeNow.put(city,currentHour+":"+minutes);
           // System.out.println(nameTimeNow);
        }

        String times=nameTimeNow.toString().replace("{","").replace("}","");
        String ListCitiesAndTimes[] = times.split(",");
        return ListCitiesAndTimes;
    }
}
