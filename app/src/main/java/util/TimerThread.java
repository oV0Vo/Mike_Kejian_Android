package util;

import android.os.CountDownTimer;

/**
 * Created by violetMoon on 2015/9/28.
 */
public class TimerThread extends Thread{

    private CountDownTimer timer;

    public TimerThread(CountDownTimer timer) {
        super();
        this.timer = timer;
    }

    @Override
    public void run() {
        timer.start();
    }
}
