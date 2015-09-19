package com.kejian.mike.mike_kejian_android.ui.course.detail;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.ActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.course.detail.introduction.CourseIntroductionActivity;
import com.kejian.mike.mike_kejian_android.ui.course.detail.menu.StudentActionProvider;
import com.kejian.mike.mike_kejian_android.ui.course.detail.menu.TeacherActionProvider;
import com.kejian.mike.mike_kejian_android.ui.course.detail.naming.CourseNamingActivity;
import com.kejian.mike.mike_kejian_android.ui.course.management.AnnoucementPublishActivity;

import bl.CourseBLService;
import bl.UserInfoService;
import model.course.CourseBriefInfo;
import model.course.CourseDetailInfo;
import model.course.CourseModel;
import model.user.UserType;

public class CourseActivity extends AppCompatActivity implements
        AnnoucementFragment.OnAnnoucementClickListener,
        CourseBriefInfoFragment.OnCourseBriefSelectedListener,
        StudentActionProvider.OnStudentMenuSelectListener,
        TeacherActionProvider.OnTeacherMenuSelectListener{

    private ProgressBar progressBar;
    private LinearLayout mainLayout;
    private CourseBriefInfoFragment courseBriefFg;
    private AnnoucementFragment annoucemntFg;
    private QuestionAndPostsLayoutFragment postsAndQuestionFg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mainLayout = (LinearLayout)findViewById(R.id.course_detail_main_layout);
        mainLayout.setVisibility(View.GONE);
        progressBar = (ProgressBar)findViewById(R.id.course_progress_bar);
        CourseBriefInfo currentCourseBrief = CourseModel.getInstance().getCurrentCourseBrief();
        if(currentCourseBrief != null) {
            String title = currentCourseBrief.getCourseName();
            this.setTitle(title);

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
        courseBriefFg = (CourseBriefInfoFragment)
                fm.findFragmentById(R.id.course_detail_brief_info_container);
        if(courseBriefFg == null) {
            courseBriefFg = new CourseBriefInfoFragment();
            fm.beginTransaction().replace(R.id.course_detail_brief_info_container, courseBriefFg)
                    .commit();
        }
    }

    private void initCourseAnnoucementFragment() {
        FragmentManager fm = getSupportFragmentManager();
        annoucemntFg = (AnnoucementFragment)
                fm.findFragmentById(R.id.course_detail_annoucement_container);
        if(annoucemntFg == null) {
            annoucemntFg = new AnnoucementFragment();
            fm.beginTransaction().replace(R.id.course_detail_annoucement_container, annoucemntFg)
                    .commit();
        }
    }

    private void initPostAndQuestionLayoutFragment() {
        FragmentManager fm = getSupportFragmentManager();
        postsAndQuestionFg = (QuestionAndPostsLayoutFragment)
                fm.findFragmentById(R.id.course_detail_post_and_question_container);
        if(postsAndQuestionFg == null) {
            postsAndQuestionFg = new QuestionAndPostsLayoutFragment();
            fm.beginTransaction().replace(R.id.course_detail_post_and_question_container, postsAndQuestionFg)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course, menu);
        initCommonMenu(menu);
        return true;
    }

    private void initCommonMenu(Menu menu) {
        MenuItem downInfoItem = menu.findItem(R.id.course_down_info_menu_item);
        downInfoItem.setOnMenuItemClickListener(new MenuHideClickListener());

        //init search menu item
    }

    private void startPublishPostActivity() {

    }

    private void startPublishAnnoucActivity() {
        Intent intent = new Intent(this, AnnoucementPublishActivity.class);
        startActivity(intent);
    }

    private void startNamingActivity() {
        Intent intent = new Intent(this, CourseNamingActivity.class);
        startActivity(intent);
    }

    private void startQuestionActivity() {

    }

    private void startSignInActivity() {
        Intent intent = new Intent(this, CourseSignInActivity.class);
        startActivity(intent);
    }

    private void hideBriefInfoAndAnnouc() {
        if(courseBriefFg != null && annoucemntFg != null) {
            View courseBriefView = courseBriefFg.getView();
            View annoucementView = annoucemntFg.getView();
            if(courseBriefView != null && annoucementView != null) {
                courseBriefView.setVisibility(View.GONE);
                annoucementView.setVisibility(View.GONE);
            } else {
                Log.i("CourseActivity", "course annouce no view");
            }
        }
    }

    private void showBriefInfoAndAnnouc() {
        if(courseBriefFg != null && annoucemntFg != null) {
            View courseBriefView = courseBriefFg.getView();
            View annoucementView = annoucemntFg.getView();
            if(courseBriefView != null && annoucementView != null) {
                courseBriefView.setVisibility(View.VISIBLE);
                annoucementView.setVisibility(View.VISIBLE);
            } else {
                Log.i("CourseActivity", "course annouce no view");
            }
        }
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

    @Override
    public void onPublishPost() {
        startPublishPostActivity();
    }

    @Override
    public void onCourseSignIn() {
        startSignInActivity();
    }

    @Override
    public void onTeacherPublishPost() {
        startPublishPostActivity();
    }

    @Override
    public void onTeacherPublishAnnouc() {
        startPublishAnnoucActivity();
    }

    @Override
    public void onCourseNaming() {
        startNamingActivity();
    }

    @Override
    public void onCourseQuestion() {
        startQuestionActivity();
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
            mainLayout.setVisibility(View.VISIBLE);
        }
    }

    private class MenuHideClickListener implements MenuItem.OnMenuItemClickListener {
        private int count = 0;
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            boolean isHide = (count % 2 == 0);
            count++;
            if(isHide)
                hideBriefInfoAndAnnouc();
            else
                showBriefInfoAndAnnouc();
            return true;
        }
    }

}
