package com.kejian.mike.mike_kejian_android.ui.course.detail.introduction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.dataType.course.CourseBriefInfo;
import com.kejian.mike.mike_kejian_android.dataType.course.CourseDetailInfo;
import com.kejian.mike.mike_kejian_android.dataType.course.UserInterestInCourse;
import com.kejian.mike.mike_kejian_android.dataType.course.UserTypeInCourse;

import net.course.CourseInfoNetService;

import java.util.ArrayList;

import model.course.CourseModel;
import util.StringUtil;

public class CourseIntroductionActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private CourseContentAdpater viewPagerAdapter;

    private TextView assistantText;

    private RadioButton briefTab;
    private RadioButton teachContentTab;
    private RadioButton referenceTab;

    private CourseDetailInfo courseDetail;

    private TextView interestText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_introduction);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CourseModel courseModel = CourseModel.getInstance();
        courseDetail = courseModel.getCurrentCourseDetail();

        initCourseBrief();
        initTeacherInfo();
        initAssitantLayout();
        initTabButton();
        initViewPager();
        initInterestText();
    }

    private void initInterestText() {
        UserTypeInCourse userTypeInCourse = CourseModel.getInstance().getUserTypeInCurrentCourse();
        if(userTypeInCourse == UserTypeInCourse.VISITOR) {
            interestText = (TextView)findViewById(R.id.show_interest_text);
            new GetUserInterestTask().execute(courseDetail.getCourseId());
        }
    }

    private void initCourseBrief() {
        TextView courseTitleView = (TextView)findViewById(R.id.course_intro_course_name);
        courseTitleView.setText(courseDetail.getCourseName());

        TextView courseAcademyView = (TextView)findViewById(R.id.course_intro_course_academy);
        courseAcademyView.setText(courseDetail.getAccademyName());

        TextView courseTypeView = (TextView)findViewById(R.id.course_intro_course_type);
        String courseTypeText = courseDetail.getCourseType();
        courseTypeView.setText(courseTypeText);

        TextView studentNumView = (TextView)findViewById(R.id.course_intro_student_num);
        String studentNumText = new Integer(courseDetail.getCurrentStudents()).toString();
        studentNumView.setText(studentNumText);
    }

    private void initTeacherInfo() {
        TextView teacherNameView = (TextView)findViewById(R.id.course_intro_teacher_name);
        ArrayList<String> teacherNames = courseDetail.getTeacherNames();
        String teacherName = StringUtil.toString(teacherNames, " ");
        teacherNameView.setText(teacherName);

        TextView teacherBriefIntroView = (TextView)findViewById(R.id.course_intro_teacher_brief_intro);
        teacherBriefIntroView.setText("");

    }

    private void initAssitantLayout() {
        assistantText = (TextView)findViewById(R.id.assistant_text);
        String assistantNamesStr = StringUtil.toString(courseDetail.getAssistantNames(), "  ");
        assistantText.setText(assistantNamesStr);
    }

    private void initViewPager() {
        viewPager = (ViewPager)findViewById(R.id.course_intro_detail_view_pager);
        viewPagerAdapter = new CourseContentAdpater(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch(position) {
                    case 0:
                        briefTab.setChecked(true);
                        break;
                    case 1:
                        teachContentTab.setChecked(true);
                        break;
                    case 2:
                        referenceTab.setChecked(true);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initTabButton() {
        briefTab = (RadioButton)findViewById(R.id.course_intro_brief_tab);
        briefTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        briefTab.setChecked(true);

        teachContentTab = (RadioButton)findViewById(R.id.course_intro_teach_content_tab);
        teachContentTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });

        referenceTab = (RadioButton)findViewById(R.id.course_intro_reference_tab);
        referenceTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
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

    private void setAlreadyInterestView() {
        interestText.setText(R.string.already_interest);
        interestText.setEnabled(false);
        interestText.setBackgroundColor(getResources().getColor(R.color.dark));
    }

    private class GetUserInterestTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            String courseId = params[0];
            return CourseInfoNetService.getUserInterestInCourse(courseId);
        }

        protected  void onPostExecute(Integer interest) {
            if(interestText == null)
                return;
            interestText.setVisibility(View.VISIBLE);
            switch(interest) {
                case UserInterestInCourse.INTEREST:
                    interestText.setText(R.string.show_interest);
                    interestText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(CourseIntroductionActivity.this,
                                    R.string.show_interest_on_progress, Toast.LENGTH_LONG).show();
                            interestText.setEnabled(false);
                            interestText.setBackgroundColor(getResources().getColor(R.color.dark));
                            new ShowInterestTask().execute(courseDetail.getCourseId());
                        }
                    });
                    break;
                case UserInterestInCourse.NO_INTEREST:
                    setAlreadyInterestView();
                    break;
                case UserInterestInCourse.ERROR:
                    Toast.makeText(CourseIntroductionActivity.this, R.string.net_disconnet,
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private class ShowInterestTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String courseId = params[0];
            return CourseInfoNetService.showInterestToCourse(courseId);
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if(interestText == null)
                return;
            if(success)
                setAlreadyInterestView();
            else {
                Toast.makeText(CourseIntroductionActivity.this, R.string.net_disconnet,
                        Toast.LENGTH_SHORT).show();
                interestText.setBackgroundColor(getResources().getColor(R.color.green));
                interestText.setEnabled(true);
            }
        }
    }
}
