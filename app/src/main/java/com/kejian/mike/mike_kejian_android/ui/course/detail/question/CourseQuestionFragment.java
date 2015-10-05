package com.kejian.mike.mike_kejian_android.ui.course.detail.question;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;

import java.util.ArrayList;
import java.util.List;

import model.course.CourseModel;
import model.course.data.question.BasicQuestion;
import model.course.data.question.CurrentQuestion;
import util.TimeFormat;
import util.TimerThread;


public class CourseQuestionFragment extends Fragment {

    private ProgressBar progressBar;
    private ViewGroup mainLayout;

    private ListView currentListView;
    private CurrentAdapter currentAdapter;

    private ListView historyListView;
    private HistoryAdapter historyAdapter;

    private CourseModel courseModel;

    private int taskCountDown;

    private ArrayList<TimerThread> timerThreads;

    public CourseQuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseModel = CourseModel.getInstance();
        timerThreads = new ArrayList<TimerThread>();
        initCurrentAdpater();
        initHistoryAdapter();
    }

    private void initCurrentAdpater() {
        ArrayList<CurrentQuestion> currentQuestions = courseModel.getCurrentQuestions();
        if(currentQuestions.size() == 0) {
            taskCountDown += 1;
            new UpdateCurrentQuestionTask().execute();
        }

        currentAdapter = new CurrentAdapter(getActivity(), android.R.layout.simple_list_item_1,
                currentQuestions);
    }

    private void initHistoryAdapter() {
        ArrayList<BasicQuestion> historyQuestion = courseModel.getHistoryQuestions();
        if (historyQuestion.size() == 0) {
            taskCountDown += 1;
            new UpdateHistoryQuestionTask().execute();
        }

        historyAdapter = new HistoryAdapter(getActivity(), android.R.layout.simple_list_item_1,
                historyQuestion);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_course_question, container, false);

        mainLayout = (ViewGroup)v.findViewById(R.id.course_question_main_layout);
        progressBar = (ProgressBar)v.findViewById(R.id.course_question_progress_bar);
        if(taskCountDown != 0) {
            mainLayout.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            mainLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

        historyListView = (ListView)v.findViewById(R.id.history_question_list);
        historyListView.setAdapter(historyAdapter);

        currentListView = (ListView)v.findViewById(R.id.current_question_list_view);
        currentListView.setAdapter(currentAdapter);

        return v;
    }

    private void notifytTaskFinished() {
        taskCountDown -= 1;
        if(progressBar != null && taskCountDown == 0) {
            mainLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void clearTimerThreads() {
        if(timerThreads == null || timerThreads.size() == 0) {
            return;
        }
        for(Thread thread: timerThreads)
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        timerThreads.clear();
    }

    private void appendNewTimer(CountDownTimer timer) {
        Log.i("CourseQuestion", "append new clock");
        removeDeadCountDownThread();
        TimerThread newThread = new TimerThread(timer);
        timerThreads.add(newThread);
        newThread.start();
    }

    private void removeDeadCountDownThread() {
        for(int i=0; i<timerThreads.size(); ++i) {
            Thread t = timerThreads.get(i);
            if (!t.isAlive())
                timerThreads.remove(i);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        clearTimerThreads();
    }

    private class CurrentAdapter extends ArrayAdapter<CurrentQuestion> {

        public CurrentAdapter(Context context, int resource, List<CurrentQuestion> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(
                        R.layout.layout_current_question, null);
            }

            CurrentQuestion currentQuestion = getItem(position);

            TextView contentView = (TextView)convertView.findViewById(R.id.current_question_content);
            String content = currentQuestion.getQuestion().getContent();
            contentView.setText(content);

            final TextView leftTimeClock = (TextView)convertView.findViewById(R.id.
                    current_question_countdown_time_text);
            final long leftTime = currentQuestion.getLeftMills();
            CountDownTimer timer = new CountDownTimer(leftTime, 1000L) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if(leftTimeClock != null)
                        leftTimeClock.setText(TimeFormat.toSeconds(millisUntilFinished));
                }

                @Override
                public void onFinish() {
                   /* if(leftTimeClock != null)
                        leftTimeClock.setTextColor(getResources().getColor(R.color.my_red));*/
                }
            };

            appendNewTimer(timer);
            initZhanKaiButton(convertView, contentView, 3);

            return convertView;
        }
    }

    private class HistoryAdapter extends ArrayAdapter<BasicQuestion> {

        public HistoryAdapter(Context context, int resource, List<BasicQuestion> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(
                        R.layout.layout_history_question_brief, null);
            }

            BasicQuestion question = getItem(position);

            TextView timeView = (TextView)convertView.findViewById(R.id.history_question_brief_time);
            timeView.setText(TimeFormat.toMinute(question.getQuestionDate()));

            ImageView joinedView = (ImageView)convertView.findViewById(R.id.
                    history_question_brief_join_image);
            boolean hasJoined = question.IJoined();
            if(hasJoined) {
               joinedView.setImageResource(R.drawable.done);
            } else {
                joinedView.setImageResource(R.drawable.undo);
                TextView unJoinedTextView = (TextView)convertView.findViewById(R.id.
                        history_question_brief_join_text);
                unJoinedTextView.setVisibility(View.VISIBLE);
            }

            TextView contentView = (TextView)convertView.findViewById(R.id.
                    history_question_brief_content);
            String content = question.getContent();
            contentView.setText(content);

            initZhanKaiButton(convertView, contentView, 2);

            return convertView;
        }
    }

    private void initZhanKaiButton(View contentView, final TextView actionTextView,
                                   final int minLines) {
        ViewGroup zhankaiLayout = (ViewGroup)contentView.findViewById(R.id.zhankai_layout);
        final TextView textView = (TextView)zhankaiLayout.findViewById(R.id.zhankai_text);
        zhankaiLayout.setOnClickListener(new View.OnClickListener() {
            private boolean isZhankai = false;

            @Override
            public void onClick(View v) {
                if (isZhankai) {
                    isZhankai = false;
                    actionTextView.setMaxLines(minLines);
                    textView.setText(R.string.zhankai);
                    textView.setCompoundDrawables(null, null,
                            getResources().getDrawable(R.drawable.down), null);
                } else {
                    isZhankai = true;
                    actionTextView.setMaxLines(Integer.MAX_VALUE);
                    textView.setText(R.string.shouqi);
                    textView.setCompoundDrawables(null, null,
                            getResources().getDrawable(R.drawable.up1), null);
                }
            }
        });
    }

    private class UpdateHistoryQuestionTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            ArrayList<BasicQuestion> updateInfos = courseModel.updateHistoryQuestions();
            return updateInfos != null;
        }

        @Override
        protected void onPostExecute(Boolean updateSuccess) {
            historyAdapter.notifyDataSetChanged();
            notifytTaskFinished();
            if(!updateSuccess) {
                Toast.makeText(getActivity(), R.string.net_disconnet, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class UpdateCurrentQuestionTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            ArrayList<CurrentQuestion> updateInfos = courseModel.updateCurrentQuestions();
            return updateInfos != null;
        }

        @Override
        protected void onPostExecute(Boolean updateSuccess) {
            currentAdapter.notifyDataSetChanged();
            notifytTaskFinished();
            if(!updateSuccess) {
                Toast.makeText(getActivity(), R.string.net_disconnet, Toast.LENGTH_LONG).show();
            }
        }
    }

}
