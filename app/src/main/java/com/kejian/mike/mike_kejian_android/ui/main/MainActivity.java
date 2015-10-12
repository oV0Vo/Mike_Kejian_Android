package com.kejian.mike.mike_kejian_android.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.campus.PostListContainerFragment;
import com.kejian.mike.mike_kejian_android.ui.campus.PostPublishActivity;
import com.kejian.mike.mike_kejian_android.ui.course.CourseListContainerFragment;
import com.kejian.mike.mike_kejian_android.ui.course.CourseListFragment;
import com.kejian.mike.mike_kejian_android.ui.course.detail.CourseActivity;
import com.kejian.mike.mike_kejian_android.ui.course.management.CourseCreateActivity;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;

import java.util.ArrayList;

import bl.UserInfoServiceMock;
import model.course.CourseModel;
import model.user.UserType;
import util.NeedRefinedAnnotation;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
            MainFragment.OnFragmentInteractionListener,
            CourseListFragment.OnCourseSelectedListener
{
    private UserInfoServiceMock userInfoMock = UserInfoServiceMock.getInstance();

    private NavigationDrawerFragment mNavigationDrawerFragment;

    private ViewPager viewPager;
    private MainPagerAdapter mainPagerAdapter;

    private RadioButton courseButton;
    private RadioButton messageButton;
    private RadioButton campusButton;

    private CourseListContainerFragment courseFg;
    private Fragment_Msg msgFg;
    private PostListContainerFragment campusFg;

    private enum FgState {
        COURSE, MESSAGE, CAMPUS
    }

    private FgState fgState;

    private MenuItem addCourseItem;
    private MenuItem addPostItem;

    private ArrayList<MenuItem> visibleItems;

    /**
     * 用来保存标题
     */
    private CharSequence title;

    private PushAgent pushAgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = getTitle();

        initNavigationDrawer();
        initViewPager();
        initRadioButtons();
        initBLService();
        initPushAgent();
        fgState = FgState.COURSE;
        courseButton.setChecked(true);
    }

    private void initNavigationDrawer() {
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.main_layout));

    }

    private void initBLService() {
        new CourseModelCreateTask().execute();
    }

    private void initPushAgent() {
        pushAgent = PushAgent.getInstance(this);
        pushAgent.enable();
        pushAgent.onAppStart();
        pushAgent.setMessageHandler(new MyUmengMessageHandler());
    }

    private void initViewPager() {
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        ViewGroup viewPagerContainer = (ViewGroup)findViewById(R.id.main_view_pager_container);
        viewPager = new ViewPager(this);
        viewPager.setId(R.id.main_view_pager);
        viewPager.setAdapter(mainPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch(position) {
                    case 0:
                        courseButton.setChecked(true);
                        fgState = FgState.COURSE;
                        setCourseMenu();
                        break;
                    case 1:
                        Log.i("MainActivity", "message page selected");
                        messageButton.setChecked(true);
                        fgState = FgState.MESSAGE;
                        setMessageMenu();
                        setTitle(R.string.message_title);
                        break;
                    case 2:
                        campusButton.setChecked(true);
                        fgState = FgState.CAMPUS;
                        setCampusMenu();
                        setTitle(R.string.campus_title);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPagerContainer.addView(viewPager);
    }

    private void initRadioButtons() {
        courseButton = (RadioButton)findViewById(R.id.main_course_button);
        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        courseButton.setChecked(true);

        messageButton = (RadioButton)findViewById(R.id.main_message_button);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });

        campusButton = (RadioButton)findViewById(R.id.main_campus_button);
        campusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_container, MainFragment.newInstance())
                .commit();
    }

    public void restoreActionBar(Menu menu) {
        setTitle(title);
        addCourseItem = menu.findItem(R.id.action_course_add);
        addCourseItem.setVisible(false);
        addPostItem = menu.findItem(R.id.publish_post);
        addPostItem.setVisible(false);
        visibleItems = new ArrayList<MenuItem>();
        switch(fgState) {
            case COURSE:
                setCourseMenu();
                break;
            case MESSAGE:
                setMessageMenu();
                break;
            case CAMPUS:
                setCampusMenu();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar(menu);
            return true;
        } else {
            title = getTitle();
            return super.onCreateOptionsMenu(menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.action_course_add:
                startAddCourseActivity();
                return true;
            case R.id.publish_post:
                startAddPostActivity();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startAddCourseActivity() {
        Intent courseAdd = new Intent(this, CourseCreateActivity.class);
        startActivity(courseAdd);
    }

    private void startAddPostActivity() {
        Intent intent = new Intent(this, PostPublishActivity.class);
        startActivity(intent);
    }

    @Override
    public void onMainFragmentInteraction(Uri uri) {

    }

    @Override
    public void onCourseSelected() {
        Intent startCourseDetailIntent = new Intent(this, CourseActivity.class);
        startActivity(startCourseDetailIntent);
    }

    private class MainPagerAdapter extends FragmentPagerAdapter {

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NeedRefinedAnnotation
        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    if(courseFg == null) {
                        courseFg = new CourseListContainerFragment();
                    }
                    return courseFg;
                case 1:
                    if(msgFg == null) {
                        msgFg = new Fragment_Msg();
                    }
                    return msgFg;
                case 2:
                    if(campusFg == null) {
                        campusFg = new PostListContainerFragment();
                    }
                    return campusFg;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    private void setCourseMenu() {
        disableCurrentMenu();
        if(userInfoMock.getUserType() == UserType.TEACHER) {
            addCourseItem.setVisible(true);
            visibleItems.add(addCourseItem);
        }
    }

    private void setMessageMenu() {
        disableCurrentMenu();
    }

    private void setCampusMenu() {
        disableCurrentMenu();
        addPostItem.setVisible(true);
        visibleItems.add(addPostItem);
    }

    private void disableCurrentMenu() {
        for(MenuItem item: visibleItems)
            item.setVisible(false);
        visibleItems.clear();
    }

    private class CourseModelCreateTask extends AsyncTask<Void, Void, Void> {
        @Override
        public Void doInBackground(Void... params) {
            CourseModel.createInstance();
            return null;
        }
    }
}














//   ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃ 　
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//   ┃　　　┃  神兽保佑　　　　　　　　
//  ┃　　　┃  代码无BUG！
// ┃　　　┗━━━┓
//┃　　　　　　　┣┓
//┃　　　　　　　┏┛
//┗┓┓┏━┳┓┏┛
// ┃┫┫　┃┫┫
// ┗┻┛　┗┻┛