package com.kejian.mike.mike_kejian_android.config;

/**
 * Created by violetMoon on 2015/10/13.
 */

public class URLUtil {
    private static final String IP = "112.124.101.41";
    private static final int PORT = 80;
    private static final String WEB_MODULE_NAME = "mike_server_v02/";
    private static final String PREFIX = "index.php/Home";
    public static final String URL = "http://" + IP + ":" + Integer.toString(PORT) + "/" +
            PREFIX + "/";
}
