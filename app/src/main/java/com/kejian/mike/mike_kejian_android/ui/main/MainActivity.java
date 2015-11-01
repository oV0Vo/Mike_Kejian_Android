package com.kejian.mike.mike_kejian_android.ui.main;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.dataType.course.CourseBriefInfo;
import com.kejian.mike.mike_kejian_android.ui.campus.PostDetailActivity;
import com.kejian.mike.mike_kejian_android.ui.campus.PostListContainerFragment;
import com.kejian.mike.mike_kejian_android.ui.campus.PostPublishActivity;
import com.kejian.mike.mike_kejian_android.ui.course.CourseListContainerFragment;
import com.kejian.mike.mike_kejian_android.ui.course.CourseListFragment;
import com.kejian.mike.mike_kejian_android.ui.course.detail.CourseActivity;
import com.kejian.mike.mike_kejian_android.ui.course.management.CourseCreateActivity;
import com.kejian.mike.mike_kejian_android.ui.util.UmengMessageAction;
import com.kejian.mike.mike_kejian_android.ui.widget.MyReceiver;
import com.kejian.mike.mike_kejian_android.ui.widget.MyUmengMessageHandler;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.UmengRegistrar;
import com.umeng.message.entity.UMessage;

import net.course.CourseInfoNetService;
import net.picture.MessagePrint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import model.user.Global;
import model.user.user;
import util.NeedRefinedAnnotation;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        MainFragment.OnFragmentInteractionListener,
        CourseListFragment.OnCourseSelectedListener,
        Fragment_Msg.OnMessageBeenReadListener
{

    private static final String TAG = "MainActivity";

    private NavigationDrawerFragment mNavigationDrawerFragment;

    private ViewPager viewPager;
    private MainPagerAdapter mainPagerAdapter;

    private RadioButton courseTextTab;
    private RadioButton courseImageTab;
    private RadioButton messageTextTab;
    private RadioButton messageImageTab;
    private RadioButton campusTextTab;
    private RadioButton campusImageTab;

    private RadioButton currentImageTab;

    private ImageView messageNewsImage;

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

    public void checkVersion(){
        if(Global.localVersion < Global.serverVersion){
            //发现新版本，提示用户更新
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("软件升级")
                    .setMessage("发现新版本,建议立即更新使用.")
                    .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //开启更新服务UpdateService
                            //这里为了把update更好模块化，可以传一些updateService依赖的值
                            //如布局ID，资源ID，动态获取的标题,这里以app_name为例
                            Intent updateIntent =new Intent(MainActivity.this, UpdateService.class);
                            updateIntent.putExtra("titleId",R.string.app_name);
                            startService(updateIntent);
                        }
                    })
                    .setNegativeButton("取消",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alert.create().show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkVersion();
        initJpush();
        if(getSupportActionBar() != null)
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);

        new MyReceiver();

        System.out.println("ID:" + JPushInterface.getRegistrationID(getApplicationContext()));

        title = getTitle();

        initNavigationDrawer();
        initViewPager();
        initRadioButtons();
        initPushAgent();
        initMessageNotice();

        fgState = FgState.COURSE;
        courseTextTab.setChecked(true);
    }

    private void initJpush(){


        JPushInterface.setDebugMode(false);
        JPushInterface.init(getApplicationContext());

        new SetTag().execute("");



    }

    //设置别名和标签

    private  class SetTag extends AsyncTask<String,Integer,String>{

        @Override
        public String doInBackground(String...Para){

            user user=(user)Global.getObjectByName("user");
            String ali=null;

            //设置手机别名
            if(user!=null){

                ali="user_"+user.getId();

            }



            ArrayList<CourseBriefInfo> list= CourseInfoNetService.getMyCourseBrief();
            int size=list.size();

            Set h=new HashSet();

            for(int i=0;i<size;i++){

                h.add("course_"+list.get(i).getCourseId());

                MessagePrint.print("set tag :"+"course_"+list.get(i).getCourseId());


            }

            JPushInterface.setAliasAndTags(getApplicationContext(),(ali==null?"":ali),h,null);


            return null;
        }
    }

    //@还不是很清楚广播，所以先这样写
    private void initMessageNotice() {
        messageNewsImage = (ImageView) findViewById(R.id.message_news_icon);
        BroadcastReceiver messageBR = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                messageNewsImage.setVisibility(View.VISIBLE);
            }
        };
        IntentFilter messageIF = new IntentFilter(UmengMessageAction.NEW_MESSAGE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(messageBR, messageIF);
    }

    private void initNavigationDrawer() {
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.main_layout));

    }

    private void initPushAgent() {
        pushAgent = PushAgent.getInstance(this);
        pushAgent.enable();
        pushAgent.onAppStart();
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler(){
            @Override
            public void dealWithCustomAction(Context context, UMessage
                    msg) {
                String message=msg.extra.get("inf_type");
                if(message.equals("INVITE")){
                    Intent intent = new Intent();
                    intent.setClass(context, PostDetailActivity.class);
                    intent.putExtra("postId", msg.extra.get("postId"));
                    getApplicationContext().startActivity(intent);
                    MessagePrint.print("get post");
                }


            }
        };
        pushAgent.setNotificationClickHandler(notificationClickHandler);


        pushAgent.setMessageHandler(new MyUmengMessageHandler());

        try {

            pushAgent.getTagManager().add("course_1");
            System.out.println("um ali " + pushAgent.getTagManager().list().get(0));
        }catch(Exception e){
            e.printStackTrace();
        }


        PushAgent.getInstance(getApplicationContext()).setMergeNotificaiton(false);

        user user = (user)Global.getObjectByName("user");

       if(user!=null) {
           String userId = user.getId();
           try {
               pushAgent.addAlias(userId, "mike_kejian_alias_type");
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
        String token=UmengRegistrar.getRegistrationId(getApplicationContext());
        System.out.println("um token " + token);

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
                if(currentImageTab != null)
                    currentImageTab.setChecked(false);
                switch(position) {
                    case 0:
                        courseTextTab.setChecked(true);
                        courseImageTab.setChecked(true);
                        currentImageTab = courseImageTab;
                        fgState = FgState.COURSE;
                        setCourseMenu();
                        setTitle(R.string.course_title);
                        break;
                    case 1:
                        messageTextTab.setChecked(true);
                        messageImageTab.setChecked(true);
                        currentImageTab = messageImageTab;
                        fgState = FgState.MESSAGE;
                        setMessageMenu();
                        setTitle(R.string.message_title);
                        break;
                    case 2:
                        campusTextTab.setChecked(true);
                        campusImageTab.setChecked(true);
                        currentImageTab = campusImageTab;
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
        courseTextTab = (RadioButton)findViewById(R.id.course_tab_text);
        courseTextTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        courseImageTab = (RadioButton)findViewById(R.id.course_tab_image);
        courseImageTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        courseTextTab.setChecked(true);
        courseImageTab.setChecked(true);
        currentImageTab = courseImageTab;

        messageTextTab = (RadioButton)findViewById(R.id.message_tab_text);
        messageTextTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        messageImageTab = (RadioButton)findViewById(R.id.message_tab_image);
        messageImageTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });

        campusTextTab = (RadioButton)findViewById(R.id.campus_tab_text);
        campusTextTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });
        campusImageTab = (RadioButton)findViewById(R.id.campus_tab_image);
        campusImageTab.setOnClickListener(new View.OnClickListener() {
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
        if(visibleItems == null)
            visibleItems = new ArrayList<MenuItem>();

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
    public void onAllMessageHasBeenRead() {
        messageNewsImage.setVisibility(View.GONE);
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
                    return new CourseListContainerFragment();
                case 1:
                    return new Fragment_Msg();
                case 2:
                    return new PostListContainerFragment();
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
        if(visibleItems == null || addCourseItem == null) {
            Log.e(TAG, "setCourseMenu");
            return;
        }
        disableCurrentMenu();/*
        user currentUser = (user)Global.getObjectByName("user");
        if(userInfoMock.getUserType() == UserType.TEACHER) {

            addCourseItem.setVisible(true);
            visibleItems.add(addCourseItem);
        }*/
    }

    private void setMessageMenu() {
        if(visibleItems == null) {
            Log.e(TAG, "setMessageMenu");
            return;
        }
        disableCurrentMenu();
    }

    private void setCampusMenu() {
        if(visibleItems == null || addPostItem == null) {
            Log.e(TAG, "setCampusMenu");
            return;
        }

        disableCurrentMenu();
        addPostItem.setVisible(true);
        visibleItems.add(addPostItem);
    }

    private void disableCurrentMenu() {
        if(visibleItems == null)
            return;
        for(MenuItem item: visibleItems)
            item.setVisible(false);
        visibleItems.clear();
    }

    @Override
    public void onMainFragmentInteraction(Uri uri) {

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