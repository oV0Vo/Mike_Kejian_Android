package com.kejian.mike.mike_kejian_android.ui.course;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.kejian.mike.mike_kejian_android.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CourseListContainerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseListContainerFragment extends Fragment {

    private static final String ARG_STUDENT_ID = "studentId";

    private String studentId;

    private RadioButton myCourseButton;
    private RadioButton allCourseButton;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment CourseListContainerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseListContainerFragment newInstance(String studentId) {
        return new CourseListContainerFragment();
    }

    public CourseListContainerFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course_list_container, container, false);
    }

}
