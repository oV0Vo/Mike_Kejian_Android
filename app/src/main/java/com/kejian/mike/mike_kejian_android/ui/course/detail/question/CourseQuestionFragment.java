package com.kejian.mike.mike_kejian_android.ui.course.detail.question;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

import java.util.ArrayList;
import java.util.List;

import model.course.CourseModel;
import model.course.data.question.BasicQuestion;


public class CourseQuestionFragment extends Fragment {

    private ProgressBar progressBar;
    private ViewGroup mainLayout;

    private ListView listView;

    private CourseModel courseModel;


    public CourseQuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseModel = CourseModel.getInstance();
        ArrayList<BasicQuestion> historyQuestion = courseModel.getHistoryQuestions();
        if (historyQuestion.size() == 0) {

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

    private class QuestionAdpater extends ArrayAdapter<BasicQuestion> {

        public QuestionAdpater(Context context, int resource, List<BasicQuestion> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(
                        R.layout.layout_history_question_brief, null);
            }

            BasicQuestion question = getItem(position);

            return null;
        }

    }

    private class UpdateQuestionTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            ArrayList<BasicQuestion> updateInfos = courseModel.updateHistoryQuestions();
            return updateInfos != null;
        }

        @Override
        protected void onPostExecute(Boolean updateResutl) {

        }
    }

}
