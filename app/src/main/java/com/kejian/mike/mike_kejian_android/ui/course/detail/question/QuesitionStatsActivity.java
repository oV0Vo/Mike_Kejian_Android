package com.kejian.mike.mike_kejian_android.ui.course.detail.question;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

import net.course.CourseQuestionNetService;

import java.util.ArrayList;
import java.util.List;

import dataType.course.question.QuestionAnswer;
import dataType.course.question.QuestionStats;
import util.UnImplementedAnnotation;

public class QuesitionStatsActivity extends AppCompatActivity {

    public static final String ARG_QUESTION_ID = "quesitonId";

    private String questionId;

    private ArrayList<QuestionAnswer> answers;

    private static final int ANSWER_INIT_NUM = 50;
    private static final int ANSWER_UPDATE_NUM = 10;

    private ProgressBar progressBar;

    private ViewGroup mainLayout;

    private ViewGroup statsTitleLayout;
    private ViewGroup statsContentLayout;

    private ViewGroup answerListTitleLayout;
    private ViewGroup answerContentListLayout;

    private ListView answerListView;

    private QuestionAnswerAdapter answerListAdapter;

    private int taskCountDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_stats);

        questionId = getIntent().getExtras().getString(ARG_QUESTION_ID);
        answers = new ArrayList<>();

        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        mainLayout = (ViewGroup)findViewById(R.id.main_layout);

        initQuestionContentView();

        initStatsLayout();

        initAnswerLayout();
    }

    @UnImplementedAnnotation
    private void initQuestionContentView() {

    }

    private void initStatsLayout() {
        statsContentLayout = (ViewGroup)findViewById(R.id.question_answer_stats_detail);
        statsTitleLayout = (ViewGroup)findViewById(R.id.question_answer_stats_title);
        final ImageView actionImageView = (ImageView)findViewById(R.id.stats_zhankai_image);
        statsTitleLayout.setOnClickListener(new View.OnClickListener() {

            private boolean isShow = false;

            @Override
            public void onClick(View v) {
                if(isShow) {
                    statsContentLayout.setVisibility(View.GONE);
                    actionImageView.setImageDrawable(getResources().getDrawable(R.drawable.down));
                } else {
                    statsContentLayout.setVisibility(View.VISIBLE);
                    actionImageView.setImageDrawable(getResources().getDrawable(R.drawable.up1));
                }
            }
        });

        new GetQuestionStatsTask().execute();
        taskCountDown++;
    }

    private void initAnswerLayout() {
        answerContentListLayout = (ViewGroup)findViewById(R.id.question_answer_all_question_container);
        answerListTitleLayout = (ViewGroup)findViewById(R.id.all_answer_title_layout);
        final ImageView actionImageView = (ImageView)findViewById(R.id.all_answer_zhankai_image);
        answerListTitleLayout.setOnClickListener(new View.OnClickListener() {

            private boolean isShow = false;

            @Override
            public void onClick(View v) {
                if (isShow) {
                    answerContentListLayout.setVisibility(View.GONE);
                    actionImageView.setImageDrawable(getResources().getDrawable(R.drawable.down));
                } else {
                    answerContentListLayout.setVisibility(View.VISIBLE);
                    actionImageView.setImageDrawable(getResources().getDrawable(R.drawable.up1));
                }
            }
        });

        answerListView = (ListView)findViewById(R.id.question_answer_answer_list);
        answerListAdapter = new QuestionAnswerAdapter(this, android.R.layout.simple_list_item_1,
                answers);
        answerListView.setAdapter(answerListAdapter);

        new InitQuestionAnswerTask().execute();
        taskCountDown++;
    }

    @UnImplementedAnnotation
    private void updateViewOnGetInitAnswers() {
        answerListAdapter.notifyDataSetChanged();
        showViewIfInitTaskFinish();
    }

    @UnImplementedAnnotation
    private void updateViewOnGetQuestionStats(QuestionStats stats) {
        showViewIfInitTaskFinish();
    }

    private void showViewIfInitTaskFinish() {
        if(taskCountDown == 0) {
            progressBar.setVisibility(View.GONE);
            mainLayout.setVisibility(View.VISIBLE);
        }
    }

    @UnImplementedAnnotation
    private void updateViewOnAnswerUpdated() {
        answerListAdapter.notifyDataSetChanged();
    }

    private class GetQuestionStatsTask extends AsyncTask<Void, Void, QuestionStats> {

        @Override
        protected QuestionStats doInBackground(Void... params) {
            return CourseQuestionNetService.getQuestionStats(questionId);
        }

        @Override
        protected void onPostExecute(QuestionStats stats) {
            taskCountDown--;
            updateViewOnGetQuestionStats(stats);
        }
    }

    private class InitQuestionAnswerTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            int beginPos = 0;
            int num = ANSWER_INIT_NUM;
            ArrayList<QuestionAnswer> initAnswers = CourseQuestionNetService.getQuestionAnswer
                    (questionId, beginPos, num);
            answers.addAll(initAnswers);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            taskCountDown--;
            updateViewOnAnswerUpdated();
        }
    }

    private class UpdateQuestionAnswerTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            int beginPos = answers.size();
            int num = ANSWER_UPDATE_NUM;
            ArrayList<QuestionAnswer> newAnswers = CourseQuestionNetService.getQuestionAnswer
                    (questionId, beginPos, num);
            answers.addAll(newAnswers);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            taskCountDown--;
            updateViewOnAnswerUpdated();
        }
    }

    private class QuestionAnswerAdapter extends ArrayAdapter<QuestionAnswer> {

        public QuestionAnswerAdapter(Context context, int resource, List<QuestionAnswer> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            if(convertView != null)
                return convertView;

            convertView = View.inflate(QuesitionStatsActivity.this, R.layout.
                    layout_question_answer_brief, null);//@null?
            QuestionAnswer answer = getItem(position);

            ImageView userImageView = (ImageView)convertView.findViewById(R.id.user_image);
            userImageView.setImageDrawable(null);

            TextView userNameText = (TextView)convertView.findViewById(R.id.user_name);
            userNameText.setText(answer.getStudentName());

            TextView answerContentText = (TextView)convertView.findViewById(R.id.answer_content);
            answerContentText.setText(answer.getAnswer());

            return convertView;
        }
    }
}
