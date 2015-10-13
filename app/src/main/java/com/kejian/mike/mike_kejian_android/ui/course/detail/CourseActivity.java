package com.kejian.mike.mike_kejian_android.ui.course.detail;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.kejian.mike.mike_kejian_android.ui.campus.PostDetailActivity;
import com.kejian.mike.mike_kejian_android.ui.campus.PostPublishActivity;
import com.kejian.mike.mike_kejian_android.ui.course.annoucement.AnnoucListActivity;
import com.kejian.mike.mike_kejian_android.ui.course.detail.introduction.CourseIntroductionActivity;
import com.kejian.mike.mike_kejian_android.ui.course.detail.naming.CourseNamingActivity;
import com.kejian.mike.mike_kejian_android.ui.course.detail.question.QuestionPublishActivity;
import com.kejian.mike.mike_kejian_android.ui.course.management.AnnoucementPublishActivity;

import bl.UserInfoServiceMock;
import com.kejian.mike.mike_kejian_android.dataType.course.CourseBriefInfo;
import com.kejian.mike.mike_kejian_android.dataType.course.UserTypeInCourse;
import model.course.CourseModel;

public class CourseActivity extends AppCompatActivity implements
        AnnoucementFragment.OnAnnoucementClickListener,
        CourseBriefInfoFragment.OnCourseBriefSelectedListener,
        CommentsAreaFragment.OnPostSelectedListener {

    private CourseModel courseModel;

    private ProgressBar progressBar;
    private TextView errorMessageText;

    private LinearLayout mainLayout;

    private CourseBriefInfoFragment courseBriefFg;
    private AnnoucementFragment annoucemntFg;
    private QuestionAndPostsLayoutFragment postsAndQuestionFg;

    private MenuItem downInfoItem;
    private MenuItem addItem;
    private View addSubMenuView;
    private PopupWindow addItemPopupWindow;

    private UserInfoServiceMock userInfoMock = UserInfoServiceMock.getInstance();

    private int taskCountDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        courseModel = CourseModel.getInstance();
        mainLayout = (LinearLayout)findViewById(R.id.course_detail_main_layout);
        progressBar = (ProgressBar)findViewById(R.id.course_progress_bar);
        errorMessageText = (TextView)findViewById(R.id.error_message_text);

        CourseBriefInfo currentCourseBrief = courseModel.getCurrentCourseBrief();
        String title = currentCourseBrief.getCourseName();
        this.setTitle(title);

        taskCountDown++;
        new InitCourseDetailTask().execute();

        taskCountDown++;
        new InitUserTypeTask().execute();

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
        initDownInfoMenuItem(menu);
        initSearchMenuItem(menu);
        addItem = menu.findItem(R.id.course_add_menu_item);
        addItem.setVisible(false);
        return true;
    }

    private void initDownInfoMenuItem(Menu menu) {
        downInfoItem = menu.findItem(R.id.course_down_info_menu_item);
        downInfoItem.setOnMenuItemClickListener(new MenuHideClickListener());
    }

    private void initSearchMenuItem(Menu menu) {

    }

    private void initAddMenuItem() {
        if(addItem == null) {
            Log.i("CourseQuestionFg", "addItem null");
            return;
        }
        UserTypeInCourse userType = courseModel.getUserTypeInCurrentCourse();
        switch(userType) {
            case TEACHER:
                addSubMenuView = createTeacherAddSubMenuView();
                break;
            case STUDENT:
                addSubMenuView = createStudentAddSubMenuView();
                break;
            case ASSISTANT:
                addSubMenuView = createAssistantAddSubMenuView();
                break;
            case VISITOR:
                addItem.setVisible(false);
                return;
            default:
                break;
        }

        addItem.setVisible(true);
        addItemPopupWindow = new PopupWindow(addSubMenuView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        addItemPopupWindow.setTouchable(true);
        addItemPopupWindow.setBackgroundDrawable(new BitmapDrawable());

        addItem.getActionView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemPopupWindow.showAsDropDown(v);
            }
        });
    }

    private View createTeacherAddSubMenuView() {
        View subMenuView =  getLayoutInflater().inflate(R.layout.layout_submenu_course_teacher,
                null);

        ViewGroup annoucPublishLayout = (ViewGroup)subMenuView.findViewById(R.id.annouc_publish_layout);
        annoucPublishLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startPublishAnnoucActivity();
                addItemPopupWindow.dismiss();
            }
        });

        ViewGroup namingLayout = (ViewGroup)subMenuView.findViewById(R.id.
                course_naming_layout);
        namingLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startNamingActivity();
                addItemPopupWindow.dismiss();
            }
        });

        ViewGroup addPostLayout = (ViewGroup)subMenuView.findViewById(R.id.
                post_publish_layout);
        addPostLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startPublishPostActivity();
                addItemPopupWindow.dismiss();
            }
        });

        ViewGroup addQuestionLayout = (ViewGroup)subMenuView.findViewById(R.id.
                ask_question_layout);
        addQuestionLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuestionActivity();
                addItemPopupWindow.dismiss();
            }
        });
        return subMenuView;
    }

    private View createStudentAddSubMenuView() {
        View subMenuView = getLayoutInflater().inflate(R.layout.layout_submenu_course_student,
                null);

        ViewGroup addPostLayout = (ViewGroup)subMenuView.findViewById(R.id.post_publish_layout);
        addPostLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startPublishPostActivity();
                addItemPopupWindow.dismiss();
            }
        });

        ViewGroup signInLayout = (ViewGroup)subMenuView.findViewById(R.id.sign_in_layout);
        signInLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignInActivity();
                addItemPopupWindow.dismiss();
            }
        });
        return subMenuView;
    }

    private View createAssistantAddSubMenuView() {
        View subMenuView = getLayoutInflater().inflate(R.layout.layout_submenu_course_assistant,
                null);

        ViewGroup addPostLayout = (ViewGroup)subMenuView.findViewById(R.id.post_publish_layout);
        addPostLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startPublishPostActivity();
                addItemPopupWindow.dismiss();
            }
        });

        ViewGroup annoucPublishLayout = (ViewGroup)subMenuView.findViewById(R.id.annouc_publish_layout);
        annoucPublishLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startPublishAnnoucActivity();
                addItemPopupWindow.dismiss();
            }
        });

        ViewGroup addQuestionLayout = (ViewGroup)subMenuView.findViewById(R.id.
                ask_question_layout);
        addQuestionLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuestionActivity();
                addItemPopupWindow.dismiss();
            }
        });

        return subMenuView;
    }

    private void startPublishPostActivity() {
        Intent intent = new Intent(this, PostPublishActivity.class);
        startActivity(intent);
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
        Intent intent = new Intent(this, QuestionPublishActivity.class);
        startActivity(intent);
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
        Intent intent = new Intent(this, AnnoucListActivity.class);
        startActivity(intent);
    }

    @Override
    public void showCourseDetail() {
        Intent startIntro = new Intent(this, CourseIntroductionActivity.class);
        startActivity(startIntro);
    }

    @Override
    public void onPostSelected() {
        Intent intent = new Intent(this, PostDetailActivity.class);
        startActivity(intent);
    }

    private void updateViewOnInitCourseDetailFinish() {
        initCourseBriefFragment();
        initCourseAnnoucementFragment();
        updateViewIfAllTaskFinish();
    }

    private void updateViewOnInitUserTypeFinish() {
        initAddMenuItem();
        updateViewIfAllTaskFinish();
    }

    private void updateViewOnTaskFail() {
        progressBar.setVisibility(View.GONE);
        errorMessageText.setVisibility(View.VISIBLE);
    }

    private void updateViewIfAllTaskFinish() {
        if(taskCountDown == 0 && mainLayout != null) {
            progressBar.setVisibility(View.GONE);
            mainLayout.setVisibility(View.VISIBLE);
        }
    }

    private class InitCourseDetailTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        public Boolean doInBackground(Void... params) {
            CourseModel courseModel = CourseModel.getInstance();
            boolean updateSuccess = courseModel.updateCourseDetail();
            if (updateSuccess)
                taskCountDown--;
            return updateSuccess;
        }

        @Override
        public void onPostExecute(Boolean getSuccess) {
            progressBar.setVisibility(View.GONE);
            if(getSuccess) {
                updateViewOnInitCourseDetailFinish();
            } else {
                updateViewOnTaskFail();
            }
        }
    }

    private class InitUserTypeTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean updateSuccess = courseModel.updateUserTypeInCurrentCourse();
            if(updateSuccess)
                taskCountDown--;
            return updateSuccess;
        }

        @Override
        protected void onPostExecute(Boolean updateSuccess) {
            if(updateSuccess) {
                updateViewOnInitUserTypeFinish();
            } else {
                updateViewOnTaskFail();
            }
        }
    }

    private class MenuHideClickListener implements MenuItem.OnMenuItemClickListener {
        private int count = 0;
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            boolean isHide = (count % 2 == 0);
            count++;
            if(isHide) {
                hideBriefInfoAndAnnouc();
                downInfoItem.setIcon(R.drawable.down_info);
            }
            else {
                showBriefInfoAndAnnouc();
                downInfoItem.setIcon(R.drawable.up_info);
            }
            return true;
        }

    }

}
