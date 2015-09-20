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

public class CourseTeachContentFragment extends Fragment {
    public CourseTeachContentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_course_teach_content, container, false);

        CourseDetailInfo courseDetail = CourseModel.getInstance().getCurrentCourseDetail();

        if(courseDetail == null) {
            Log.e("CourseBriefIntro", "current course detail null!");
            return v;
        }

        TextView contentView = (TextView)v.findViewById(R.id.course_teach_content_content);
        contentView.setText("电影《小岛惊魂》是由亚历桑德罗·阿曼巴执导，妮可·基德曼、伊莲·卡西迪、基思·艾伦主演的一部惊悚悬疑剧情片。该片讲述了二战结束后，在英伦的小岛上独自抚养着一对儿女，等待着丈夫归来的格蕾丝，迎来了三个古怪的新仆人的故事");
        return v;
    }

}
