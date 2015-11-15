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
import util.StringUtil;


public class CourseReferenceFragment extends Fragment {

    private static final String TAG = "CourseRefrenceFg";

    public CourseReferenceFragment() {
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
            Log.e(TAG, "current course detail null!");
            return v;
        }

        TextView contentView = (TextView)v.findViewById(R.id.course_reference_content);
        String refrences = StringUtil.toString(courseDetail.getReferences(), "\n");
        if(refrences == null) {
            showEmptyText(v);
        } else if (refrences.isEmpty() || refrences.trim().equals("0")) {
            showEmptyText(v);
        } else {
            contentView.setText(refrences);
        }
        return v;
    }

    private void showEmptyText(View v) {
        TextView emptyText = (TextView)v.findViewById(R.id.empty_text);
        emptyText.setVisibility(View.VISIBLE);
    }

}
