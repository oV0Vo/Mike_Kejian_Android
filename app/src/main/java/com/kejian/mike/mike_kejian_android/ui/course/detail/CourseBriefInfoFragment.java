package com.kejian.mike.mike_kejian_android.ui.course.detail;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

import java.util.ArrayList;

import com.kejian.mike.mike_kejian_android.dataType.course.CourseBriefInfo;
import com.kejian.mike.mike_kejian_android.dataType.course.CourseDetailInfo;
import model.course.CourseModel;

/**
 */
public class CourseBriefInfoFragment extends Fragment {

    private OnCourseBriefSelectedListener mListener;

    public CourseBriefInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_course_brief_info, container, false);
        CourseDetailInfo courseDetailInfo = CourseModel.getInstance().getCurrentCourseDetail();

        ImageView imageView = (ImageView)v.findViewById(R.id.course_detail_brief_image);
        imageView.setImageResource(R.drawable.default_book);

        TextView academyView = (TextView)v.findViewById(R.id.course_detail_brief_academy);
        academyView.setText(courseDetailInfo.getAccademyName());

        TextView teacherView = (TextView)v.findViewById(R.id.course_detail_brief_teacher_text);
        String teacherNames = getMergeTeacherName();
        teacherView.setText(teacherNames);

        v.setOnClickListener(new onViewClickListener());

        return v;
    }

    private String getMergeTeacherName() {
        CourseDetailInfo courseDetail = CourseModel.getInstance().getCurrentCourseDetail();
        if(courseDetail != null) {
            ArrayList<String> teacherNames = courseDetail.getTeacherNames();
            String mergeName = new String();
            for(String teacherName: teacherNames)
                mergeName.concat(teacherName);
            return mergeName;
        } else {
            return "";
        }


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnCourseBriefSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnCourseBriefSelectedListener {
        public void showCourseDetail();
    }

    private class onViewClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(mListener != null)
                mListener.showCourseDetail();
        }
    }

}
