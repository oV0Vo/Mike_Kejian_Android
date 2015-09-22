package com.kejian.mike.mike_kejian_android.ui.course.management;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;

import java.util.ArrayList;

import model.course.CourseDetailInfo;
import util.NetOperateResultMessage;
import util.UnImplementedAnnotation;

public class CourseCreateActivity extends AppCompatActivity {

    private EditText titleText;
    private EditText introText;
    private EditText contentText;
    private Button addTeacherButton;
    private ViewGroup teacherContainer;
    private Button addAssistantButton;
    private ViewGroup assistantContainer;
    private Button commitButton;
    private ProgressBar progressBar;

    private ArrayList<String> teacherIds;

    @UnImplementedAnnotation("需要恢复状态的逻辑")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_create);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.course_add_title);

        titleText = (EditText)findViewById(R.id.course_create_name_text);
        introText = (EditText)findViewById(R.id.course_create_intro_text);
        contentText = (EditText)findViewById(R.id.course_create_content_text);
        addTeacherButton = (Button)findViewById(R.id.course_create_add_teacher_button);
        teacherContainer = (ViewGroup)findViewById(R.id.course_create_teacher_container);
        addAssistantButton = (Button)findViewById(R.id.course_create_add_assistant_button);
        assistantContainer = (ViewGroup)findViewById(R.id.course_create_assistant_container);
        commitButton = (Button)findViewById(R.id.course_create_commit_button);
        progressBar = (ProgressBar)findViewById(R.id.course_create_progress_bar);
        progressBar.setVisibility(View.GONE);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
    }

    private void initAddTeacherButtonListener() {
        addTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initAddAssistantButtonListener() {
        addAssistantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @UnImplementedAnnotation
    private void initCommitButtonListener() {
        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleText.getText().toString();
                if(title.length() == 0) {
                    Toast.makeText(CourseCreateActivity.this, R.string.course_create_no_name_message
                            , Toast.LENGTH_SHORT);
                    return;
                }

                if(teacherIds.size() == 0) {
                    Toast.makeText(CourseCreateActivity.this, R.string.course_create_no_teacher_message
                            , Toast.LENGTH_SHORT);
                    return;
                }

                String intro = introText.getText().toString();
                String content = contentText.getText().toString();

                CourseDetailInfo newCourse = new CourseDetailInfo();
                progressBar.setVisibility(View.VISIBLE);
                new SubmitNewCourseTask().execute(newCourse);
            }
        });
    }

    @UnImplementedAnnotation("需要加上acitvity状态保存逻辑")
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_create, menu);
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

    private class SubmitNewCourseTask extends AsyncTask<CourseDetailInfo, Void, NetOperateResultMessage> {

        @UnImplementedAnnotation
        @Override
        protected NetOperateResultMessage doInBackground(CourseDetailInfo... params) {
            return null;
        }

        @Override
        protected void onPostExecute(NetOperateResultMessage resultMessage) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(CourseCreateActivity.this, R.string.course_create_success_message,
                    Toast.LENGTH_LONG).show();
            CourseCreateActivity.this.finish();
        }
    }
}
