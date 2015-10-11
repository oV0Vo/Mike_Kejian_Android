package com.kejian.mike.mike_kejian_android.ui.course.detail.question;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
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
import com.kejian.mike.mike_kejian_android.ui.util.TextExpandListener;

import net.course.CourseQuestionNetService;

import java.util.ArrayList;
import java.util.List;

import dataType.course.UserTypeInCourse;
import dataType.course.question.BasicQuestion;
import dataType.course.question.CurrentQuestion;
import model.course.CourseModel;
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
            if(convertView != null) {
                return convertView;
            }

            convertView = getActivity().getLayoutInflater().inflate(
                    R.layout.layout_current_question, null);
            CurrentQuestion currentQuestion = getItem(position);

            String content = currentQuestion.getQuestion().getContent();
            TextView contentText = initContentText(convertView, content);

            initTextExpandLayout(convertView, contentText);

            UserTypeInCourse userType = courseModel.getUserTypeInCurrentCourse();
            TextView actionText = initActionText(convertView, userType,
                    currentQuestion.getQuestion().getQuestionId());

            long leftMillis = currentQuestion.getLeftMills();
            initLeftTimeText(convertView, actionText, leftMillis, userType);

            return convertView;
        }

        private TextView initContentText(View convertView, String content) {
            TextView contentText = (TextView)convertView.findViewById(R.id.current_question_content);
            contentText.setText(content);
            return contentText;
        }

        private void initTextExpandLayout(View convertView, TextView contentText) {
            ViewGroup zhankaiLayout = (ViewGroup)convertView.findViewById(R.id.zhankai_layout);
            TextView zhankaiText = (TextView)convertView.findViewById(R.id.zhankai_text);
            ImageView zhankaiImage = (ImageView)convertView.findViewById(R.id.zhankai_image);
            TextExpandListener textExpandListener = new TextExpandListener(contentText, zhankaiText,
                    zhankaiImage, 3);
            zhankaiLayout.setOnClickListener(textExpandListener);
        }

        private TextView initActionText(View convertView, UserTypeInCourse userType, String questionId) {
            TextView actionText = (TextView)convertView.findViewById(R.id.action_text);
            switch(userType) {
                case TEACHER:
                    actionText.setOnClickListener(new ShutDownQuestionClickListener(questionId, actionText));
                    actionText.setText(R.string.close_question_text);
                    break;
                case STUDENT:
                    actionText.setOnClickListener(new AnswerQuestionClickListener());
                    actionText.setText(R.string.answer_question_text);
                    break;
                case ASSISTANT:
                    actionText.setOnClickListener(new ShutDownQuestionClickListener(questionId, actionText));
                    actionText.setText(R.string.close_question_text);
                    break;
                case VISITOR:
                    actionText.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
            return actionText;
        }

        private void initLeftTimeText(View convertView, final TextView actionText, long leftMills,
                                      final UserTypeInCourse userType) {
            final TextView leftTimeText = (TextView)convertView.findViewById(R.id.
                    current_question_countdown_time_text);
            CountDownTimer timer = new CountDownTimer(leftMills, 1000L) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if(leftTimeText != null)
                        leftTimeText.setText(TimeFormat.toSeconds(millisUntilFinished));
                }

                @Override
                public void onFinish() {
                    if(leftTimeText != null)
                        leftTimeText.setTextColor(getActivity().getResources().getColor(R.color.my_red));
                    switch(userType) {
                        case TEACHER:
                            actionText.setOnClickListener(new ShowQuestionStatsClickListener());
                            break;
                        case STUDENT:
                            actionText.setText(R.string.question_finish_text);
                            actionText.setEnabled(false);
                            break;
                        case ASSISTANT:
                            actionText.setOnClickListener(new ShowQuestionStatsClickListener());
                            break;
                        case VISITOR:
                            break;
                        default:
                            break;
                    }
                }
            };
            appendNewTimer(timer);
        }
    }

    private class HistoryAdapter extends ArrayAdapter<BasicQuestion> {

        public HistoryAdapter(Context context, int resource, List<BasicQuestion> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView != null)
                return convertView;

            convertView = getActivity().getLayoutInflater().inflate(
                    R.layout.layout_history_question_brief, null);
            BasicQuestion question = getItem(position);

            TextView timeText = (TextView)convertView.findViewById(R.id.history_question_brief_time);
            timeText.setText(TimeFormat.toMinute(question.getQuestionDate()));

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

            TextView contentText = (TextView)convertView.findViewById(R.id.
                    history_question_brief_content);
            String content = question.getContent();
            contentText.setText(content);

            ViewGroup zhankaiLayout = (ViewGroup)convertView.findViewById(R.id.zhankai_layout);
            TextView zhankaiText = (TextView)convertView.findViewById(R.id.zhankai_text);
            ImageView zhankaiImage = (ImageView)convertView.findViewById(R.id.zhankai_image);
            TextExpandListener textExpandListener = new TextExpandListener(contentText, zhankaiText,
                    zhankaiImage, 2);
            zhankaiLayout.setOnClickListener(textExpandListener);

            return convertView;
        }
    }

    private class AnswerQuestionClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent i = new Intent(getActivity(), QuestionAnswerActivity.class);
            getActivity().startActivity(i);
        }
    }

    private class ShutDownQuestionClickListener implements View.OnClickListener {

        private String questionId;

        private TextView actionText;

        public ShutDownQuestionClickListener(String questionId, TextView actionText) {
            this.questionId = questionId;
            this.actionText = actionText;
        }

        @Override
        public void onClick(View v) {
            boolean shutDownSuccess = CourseQuestionNetService.shutDownQuestion(questionId);
            if(shutDownSuccess) {
                actionText.setText(R.string.show_question_stats);
                actionText.setOnClickListener(new ShowQuestionStatsClickListener());
            } else {
                Toast.makeText(getActivity(), R.string.close_question_error_message, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class ShowQuestionStatsClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent i = new Intent(getActivity(), QuesitionStatsActivity.class);
            //getActivity().startActivity(i);
        }
    }

    private class UpdateHistoryQuestionTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            ArrayList<BasicQuestion> updateInfos = courseModel.updateHistoryQuestions();
            taskCountDown--;
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
            taskCountDown--;
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
