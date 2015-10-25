package com.kejian.mike.mike_kejian_android.ui.course.detail.naming;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.widget.ColorBar;

import net.course.CourseNamingNetService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kejian.mike.mike_kejian_android.dataType.course.CourseNamingRecord;
import com.kejian.mike.mike_kejian_android.ui.widget.TextExpandListener;

import model.course.CourseModel;
import util.NumberUtil;
import util.TimeFormat;
import util.TimerThread;

public class CourseNamingActivity extends AppCompatActivity {

    private static final String TAG = "CourseNamingActivity";

    private ViewGroup mainLayout;
    private ProgressBar progressBar;
    private ListView historyListView;
    private ArrayAdapter historyNamingAdapter;

    private TextView namingTimeTitleText;
    private TextView namingTimeText;
    private TextView leftTimeClock;
    private TextView namingActionText;

    private Dialog timeSetDialog;

    private TimerThread timerThread;

    private int taskCountDown;

    private CourseModel courseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_naming);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        courseModel = CourseModel.getInstance();

        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        mainLayout = (ViewGroup)findViewById(R.id.course_naming_main_layout);
        mainLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        taskCountDown++;
        new GetHistoryNamingTask().execute();
        taskCountDown++;
        new GetCurrentNamingTask().execute();
    }

    private void updateViewOnGetHistoryNaming(ArrayList<CourseNamingRecord> namingRecords) {
        if(mainLayout == null) {
            return;
        }

        historyListView = (ListView)findViewById(R.id.course_naming_history_list);
        historyNamingAdapter = new HistoryNamingAdapter(this, android.R.layout.simple_list_item_1,
                namingRecords);
        historyListView.setAdapter(historyNamingAdapter);

        updateViewIfAllTaskFinish();
    }

    private void updateViewOnGetCurrentNaming(CourseNamingRecord currentNaming) {
        if(progressBar == null)
            return;

        namingTimeTitleText = (TextView)findViewById(R.id.course_naming_time_title_text);
        namingTimeText = (TextView)findViewById(R.id.course_naming_time_text);
        leftTimeClock = (TextView)findViewById(R.id.left_time_text);
        namingActionText = (TextView)findViewById(R.id.naming_action_text);

        if(currentNaming != null) {
            setViewOnNaming(currentNaming);
        } else {
            setViewOnNamingNotStart();
        }

        updateViewIfAllTaskFinish();
    }

    private void setViewOnNaming(CourseNamingRecord currentNaming) {
        Date beginTime = currentNaming.getBeginTime();
        Date endTime = currentNaming.getEndTime();
        String timeStr = TimeFormat.convertDateInterval(beginTime, endTime);
        namingTimeText.setText(timeStr);

        namingTimeTitleText.setText(R.string.course_naming_type_qiandao);
        namingActionText.setText(R.string.course_naming_on_naming);
        namingActionText.setEnabled(false);
        long leftTime = currentNaming.getLeftMillis();
        startLeftTimeClock(leftTime);
    }

    private void startLeftTimeClock(long leftTime) {
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
                setViewOnNamingFinish();
            }
        };
        timerThread = new TimerThread(timer);
        timerThread.start();
    }

    private void setViewOnNamingNotStart() {
        namingActionText.setText(R.string.course_naming_naming_action);
        timeSetDialog = getTimeSetDialog();
        namingActionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSetDialog.show();
            }
        });
        namingTimeText.setText(R.string.course_naming_not_start);
        leftTimeClock.setText(R.string.course_naming_not_start_clock_text);
    }

    private void onTimeSet(int minute) {
        long millis = minute * 60 * 1000;
        new BeginNamingTask().execute(millis);
        namingActionText.setEnabled(false);
        namingActionText.setBackgroundColor(getResources().getColor(R.color.dark));
        Toast.makeText(CourseNamingActivity.this, R.string.on_send_naming_request,
                Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);
    }

    private Dialog getTimeSetDialog() {
        final Dialog dialog = new Dialog(this);
        View v = getLayoutInflater().inflate(R.layout.layout_time_dialog, null);
        final EditText continueTimeText = (EditText)v.findViewById(R.id.continue_time_text);
        continueTimeText.setText("5");
        continueTimeText.requestFocus();
        View confirmView = v.findViewById(R.id.confirm_text);
        confirmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable text = continueTimeText.getText();
                if (isCorrectMinuteFormat(text.toString())) {
                    dialog.dismiss();
                    onTimeSet(Integer.parseInt(text.toString()));
                } else {
                    text.clear();
                    Toast.makeText(CourseNamingActivity.this, R.string.check_time_message,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        View cancelView = v.findViewById(R.id.cancel_text);
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(v);

        return dialog;
    }

    private boolean isCorrectMinuteFormat(String minuteStr) {
        try {
            Integer minute = Integer.parseInt(minuteStr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void setViewOnNamingFinish() {
        if(namingActionText == null)
            return;

        namingActionText.setText(R.string.course_naming_finish);
        namingActionText.setBackgroundColor(getResources().getColor(R.color.dark));
        namingActionText.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        Toast.makeText(this, R.string.on_getting_naming_result, Toast.LENGTH_SHORT).show();
        new GetNamingResultTask().execute();
    }

    private void setViewOnBeginNamingSuccess(CourseNamingRecord namingRecord) {
        if(progressBar == null)
            return;
        progressBar.setVisibility(View.GONE);
        namingActionText.setText(R.string.course_naming_on_naming);
        namingActionText.setEnabled(false);
        namingActionText.setBackgroundColor(getResources().getColor(R.color.green));
        setViewOnNaming(namingRecord);
    }

    private void setViewOnBeginNamingFailure() {
        if(progressBar == null)
            return;

        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, R.string.net_disconnet, Toast.LENGTH_LONG).show();
        namingActionText.setBackgroundColor(getResources().getColor(R.color.green));
        namingActionText.setEnabled(true);

    }

    private void setViewOnGetNamingResult(CourseNamingRecord namingResult) {
        if(progressBar == null)
            return;

        if(namingResult == null)
            return;

        progressBar.setVisibility(View.GONE);

        View resultView = getLayoutInflater().inflate(R.layout.layout_course_naming_result, null);
        TextView statsText = (TextView)resultView.findViewById(R.id.stats_text);
        int signInNum = namingResult.getSignInNum();
        int totalNum = courseModel.getCurrentCourseDetail().getCurrentStudents();
        String statsStr = Integer.toString(signInNum) + "/" + Integer.toString(totalNum);
        statsText.setText(statsStr);

        ColorBar colorBar = ColorBar.getDefaultStyleColorBar(
                CourseNamingActivity.this, (((double) signInNum) / totalNum));
        ViewGroup colorBarContainer = (ViewGroup)findViewById(R.id.color_bar_container);
        colorBarContainer.addView(colorBar);

        double percent = signInNum / (double)totalNum;
        TextView percentText = (TextView)resultView.findViewById(R.id.percent_text);
        percentText.setText(Double.toString(NumberUtil.round(percent, 3) * 100));

        TextView absentListText = (TextView)resultView.findViewById(R.id.absent_list_text);
        String absentListStr = convertToString(namingResult.getAbsentNames(),
                namingResult.getAbsentIds());
        absentListText.setText(absentListStr);
        resultView.setVisibility(View.VISIBLE);

        initTextExpandLayout(resultView, absentListText);
    }

    private void initTextExpandLayout(View convertView, TextView contentText) {
        ViewGroup zhankaiLayout = (ViewGroup)convertView.findViewById(R.id.zhankai_layout);
        TextView zhankaiText = (TextView)convertView.findViewById(R.id.zhankai_text);
        ImageView zhankaiImage = (ImageView)convertView.findViewById(R.id.zhankai_image);
        TextExpandListener textExpandListener = new TextExpandListener(contentText, zhankaiText,
                zhankaiImage, 3);
        zhankaiLayout.setOnClickListener(textExpandListener);
    }


    private String convertToString(ArrayList<String> absentNames, ArrayList<String> ids) {
        StringBuilder strBuilder = new StringBuilder();
        for(int i=0; i<absentNames.size(); ++i) {
            String name = absentNames.get(i);
            String sid = ids.get(i);
            strBuilder.append(name);
            strBuilder.append("(");
            strBuilder.append(sid);
            strBuilder.append(")");
            if(i != absentNames.size())
                strBuilder.append("、");
        }
        return strBuilder.toString();
    }

    private void updateViewIfAllTaskFinish() {
        if(taskCountDown == 0 && mainLayout != null) {
            mainLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
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

    private class HistoryNamingAdapter extends ArrayAdapter<CourseNamingRecord> {

        public HistoryNamingAdapter(Context context, int resource, List<CourseNamingRecord> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            if(convertView != null) {
                return convertView;
            }

            convertView = getLayoutInflater().inflate(R.layout.layout_history_naming, null);
            CourseNamingRecord r = getItem(position);
            Date beginTime = r.getBeginTime();
            Date endTime = r.getEndTime();
            String timeStr = TimeFormat.convertDateInterval(beginTime, endTime);
            TextView textView = (TextView)convertView.findViewById(R.id.history_naming_time);
            textView.setText(timeStr);

            TextView statsText = (TextView)convertView.findViewById(R.id.stats_text);
            int signInNum = r.getSignInNum();
            int totalNum = courseModel.getCurrentCourseDetail().getCurrentStudents();
            String statsStr = Integer.toString(signInNum) + "/" + Integer.toString(totalNum);
            statsText.setText(statsStr);

            ColorBar colorBar = ColorBar.getDefaultStyleColorBar(
                    CourseNamingActivity.this, ((double) signInNum) / totalNum);
            ViewGroup colorBarContainer = (ViewGroup)convertView.findViewById(R.id.color_bar_container);
            colorBarContainer.addView(colorBar);

            TextView percentText = (TextView)convertView.findViewById(R.id.percent_text);
            percentText.setText("93.2%");
            return convertView;
        }
    }

    private class GetHistoryNamingTask extends AsyncTask<Void, Void, ArrayList<CourseNamingRecord>> {

        @Override
        protected ArrayList<CourseNamingRecord> doInBackground(Void... params) {
            String courseId = CourseModel.getInstance().getCurrentCourseId();
            return CourseNamingNetService.getNamingRecords(courseId);
        }

        @Override
        protected void onPostExecute(ArrayList<CourseNamingRecord> namingRecords) {
            taskCountDown--;
            updateViewOnGetHistoryNaming(namingRecords);
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
            updateViewOnGetCurrentNaming(currentNaming);
        }
    }

    private class BeginNamingTask extends AsyncTask<Long, Void, CourseNamingRecord> {

        @Override
        protected CourseNamingRecord doInBackground(Long... params) {
            CourseModel courseModel = CourseModel.getInstance();
            String courseId = courseModel.getCurrentCourseId();
            long time = params[0];
            return CourseNamingNetService.beginNaming(courseId, time);
        }

        @Override
        protected void onPostExecute(CourseNamingRecord namingResult) {
            if(namingResult != null)
                setViewOnBeginNamingSuccess(namingResult);
            else
                setViewOnBeginNamingFailure();
        }
    }

    private class GetNamingResultTask extends AsyncTask<Void, Void, CourseNamingRecord> {

        @Override
        protected CourseNamingRecord doInBackground(Void... params) {
            CourseModel courseModel = CourseModel.getInstance();
            String courseId = courseModel.getCurrentCourseId();
            return CourseNamingNetService.getCurrentNamingRecord(courseId);
        }

        @Override
        protected void onPostExecute(CourseNamingRecord currentNaming) {
            setViewOnGetNamingResult(currentNaming);
        }
    }

}
