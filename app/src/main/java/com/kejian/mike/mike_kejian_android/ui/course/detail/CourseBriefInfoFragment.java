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
import util.GetBitmapByPinyin;
import util.StringUtil;

/**
 */
public class CourseBriefInfoFragment extends Fragment {

    private static final String TAG = "CourseBriefInfoFg";

    private OnCourseBriefSelectedListener mListener;

    private View layoutView;

    public CourseBriefInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initView() {
        if(layoutView == null) {
            return;
        }
        CourseDetailInfo courseDetailInfo = CourseModel.getInstance().getCurrentCourseDetail();

        ImageView imageView = (ImageView)layoutView.findViewById(R.id.course_detail_brief_image);
        GetBitmapByPinyin.getBitmapByPinyin(courseDetailInfo.getCourseName(), getContext(),
                imageView);

        TextView academyView = (TextView)layoutView.findViewById(R.id.course_detail_brief_academy);
        academyView.setText(courseDetailInfo.getAccademyName());

        TextView teacherView = (TextView)layoutView.findViewById(R.id.course_detail_brief_teacher_text);
        ArrayList<String> teacherNames = courseDetailInfo.getTeacherNames();
        String teacherNameStr = StringUtil.toString(teacherNames, " ");
        teacherView.setText(teacherNameStr);

        TextView timeAndPlaceText = (TextView)layoutView.findViewById(R.id.time_and_place_text);
        timeAndPlaceText.setText(courseDetailInfo.getTimeAndPlace());

        layoutView.setOnClickListener(new onViewClickListener());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_course_brief_info, container, false);
        return layoutView;
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
