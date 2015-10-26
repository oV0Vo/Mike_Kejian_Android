package com.kejian.mike.mike_kejian_android.ui.course.management;

import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;

import bl.UserInfoServiceMock;
import com.kejian.mike.mike_kejian_android.dataType.course.CourseAnnoucement;
import com.kejian.mike.mike_kejian_android.dataType.course.CourseBriefInfo;
import com.kejian.mike.mike_kejian_android.dataType.course.CourseDetailInfo;

import net.course.CourseAnnoucNetService;

import model.course.CourseModel;
import util.NetOperateResultMessage;
import util.UnImplementedAnnotation;

public class AnnoucementPublishActivity extends AppCompatActivity {

    private CourseModel courseModel;
    private EditText titleText;
    private EditText contentText;
    private TextView commitButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annoucement_publish);

        courseModel = CourseModel.getInstance();
        TextView courseTitle = (TextView)findViewById(R.id.annoucment_publish_course_name);
        String courseName = courseModel.getCurrentCourseDetail().getCourseName();
        courseTitle.setText(courseName);

        titleText = (EditText)findViewById(R.id.annoucement_publish_title_text);
        contentText = (EditText)findViewById(R.id.annoucement_publish_content_text);

        commitButton = (TextView)findViewById(R.id.annoucement_publish_commit_button);
        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleText.getText().toString();
                if(title.length() == 0) {
                    Toast.makeText(AnnoucementPublishActivity.this, R.string.annoucement_publish_no_title_message
                            , Toast.LENGTH_SHORT).show();
                    return;
                }

                String content = contentText.getText().toString();
                if(content.length() == 0) {
                    Toast.makeText(AnnoucementPublishActivity.this, R.string.annoucement_publish_no_content_message
                            , Toast.LENGTH_SHORT).show();
                    return;
                }

                String courseId = courseModel.getCurrentCourseId();
                new CommitAnnoucementTask().execute(courseId, title, content);
                commitButton.setEnabled(false);
                commitButton.setBackgroundColor(getResources().getColor(R.color.dark));
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        progressBar = (ProgressBar)findViewById(R.id.annoucement_publish_progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            case android.R.id.home:
                if(NavUtils.getParentActivityIntent(this) != null)
                    NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class CommitAnnoucementTask extends AsyncTask<String, Void , Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String courseId = params[0];
            String title = params[1];
            String content = params[2];
            boolean success = CourseAnnoucNetService.newAnnouc(courseId,
                    title, content);
            return success;
        }

        @UnImplementedAnnotation
        @Override
        protected void onPostExecute(Boolean success) {
            if(progressBar == null)
                return;

            progressBar.setVisibility(View.GONE);
            if(success) {
                Toast.makeText(AnnoucementPublishActivity.this, R.string.annoucement_publish_success_message
                        , Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(AnnoucementPublishActivity.this, R.string.net_disconnet
                        , Toast.LENGTH_SHORT).show();
                commitButton.setEnabled(true);
                commitButton.setBackgroundColor(getResources().getColor(R.color.green));
            }
        }
    }
}
