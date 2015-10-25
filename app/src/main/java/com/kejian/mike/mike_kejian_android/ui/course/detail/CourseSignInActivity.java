package com.kejian.mike.mike_kejian_android.ui.course.detail;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;

import net.course.CourseSignInNetService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.course.CourseModel;
import com.kejian.mike.mike_kejian_android.dataType.course.CourseSignInRecord;
import util.TimeFormat;
import util.TimerThread;

public class CourseSignInActivity extends AppCompatActivity {

    private static final String TAG = "CourseSignIn";

    private ViewGroup mainLayout;
    private ProgressBar progressBar;

    private TextView signInActionText;
    private TextView signInStatusText;

    private ListView historyListView;
    private HistorySignInAdapter historyAdapter;

    private int taskCountDown;

    private TimerThread timerThread;

    private TextView emptyText;

    private int notEmptyCount;

    private String currentNamingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_sign_in);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mainLayout = (ViewGroup)findViewById(R.id.main_layout);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        emptyText = (TextView)findViewById(R.id.empty_text);
        notEmptyCount = 2;

        taskCountDown++;
        new GetHistorySignInTask().execute();
        taskCountDown++;
        new GetCurrentNamingTask().execute();
    }

    private void updateViewOnGetHistoryRecord(ArrayList<CourseSignInRecord> records) {
        if(mainLayout == null) {
            return;
        }

        if(records == null) {
            Toast.makeText(this, R.string.net_disconnet, Toast.LENGTH_SHORT).show();
            notEmptyCount--;
            return;
        }

        if(records.size() == 0) {
            notEmptyCount--;
            return;
        }

        historyListView = (ListView)findViewById(R.id.course_sign_in_history_container);
        historyAdapter = new HistorySignInAdapter(this, android.R.layout.simple_list_item_1,
                records);
        historyListView.setAdapter(historyAdapter);

        UpdateIfAllTaskFinish();
    }
 
    private void updateViewOnGetCurrentSignRecord(CourseSignInRecord currentNaming) {
        if(mainLayout == null) {
            return;
        }

        if(currentNaming != null) {
            Log.i(TAG, "has current naming");

            currentNamingId = currentNaming.getNamingId();
            signInActionText = (TextView)findViewById(R.id.course_sign_in_sign_in_text);
            signInStatusText = (TextView)findViewById(R.id.status_text);

            boolean hasSignIn = currentNaming.isHasSignIn();
            if(hasSignIn) {
                setHasSignInView();
            } else {
                setNotSignInView();
            }

            TextView namingTimeText =(TextView)findViewById(R.id.course_sign_in_time_text);
            Date beginTime = currentNaming.getBeginTime();
            Date endTime = currentNaming.getEndTime();
            String timeStr = TimeFormat.convertDateInterval(beginTime, endTime);
            namingTimeText.setText(timeStr);

            TextView teacherNameText = (TextView)findViewById(R.id.
                    course_sign_in_teacher_text);
            String signInTeacher = currentNaming.getTeacherName();
            teacherNameText.setText(signInTeacher);

            final TextView leftTimeClock = (TextView)findViewById(R.id.left_time_text);
            long leftTime = currentNaming.getLeftMillis();
            CountDownTimer timer = new CountDownTimer(leftTime, 1000L) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if(leftTimeClock == null)
                        return;
                    leftTimeClock.setText(TimeFormat.toSeconds(millisUntilFinished));
                    if(millisUntilFinished < 10 * 1000)
                        leftTimeClock.setTextColor(getResources().getColor(R.color.red));
                }

                @Override
                public void onFinish() {
                    if(leftTimeClock == null)
                        return;
                    leftTimeClock.setText(TimeFormat.toSeconds(0));
                    leftTimeClock.setTextColor(getResources().getColor(R.color.black));
                    setNotLeftTimeText();
                }
            };
            timerThread = new TimerThread(timer);
            timerThread.start();

            ViewGroup currentNamingLayout = (ViewGroup)findViewById(R.id.current_naming_layout);
            currentNamingLayout.setVisibility(View.VISIBLE);

        } else {
            Log.i(TAG, "has no current naming");
            notEmptyCount--;
            TextView noCurrentNamingText = (TextView)findViewById(R.id.no_current_naming_text);
            noCurrentNamingText.setVisibility(View.VISIBLE);
        }

        UpdateIfAllTaskFinish();
    }

    private void setNotLeftTimeText() {
        signInActionText.setEnabled(false);
        signInActionText.setBackgroundColor(getResources().getColor(R.color.dark));
    }

    private void setNotSignInView() {
        signInActionText.setText(R.string.course_sign_in_sign_in_action);
        signInActionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                new SignInTask().execute(currentNamingId);
                signInActionText.setEnabled(false);
                signInActionText.setBackgroundColor(getResources().getColor(R.color.dark));
            }
        });

        signInStatusText.setBackground(getResources().getDrawable(R.drawable.dark_round));

    }

    private void setHasSignInView() {
        signInActionText.setText(R.string.course_sign_in_already_sign_in);
        signInActionText.setEnabled(false);
        signInActionText.setBackgroundColor(getResources().getColor(R.color.green));

        signInStatusText.setBackground(getResources().getDrawable(R.drawable.green_round));
    }

    private void UpdateIfAllTaskFinish() {
        if(taskCountDown == 0 && mainLayout != null) {
            progressBar.setVisibility(View.GONE);
            if(notEmptyCount != 0) {
                mainLayout.setVisibility(View.VISIBLE);
            } else {
                emptyText.setVisibility(View.VISIBLE);
            }
        }
    }

    private void updateViewOnSignInSuccess() {
        setHasSignInView();
    }

    private void updateViewOnSignInFailure() {
        Log.i(TAG, "updateViewOnSignInFailure");
        Toast.makeText(this, R.string.net_disconnet, Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.GONE);
        signInActionText.setBackgroundColor(getResources().getColor(R.color.green));
        signInActionText.setEnabled(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(timerThread != null) {
            try {
                timerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private class HistorySignInAdapter extends ArrayAdapter<CourseSignInRecord> {

        public HistorySignInAdapter(Context context, int resource, List<CourseSignInRecord> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.layout_history_sign_in, null);
            }

            CourseSignInRecord  r = getItem(position);

            Date beginTime = r.getBeginTime();
            Date endTime = r.getEndTime();
            String timeStr = TimeFormat.convertDateInterval(beginTime, endTime);
            TextView textView = (TextView)convertView.findViewById(R.id.history_course_sign_in_time);
            textView.setText(timeStr);

            String tn = r.getTeacherName();
            TextView teacherNameText = (TextView)convertView.findViewById(R.id.
                    history_course_sign_in_teacher);
            teacherNameText.setText(tn);

            ImageView signInImage = (ImageView)convertView.findViewById(R.id.
                    history_course_sign_in_state_image);
            TextView signInText = (TextView)convertView.findViewById(R.id.
                    history_course_sign_in_state_text);
            boolean hasSignIn = r.isHasSignIn();
            if(hasSignIn) {
                signInImage.setBackgroundResource(R.drawable.smile);
                signInText.setText(R.string.course_sign_in_already_sign_in);
                signInText.setTextColor(getResources().getColor(R.color.green));
            } else {
                signInImage.setBackgroundResource(R.drawable.bad_sad);
                signInText.setText(R.string.course_sign_in_miss);
                signInText.setTextColor(getResources().getColor(R.color.dark));
            }

            return convertView;
        }
    }

    private class GetHistorySignInTask extends AsyncTask<Void, Void, ArrayList<CourseSignInRecord>> {

        @Override
        protected  ArrayList<CourseSignInRecord> doInBackground(Void... params) {
            CourseModel courseModel = CourseModel.getInstance();
            String courseId = courseModel.getCurrentCourseId();
            return CourseSignInNetService.getSignInRecords(courseId);
        }

        @Override
        protected void onPostExecute(ArrayList<CourseSignInRecord> signInRecords) {
            taskCountDown--;
            updateViewOnGetHistoryRecord(signInRecords);
        }
    }

    private class GetCurrentNamingTask extends AsyncTask<Void, Void, CourseSignInRecord> {

        @Override
        protected CourseSignInRecord doInBackground(Void... params) {
            CourseModel courseModel = CourseModel.getInstance();
            String courseId = courseModel.getCurrentCourseId();
            return CourseSignInNetService.getCurrentSignInRecord(courseId);
        }

        @Override
        protected void onPostExecute(CourseSignInRecord currentNaming) {
            taskCountDown--;
            updateViewOnGetCurrentSignRecord(currentNaming);
        }
    }

    private class SignInTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String namingId = params[0];
            return CourseSignInNetService.signIn(namingId);
        }

        @Override
        protected void onPostExecute(Boolean signInSuccess) {
            if(signInSuccess) {
                updateViewOnSignInSuccess();
            } else {
                updateViewOnSignInFailure();
            }
        }
    }

}
