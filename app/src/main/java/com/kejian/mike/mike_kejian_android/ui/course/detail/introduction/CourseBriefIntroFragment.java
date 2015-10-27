package com.kejian.mike.mike_kejian_android.ui.course.detail.introduction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

import com.kejian.mike.mike_kejian_android.dataType.course.CourseDetailInfo;
import model.course.CourseModel;


public class CourseBriefIntroFragment extends Fragment {

    private static final String TAG = "CourseOutlineFg";

    public CourseBriefIntroFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_course_brief_intro, container, false);

        CourseDetailInfo courseDetail = CourseModel.getInstance().getCurrentCourseDetail();

        if(courseDetail == null) {
            Log.e("CourseBriefIntro", "current course detail null!");
            return v;
        }

        TextView contentView = (TextView)v.findViewById(R.id.course_brief_intro_content);
        String outLine = courseDetail.getOutline();
        if(outLine == null) {
            Log.e(TAG, "outLine null!");
            showEmptyText(v);
        } else if (outLine.length() == 0) {
            showEmptyText(v);
        } else {
            contentView.setText(outLine);
        }
        return v;
    }

    private void showEmptyText(View v) {
        TextView emptyText = (TextView)v.findViewById(R.id.empty_text);
        emptyText.setVisibility(View.VISIBLE);
    }

}
