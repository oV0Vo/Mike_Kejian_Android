package com.kejian.mike.mike_kejian_android.ui.course.detail;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.course.CourseListContainerFragment;

import model.course.CourseBriefInfo;
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
        View v = inflater.inflate(R.layout.layout_course_brief, container, false);
        CourseBriefInfo courseBrief = CourseModel.getInstance().getCurrentCourseBrief();
        if(courseBrief == null) {
            Log.i("CourseBriefInfoFragment", "courseBrief null!");
            return v;
        }

        ImageView imageView = (ImageView)v.findViewById(R.id.course_brief_image);
        imageView.setImageResource(R.drawable.default_book);
        TextView nameView = (TextView)v.findViewById(R.id.course_brief_name);
        nameView.setText(courseBrief.getCourseName());
        TextView academyView = (TextView)v.findViewById(R.id.course_brief_academy);
        academyView.setText(courseBrief.getAcademyName());
        TextView processWeekView = (TextView)v.findViewById(R.id.course_brief_progress_week);
        processWeekView.setText("第" + courseBrief.getProgressWeek() + "周");

        v.setOnClickListener(new onViewClickListener());

        return v;
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
