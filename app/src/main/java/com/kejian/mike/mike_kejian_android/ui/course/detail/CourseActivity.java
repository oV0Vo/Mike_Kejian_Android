package com.kejian.mike.mike_kejian_android.ui.course.detail;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.kejian.mike.mike_kejian_android.R;

import bl.CourseBLService;
import model.course.CourseDetailInfo;
import model.course.CourseModel;

public class CourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        if(getIntent() != null) {
            String courseId = getIntent().getStringExtra(CourseModel.ARG_COURSE_ID);
            new GetCourseDetailTask().execute(courseId);
        } else {
            Log.i("CourseActivity", "Intent with no courseId !!");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course, menu);
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

    private class GetCourseDetailTask extends AsyncTask<String, Integer, CourseDetailInfo> {
        @Override
        public CourseDetailInfo doInBackground(String... params) {
            String courseId = params[0];
            CourseDetailInfo theCourse = CourseBLService.getInstance().getCourseDetail(courseId);
            return theCourse;
        }

        @Override
        public void onPostExecute(CourseDetailInfo result) {
            CourseModel.getInstance().setCurrentCourseDetail(result);
        }
    }
}
