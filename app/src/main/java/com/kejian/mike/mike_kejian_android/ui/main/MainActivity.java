package com.kejian.mike.mike_kejian_android.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.campus.PostListContainerFragment;
import com.kejian.mike.mike_kejian_android.ui.course.CourseListContainerFragment;
import com.kejian.mike.mike_kejian_android.ui.course.CourseListFragment;
import com.kejian.mike.mike_kejian_android.ui.course.detail.CourseActivity;
import com.kejian.mike.mike_kejian_android.ui.course.management.CourseCreateActivity;

import java.util.ArrayList;

import bl.UserInfoService;
import model.course.CourseModel;
import util.NeedRefinedAnnotation;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
            MainFragment.OnFragmentInteractionListener,
            CourseListFragment.OnCourseSelectedListener
{
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private FragmentTabHost tabHost;

    private ViewPager viewPager;
    private MainPagerAdapter mainPagerAdapter;

    private RadioButton courseButton;
    private RadioButton messageButton;
    private RadioButton campusButton;
    private RadioButton currentButton;

    private CourseListContainerFragment courseFg;
    private Fragment_Msg msgFg;
    private PostListContainerFragment campusFg;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private TextView messageView;

    private MenuItem action_course_add;

    private ArrayList<MenuItem> visibleActions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle = getTitle();
        initUserAccountBLService();
        initNavigationDrawer();
        initViewPager();
        initRadioButtons();
        initBLService();
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

    /*
    这个不应该放到这来的，感觉应该放到Login里面
     */
    private void initUserAccountBLService() {
        if(UserInfoService.getInstance() == null) {
            new AsyncTask<Void, Void, Void>(){
                @Override
                protected Void doInBackground(Void... params) {
                    UserInfoService.createInstance();
                    return null;
                }
            }.execute();
        }
    }

    private void initViewPager() {
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        ViewGroup viewPagerContainer = (ViewGroup)findViewById(R.id.main_view_pager_container);
        viewPager = new ViewPager(this);
        viewPager.setId(R.id.main_view_pager);
        viewPager.setAdapter(mainPagerAdapter);
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

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        getMenuInflater().inflate(R.menu.main, menu);
        visibleActions = new ArrayList<MenuItem>();
        action_course_add = (MenuItem)menu.findItem(R.id.action_course_add);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.action_course_add:
                startCourseAddActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startCourseAddActivity() {
        Intent courseAdd = new Intent(this, CourseCreateActivity.class);
        startActivity(courseAdd);
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
        public Fragment getItem(int position) {Log.e("MainActivity", "viewPager getItehttps://github.com/oV0Vo/Mike_Kejian_Android.gitm " + new Integer(position).toString());
            switch(position) {
                case 0:
                    if(courseFg == null) { Log.e("MainActivity", "courseFg null");
                        courseFg = new CourseListContainerFragment();
                    }
                    //setCourseMenu();
                    return courseFg;
                case 1:
                    if(msgFg == null) { Log.e("MainActivity", "msgFg null");
                        msgFg = new Fragment_Msg();
                    }
                    return msgFg;
                case 2:Log.e("MainActivity", "get campusFg");
                    if(campusFg == null) {
                        getSupportActionBar().setTitle("校内动态");
                        campusFg = new PostListContainerFragment();
                    }
                    return campusFg;
                default:
                    //unreach block
                    Log.i("MainActivity", "getItem logic error");
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    private void setCourseMenu() {
        if(visibleActions == null || action_course_add == null) {
            return;
        }
        Log.e("MainActivity", "I come here");

        disableCurrentMenu();
        action_course_add.setVisible(true);
        visibleActions.add(action_course_add);
    }

    private void disableCurrentMenu() {
        for(MenuItem item: visibleActions)
            item.setVisible(false);
        visibleActions.clear();
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