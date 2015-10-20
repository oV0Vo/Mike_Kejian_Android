package com.kejian.mike.mike_kejian_android.ui.util;

import model.user.Global;
import model.user.user;

/**
 * Created by violetMoon on 2015/10/17.
 */
public class GetUserInfoMock {

    public static String getSid() {
        return ((user)Global.getObjectByName("user")).getIdentify();
    }
}
