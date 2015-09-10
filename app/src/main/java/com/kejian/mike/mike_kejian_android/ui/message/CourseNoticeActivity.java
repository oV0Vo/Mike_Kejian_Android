package com.kejian.mike.mike_kejian_android.ui.message;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

import bl.MessageBLService;

public class CourseNoticeActivity extends Activity implements View.OnClickListener{

    private View layout_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_notice);
        this.layout_title = findViewById(R.id.layout_bar);
        initViews();

    }
    private void initViews(){
        ImageView iv = (ImageView)this.layout_title.findViewById(R.id.image_title);
        iv.setImageResource(R.drawable.left);
        iv.setOnClickListener(this);
        TextView tv = (TextView)this.layout_title.findViewById(R.id.txt_title);
        tv.setText("课程公告");

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
