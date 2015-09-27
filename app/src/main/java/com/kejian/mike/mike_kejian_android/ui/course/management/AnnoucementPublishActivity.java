package com.kejian.mike.mike_kejian_android.ui.course.management;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;

import bl.UserInfoService;
import model.course.data.CourseAnnoucement;
import model.course.data.CourseBriefInfo;
import model.course.CourseModel;
import util.NetOperateResultMessage;
import util.UnImplementedAnnotation;

public class AnnoucementPublishActivity extends AppCompatActivity {

    private CourseModel courseModel;
    private EditText titleText;
    private EditText contentText;
    private Button commitButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annoucement_publish);

        courseModel = CourseModel.getInstance();
        TextView courseTitle = (TextView)findViewById(R.id.annoucment_publish_course_name);
        CourseBriefInfo courseBrief = courseModel.getCurrentCourseBrief();
        if(courseBrief != null)
            courseTitle.setText(courseBrief.getCourseName());
        else
            Log.e("AnnoucPublish", "current brief null!");

        titleText = (EditText)findViewById(R.id.annoucement_publish_title_text);
        contentText = (EditText)findViewById(R.id.annoucement_publish_content_text);

        commitButton = (Button)findViewById(R.id.annoucement_publish_commit_button);
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

                CourseBriefInfo courseBrief = CourseModel.getInstance().getCurrentCourseBrief();
                String personId = UserInfoService.getInstance().getPersonId();
                if(courseBrief == null || personId == null) {
                    Toast.makeText(AnnoucementPublishActivity.this, R.string.internal_logic_error_message
                            , Toast.LENGTH_SHORT).show();
                    return;
                }

                CourseAnnoucement newAnnoucement = new CourseAnnoucement();
                newAnnoucement.setPersonId(personId);
                String courseId = courseBrief.getCourseId();
                newAnnoucement.setCourseId(courseId);
                newAnnoucement.setTitle(title);
                newAnnoucement.setContent(content);

                new CommitAnnoucementTask().execute(newAnnoucement);
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        progressBar = (ProgressBar)findViewById(R.id.annoucement_publish_progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_annoucement_publish, menu);
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

    private class CommitAnnoucementTask extends AsyncTask<CourseAnnoucement, Void , NetOperateResultMessage> {

        @Override
        protected NetOperateResultMessage doInBackground(CourseAnnoucement... params) {
            CourseAnnoucement newAnnoucement = params[0];
            NetOperateResultMessage resultMessage = CourseModel.getInstance().newAnnoucement(newAnnoucement);
            return resultMessage;
        }

        @UnImplementedAnnotation
        @Override
        protected void onPostExecute(NetOperateResultMessage reusltMessage) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(AnnoucementPublishActivity.this, R.string.annoucement_publish_success_message
                    , Toast.LENGTH_LONG).show();
            AnnoucementPublishActivity.this.finish();
        }
    }
}
