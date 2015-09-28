package com.kejian.mike.mike_kejian_android.ui.course.detail;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.course.detail.question.CourseQuestionFragment;

public class QuestionAndPostsLayoutFragment extends Fragment {

    private ViewPager viewPager;
    private CommentsPostFragmentAdapter viewPagerAdapter;

    private RadioButton commentsButton;
    private RadioButton questionButton;

    public QuestionAndPostsLayoutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question_and_posts_layout, container, false);

        viewPagerAdapter = new CommentsPostFragmentAdapter(getChildFragmentManager());
        viewPager = (ViewPager)v.findViewById(R.id.comments_post_view_pager);
        viewPager.setAdapter(viewPagerAdapter);

        commentsButton = (RadioButton)v.findViewById(R.id.comments_area_button);
        commentsButton.setChecked(true);
        commentsButton.setTextColor(getResources().getColor(R.color.green));
        commentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentsButton.setTextColor(getResources().getColor(R.color.green));
                questionButton.setTextColor(getResources().getColor(R.color.black));
                viewPager.setCurrentItem(0);
            }
        });

        questionButton = (RadioButton)v.findViewById(R.id.course_question_area_button);
        questionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                questionButton.setTextColor(getResources().getColor(R.color.green));
                commentsButton.setTextColor(getResources().getColor(R.color.black));
                viewPager.setCurrentItem(1);
            }
        });

        return v;
    }

    private class CommentsPostFragmentAdapter extends FragmentStatePagerAdapter{

        public CommentsPostFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new CommentsAreaFragment();
                case 1:
                    return new CourseQuestionFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
