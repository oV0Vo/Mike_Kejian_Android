package com.kejian.mike.mike_kejian_android.ui.course.detail.introduction;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

import model.course.CourseDetailInfo;
import model.course.CourseModel;


public class CourseReferenceFragment extends Fragment {
    public CourseReferenceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_course_reference, container, false);

        CourseDetailInfo courseDetail = CourseModel.getInstance().getCurrentCourseDetail();

        if(courseDetail == null) {
            Log.e("CourseBriefIntro", "current course detail null!");
            return v;
        }

        TextView contentView = (TextView)v.findViewById(R.id.course_reference_content);
        contentView.setText("alalalallalalallalalalalalalala");
        return v;
    }

}
