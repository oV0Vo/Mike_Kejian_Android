package com.kejian.mike.mike_kejian_android.ui.main;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.course.CourseListContainerFragment;
import com.kejian.mike.mike_kejian_android.ui.course.CourseListFragment;

import bl.UserAccountBLService;
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

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private static final String courseTabTag = "课程";
    private static final String messageTabTag = "消息";
    private static final String campusTabTag = "校内";

    private TextView messageView;

    private String studentId = "131250012";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = getTitle();
        initUserAccountBLService();
        initNavigationDrawer();
        initViewPager();
        initRadioButtons();
        //initNavigationTab();
        //initTabAndPageChangeListner();

    }

    private void initNavigationDrawer() {
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.main_layout));

    }

    /*
    这个不应该放到这来的，感觉应该放到Login里面
     */
    private void initUserAccountBLService() {
        if(UserAccountBLService.getInstance() == null) {
            new AsyncTask<Void, Void, Void>(){
                @Override
                protected Void doInBackground(Void... params) {
                    UserAccountBLService.createInstance();
                    return null;
                }
            }.execute();
        }
    }
/*
    private void initNavigationTab() {
        tabHost = (FragmentTabHost)findViewById(R.id.main_tab_host);
        tabHost.setup(this, getSupportFragmentManager(), R.id.main_container_content);

        TabHost.TabSpec courseTab = tabHost.newTabSpec(courseTabTag);
        TextView courseTabView = (TextView)View.inflate(this, R.layout.textview_main_navigation_tab, null);
        courseTabView.setText(courseTabTag);
        courseTabView.setCompoundDrawables(null, null, null, null);//left top right bottom, need to replace top
        courseTab.setIndicator(courseTabView);
        courseTabView.setBackgroundResource(R.drawable.radiobutton_middle);
        tabHost.addTab(courseTab, CourseFragmentMock.class, null);

        TabHost.TabSpec messageTab = tabHost.newTabSpec(messageTabTag);
        TextView messageTabView = (TextView)View.inflate(this, R.layout.textview_main_navigation_tab, null);
        messageTabView.setText(messageTabTag);
        messageTabView.setCompoundDrawables(null, null, null, null);//left top right bottom, need to replace top
        messageTab.setIndicator(messageTabView);
        messageTabView.setBackgroundResource(R.drawable.radiobutton_middle);
        tabHost.addTab(messageTab, MessageFragmentMock.class, null);

        TabHost.TabSpec campusTab = tabHost.newTabSpec(campusTabTag);
        TextView campusTabView = (TextView)View.inflate(this, R.layout.textview_main_navigation_tab, null);
        campusTabView.setText(campusTabTag);
        campusTabView.setCompoundDrawables(null, null, null, null);//left top right bottom, need to replace top
        campusTab.setIndicator(campusTabView);
        campusTabView.setBackgroundResource(R.drawable.radiobutton_middle);
        tabHost.addTab(campusTab, CampusFragmentMock.class, null);
    }*/

    private void initViewPager() {
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager)findViewById(R.id.main_view_pager);
        viewPager.setAdapter(mainPagerAdapter);
    }

   /* private void initTabAndPageChangeListner() {
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Log.i("MainActivity", "tab change then change viewpager to " + tabId);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("MainActivity", "page changed!");
                //tabHost.setCurrentTab(position);
            }

            @Override
            public void onPageSelected(int position) {
                //by default do nothing
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //by default do nothing
            }
        });
    }*/

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
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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
    public void onMainFragmentInteraction(Uri uri) {

    }

    @Override
    public void onCourseSelected(String courseId) {

    }

    private class MainPagerAdapter extends FragmentStatePagerAdapter {

        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NeedRefinedAnnotation
        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new CourseListContainerFragment();
                case 1:
                    return new Fragment_Msg();
                case 2:
                    return new CampusFragmentMock();
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