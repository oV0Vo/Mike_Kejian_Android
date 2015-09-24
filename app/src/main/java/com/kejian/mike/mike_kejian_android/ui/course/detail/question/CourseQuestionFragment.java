package com.kejian.mike.mike_kejian_android.ui.course.detail.question;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

import bl.CourseBLService;
import model.course.CourseDetailInfo;
import model.course.CourseModel;
import model.course.question.BasicQuestion;
import model.course.question.QuestionSet;


public class CourseQuestionFragment extends Fragment {

    private ProgressBar progressBar;
    private ViewGroup mainLayout;

    private CourseModel courseModel;
    private CourseBLService courseBL;

    public CourseQuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseModel = CourseModel.getInstance();
        courseBL = CourseBLService.getInstance();
    }

    private void updateView(QuestionSet questionSet) {
        if(questionSet == null || mainLayout == null)
            return;

        if(questionSet.getCurrentQuestions().size() != 0) {
            BasicQuestion currentQuestion = questionSet.getCurrentQuestions().get(0);
            long leftMills = questionSet.getCurrentQuestionLeftMills().get(0);

            TextView questionContent = (TextView)mainLayout.findViewById
                    (R.id.course_question_current_question_content);
            questionContent.setText(currentQuestion.getContent());
        }

        if(questionSet.getHistoryQuestions().size() != 0) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_course_question, container, false);

        mainLayout = (ViewGroup)v.findViewById(R.id.course_question_main_layout);
        mainLayout.setVisibility(View.INVISIBLE);

        progressBar = (ProgressBar)v.findViewById(R.id.course_question_progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        return v;
    }

    private void startGetQuestionTask() {
        new GetQuestionTask().execute();
    }

    private class GetQuestionTask extends AsyncTask<Void, Void, QuestionSet> {

        @Override
        protected QuestionSet doInBackground(Void... params) {
            if(courseBL != null && courseModel != null)
                return courseBL.getQuestion(courseModel.getCurrentCourseBrief().getCourseId());
            else {
                Log.e("CourseQuestion", "courseModel or courseBL null!");
                return null;
            }
        }

        @Override
        protected void onPostExecute(QuestionSet questionSet) {
            updateView(questionSet);
        }
    }

}
