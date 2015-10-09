package com.kejian.mike.mike_kejian_android.ui.util;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

import util.TimeFormat;

/**
 * Created by violetMoon on 2015/10/9.
 */
public class MyCountDownTimer extends CountDownTimer{

    private Context context;

    private TextView countDownText;

    public MyCountDownTimer(Context context, TextView countDownText, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.context = context;
        this.countDownText = countDownText;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if(countDownText != null)
            countDownText.setText(TimeFormat.toSeconds(millisUntilFinished));
    }

    @Override
    public void onFinish() {
        if(countDownText != null)
            countDownText.setTextColor(context.getResources().getColor(R.color.my_red));
    }
}
