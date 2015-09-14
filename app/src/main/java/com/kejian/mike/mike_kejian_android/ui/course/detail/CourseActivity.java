package com.kejian.mike.mike_kejian_android.ui.course.detail;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.course.detail.introduction.CourseIntroductionActivity;

import bl.CourseBLService;
import model.course.CourseBriefInfo;
import model.course.CourseDetailInfo;
import model.course.CourseModel;

public class CourseActivity extends AppCompatActivity implements
        AnnoucementFragment.OnAnnoucementClickListener,
        CourseBriefInfoFragment.OnCourseBriefSelectedListener {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        progressBar = (ProgressBar)findViewById(R.id.course_progress_bar);
        //progressBar.setVisibility(View.INVISIBLE);//我觉得后面应该设置个200ms这样才显示这个progressBar
        CourseBriefInfo currentCourseBrief = CourseModel.getInstance().getCurrentCourseBrief();
        if(currentCourseBrief != null) {
            String courseId = currentCourseBrief.getCourseId();
            new GetCourseDetailTask().execute(courseId);
        } else {
            Log.e("CourseActivity", "start with no currentCourse !!");
        }

    }

    private void initFragments() {
        initCourseBriefFragment();
        initCourseAnnoucementFragment();
        initPostAndQuestionLayoutFragment();
    }

    private void initCourseBriefFragment() {
        FragmentManager fm = getSupportFragmentManager();
        CourseBriefInfoFragment courseBriefFg = (CourseBriefInfoFragment)
                fm.findFragmentById(R.id.course_detail_brief_info_container);
        if(courseBriefFg == null) {
            courseBriefFg = new CourseBriefInfoFragment();
            fm.beginTransaction().add(R.id.course_detail_brief_info_container, courseBriefFg)
                    .commit();
        }
    }

    private void initCourseAnnoucementFragment() {
        FragmentManager fm = getSupportFragmentManager();
        AnnoucementFragment annoucemntFg = (AnnoucementFragment)
                fm.findFragmentById(R.id.course_detail_annoucement_container);
        if(annoucemntFg == null) {
            annoucemntFg = new AnnoucementFragment();
            fm.beginTransaction().add(R.id.course_detail_annoucement_container, annoucemntFg)
                    .commit();
        }
    }

    private void initPostAndQuestionLayoutFragment() {
        FragmentManager fm = getSupportFragmentManager();
        CommentsAreaFragment postsAndQuestionFg = (CommentsAreaFragment)
                fm.findFragmentById(R.id.course_detail_post_and_question_container);
        if(postsAndQuestionFg == null) {
            postsAndQuestionFg = new CommentsAreaFragment();
            fm.beginTransaction().add(R.id.course_detail_post_and_question_container, postsAndQuestionFg)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course, menu);
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

    @Override
    public void onAnnoucementClick() {
        Log.i("CourseActivity", "OnAnnoucementClick");
    }

    @Override
    public void showCourseDetail() {
        Intent startIntro = new Intent(this, CourseIntroductionActivity.class);
        startActivity(startIntro);
    }

    private class GetCourseDetailTask extends AsyncTask<String, Integer, CourseDetailInfo> {
        @Override
        public CourseDetailInfo doInBackground(String... params) {
            String courseId = params[0];
            CourseDetailInfo theCourse = CourseBLService.getInstance().getCourseDetail(courseId);
            return theCourse;
        }

        @Override
        public void onPostExecute(CourseDetailInfo result) {
            CourseModel.getInstance().setCurrentCourseDetail(result);
            progressBar.setVisibility(View.GONE);
            initFragments();
        }
    }
}
