package util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by violetMoon on 2015/9/27.
 */
public class TimeFormat {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm");

    public static String toMinute(Date date) {
        checkDateFormat();
        String formatStr = dateFormat.format(date);
        return formatStr;
    }

    private static void checkDateFormat() {
        if (dateFormat == null)
            dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm");
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
}
