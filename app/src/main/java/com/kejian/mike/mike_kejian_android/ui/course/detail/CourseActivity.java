package com.kejian.mike.mike_kejian_android.ui.course.detail;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.course.detail.introduction.CourseIntroductionActivity;
import com.kejian.mike.mike_kejian_android.ui.course.detail.menu.StudentActionProvider;
import com.kejian.mike.mike_kejian_android.ui.course.detail.naming.CourseNamingActivity;
import com.kejian.mike.mike_kejian_android.ui.course.management.AnnoucementPublishActivity;

import model.course.data.CourseBriefInfo;
import model.course.data.CourseDetailInfo;
import model.course.CourseModel;

public class CourseActivity extends AppCompatActivity implements
        AnnoucementFragment.OnAnnoucementClickListener,
        CourseBriefInfoFragment.OnCourseBriefSelectedListener,
        StudentActionProvider.OnStudentMenuSelectListener,
        CommentsAreaFragment.OnPostSelectedListener {

    private CourseModel courseModel;

    private ProgressBar progressBar;
    private LinearLayout mainLayout;
    private CourseBriefInfoFragment courseBriefFg;
    private AnnoucementFragment annoucemntFg;
    private QuestionAndPostsLayoutFragment postsAndQuestionFg;

    private MenuItem addItem;
    private PopupWindow addItemSubMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseModel = CourseModel.getInstance();

        setContentView(R.layout.activity_course);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mainLayout = (LinearLayout)findViewById(R.id.course_detail_main_layout);
        mainLayout.setVisibility(View.GONE);
        progressBar = (ProgressBar)findViewById(R.id.course_progress_bar);
        CourseBriefInfo currentCourseBrief = courseModel.getCurrentCourseBrief();
        if(currentCourseBrief != null) {
            String title = currentCourseBrief.getCourseName();
            this.setTitle(title);
            new UpdateCourseDetailTask().execute();
        } else {
            Log.e("CourseActivity", "start with no currentCourse !!");
        }

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
        initTeacherAddMenuItem(menu);
        //init search menu item
    }

    private void initTeacherAddMenuItem(Menu menu) {
        addItem = menu.findItem(R.id.course_add_menu_item);

        View subMenuView = createTeacherAddItemSubMenuView();
        addItemSubMenu = new PopupWindow(subMenuView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        addItemSubMenu.setTouchable(true);
        addItemSubMenu.setBackgroundDrawable(new BitmapDrawable());

        addItem.getActionView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemSubMenu.showAsDropDown(v);
            }
        });
    }

    private View createTeacherAddItemSubMenuView() {
        View subMenuView =  View.inflate(CourseActivity.this, R.layout.layout_submenu_course_teacher, null);

        ViewGroup annoucPublishLayout = (ViewGroup)subMenuView.findViewById(R.id.course_submenu_teacher_publish_annouc);
        annoucPublishLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startPublishAnnoucActivity();
                addItemSubMenu.dismiss();
            }
        });
        return subMenuView;
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
    public void onPostSelected() {

    }

    private class UpdateCourseDetailTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        public Boolean doInBackground(Void... params) {
            CourseModel courseModel = CourseModel.getInstance();
            return courseModel.updateCourseDetail();
        }

        @Override
        public void onPostExecute(Boolean getSuccess) {
            progressBar.setVisibility(View.GONE);
            if(getSuccess) {
                initCourseBriefFragment();
                initCourseAnnoucementFragment();
                mainLayout.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(CourseActivity.this, R.string.net_disconnet, Toast.LENGTH_LONG).show();
            }
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
