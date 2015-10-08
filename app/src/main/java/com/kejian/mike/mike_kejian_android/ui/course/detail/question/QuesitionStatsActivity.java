package com.kejian.mike.mike_kejian_android.ui.course.detail.question;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.util.ColorBarFragment;

import net.course.CourseQuestionNetService;

import java.util.ArrayList;
import java.util.List;

import dataType.course.question.BasicQuestion;
import dataType.course.question.ChoiceQuestion;
import dataType.course.question.QuestionAnswer;
import dataType.course.question.QuestionStats;
import model.course.CourseModel;
import util.UnImplementedAnnotation;

public class QuesitionStatsActivity extends AppCompatActivity {

    public static final String ARG_QUESTION_ID = "quesitonId";

    private BasicQuestion question;

    private ArrayList<QuestionAnswer> answers;

    private CourseModel courseModel;

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

        answers = new ArrayList<>();
        courseModel = CourseModel.getInstance();
        question = courseModel.getFocusQuestion();

        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        mainLayout = (ViewGroup)findViewById(R.id.main_layout);

        initQuestionContentView();

        initStatsLayout();

        initAnswerLayout();
    }

    private void initQuestionContentView() {
        TextView questionContentText = (TextView)findViewById(R.id.question_content_text);
        questionContentText.setText(question.getContent());

        if(question instanceof ChoiceQuestion)
            setChoiceContentView();

        initContentZhankaiButton();
    }

    private void setChoiceContentView() {
        ViewGroup choiceContainer = (ViewGroup)findViewById(R.id.choice_container);

        ChoiceQuestion choiceQuestion = (ChoiceQuestion)question;
        ArrayList<String> choiceContents = choiceQuestion.getChoiceContents();
        for(int i=0; i<choiceContents.size(); ++i){
            ViewGroup choiceContentLayout = (ViewGroup)getLayoutInflater().inflate(R.layout.
                    layout_quesiton_choice_content, null);

            TextView choiceIndexText = (TextView)choiceContentLayout.findViewById(
                    R.id.choice_index_text);
            choiceIndexText.setText(Character.toString((char)('A' + i)));

            TextView choiceContentText = (TextView)choiceContentLayout.findViewById
                    (R.id.choice_content_text);
            choiceContentText.setText(choiceContents.get(i));

            choiceContainer.addView(choiceContentLayout);
        }

        choiceContainer.setVisibility(View.VISIBLE);
    }

    @UnImplementedAnnotation
    private void initContentZhankaiButton() {
        Button zhankaiButton = (Button)findViewById(R.id.question_answer_question_detail);
        zhankaiButton.setVisibility(View.GONE);
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
        ViewGroup statsContentLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.layout_question_stats, null);

        int joinNum = stats.getTotalAnswerNum();
        int totalNum = courseModel.getCurrentCourseDetail().getCurrentStudents();
        String joinNumStr = Integer.toString(joinNum) + "/" + Integer.toString(totalNum);
        TextView joinNumText = (TextView)statsContentLayout.findViewById(R.id.join_num_text);
        joinNumText.setText(joinNumStr);

        int colorBarWidth = (int)getResources().getDimension(R.dimen.color_bar_width);
        int colorBarHeight = (int)getResources().getDimension(R.dimen.color_bar_height);
        int redColor = getResources().getColor(R.color.my_red);
        int greenColor = getResources().getColor(R.color.green);

        double joinRate = ((double)joinNum) / totalNum;
        ColorBarFragment colorBarFragment = ColorBarFragment.getInstance(redColor, greenColor, joinRate,
                colorBarWidth, colorBarHeight);
        FragmentManager fm = getSupportFragmentManager();
        ViewGroup statsContainer = (ViewGroup)findViewById(R.id.stats_container);

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
            return CourseQuestionNetService.getQuestionStats(question.getQuestionId());
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
                    (question.getQuestionId(), beginPos, num);
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
                    (question.getQuestionId(), beginPos, num);
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
