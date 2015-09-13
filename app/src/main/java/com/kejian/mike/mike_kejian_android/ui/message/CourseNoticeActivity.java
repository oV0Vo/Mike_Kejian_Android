package com.kejian.mike.mike_kejian_android.ui.message;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

import java.util.ArrayList;

import bl.MessageBLService;
import model.message.CourseNotice;
import util.DensityUtil;

public class CourseNoticeActivity extends Activity implements View.OnClickListener{

    private View layout_title;
    private ArrayList<CourseNotice> courseNotices = new ArrayList<CourseNotice>();
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_notice);
        this.layout_title = findViewById(R.id.layout_bar);
        this.container = (LinearLayout)findViewById(R.id.course_notice_container);
        initData();
        initViews();

    }
    private void initData(){
        this.courseNotices = MessageBLService.getCourseNoticeList();
    }
    private void initViews(){
        ImageView iv = (ImageView)this.layout_title.findViewById(R.id.image_title);
        iv.setImageResource(R.drawable.left);
        iv.setOnClickListener(this);
        TextView tv = (TextView)this.layout_title.findViewById(R.id.txt_title);
        tv.setText("课程公告");
        for(int i = 0;i<this.courseNotices.size();i++){
            this.container.addView(this.genCourseNoticeLayout(this.courseNotices.get(i)));
            this.container.addView(this.genLineSplitView());
        }


    }
    private View genLineSplitView(){
        View lineView = new View(this);
        LinearLayout.LayoutParams layout_line = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,2);
        lineView.setBackgroundResource(R.color.black2);
        lineView.setLayoutParams(layout_line);
        return lineView;
    }
    private LinearLayout genCourseNoticeLayout(CourseNotice courseNotice){
        LinearLayout coursenoticeLayout = new LinearLayout(this);
        LinearLayout.LayoutParams  layout_linear = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(this,125));
        layout_linear.setMargins(0,5,0,0);
        coursenoticeLayout.setOrientation(LinearLayout.VERTICAL);
        coursenoticeLayout.setBaselineAligned(false);
        coursenoticeLayout.setBackgroundResource(R.drawable.setting_item_selector);
        coursenoticeLayout.setLayoutParams(layout_linear);
        String courseName = courseNotice.getCourseName();
        String content = courseNotice.getContent();
        String pulisher = courseNotice.getPublisher();
        String pulishTime = courseNotice.getPublishTime();

        TextView course_name_view = new TextView(this);
        LinearLayout.LayoutParams layout_course_name = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout_course_name.setMargins(15, 15, 0, 4);
        course_name_view.setText(courseName);
        course_name_view.setTextColor(getResources().getColor(R.color.green));
        course_name_view.setTextSize(20);
        course_name_view.setLayoutParams(layout_course_name);

        View line_view = new View(this);
        line_view.setBackgroundResource(R.color.green);
        LinearLayout.LayoutParams layout_line = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1);
        layout_line.setMargins(15, 0, 15, 0);
        line_view.setLayoutParams(layout_line);

        TextView content_view =  new TextView(this);
        LinearLayout.LayoutParams layout_content = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout_content.setMargins(15, 4, 15, 0);
        content_view.setText(content);
        content_view.setLayoutParams(layout_content);

        LinearLayout bottomLinearView = new LinearLayout(this);
        LinearLayout.LayoutParams layout_bottom = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout_bottom.setMargins(15, DensityUtil.dip2px(this,8), 0, 0);
        bottomLinearView.setLayoutParams(layout_bottom);
        bottomLinearView.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout bottomLeftView = new LinearLayout(this);
        LinearLayout.LayoutParams layout_bottom_left = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bottomLeftView.setGravity(Gravity.LEFT);
        bottomLeftView.setLayoutParams(layout_bottom_left);

        TextView publisher_view = new TextView(this);
        LinearLayout.LayoutParams layout_publisher = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        publisher_view.setText(pulisher);
        publisher_view.setTextSize(12);
        publisher_view.setLayoutParams(layout_publisher);

        TextView publish_view = new TextView(this);
        LinearLayout.LayoutParams layout_publish = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout_publish.setMargins(2, 0, 2, 0);
        publish_view.setText("发表于");
        publish_view.setTextSize(12);
        publish_view.setLayoutParams(layout_publish);

        TextView publish_time_view = new TextView(this);
        publish_time_view.setText(pulishTime);
        publish_time_view.setTextSize(12);
        publish_time_view.setLayoutParams(layout_publisher);

        bottomLeftView.addView(publisher_view);
        bottomLeftView.addView(publish_view);
        bottomLeftView.addView(publish_time_view);

        LinearLayout bottomRightView = new LinearLayout(this);
        LinearLayout.LayoutParams layout_bottom_right = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layout_bottom_right.setMargins(0, 0, 15, 0);
        bottomRightView.setGravity(Gravity.RIGHT);
        bottomRightView.setLayoutParams(layout_bottom_right);

        TextView arrow_view = new TextView(this);
        arrow_view.setText(">");
        arrow_view.setTextColor(getResources().getColor(R.color.green));
        arrow_view.setLayoutParams(layout_publisher);

        bottomRightView.addView(arrow_view);

        bottomLinearView.addView(bottomLeftView);
        bottomLinearView.addView(bottomRightView);

        coursenoticeLayout.addView(course_name_view);
        coursenoticeLayout.addView(line_view);
        coursenoticeLayout.addView(content_view);
        coursenoticeLayout.addView(bottomLinearView);
        return coursenoticeLayout;

    }

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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        MessageBLService.unreadCourseNoticeNum = 0;
        CourseNoticeActivity.this.finish();

    }
}
