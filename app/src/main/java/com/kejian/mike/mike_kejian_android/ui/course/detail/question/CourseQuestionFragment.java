package com.kejian.mike.mike_kejian_android.ui.course.detail.question;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.Layout;
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
import com.kejian.mike.mike_kejian_android.dataType.course.question.ChoiceQuestion;
import com.kejian.mike.mike_kejian_android.dataType.course.question.QuestionType;
import com.kejian.mike.mike_kejian_android.ui.widget.TextExpandListener;

import net.course.CourseQuestionNetService;

import java.util.ArrayList;
import java.util.List;

import com.kejian.mike.mike_kejian_android.dataType.course.UserTypeInCourse;
import com.kejian.mike.mike_kejian_android.dataType.course.question.BasicQuestion;
import com.kejian.mike.mike_kejian_android.dataType.course.question.CurrentQuestion;
import model.course.CourseModel;
import model.user.UserType;
import util.TimeFormat;
import util.TimerThread;


public class CourseQuestionFragment extends Fragment {

    private static final String TAG = "CourseQuestionFG";

    private ProgressBar progressBar;
    private ViewGroup mainLayout;
    private TextView historyEmptyText;

    private ViewGroup currentQuestionContainer;

    private ListView historyListView;
    private HistoryAdapter historyAdapter;

    private CourseModel courseModel;

    private int taskCountDown;

    private ArrayList<TimerThread> timerThreads;

    private boolean initFinish;

    public CourseQuestionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseModel = CourseModel.getInstance();
        timerThreads = new ArrayList<TimerThread>();
    }

    public void initView() {
        //by default do nothing
    }

    private void initCurrentQuestionContainer() {
        if(getContext() == null)
            return;

        ArrayList<CurrentQuestion> currentQuestions = courseModel.getCurrentQuestions();
        ViewGroup currentQuestionLayout = (ViewGroup)mainLayout.findViewById(R.id.
                current_question_layout);
        if(currentQuestions.size() != 0) {
            currentQuestionContainer.removeAllViews();
            clearTimerThreads();
            for(CurrentQuestion currentQuestion: currentQuestions) {
                View newQuestionView = createCurrentQuestionView(currentQuestion);
                currentQuestionContainer.addView(newQuestionView);
            }
            currentQuestionLayout.setVisibility(View.VISIBLE);
        } else {
            currentQuestionLayout.setVisibility(View.GONE);
        }
    }

    private View createCurrentQuestionView(CurrentQuestion currentQuestion) {
        View convertView = LayoutInflater.from(getContext()).inflate(R.layout.
                layout_current_question, null);

        TextView contentText = initContentText(convertView, currentQuestion.getQuestion());

        initTextExpandLayout(convertView, contentText);

        UserTypeInCourse userType = courseModel.getUserTypeInCurrentCourse();
        TextView actionText = initActionText(convertView, userType, currentQuestion.getQuestion());

        long leftMillis = currentQuestion.getLeftMills();
        initLeftTimeText(convertView, actionText, leftMillis, userType, currentQuestion.getQuestion());

        return convertView;
    }


    private TextView initContentText(View convertView, BasicQuestion questionInfo) {
        TextView contentText = (TextView)convertView.findViewById(R.id.current_question_content);
        String content = getQuestionContent(questionInfo);
        contentText.setText(content);
        return contentText;
    }

    private String getQuestionContent(BasicQuestion question) {
        String content = question.getContent();
        QuestionType questionType = question.getQuestionType();
        // 如果是选择题的话还应该加上选项内容
        if(questionType == QuestionType.单选题 || questionType == QuestionType.多选题) {
            StringBuilder strBuilder = new StringBuilder(content);
            ChoiceQuestion choiceQuestion = (ChoiceQuestion)question;
            ArrayList<String> choiceContents = choiceQuestion.getChoiceContents();
            for(int i=0; i<choiceContents.size(); ++i) {
                strBuilder.append("\n");
                strBuilder.append("  ");
                String indexStr = Character.toString((char)('A' + i));
                Log.i(TAG, "question choice " + indexStr);
                strBuilder.append(indexStr);
                strBuilder.append("  ");
                String choiceContent = choiceContents.get(i);
                strBuilder.append(choiceContent);
            }
            content = strBuilder.toString();
        }
        return content;
    }

    private void initTextExpandLayout(View convertView, TextView contentText) {
        ViewGroup zhankaiLayout = (ViewGroup)convertView.findViewById(R.id.zhankai_layout);
        TextView zhankaiText = (TextView)convertView.findViewById(R.id.zhankai_text);
        ImageView zhankaiImage = (ImageView)convertView.findViewById(R.id.zhankai_image);
        TextExpandListener textExpandListener = new TextExpandListener(contentText, zhankaiText,
                zhankaiImage, 3);
        zhankaiLayout.setOnClickListener(textExpandListener);
    }

    private TextView initActionText(View convertView, UserTypeInCourse userType,
                                    BasicQuestion question) {
        TextView actionText = (TextView)convertView.findViewById(R.id.action_text);
        switch(userType) {
            case TEACHER:
                actionText.setOnClickListener(new ShutDownQuestionClickListener(question, actionText));
                actionText.setText(R.string.shut_down_question_text);
                break;
            case STUDENT:
                actionText.setOnClickListener(new AnswerQuestionClickListener(question));
                actionText.setText(R.string.answer_question_text);
                break;
            case ASSISTANT:
                actionText.setOnClickListener(new ShutDownQuestionClickListener(question, actionText));
                actionText.setText(R.string.shut_down_question_text);
                break;
            case VISITOR:
                actionText.setVisibility(View.GONE);
                break;
            default:
                break;
        }
        return actionText;
    }

    private void initLeftTimeText(View convertView, TextView actionText, long leftMills,
                                  UserTypeInCourse userType, BasicQuestion question) {
        TextView leftTimeText = (TextView)convertView.findViewById(R.id.
                current_question_countdown_time_text);
        CountDownTimer timer = new MyCountDownTimer(leftTimeText, actionText, question,
                userType, leftMills, 1000L);
        appendNewTimer(timer);
    }

    private void initHistoryAdapter() {
        if(getContext() == null)
            return;

        ArrayList<BasicQuestion> historyQuestion = courseModel.getHistoryQuestions();
        if (historyQuestion.size() == 0) {
            taskCountDown += 1;
            new UpdateHistoryQuestionTask().execute();
        }

        historyAdapter = new HistoryAdapter(getActivity(), android.R.layout.simple_list_item_1,
                historyQuestion);
        historyListView.setAdapter(historyAdapter);
    }

    public void refreshView() {
        if(!initFinish)  //no need to refresh
            return;
        Log.i(TAG, "refreshView");
        taskCountDown += 1;
        new UpdateCurrentQuestionTask().execute();
        taskCountDown += 1;
        new UpdateHistoryQuestionTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        View v = inflater.inflate(R.layout.fragment_course_question, container, false);

        mainLayout = (ViewGroup)v.findViewById(R.id.course_question_main_layout);
        progressBar = (ProgressBar)v.findViewById(R.id.course_question_progress_bar);
        historyEmptyText = (TextView)v.findViewById(R.id.empty_text);
        if(taskCountDown != 0) {
            mainLayout.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            mainLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

        currentQuestionContainer = (ViewGroup)v.findViewById(R.id.current_question_container);
        historyListView = (ListView)v.findViewById(R.id.history_question_list);
        if(courseModel.getUserTypeInCurrentCourse() == null) {
            new InitUserTypeTask().execute();
        } else {
            initHistoryAdapter();
            taskCountDown++;
            new UpdateCurrentQuestionTask().execute();
        }

        return v;
    }

    private class InitUserTypeTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean updateSuccess = courseModel.updateUserTypeInCurrentCourse();
            if(updateSuccess)
                taskCountDown--;
            return updateSuccess;
        }

        @Override
        protected void onPostExecute(Boolean updateSuccess) {
            if(updateSuccess) {
                initHistoryAdapter();
                taskCountDown++;
                new UpdateCurrentQuestionTask().execute();
            }
        }
    }

    private void notifytTaskFinished() {
        if(taskCountDown == 0)
            initFinish = true;
        if(getContext() == null)
            return;

        mainLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
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

    private class HistoryAdapter extends ArrayAdapter<BasicQuestion> {

        public HistoryAdapter(Context context, int resource, List<BasicQuestion> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.i(TAG, "get history question view" + Boolean.toString(convertView == null) + Integer.toString(position));
            if(convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(
                        R.layout.layout_history_question_brief, null);
            }
            BasicQuestion question = getItem(position);

            TextView timeText = (TextView)convertView.findViewById(R.id.history_question_brief_time);
            timeText.setText(TimeFormat.toMinute(question.getQuestionDate()));

            UserTypeInCourse userTypeInCourse = courseModel.getUserTypeInCurrentCourse();
            if(userTypeInCourse == UserTypeInCourse.STUDENT) {
                ImageView joinedView = (ImageView)convertView.findViewById(R.id.
                        history_question_brief_join_image);
                boolean hasJoined = question.IJoined();
                if (hasJoined) {
                    joinedView.setImageResource(R.drawable.done);
                } else {
                    joinedView.setImageResource(R.drawable.undo);
                    TextView unJoinedTextView = (TextView) convertView.findViewById(R.id.
                            history_question_brief_join_text);
                    unJoinedTextView.setVisibility(View.VISIBLE);
                }
                joinedView.setVisibility(View.VISIBLE);
            }

            TextView contentText = (TextView)convertView.findViewById(R.id.
                    history_question_brief_content);
            String content = getQuestionContent(question);
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

        private BasicQuestion question;

        public AnswerQuestionClickListener(BasicQuestion question) {
            this.question = question;
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(getActivity(), QuestionAnswerActivity.class);
            courseModel.setAnswerFocusQuestion(question);
            getActivity().startActivity(i);
        }
    }

    private class ShutDownQuestionClickListener implements View.OnClickListener {

        private TextView actionText;

        private ShutDownQuestionTask shutDownTask;

        public ShutDownQuestionClickListener(BasicQuestion question, TextView actionText) {
            this.actionText = actionText;
            shutDownTask = new ShutDownQuestionTask(actionText, question);
        }

        @Override
        public void onClick(View v) {
            disableActionText();
            shutDownTask.execute();
        }

        private void disableActionText() {
            actionText.setText(R.string.shut_down_question_on_progress);
            actionText.setEnabled(false);
        }
    }

    private class ShowQuestionStatsClickListener implements View.OnClickListener {

        private BasicQuestion question;

        public ShowQuestionStatsClickListener(BasicQuestion question) {
            this.question = question;
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(getActivity(), QuestionStatsActivity.class);
            courseModel.setStatsFocusQuestion(question);
            getActivity().startActivity(i);
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
            if(getActivity() == null)
                return;

            notifytTaskFinished();

            if (!updateSuccess) {
                Toast.makeText(getActivity(), R.string.net_disconnet, Toast.LENGTH_LONG).show();
                Log.i(TAG, "UpdateHistoryQuestionTask net_disconnet");
            }

            if(historyAdapter.getCount() != 0) {
                if(historyListView.getVisibility() != View.VISIBLE) {
                    historyListView.setVisibility(View.VISIBLE);
                    historyEmptyText.setVisibility(View.GONE);
                    Log.i(TAG, "historyEmptyText GONE");
                } else {
                    Log.i(TAG, "???");
                }
                historyAdapter.notifyDataSetChanged();
            } else {
                historyListView.setVisibility(View.GONE);
                historyEmptyText.setVisibility(View.VISIBLE);
                Log.i(TAG, "historyEmptyText visible");
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
            notifytTaskFinished();
            if(updateSuccess) {
                initCurrentQuestionContainer();
            } else {
                if(getContext() != null)
                    Toast.makeText(getContext(), R.string.net_disconnet, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class ShutDownQuestionTask extends AsyncTask<Void, Void, Boolean> {

        private TextView actionText;

        private BasicQuestion question;

        public ShutDownQuestionTask(TextView actionText, BasicQuestion question) {
            this.actionText = actionText;
            this.question = question;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Boolean shutDownSuccess = CourseQuestionNetService.shutDownQuestion(question.getQuestionId());
            return shutDownSuccess;
        }

        @Override
        protected void onPostExecute(Boolean shutDownSuccess) {
            Log.i(TAG, "shut down question finish");
            if(shutDownSuccess) {
                Log.i(TAG, "shut down success");
                updateViewOnShutDownSuccess();
            } else {
                Log.i(TAG, "shut down fail");
                updateViewOnShutDownFail();
            }
        }

        private void updateViewOnShutDownSuccess() {
            actionText.setEnabled(true);
            actionText.setText(R.string.show_question_stats);
            actionText.setOnClickListener(new ShowQuestionStatsClickListener(question));
        }

        private void updateViewOnShutDownFail() {
            if(getActivity() == null)
                return;

            actionText.setEnabled(true);
            actionText.setText(R.string.shut_down_question_text);
            Toast.makeText(getActivity(), R.string.net_disconnet, Toast.LENGTH_LONG).show();
            Log.i(TAG, "updateViewOnShutDownFail net disconnet");
        }
    }

    private class MyCountDownTimer extends CountDownTimer {

        private TextView leftTimeText;

        private TextView actionText;

        private BasicQuestion question;

        private UserTypeInCourse userType;

        public MyCountDownTimer(TextView leftTimeText, TextView actionText, BasicQuestion question,
                                UserTypeInCourse userType, long totalTime, long interval) {
            super(totalTime, interval);
            this.leftTimeText = leftTimeText;
            this.actionText = actionText;
            this.question = question;
            this.userType = userType;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if(leftTimeText == null || getActivity() == null)
                return;
            leftTimeText.setText(TimeFormat.toSeconds(millisUntilFinished));
            if(millisUntilFinished < 10 * 1000)
                leftTimeText.setTextColor(getActivity().getResources().getColor(R.color.red));
        }

        @Override
        public void onFinish() {
            if(leftTimeText == null || getContext() == null)
                return;
            leftTimeText.setText(TimeFormat.toSeconds(0));
            leftTimeText.setTextColor(getResources().getColor(R.color.black));
            switch(userType) {
                case TEACHER:
                    actionText.setText(R.string.show_question_stats);
                    actionText.setOnClickListener(new ShowQuestionStatsClickListener(question));
                    break;
                case STUDENT:
                    actionText.setText(R.string.question_finish_text);
                    actionText.setEnabled(false);
                    break;
                case ASSISTANT:
                    actionText.setText(R.string.show_question_stats);
                    actionText.setOnClickListener(new ShowQuestionStatsClickListener(question));
                    break;
                case VISITOR:
                    break;
                default:
                    break;
            }
        }
    }

}
