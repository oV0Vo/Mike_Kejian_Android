package com.kejian.mike.mike_kejian_android.ui.course.detail;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
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
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.campus.PostDetailActivity;
import com.kejian.mike.mike_kejian_android.ui.campus.PostPublishActivity;
import com.kejian.mike.mike_kejian_android.ui.course.annoucement.AnnoucListActivity;
import com.kejian.mike.mike_kejian_android.ui.course.detail.introduction.CourseIntroductionActivity;
import com.kejian.mike.mike_kejian_android.ui.course.detail.naming.CourseNamingActivity;
import com.kejian.mike.mike_kejian_android.ui.course.detail.question.QuestionPublishActivity;
import com.kejian.mike.mike_kejian_android.ui.course.management.AnnoucementPublishActivity;

import com.kejian.mike.mike_kejian_android.dataType.course.UserTypeInCourse;
import com.kejian.mike.mike_kejian_android.ui.message.SearchViewDemo;

import model.course.CourseModel;


/**
 * 当初为什么要写得那么复杂呢...q
 */
public class CourseActivity extends AppCompatActivity implements
        LatestAnnoucFragment.OnAnnoucementClickListener,
        CourseBriefInfoFragment.OnCourseBriefSelectedListener,
        CommentsAreaFragment.OnPostSelectedListener,MenuItem.OnMenuItemClickListener {

    private static final String TAG = "CourseActivity";

    private CourseModel courseModel;

    private ProgressBar progressBar;
    private ViewGroup mainLayout;
    private View netErrorText;

    private CourseBriefInfoFragment courseBriefFg;
    private LatestAnnoucFragment annoucemntFg;
    private QuestionAndPostsLayoutFragment postsAndQuestionFg;

    private MenuItem downInfoItem;
    private MenuItem addItem;
    private MenuItem searchItem;
    private View addSubMenuView;
    private PopupWindow addItemPopupWindow;

    private int taskCountDown;
    private boolean initFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        courseModel = CourseModel.getInstance();
        mainLayout = (ViewGroup)findViewById(R.id.course_detail_main_layout);
        progressBar = (ProgressBar)findViewById(R.id.course_progress_bar);
        netErrorText = findViewById(R.id.net_error_text);

        this.setTitle(R.string.course_title);

        taskCountDown++;
        new InitCourseDetailTask().execute();

        taskCountDown++;
        new InitUserTypeTask().execute();

        setCourseBriefLayout();
        setCourseAnnoucementLayout();
        setPostAndQuestionLayout();

    }

    private void setCourseBriefLayout() {
        FragmentManager fm = getSupportFragmentManager();
        courseBriefFg = (CourseBriefInfoFragment)
                fm.findFragmentById(R.id.course_detail_brief_info_container);
        if(courseBriefFg == null) {
            courseBriefFg = new CourseBriefInfoFragment();
            fm.beginTransaction().replace(R.id.course_detail_brief_info_container, courseBriefFg)
                    .commit();
        }
    }

    private void setCourseAnnoucementLayout() {
        FragmentManager fm = getSupportFragmentManager();
        annoucemntFg = (LatestAnnoucFragment)
                fm.findFragmentById(R.id.course_detail_annoucement_container);
        if(annoucemntFg == null) {
            annoucemntFg = new LatestAnnoucFragment();
            fm.beginTransaction().replace(R.id.course_detail_annoucement_container, annoucemntFg)
                    .commit();
        }
    }

    private void setPostAndQuestionLayout() {
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
    public void onResume() {
        super.onResume();
        if(!initFinish) // no need to refresh
            return;
        postsAndQuestionFg.refreshView();
        annoucemntFg.refreshView();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initDownInfoMenuItem(Menu menu) {
        downInfoItem = menu.findItem(R.id.course_down_info_menu_item);
        downInfoItem.setOnMenuItemClickListener(new MenuHideClickListener());
    }

    private void initSearchMenuItem(Menu menu) {
        searchItem = menu.findItem(R.id.course_search_menu_item);
        searchItem.setOnMenuItemClickListener(this);
    }

    private void initAddMenuItem() {
        if(addItem == null) {
            Log.i("CourseQuestionFg", "addItem null");
            return;
        }

        UserTypeInCourse userType = courseModel.getUserTypeInCurrentCourse();
        if(userType == null) {
            Toast.makeText(this, R.string.net_disconnet, Toast.LENGTH_SHORT).show();
            Log.e(TAG, "unexpected userType null");
            return;
        }

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
                int yOff = (int)getResources().getDimension(R.dimen.over_over_small_size);
                addItemPopupWindow.showAsDropDown(v, 0, yOff);
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
        intent.putExtra("courseId", courseModel.getCurrentCourseId());
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
    public void onPostSelected(String postId) {
        Intent intent = new Intent(this, PostDetailActivity.class);
        intent.putExtra("postId", postId);
        startActivity(intent);
    }

    private void updateViewOnInitCourseDetailFinish() {
        courseBriefFg.initView();
        updateViewIfAllTaskFinish();
    }

    private void updateViewOnInitUserTypeFinish() {
        if(mainLayout == null)
            return;

        initAddMenuItem();
        postsAndQuestionFg.initView();
        updateViewIfAllTaskFinish();
    }

    private void updateViewOnTaskFail() {
        if (progressBar == null)
            return;

        progressBar.setVisibility(View.GONE);
        netErrorText.setVisibility(View.VISIBLE);
        Toast.makeText(this, R.string.net_disconnet,Toast.LENGTH_SHORT).show();
    }

    private void updateViewIfAllTaskFinish() {
        if(taskCountDown == 0 && mainLayout != null) {
            initFinish = true;
            progressBar.setVisibility(View.GONE);
            mainLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent intent = new Intent();
        intent.setClass(this,SearchViewDemo.class);
        startActivity(intent);
        return true;
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

    private class InitCourseDetailTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean updateSuccess = courseModel.updateCourseDetail();
            if(updateSuccess)
                taskCountDown--;
            return updateSuccess;
        }

        @Override
        protected void onPostExecute(Boolean updateSuccess) {
            if(updateSuccess)
                updateViewOnInitCourseDetailFinish();
            else
                updateViewOnTaskFail();
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
