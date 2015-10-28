package com.kejian.mike.mike_kejian_android.ui.course.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.course.detail.question.CourseQuestionFragment;

public class QuestionAndPostsLayoutFragment extends Fragment {

    private static final String TAG = "QuestionAndPostFg";

    private ViewPager viewPager;
    private CommentsPostFragmentAdapter viewPagerAdapter;

    private RadioButton commentsButton;
    private RadioButton questionButton;

    private CommentsAreaFragment postFg;

    private CourseQuestionFragment questionFg;

    public QuestionAndPostsLayoutFragment() {
        postFg = new CommentsAreaFragment();
        questionFg = new CourseQuestionFragment();
    }

    public void initView() {
        if(questionFg == null || postFg == null) {
            Log.i(TAG, "initView fg null");
            return;
        }
        Log.i(TAG, "initView");
        postFg.initView();
        questionFg.initView();
    }

    public void refreshView() {
        if(questionFg == null || postFg == null) {
            return;
        }
        postFg.refreshView();
        questionFg.refreshView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_question_and_posts_layout, container, false);

        initViewPager(contentView);

        initTabButton(contentView);

        return contentView;
    }

    private void initViewPager(View contentView) {
        viewPagerAdapter = new CommentsPostFragmentAdapter(getChildFragmentManager());
        viewPager = (ViewPager)contentView.findViewById(R.id.comments_post_view_pager);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch(position) {
                    case 0:
                        commentsButton.setChecked(true);
                        break;
                    case 1:
                        questionButton.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    private void initTabButton(View contentView) {
        commentsButton = (RadioButton)contentView.findViewById(R.id.comments_area_button);
        commentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        commentsButton.setChecked(true);

        questionButton = (RadioButton)contentView.findViewById(R.id.course_question_area_button);
        questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
    }

    private class CommentsPostFragmentAdapter extends FragmentPagerAdapter{

        public CommentsPostFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    if(postFg == null)
                        postFg = new CommentsAreaFragment();
                    return postFg;
                case 1:
                    if(questionFg == null)
                        questionFg = new CourseQuestionFragment();
                    return questionFg;
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
