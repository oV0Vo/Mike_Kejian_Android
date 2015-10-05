package util;

import android.os.CountDownTimer;
import android.util.Log;

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
        Log.i("TimerThread", "start count down");
        timer.start();
        Log.i("TimerThread", "end count down");
    }
}
