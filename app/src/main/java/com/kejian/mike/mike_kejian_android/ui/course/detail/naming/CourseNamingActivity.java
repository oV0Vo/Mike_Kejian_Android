package com.kejian.mike.mike_kejian_android.ui.course.detail.naming;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
    private TextView historyEmptyText;
    private ListView historyListView;
    private ArrayAdapter historyNamingAdapter;

    private TextView namingTimeTitleText;
    private TextView namingTimeText;
    private TextView leftTimeClock;
    private TextView namingActionText;

    private ViewGroup namingResultLayout;

    private Dialog timeSetDialog;

    private TimerThread timerThread;

    private TimerThread getNamingResultThread;

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
        namingResultLayout = (ViewGroup)findViewById(R.id.naming_result_layout);
        historyEmptyText = (TextView)findViewById(R.id.history_empty_text);

        taskCountDown++;
        new GetHistoryNamingTask().execute();
        taskCountDown++;
        new GetCurrentNamingTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            case android.R.id.home:
                if(NavUtils.getParentActivityIntent(this) != null)
                    NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateViewOnGetHistoryNaming(ArrayList<CourseNamingRecord> namingRecords) {
        if(mainLayout == null) {
            return;
        }

        historyListView = (ListView)findViewById(R.id.course_naming_history_list);
        historyNamingAdapter = new HistoryNamingAdapter(this, android.R.layout.simple_list_item_1,
                namingRecords);
        historyListView.setAdapter(historyNamingAdapter);
        if(namingRecords == null) {
            return;
        }
        if(namingRecords.size() == 0) {
            historyListView.setVisibility(View.GONE);
            historyEmptyText.setVisibility(View.VISIBLE);
        }

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
        namingActionText.setTextColor(getResources().getColor(R.color.white));
        namingActionText.setEnabled(false);
        long leftTime = currentNaming.getLeftMillis();
        startLeftTimeClock(leftTime);
        startGettingNamingResult(leftTime);
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

    private void startGettingNamingResult(long leftTime) {
        CountDownTimer timer = new CountDownTimer(leftTime, 10000L) {

            @Override
            public void onTick(long millisUntilFinished) {
                new GetNamingResultTask().execute();
            }

            @Override
            public void onFinish() {
                new GetNamingResultTask().execute();
            }
        };
        getNamingResultThread = new TimerThread(timer);
        getNamingResultThread.start();
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
        Toast.makeText(CourseNamingActivity.this, R.string.on_send_naming_request,
                Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);
    }

    private Dialog getTimeSetDialog() {
        final Dialog dialog = new Dialog(this);
        View v = getLayoutInflater().inflate(R.layout.layout_time_dialog, null);
        final EditText continueTimeText = (EditText)v.findViewById(R.id.continue_time_text);
        continueTimeText.setText("8");
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
        namingActionText.setText(R.string.course_naming_finish);
        namingActionText.setEnabled(false);
    }

    private void setViewOnBeginNamingSuccess(CourseNamingRecord namingRecord) {
        if(progressBar == null)
            return;
        progressBar.setVisibility(View.GONE);
        setViewOnNaming(namingRecord);
    }

    private void setViewOnBeginNamingFailure() {
        if(progressBar == null)
            return;

        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, R.string.net_disconnet, Toast.LENGTH_SHORT).show();
        namingActionText.setEnabled(true);

    }

    private void setViewOnGetNamingResult(CourseNamingRecord namingResult) {
        if(progressBar == null)
            return;

        if(namingResult == null) {
            Log.e(TAG, "naming result null");
            return;
        }

        TextView signInNumText = (TextView)namingResultLayout.findViewById(R.id.sign_in_num_text);
        int signInNum = namingResult.getSignInNum();
        signInNumText.setText(Integer.toString(signInNum));

        TextView totalNumText = (TextView)namingResultLayout.findViewById(R.id.total_num_text);
        int totalNum = courseModel.getCurrentCourseDetail().getCurrentStudents();
        totalNumText.setText(Integer.toString(totalNum));

        ColorBar colorBar = ColorBar.getDefaultStyleColorBar(
                CourseNamingActivity.this, (((double) signInNum) / totalNum));
        ViewGroup colorBarContainer = (ViewGroup)namingResultLayout.findViewById(R.id.color_bar_container);
        colorBarContainer.addView(colorBar);

        double percent = signInNum / (double)totalNum;
        TextView percentText = (TextView)namingResultLayout.findViewById(R.id.percent_text);
        percentText.setText(Double.toString(NumberUtil.round(percent, 3) * 100) + "%");
        setTextColorAccordingToRate(percentText, percent);

        TextView absentListText = (TextView)namingResultLayout.findViewById(R.id.absent_list_text);
        String absentListStr = convertToString(namingResult.getAbsentNames(),
                namingResult.getAbsentIds());
        absentListText.setText(absentListStr);

        ViewGroup zhankaiLayout = (ViewGroup)namingResultLayout.findViewById(R.id.zhankai_layout);
        TextView zhankaiText = (TextView)namingResultLayout.findViewById(R.id.zhankai_text);
        ImageView zhankaiImage = (ImageView)namingResultLayout.findViewById(R.id.zhankai_image);
        TextExpandListener textExpandListener = new TextExpandListener(absentListText, zhankaiText,
                zhankaiImage, 3);
        zhankaiLayout.setOnClickListener(textExpandListener);

        namingResultLayout.setVisibility(View.VISIBLE);
    }

    private void setTextColorAccordingToRate(TextView rateText, double rate) {
        if (rate < 0.6)
            rateText.setTextColor(getResources().getColor(R.color.red));
        else
            rateText.setTextColor(getResources().getColor(R.color.green));
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
            if(i != (absentNames.size() - 1))
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
        if(timerThread != null && getNamingResultThread != null) {
            try {
                timerThread.join();
                getNamingResultThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "thread null onDestroy");
        }
    }

    private class HistoryNamingAdapter extends ArrayAdapter<CourseNamingRecord> {

        public HistoryNamingAdapter(Context context, int resource, List<CourseNamingRecord> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            NamingRecordViewHolder viewHolder = null;
            CourseNamingRecord record = getItem(position);
            int signInNum = record.getSignInNum();
            int totalNum = courseModel.getCurrentCourseDetail().getCurrentStudents();
            Log.i(TAG, "getView " + Integer.toString(position) + Boolean.toString(convertView == null));
            if(convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.layout_history_naming, null);
                viewHolder = new NamingRecordViewHolder();

                ColorBar colorBar = ColorBar.getDefaultStyleColorBar(
                        CourseNamingActivity.this, ((double) signInNum) / totalNum);
                ViewGroup colorBarContainer = (ViewGroup)convertView.findViewById(R.id.color_bar_container);
                colorBarContainer.addView(colorBar);
                viewHolder.colorBarContainer = colorBarContainer;

                viewHolder.timeText = (TextView)convertView.findViewById(R.id.history_naming_time);
                viewHolder.percentText = (TextView)convertView.findViewById(R.id.percent_text);
                viewHolder.statusText = (TextView)convertView.findViewById(R.id.stats_text);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (NamingRecordViewHolder)convertView.getTag();
            }

            Date beginTime = record.getBeginTime();
            Date endTime = record.getEndTime();
            String timeStr = TimeFormat.convertDateInterval(beginTime, endTime);
            viewHolder.timeText.setText(timeStr);

            String statsStr = Integer.toString(signInNum) + "/" + Integer.toString(totalNum);
            viewHolder.statusText.setText(statsStr);

            double joinRate = (totalNum != 0)? ((double)signInNum) / totalNum: 0.0;
            viewHolder.percentText.setText(Double.toString(NumberUtil.round(joinRate, 3) * 100) +
                    "%");
            setTextColorAccordingToRate(viewHolder.percentText, joinRate);

            return convertView;
        }

        private void setTextColorAccordingToRate(TextView rateText, double rate) {
            if (rate < 0.6)
                rateText.setTextColor(getResources().getColor(R.color.red));
            else
                rateText.setTextColor(getResources().getColor(R.color.green));
        }

    }

    static class NamingRecordViewHolder {
        ViewGroup colorBarContainer;
        TextView statusText;
        TextView percentText;
        TextView timeText;
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

        int count = 0;

        @Override
        protected CourseNamingRecord doInBackground(Void... params) {
            Log.i(TAG, "get naming result" + count++);
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
