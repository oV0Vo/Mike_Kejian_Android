package com.kejian.mike.mike_kejian_android.ui.course;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

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
    private TextView allCourseButton;
    private CourseListFragment listFragment;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment CourseListContainerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseListContainerFragment newInstance(String studentId) {
        CourseListContainerFragment fg = new CourseListContainerFragment();
        fg.studentId = studentId;
        return fg;
    }

    public CourseListContainerFragment() {
        // Required empty public constructor
    }

    private void createListFragment() {
        FragmentManager fm = getChildFragmentManager();
        CourseListFragment fg= (CourseListFragment)fm.findFragmentById(R.id.main_course_course_list);
        if(fg == null) {
            fg = CourseListFragment.newInstance(studentId);
            fm.beginTransaction().add(R.id.main_course_course_list, fg).commit();
        }
        listFragment = fg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_course_list_container, container, false);
        allCourseButton = (RadioButton)v.findViewById(R.id.main_course_all_course_button);
        myCourseButton = (RadioButton)v.findViewById(R.id.main_course_my_course_button);
        return v;
    }

}
