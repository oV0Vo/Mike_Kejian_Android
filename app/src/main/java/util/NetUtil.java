package util;

import android.graphics.drawable.Drawable;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by violetMoon on 2015/10/6.
 */
public class NetUtil {

    public static Drawable getImage(String url) {
        return null;
    }

    private static URL createUrl(String url) throws MalformedURLException {
        URL u = new URL(url);
        return u;
    }
}
