package com.kejian.mike.mike_kejian_android.ui.course.detail;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

import model.course.CourseDetailInfo;
import model.course.CourseModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CourseQuestionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CourseQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseQuestionFragment extends Fragment {

    public CourseQuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_course_question, container, false);

        ViewGroup currentQuestionView = (ViewGroup)v.findViewById(R.id.current_question);
        CourseDetailInfo courseDetail = CourseModel.getInstance().getCurrentCourseDetail();
    }


}
