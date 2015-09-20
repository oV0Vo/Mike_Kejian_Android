package util;

import java.util.ArrayList;

/**
 * Created by violetMoon on 2015/9/19.
 */
public class StringUtil {
    public static String toString(ArrayList<String> strs, String seperator) {
        StringBuffer strBuf = new StringBuffer();
        for(String str: strs)
            strBuf.append(str).append(seperator);
        return strBuf.toString();
    }
}
