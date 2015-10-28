package util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by violetMoon on 2015/9/27.
 */
public class TimeFormat {

    private static final String TAG = "TimeFormat";

   // private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm");

    public static String toMinute(Date date) {
        StringBuilder strBuilder = new StringBuilder();

        int year = date.getYear();
        strBuilder.append(year);
        strBuilder.append("-");

        int month = date.getMonth();
        if(month < 10)
            strBuilder.append("0");
        strBuilder.append(month);
        strBuilder.append("-");

        int day = date.getDate();
        if(day < 10)
            strBuilder.append("0");
        strBuilder.append(day);
        strBuilder.append(" ");

        int hour = date.getHours();
        if(hour < 10)
            strBuilder.append("0");
        strBuilder.append(hour);
        strBuilder.append(":");

        int minute = date.getMinutes();
        if(minute < 10)
            strBuilder.append("0");
        strBuilder.append(minute);

        String result = strBuilder.toString();

        return result;
    }

    public static String toTime(Date date) {
        StringBuilder strBuilder = new StringBuilder();

        int hour = date.getHours();
        strBuilder.append(Integer.toString(hour));
        strBuilder.append(":");

        int minute = date.getMinutes();
        strBuilder.append(Integer.toString(minute));
        strBuilder.append(":");

        int seconds = date.getSeconds();
        strBuilder.append(Integer.toString(seconds));

        String result = strBuilder.toString();
        Log.i(TAG, "toTime " + result);

        return result;
    }

    public static String toSeconds(long millis) {
        millis /= 1000;
        long seconds = millis % 60;
        millis /= 60;
        long minutes = millis % 60;
        millis /= 60;
        long hours = millis % 24;
        millis /= 24;
        long days = millis;

        StringBuilder strBuilder = new StringBuilder();
        if(days != 0) {
            strBuilder.append(Long.toString(days) + "天 ");
        }
        if(hours != 0) {
            strBuilder.append(Long.toString(hours) + ":");
        }

        strBuilder.append(Long.toString(minutes) + ":");

        if(seconds/10 != 0) {
            strBuilder.append(Long.toString(seconds));
        } else {
            strBuilder.append("0" + Long.toString(seconds));
        }

        return strBuilder.toString();
    }

    public static String convertDateInterval(Date begin, Date endDate) {
        StringBuilder strBuilder = new StringBuilder();

        int by = begin.getYear();
        strBuilder.append(Integer.toString(by) + "/");

        int bm = begin.getMonth();
        append(strBuilder, bm);
        strBuilder.append("/");

        int bd = begin.getDate();
        append(strBuilder, bd);
        strBuilder.append(" ");

        int bh = begin.getHours();
        append(strBuilder, bh);
        strBuilder.append(":");

        int bminute = begin.getMinutes();
        append(strBuilder, bminute);
        strBuilder.append("-");

        int ey = endDate.getYear();
        if(by != ey)
            strBuilder.append(Integer.toString(ey) + "/");

        int em = endDate.getMonth();
        if(bm != em) {
            append(strBuilder, em);
            strBuilder.append("/");
        }

        int ed = endDate.getDate();
        if(bd != ed) {
            append(strBuilder, ed);
            strBuilder.append(" ");
        }

        int eh = endDate.getHours();
        append(strBuilder, eh);
        strBuilder.append(":");

        int eminute = endDate.getMinutes();
        append(strBuilder, eminute);

        return strBuilder.toString();
    }

    private static void append(StringBuilder strBuilder, int data) {
        if(data >= 10)
            strBuilder.append(Integer.toString(data));
        else
            strBuilder.append("0" + Integer.toString(data));
    }

}
