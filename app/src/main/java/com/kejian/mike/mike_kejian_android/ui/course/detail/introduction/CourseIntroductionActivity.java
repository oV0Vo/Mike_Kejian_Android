package com.kejian.mike.mike_kejian_android.ui.course.detail.introduction;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.dataType.course.CourseBriefInfo;
import com.kejian.mike.mike_kejian_android.dataType.course.CourseDetailInfo;
import com.kejian.mike.mike_kejian_android.dataType.course.UserInterestInCourse;
import com.kejian.mike.mike_kejian_android.dataType.course.UserTypeInCourse;
import com.kejian.mike.mike_kejian_android.ui.main.SearchPeopleActivity;
import com.umeng.message.PushAgent;

import net.course.CourseInfoNetService;
import net.course.CourseTeacherNetService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import model.course.CourseModel;
import model.helper.SearchType;
import model.user.Global;
import util.GetBitmapByPinyin;
import util.StringUtil;

public class CourseIntroductionActivity extends AppCompatActivity {

    private static final String TAG = "CourseIntroActivity";

    private ViewPager viewPager;
    private CourseContentAdpater viewPagerAdapter;

    private Menu menu;

    private ViewGroup assistantAddLayout;
    private ViewGroup assistantConatainer;
    private TextView assistantEmptyText;
    private HashMap<String, ViewGroup> assitantViewMap;
    private ArrayList<ImageView> deleteImageViews;

    private RadioButton briefTab;
    private RadioButton teachContentTab;
    private RadioButton referenceTab;

    private CourseDetailInfo courseDetail;

    private TextView interestText;

    //private PushAgent pushAgent;

    private int searchRequestCode = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_introduction);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CourseModel courseModel = CourseModel.getInstance();
        courseDetail = courseModel.getCurrentCourseDetail();

        initCourseBrief();
        initTeacherInfo();
        initTabButton();
        initViewPager();
        initInterestText();
        //initPushAgent();

        UserTypeInCourse userTypeInCourse = CourseModel.getInstance().getUserTypeInCurrentCourse();
        if(userTypeInCourse == UserTypeInCourse.TEACHER) {
            ArrayList<String> assistantNames = courseDetail.getAssistantNames();
            ArrayList<String> assistantIds = courseDetail.getAssistantIds();
            initAssistantManagement(assistantNames, assistantIds);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_introduction, menu);
        this.menu = menu;
        MenuItem manageItem = menu.findItem(R.id.assistant_management_menu);
        UserTypeInCourse userTypeInCourse = CourseModel.getInstance().getUserTypeInCurrentCourse();
        if(userTypeInCourse != UserTypeInCourse.TEACHER) {
            manageItem.setVisible(false);
        }
        MenuItem exitEditItem = menu.findItem(R.id.exit_edit_menu);
        exitEditItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.assistant_management_menu:
                item.setVisible(false);
                MenuItem exitEditItem = menu.findItem(R.id.exit_edit_menu);
                exitEditItem.setVisible(true);
                showAssistantManageLayout();
                return true;
            case R.id.exit_edit_menu:
                item.setVisible(false);
                MenuItem manageItem = menu.findItem(R.id.assistant_management_menu);
                manageItem.setVisible(true);
                hideAssistantManageLayout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAssistantManageLayout() {
        assistantAddLayout.setVisibility(View.VISIBLE);
        for(ViewGroup assistantLayout: assitantViewMap.values()) {
            ImageView deleteImage = (ImageView)assistantLayout.findViewById(R.id.delete_image);
            deleteImage.setVisibility(View.VISIBLE);
        }
        for(ImageView deleteImage: deleteImageViews)
            deleteImage.setVisibility(View.VISIBLE);
    }

    private void hideAssistantManageLayout() {
        assistantAddLayout.setVisibility(View.GONE);
        for(ViewGroup assistantLayout: assitantViewMap.values()) {
            ImageView deleteImage = (ImageView)assistantLayout.findViewById(R.id.delete_image);
            deleteImage.setVisibility(View.GONE);
        }
        for(ImageView deleteImage: deleteImageViews)
            deleteImage.setVisibility(View.GONE);
    }

    /*private void initPushAgent() {
        pushAgent = PushAgent.getInstance(this);
        pushAgent.onAppStart();
    }*/

    private void initInterestText() {
        UserTypeInCourse userTypeInCourse = CourseModel.getInstance().getUserTypeInCurrentCourse();
        if(userTypeInCourse == UserTypeInCourse.VISITOR) {
            interestText = (TextView)findViewById(R.id.show_interest_text);
            new GetUserInterestTask().execute(courseDetail.getCourseId());
        }
    }

    private void initCourseBrief() {
        TextView courseTitleView = (TextView)findViewById(R.id.course_intro_course_name);
        String courseName = courseDetail.getCourseName();
        if(courseName.length() > 10) {
            float subBigFont = getResources().getDimension(R.dimen.sub_big_font);
            int font = (int)(subBigFont / 2);
            courseTitleView.setTextSize(font);
            courseTitleView.invalidate();
        }
        if(courseName.length() > 14) {
            courseTitleView.setMaxLines(2);
            courseTitleView.setEllipsize(TextUtils.TruncateAt.END);
            courseTitleView.invalidate();
        }
        courseTitleView.setText(courseName);

        ImageView courseImage = (ImageView)findViewById(R.id.course_intro_image);
        GetBitmapByPinyin.getBitmapByPinyin(
                courseDetail.getCourseName(), this,courseImage);

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
        teacherBriefIntroView.setText("目前还没有该老师的相关介绍");

    }

    private void initAssistantManagement(ArrayList<String> names, ArrayList<String> ids) {
        initAssistantContainer(names, ids);
        initAssistantAddLayout();
        assistantEmptyText = (TextView)findViewById(R.id.assistant_empty_text);

        if(names.size() == 0) {
            assistantEmptyText.setVisibility(View.VISIBLE);
        }
    }

    private void initAssistantContainer(ArrayList<String> names, ArrayList<String> ids) {
        assistantConatainer = (ViewGroup)findViewById(R.id.assistant_container);
        assitantViewMap = new HashMap<String, ViewGroup>();
        deleteImageViews = new ArrayList<>(names.size());

        for(int i=0; i<names.size(); ++i) {
            String name = names.get(i);
            String id = ids.get(i);
            addAssistantView(name, id);
        }
    }

    private void addAssistantView(String name, String id) {
        ViewGroup newAssistantLayout = createAssistantLayout(name, id);
        assistantConatainer.addView(newAssistantLayout);
        assitantViewMap.put(id, newAssistantLayout);
    }

    private void startAddAssistantTask(String name, String id) {
        String courseId = courseDetail.getCourseId();
        new AddAssistantTask(id, name).execute(courseId);
        Toast.makeText(this, R.string.add_assistant_request, Toast.LENGTH_SHORT).show();
    }

    private void initAssistantAddLayout() {
        assistantAddLayout = (ViewGroup)findViewById(R.id.assistant_add_layout);
        assistantAddLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(CourseIntroductionActivity.this, SearchPeopleActivity.class);
                intent.putExtra("searchType", SearchType.addAssistant);
                startActivityForResult(intent,searchRequestCode);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == searchRequestCode) {
            if(data == null) {
                return;
            }

            String sid = data.getStringExtra("school_identify");
            if(sid.length() == 0) {
                Toast.makeText(this, R.string.add_assistant_jw_not_bound, Toast.LENGTH_SHORT)
                    .show();
                return;
            }
            String name = data.getStringExtra("real_name");
            String id = data.getStringExtra("user_id");
            ArrayList<String> assistantIds = courseDetail.getAssistantIds();
            if(!assistantIds.contains(id)) {
                startAddAssistantTask(name, id);
                if (assistantEmptyText.getVisibility() == View.VISIBLE)
                    assistantEmptyText.setVisibility(View.GONE);
            } else {
                Toast.makeText(this, R.string.assistant_exist, Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private ViewGroup createAssistantLayout(String name, String id) {
        ViewGroup layout = (ViewGroup)getLayoutInflater().inflate(R.layout.layout_user_name_delete,
                null);

        TextView nameText = (TextView)layout.findViewById(R.id.user_name_text);
        nameText.setText(name);

        ImageView deleteImage = (ImageView)layout.findViewById(R.id.delete_image);
        deleteImage.setOnClickListener(new AssistantDeleteListener(id, name));
        deleteImageViews.add(deleteImage);

        return layout;
    }

    private class AssistantDeleteListener implements View.OnClickListener {

        private String assistantId;

        private String assistantName;

        public AssistantDeleteListener(String assistantId, String assistantName) {
            this.assistantId = assistantId;
            this.assistantName = assistantName;
        }

        @Override
        public void onClick(View v) {
            v.setEnabled(false);
            ViewGroup deleteView = assitantViewMap.get(assistantId);
            new AssistantDeleteTask(assistantId, assistantName, courseDetail.getCourseId(),
                    deleteView)
                    .execute();
            Toast.makeText(CourseIntroductionActivity.this, R.string.delete_assistant_on_progress,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private class AssistantDeleteTask extends AsyncTask<Void, Void, Boolean> {

        private String courseId;

        private String assistantId;

        private String assistantName;

        private View deleteView;

        public AssistantDeleteTask(String assistantId, String assistantName, String courseId,
                                   View deleteView) {
            this.courseId = courseId;
            this.assistantId = assistantId;
            this.assistantName = assistantName;
            this.deleteView = deleteView;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean deleteSuccess = CourseTeacherNetService.deleteAssistant(courseId, assistantId);
            return deleteSuccess;
        }

        @Override
        protected void onPostExecute(Boolean deleteSuccess) {
            if(assistantConatainer == null)
                return;

            if(deleteSuccess) {
                assistantConatainer.removeView(deleteView);
                Toast.makeText(CourseIntroductionActivity.this, R.string.delete_assistant_success
                    , Toast.LENGTH_SHORT).show();
                ArrayList<String> aNames = courseDetail.getAssistantNames();
                ArrayList<String> aIds = courseDetail.getAssistantIds();
                if(aNames != null && aIds != null) {
                    aNames.remove(assistantName);
                    aIds.remove(assistantId);
                    if(aNames.size() == 0)
                        assistantEmptyText.setVisibility(View.VISIBLE);
                } else {
                    Log.e(TAG, "courseDetail assistants null!");
                }

            } else {
                Toast.makeText(CourseIntroductionActivity.this, R.string.delete_assistant_fail
                        , Toast.LENGTH_SHORT).show();
                deleteView.setEnabled(true);
            }
        }
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
                switch (position) {
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
        openCoursePushService();
    }

    private void openCoursePushService() {

       // JPushInterface.setAliasAndTags(getApplicationContext(), (ali == null ? "" : ali), h, null);
        Set<String> set= new HashSet();
        set.add("course_"+courseDetail.getCourseId());
        set.addAll((Set<String>)Global.getObjectByName("tag"));
        JPushInterface.setTags(getApplicationContext(),set,null);
        /*try {
            pushAgent.getTagManager().add(courseDetail.getCourseId());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
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
                case UserInterestInCourse.NO_INTEREST:
                    interestText.setText(R.string.show_interest);
                    interestText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           /* Toast.makeText(CourseIntroductionActivity.this,
                                    R.string.show_interest_on_progress, Toast.LENGTH_SHORT).show();*/
                            interestText.setEnabled(false);
                            new ShowInterestTask().execute(courseDetail.getCourseId());
                        }
                    });
                    break;
                case UserInterestInCourse.INTEREST:
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
                interestText.setEnabled(true);
            }
        }
    }

    private class AddAssistantTask extends AsyncTask<String, Void, Boolean> {

        private String assistantId;
        private String assistantName;

        public AddAssistantTask(String assistantId, String assistantName) {
            this.assistantId = assistantId;
            this.assistantName = assistantName;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String courseId = params[0];
            boolean addSuccess = CourseTeacherNetService.addAssistant(courseId, assistantId);
            return addSuccess;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if(viewPager == null)
                return;

            if(success == null)
                Toast.makeText(CourseIntroductionActivity.this, R.string.net_disconnet,
                        Toast.LENGTH_SHORT).show();
            else if(success) {
                Toast.makeText(CourseIntroductionActivity.this, R.string.add_assistant_success,
                        Toast.LENGTH_SHORT).show();
                ArrayList<String> ids = courseDetail.getAssistantIds();
                ArrayList<String> names = courseDetail.getAssistantNames();
                if(ids != null && names != null) {
                    ids.add(assistantId);
                    names.add(assistantName);
                } else {
                    Log.e(TAG, "courseDetail assistants null!");
                }
                addAssistantView(assistantName, assistantId);
            }
            else
                Toast.makeText(CourseIntroductionActivity.this, R.string.net_disconnet,
                        Toast.LENGTH_SHORT).show();
        }
    }
}
