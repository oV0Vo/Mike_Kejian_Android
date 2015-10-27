package com.kejian.mike.mike_kejian_android.ui.message;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.broadcast.ReceiverActions;
import com.kejian.mike.mike_kejian_android.ui.main.CoursePostSearchActivity;
import com.kejian.mike.mike_kejian_android.ui.main.SearchableActivity;
import com.kejian.mike.mike_kejian_android.ui.widget.MyUmengMessageHandler;

import java.util.List;

import bl.MessageBLService;
import model.message.CourseNotice;
import model.message.MessageType;

public class CourseNoticeActivity extends AppCompatActivity implements View.OnClickListener, OnRefreshListener, AdapterView.OnItemClickListener{

//    private View layout_title;
    private RefreshListView container;
    private LayoutInflater myInflater;
    private LinearLayout mainLayout;
    private ProgressBar progressBar;
    private ArrayAdapter<CourseNotice> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_notice);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        this.layout_title = findViewById(R.id.layout_bar);
        this.mainLayout =(LinearLayout)findViewById(R.id.course_notice);
        this.mainLayout.setVisibility(View.GONE);

        this.progressBar = (ProgressBar)findViewById(R.id.course_notice_progress_bar);

        initData();
//        initViews();

    }

    @Override
    public void onDownPullRefresh() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                MessageBLService.refreshCourseNotices("123");
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                adapter.notifyDataSetChanged();
                container.hideHeaderView();
                refreshTotalCourseNoticeNum();
            }
        }.execute(new Void[]{});

    }

    @Override
    public void onLoadingMore() {
        if(MessageBLService.totalCourseNotice > MessageBLService.courseNotices.size()){
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    MessageBLService.addCourseNotices("12343");
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    adapter.notifyDataSetChanged();

                    // 控制脚布局隐藏
                    container.hideFooterView();
                }
            }.execute(new Void[]{});
        }else{
            container.hideFooterView();
        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent messageIncreIntent = new Intent(ReceiverActions.increment_action);
//        messageIncreIntent.putExtra("messageType", MessageType.mentionMe);
//        LocalBroadcastManager.getInstance(this).sendBroadcast(messageIncreIntent);
//        Intent intent = new Intent(ReceiverActions.increment_action);
//        intent.putExtra("messageType", MessageType.mentionMe);
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
//        Intent intent = new Intent();
//        intent.setClass(this,CoursePostSearchActivity.class);
//        startActivity(intent);
    }

    private class InitDataTask extends AsyncTask<String, Integer, String> {
        @Override
        public String doInBackground(String... params) {
            String userId = params[0];
            MessageBLService.refreshTotalCourseNoticeNum(userId);
            MessageBLService.initCourseNotices(userId);
            return "";
        }

        @Override
        public void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            initViews();
            mainLayout.setVisibility(View.VISIBLE);
        }
    }
    private void initData(){
//        this.courseNotices = MessageBLService.getCourseNoticeList();
//        this.courseNoticeNum = this.courseNotices.size();
        new InitDataTask().execute("123");
    }
    private void initViews(){
//        ImageView iv = (ImageView)this.layout_title.findViewById(R.id.image_title);
//        iv.setImageResource(R.drawable.left);
//        iv.setOnClickListener(this);
//        TextView tv = (TextView)this.layout_title.findViewById(R.id.txt_title);
//        tv.setText("课程公告");
        this.container = (RefreshListView)findViewById(R.id.course_notice_container);
        this.myInflater = getLayoutInflater();
        this.refreshTotalCourseNoticeNum();
//        for(int i = 0;i<this.courseNotices.size();i++){
//            this.container.addView(this.genCourseNoticeLayout(this.courseNotices.get(i)));
//            this.container.addView(this.genLineSplitView());
//        }
        this.adapter = new CourseNoticeAdapter(this, android.R.layout.simple_list_item_1, MessageBLService.courseNotices);
        this.container.setAdapter(this.adapter);
        this.container.setOnRefreshListener(this);
        this.container.setOnItemClickListener(this);


    }
    private void refreshTotalCourseNoticeNum(){
        TextView course_notice_num = (TextView)this.findViewById(R.id.course_notice_num);
        course_notice_num.setText("共 "+MessageBLService.totalCourseNotice+" 条");
    }
    static class ViewHolder{
        TextView course_name_view;
        TextView notice_content_view;
        TextView publisher_view;
        TextView publish_time_view;
    }

    private class CourseNoticeAdapter extends ArrayAdapter<CourseNotice>{
        public CourseNoticeAdapter(Context context, int layoutId, List<CourseNotice> courseNotices){
            super(context,layoutId,courseNotices);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = myInflater.inflate(R.layout.layout_course_notice
                        , null);
                viewHolder = new ViewHolder();
                viewHolder.course_name_view = (TextView)convertView.findViewById(R.id.course_name_view);
                viewHolder.notice_content_view = (TextView)convertView.findViewById(R.id.notice_content_view);
                viewHolder.publisher_view = (TextView)convertView.findViewById(R.id.publisher_view);
                viewHolder.publish_time_view = (TextView)convertView.findViewById(R.id.publish_time_view);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }
            CourseNotice courseNotice = getItem(position);
            viewHolder.course_name_view.setText(courseNotice.getCourseName());
            viewHolder.notice_content_view.setText(courseNotice.getContent());
            viewHolder.publisher_view.setText(courseNotice.getPublisher());
            viewHolder.publish_time_view.setText(courseNotice.getPublishTime());
            return convertView;
        }
    }
//    private View genLineSplitView(){
//        View lineView = new View(this);
//        LinearLayout.LayoutParams layout_line = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,2);
//        lineView.setBackgroundResource(R.color.black2);
//        lineView.setLayoutParams(layout_line);
//        return lineView;
//    }
//    private LinearLayout genCourseNoticeLayout(CourseNotice courseNotice){
//        int dp5 = DensityUtil.dip2px(this,5);
//        int dp15 = DensityUtil.dip2px(this,15);
//        int dp8 = DensityUtil.dip2px(this,8);
//        int dp2 = DensityUtil.dip2px(this,2);
//
//        LinearLayout coursenoticeLayout = new LinearLayout(this);
//        LinearLayout.LayoutParams  layout_linear = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(this,125));
//        layout_linear.setMargins(0,dp5,0,0);
//        coursenoticeLayout.setOrientation(LinearLayout.VERTICAL);
//        coursenoticeLayout.setBaselineAligned(false);
//        coursenoticeLayout.setBackgroundResource(R.drawable.setting_item_selector);
//        coursenoticeLayout.setLayoutParams(layout_linear);
//        String courseName = courseNotice.getCourseName();
//        String content = courseNotice.getContent();
//        String pulisher = courseNotice.getPublisher();
//        String pulishTime = courseNotice.getPublishTime();
//
//        TextView course_name_view = new TextView(this);
//        LinearLayout.LayoutParams layout_course_name = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layout_course_name.setMargins(dp15, dp8, 0, dp5);
//        course_name_view.setText(courseName);
//        course_name_view.setTextColor(getResources().getColor(R.color.green));
//        course_name_view.setTextSize(20);
//        course_name_view.setLayoutParams(layout_course_name);
//
//        View line_view = new View(this);
//        line_view.setBackgroundResource(R.color.green);
//        LinearLayout.LayoutParams layout_line = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1);
//        layout_line.setMargins(dp15, 0, dp15, 0);
//        line_view.setLayoutParams(layout_line);
//
//        TextView content_view =  new TextView(this);
//        LinearLayout.LayoutParams layout_content = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layout_content.setMargins(dp15, dp5, dp15, 0);
//        content_view.setText(content);
//        content_view.setLayoutParams(layout_content);
//
//        LinearLayout bottomLinearView = new LinearLayout(this);
//        LinearLayout.LayoutParams layout_bottom = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layout_bottom.setMargins(dp15, dp8, 0, 0);
//        bottomLinearView.setLayoutParams(layout_bottom);
//        bottomLinearView.setOrientation(LinearLayout.HORIZONTAL);
//
//        LinearLayout bottomLeftView = new LinearLayout(this);
//        LinearLayout.LayoutParams layout_bottom_left = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        bottomLeftView.setGravity(Gravity.LEFT);
//        bottomLeftView.setLayoutParams(layout_bottom_left);
//
//        TextView publisher_view = new TextView(this);
//        LinearLayout.LayoutParams layout_publisher = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        publisher_view.setText(pulisher);
//        publisher_view.setTextSize(12);
//        publisher_view.setLayoutParams(layout_publisher);
//
//        TextView publish_view = new TextView(this);
//        LinearLayout.LayoutParams layout_publish = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layout_publish.setMargins(dp2, 0, dp2, 0);
//        publish_view.setText("发表于");
//        publish_view.setTextSize(12);
//        publish_view.setLayoutParams(layout_publish);
//
//        TextView publish_time_view = new TextView(this);
//        publish_time_view.setText(pulishTime);
//        publish_time_view.setTextSize(12);
//        publish_time_view.setLayoutParams(layout_publisher);
//
//        bottomLeftView.addView(publisher_view);
//        bottomLeftView.addView(publish_view);
//        bottomLeftView.addView(publish_time_view);
//
//        LinearLayout bottomRightView = new LinearLayout(this);
//        LinearLayout.LayoutParams layout_bottom_right = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        layout_bottom_right.setMargins(0, 0, dp15, 0);
//        bottomRightView.setGravity(Gravity.RIGHT);
//        bottomRightView.setLayoutParams(layout_bottom_right);
//
//        TextView arrow_view = new TextView(this);
//        arrow_view.setText(">");
//        arrow_view.setTextColor(getResources().getColor(R.color.green));
//        arrow_view.setLayoutParams(layout_publisher);
//
//        bottomRightView.addView(arrow_view);
//
//        bottomLinearView.addView(bottomLeftView);
//        bottomLinearView.addView(bottomRightView);
//
//        coursenoticeLayout.addView(course_name_view);
//        coursenoticeLayout.addView(line_view);
//        coursenoticeLayout.addView(content_view);
//        coursenoticeLayout.addView(bottomLinearView);
//        return coursenoticeLayout;
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_notice, menu);
        return true;
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
        }else if(id == android.R.id.home){
            MessageBLService.unreadCourseNoticeNum = 0;
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        MessageBLService.unreadCourseNoticeNum = 0;
        CourseNoticeActivity.this.finish();

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            MessageBLService.unreadCourseNoticeNum = 0;
            CourseNoticeActivity.this.finish();
        }

        return false;

    }
}
