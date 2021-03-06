package com.kejian.mike.mike_kejian_android.ui.user;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Looper;

/**
 * Created by kisstheraik on 15/9/25.
 */
public class UserUIError {

    private String title;
    private String errorDetail;

    public UserUIError(String title,String errorDetail,Context context){


        Looper.prepare();
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(errorDetail)
                .setPositiveButton("确定", null)
                .show();



    }
}
