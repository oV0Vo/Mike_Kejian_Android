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

        int bs = endDate.getSeconds();
        append(strBuilder, bs);
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

        int es = endDate.getSeconds();
        append(strBuilder, es);

        return strBuilder.toString();
    }

    private static void append(StringBuilder strBuilder, int data) {
        if(data >= 10)
            strBuilder.append(Integer.toString(data));
        else
            strBuilder.append("0" + Integer.toString(data));
    }

}
