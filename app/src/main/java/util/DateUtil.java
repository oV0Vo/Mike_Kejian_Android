package util;

import java.util.Date;

/**
 * Created by violetMoon on 2015/10/15.
 */
public class DateUtil {

    public static Date convertPhpTimeStamp(String timeStamp) {
        Date date = new Date();
        String[] parts = timeStamp.split(" ");
        String dateStr = parts[0];
        String[] dateStrs = dateStr.split("-");
        int year = Integer.parseInt(dateStrs[0]);
        int month = Integer.parseInt(dateStrs[1]);
        int day = Integer.parseInt(dateStrs[2]);
        String timeStr = parts[1];
        String[] timeStrs = timeStr.split(":");
        int hours = Integer.parseInt(timeStrs[0]);
        int minutes = Integer.parseInt(timeStrs[1]);
        int seconds = Integer.parseInt(timeStrs[2]);
        date.setYear(year);
        date.setMonth(month);
        date.setDate(day);
        date.setMinutes(minutes);
        date.setHours(hours);
        date.setSeconds(seconds);
        return date;
    }

    public static Date caculatePhpTime(Date date, int timeIncre) {
        Date newDate = new Date(date.getTime() + timeIncre);
        return newDate;
    }
}
