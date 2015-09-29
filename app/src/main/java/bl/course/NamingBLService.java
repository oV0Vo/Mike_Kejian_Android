package bl.course;

import android.os.AsyncTask;
import android.os.CountDownTimer;

import java.util.Date;
import java.util.Timer;
import java.util.ArrayList;

import model.course.data.CourseNamingRecord;
import util.NeedAsyncAnnotation;
import util.TimerThread;

/**
 * Created by violetMoon on 2015/9/28.
 */
public class NamingBLService {

    private static NamingBLService instance;

    private String theNamingCourseId;

    private long beginMillis;
    private long endMillis;
    private long leftMills;
    private TimerThread timerThread;

    private ArrayList<OnGetNamingResult> listeners;

    private NamingBLService() {

    }

    public static NamingBLService getInstance() {
        if(instance == null) {
            instance = new NamingBLService();
        }
        return instance;
    }

    public void beginNaming(Date endTime, String courseId) {
        beginMillis = System.currentTimeMillis();
        endMillis = endTime.getTime();
        leftMills = endMillis - beginMillis;
        theNamingCourseId = courseId;
        clearPreTimerRecord();

        CountDownTimer timer = createSecondCountDownTimer(leftMills);
            timerThread = new TimerThread(timer);
            timerThread.start();
        }

    private CountDownTimer createSecondCountDownTimer(long millis) {
        CountDownTimer timer = new CountDownTimer(millis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                leftMills = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                clearTimerData();
            }
        };
        return timer;
    }

    private void clearTimerData() {
        endMillis = 0;
        beginMillis = 0;
        leftMills = 0;
    }

    private void clearPreTimerRecord() {
        //namingRecord = null;
    }

    public boolean isOnNaming(String courseId) {
        return courseId.equals(theNamingCourseId);
    }

    public long getBeginTime() {
        return beginMillis;
    }

    public long getEndTime() {
        return beginMillis;
    }

    public long getLeftTime() {
        return leftMills;
    }

    @NeedAsyncAnnotation
    public CourseNamingRecord getCourseNamingResult(OnGetNamingResult listener) {
        return null;
    }

    private class GetNamingResultTask extends AsyncTask<Void, Integer, CourseNamingRecord> {

        @Override
        protected CourseNamingRecord doInBackground(Void... params) {
            return null;
        }
    }

    public interface OnGetNamingResult {
        public void onGetNamingResult(CourseNamingRecord namingRecord);
    }
}
