package com.kejian.mike.mike_kejian_android.ui.course.detail;

import android.content.Context;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;

import net.CourseNamingNetService;
import net.CourseSignInNetService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.course.CourseModel;
import model.course.data.CourseNamingRecord;
import model.course.data.CourseSignInRecord;
import util.TimeFormat;
import util.TimerThread;

public class CourseSignInActivity extends AppCompatActivity {

    private ViewGroup mainLayout;
    private ProgressBar progressBar;

    private TextView signInActionText;
    private TextView signInStatusText;

    private ListView historyListView;
    private HistorySignInAdapter historyAdapter;

    private int taskCountDown;

    private TimerThread timerThread;

    private String sidMock = "131250012";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_sign_in);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mainLayout = (ViewGroup)findViewById(R.id.main_layout);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        mainLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        taskCountDown++;
        new GetHistorySignInTask().execute();
        taskCountDown++;
        new GetCurrentNamingTask().execute();
    }

    private void updateView(ArrayList<CourseSignInRecord> records) {
        if(mainLayout == null) {
            return;
        }

        historyListView = (ListView)findViewById(R.id.course_sign_in_history_container);
        historyAdapter = new HistorySignInAdapter(this, android.R.layout.simple_list_item_1,
                records);
        historyListView.setAdapter(historyAdapter);

        UpdateIfAllTaskFinish();
    }

    private void updateView(CourseSignInRecord currentNaming) {
        if(mainLayout == null) {
            return;
        }

        if(currentNaming == null) {
            ViewGroup currentNamingLayout = (ViewGroup)findViewById(R.id.current_naming_layout);
            signInStatusText = (TextView)findViewById(R.id.course_sign_in_status_text);
            signInActionText = (TextView)findViewById(R.id.course_sign_in_sign_in_text);

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

            TextView teacherNameText = (TextView)currentNamingLayout.findViewById(R.id.
                    course_sign_in_teacher_text);
            String signInTeacher = currentNaming.getTeacherName();
            teacherNameText.setText(signInTeacher);

            final TextView leftTimeClock = (TextView)findViewById(R.id.left_time_text);
            long leftTime = endTime.getTime() - beginTime.getTime();
            CountDownTimer timer = new CountDownTimer(leftTime, 1000L) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if(leftTimeClock != null)
                        leftTimeClock.setText(TimeFormat.toSeconds(millisUntilFinished));
                }

                @Override
                public void onFinish() {
                    if(leftTimeClock != null)
                        leftTimeClock.setTextColor(getResources().getColor(R.color.my_red));
                }
            };
            timerThread = new TimerThread(timer);
            timerThread.start();

        } else {
            TextView noCurrentNamingText = (TextView)findViewById(R.id.no_current_naming_text);
            noCurrentNamingText.setVisibility(View.VISIBLE);
        }

        UpdateIfAllTaskFinish();
    }

    private void setNotSignInView() {
        signInStatusText.setText(R.string.course_sign_in_not_sign_in_status);
        //signInStatusText.setBackground(R.color.dark);

        signInActionText.setText(R.string.course_sign_in_sign_in_action);
        signInActionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                new SignInTask().execute();
            }
        });
    }

    private void setHasSignInView() {
        signInStatusText.setText(R.string.course_sign_in_already_sign_in);
        //signInStatusText.setBackground(R.color.green);

        signInActionText.setText(R.string.course_sign_in_already_sign_in);
        signInActionText.setEnabled(false);
    }

    private void UpdateIfAllTaskFinish() {
        if(taskCountDown == 0 && mainLayout != null) {
            progressBar.setVisibility(View.GONE);
            mainLayout.setVisibility(View.VISIBLE);
        }
    }

    private void updateViewOnSignInSuccess() {
        setHasSignInView();
    }

    private void updateViewOnSignInFailure() {
        Toast.makeText(this, R.string.net_disconnet, Toast.LENGTH_LONG).show();
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
                convertView = getLayoutInflater().inflate(R.layout.layout_history_naming, null);
            }

            CourseSignInRecord  r = getItem(position);

            Date beginTime = r.getBeginTime();
            Date endTime = r.getEndTime();
            String timeStr = TimeFormat.convertDateInterval(beginTime, endTime);
            TextView textView = (TextView)convertView.findViewById(R.id.course_sign_in_time_text);
            textView.setText(timeStr);

            String tn = r.getTeacherName();
            TextView teacherNameText = (TextView)convertView.findViewById(R.id.
                    course_sign_in_teacher_text);
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
            return CourseSignInNetService.getSignInRecords(sidMock, courseId);
        }

        @Override
        protected void onPostExecute(ArrayList<CourseSignInRecord> signInRecords) {
            taskCountDown--;
            updateView(signInRecords);
        }
    }

    private class GetCurrentNamingTask extends AsyncTask<Void, Void, CourseNamingRecord> {

        @Override
        protected CourseNamingRecord doInBackground(Void... params) {
            CourseModel courseModel = CourseModel.getInstance();
            String courseId = courseModel.getCurrentCourseId();
            return CourseNamingNetService.getCurrentNamingRecord(courseId);
        }

        @Override
        protected void onPostExecute(CourseNamingRecord currentNaming) {
            taskCountDown--;
            //updateView(currentNaming);
        }
    }

    private class SignInTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            CourseModel courseModel = CourseModel.getInstance();
            String courseId = courseModel.getCurrentCourseId();
            return CourseSignInNetService.signIn("", "");
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
