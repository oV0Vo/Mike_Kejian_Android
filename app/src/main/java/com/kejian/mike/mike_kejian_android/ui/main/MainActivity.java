package com.kejian.mike.mike_kejian_android.ui.main;

import android.net.Uri;
import android.support.v4.app.FragmentStatePagerAdapter;
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
import android.widget.TabHost;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.course.CourseDetailFragment;
import com.kejian.mike.mike_kejian_android.ui.course.CourseListFragment;

import org.w3c.dom.Text;

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

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private static final String courseTabTag = "课程";
    private static final String messageTabTag = "消息";
    private static final String campusTabTag = "校内";

    private TextView messageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = getTitle();
        initNavigationDrawer();
        initNavigationTab();
        initViewPager();
        initTabAndPageChangeListner();
    }

    private void initNavigationDrawer() {
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.main_layout));

    }

    private void initNavigationTab() {
        tabHost = (FragmentTabHost)findViewById(R.id.main_tab_host);
        tabHost.setup(this, getSupportFragmentManager(), R.id.main_tab_content);

        TabHost.TabSpec courseTab = tabHost.newTabSpec(courseTabTag);
        TextView courseTabView = (TextView)View.inflate(this, R.layout.textview_main_navigation_tab, null);
        courseTabView.setText(courseTabTag);
        courseTabView.setCompoundDrawables(null, null, null, null);//left top right bottom, need to replace top
        courseTab.setIndicator(courseTabView);
        tabHost.addTab(courseTab, CourseFragmentMock.class, null);

        TabHost.TabSpec messageTab = tabHost.newTabSpec(messageTabTag);
        TextView messageTabView = (TextView)View.inflate(this, R.layout.textview_main_navigation_tab, null);
        messageTabView.setText(messageTabTag);
        messageTabView.setCompoundDrawables(null, null, null, null);//left top right bottom, need to replace top
        messageTab.setIndicator(messageTabView);
        tabHost.addTab(messageTab, MessageFragmentMock.class, null);

        TabHost.TabSpec campusTab = tabHost.newTabSpec(campusTabTag);
        TextView campusTabView = (TextView)View.inflate(this, R.layout.textview_main_navigation_tab, null);
        campusTabView.setText(campusTabTag);
        campusTabView.setCompoundDrawables(null, null, null, null);//left top right bottom, need to replace top
        campusTab.setIndicator(campusTabView);
        tabHost.addTab(campusTab, CampusFragmentMock.class, null);
    }

    private void initViewPager() {
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager)findViewById(R.id.main_view_pager);
        viewPager.setAdapter(mainPagerAdapter);
    }

    private void initTabAndPageChangeListner() {
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Log.i("MainActivity", "tab change then change viewpager to " + tabId);
                switch (tabId) {
                    case courseTabTag:
                        viewPager.setCurrentItem(0);
                        break;
                    case messageTabTag:
                        viewPager.setCurrentItem(1);
                        break;
                    case campusTabTag:
                        viewPager.setCurrentItem(2);
                        break;
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabHost.setCurrentTab(position);
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
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Log.i("MainActivity", "onNavigationDrawerItemSelected");
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_container, MainFragment.newInstance())
                .commit();
    }

    public void onSectionAttached(int number) {
        Log.i("MainActivity", "onSectionAttached");

        switch (number) {
            case 0:
                //tabHost.setCurrentTab(0);
                mTitle = getString(R.string.title_section1);
                break;
            case 1:
                //tabHost.setCurrentTab(1);
                mTitle = getString(R.string.title_section2);
                break;
            case 2:
                //tabHost.setCurrentTab(2);
                mTitle = getString(R.string.title_section3);
                break;
        }
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

    private void fragmentReplace(int containerViewId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(containerViewId, fragment).commit();
    }

    @Override
    public void onMainFragmentInteraction(Uri uri) {

    }

    @Override
    public void onCourseSelected(int courseId) {
        CourseDetailFragment fragment = CourseDetailFragment.newInstance(courseId);
        fragmentReplace(R.id.main_container, fragment);
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
                    return new CourseFragmentMock();
                case 1:
                    return new MessageFragmentMock();
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
