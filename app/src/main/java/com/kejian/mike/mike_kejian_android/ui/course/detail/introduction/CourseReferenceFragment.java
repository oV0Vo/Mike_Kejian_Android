package com.kejian.mike.mike_kejian_android.ui.course.detail.introduction;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
        contentView.setText("龙珠超\n" +
                "简介：《龙珠超》（日文：ドラゴンボール超，英文：Dragon Ball Super）是根据日本漫画家鸟山明的代表作《龙珠》改编而成的电视动画，由原作者鸟山明担当原案");
        return v;
    }

}
