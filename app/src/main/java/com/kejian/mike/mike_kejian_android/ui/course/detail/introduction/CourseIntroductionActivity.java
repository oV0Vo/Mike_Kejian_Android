package com.kejian.mike.mike_kejian_android.ui.course.detail.introduction;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

import java.util.ArrayList;

import model.course.data.CourseBriefInfo;
import model.course.data.CourseDetailInfo;
import model.course.CourseModel;
import util.StringUtil;

public class CourseIntroductionActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private CourseContentAdpater viewPagerAdapter;

    private RadioButton briefTab;
    private RadioButton teachContentTab;
    private RadioButton referenceTab;

    private RadioButton currentTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_introduction);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContent();

    }

    private void setContent() {
        CourseDetailInfo courseDetail = CourseModel.getInstance().getCurrentCourseDetail();
        if(courseDetail == null) {
            Log.e("CourseIntro", "current CourseDetail null!");
            return;
        }

        CourseBriefInfo courseBrief = CourseModel.getInstance().getCurrentCourseBrief();
        if(courseBrief == null) {
            Log.e("CourseIntro", "current CourseBrief null!");
            return;
        }

        /*
        ImageView courseImage = (ImageView)findViewById(R.id.course_intro_course_image);
        courseImage.setBackground();
         */

        TextView courseTitleView = (TextView)findViewById(R.id.course_intro_course_name);
        courseTitleView.setText(courseBrief.getCourseName());

        TextView courseAcademyView = (TextView)findViewById(R.id.course_intro_course_academy);
        courseAcademyView.setText(courseBrief.getAcademyName());

        TextView courseTypeView = (TextView)findViewById(R.id.course_intro_course_type);
        String courseTypeText = courseDetail.getCourseType().toString();
        courseTypeView.setText(courseTypeText);

        TextView studentNumView = (TextView)findViewById(R.id.course_intro_student_num);
        String studentNumText = new Integer(courseDetail.getCurrentStudents()).toString();
        studentNumView.setText(studentNumText);

        TextView teacherNameView = (TextView)findViewById(R.id.course_intro_teacher_name);
        ArrayList<String> teacherNames = courseDetail.getTeacherNames();
        String teacherName = StringUtil.toString(teacherNames, " ");
        teacherNameView.setText(teacherName);

        TextView teacherBriefIntroView = (TextView)findViewById(R.id.course_intro_teacher_brief_intro);
        String teacherBriefIntro = "南京大学教授、博士生导师";
        teacherBriefIntroView.setText(teacherBriefIntro);

        initViewPager();
        initTabButton();
    }

    private void initViewPager() {
        viewPager = (ViewPager)findViewById(R.id.course_intro_detail_view_pager);
        viewPagerAdapter = new CourseContentAdpater(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void initTabButton() {
        briefTab = (RadioButton)findViewById(R.id.course_intro_brief_tab);
        briefTab.setChecked(true);
        currentTab = briefTab;
        briefTab.setTextColor(getResources().getColor(R.color.green));
        briefTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentTab != briefTab) {
                    currentTab.setTextColor(getResources().getColor(R.color.black));
                    briefTab.setTextColor(getResources().getColor(R.color.green));
                    currentTab = briefTab;
                    viewPager.setCurrentItem(0);
                }
            }
        });

        teachContentTab = (RadioButton)findViewById(R.id.course_intro_teach_content_tab);
        teachContentTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentTab != teachContentTab) {
                    currentTab.setTextColor(getResources().getColor(R.color.black));
                    teachContentTab.setTextColor(getResources().getColor(R.color.green));
                    currentTab = teachContentTab;
                    viewPager.setCurrentItem(1);
                }
            }
        });

        referenceTab = (RadioButton)findViewById(R.id.course_intro_reference_tab);
        referenceTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentTab != referenceTab) {
                    currentTab.setTextColor(getResources().getColor(R.color.black));
                    referenceTab.setTextColor(getResources().getColor(R.color.green));
                    currentTab = referenceTab;
                    viewPager.setCurrentItem(2);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_introduction, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class CourseContentAdpater extends FragmentStatePagerAdapter {

        public CourseContentAdpater(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new CourseBriefIntroFragment();
                case 1:
                    return new CourseTeachContentFragment();
                case 2:
                    return new CourseReferenceFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
