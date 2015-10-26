package com.kejian.mike.mike_kejian_android.ui.course.annoucement;

import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.dataType.course.CourseAnnoucement;

import net.course.CourseAnnoucNetService;

import model.course.CourseModel;
import util.TimeFormat;

public class AnnoucDetailActivity extends AppCompatActivity {

    private CourseAnnoucement annouc;

    private TextView putOnTopView;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annouc_detail);

        CourseModel courseModel = CourseModel.getInstance();
        annouc = courseModel.getCurrentFocusAnnouc();

        TextView authorNameText = (TextView)findViewById(R.id.author_name_text);
        authorNameText.setText(annouc.getPersonName());

        TextView timeText = (TextView)findViewById(R.id.time_text);
        timeText.setText(TimeFormat.toMinute(annouc.getDate()));

        TextView titleText = (TextView)findViewById(R.id.title_text);
        titleText.setText(annouc.getTitle());

        TextView contentText = (TextView)findViewById(R.id.content_text);
        contentText.setText(annouc.getContent());

        putOnTopView = (TextView)findViewById(R.id.put_on_top_action_text);
        if(!annouc.isOnTop()) {
            putOnTopView.setText(R.string.put_on_top);
            putOnTopView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new PutOnTopTask().execute(annouc.getAnnoucId());
                    putOnTopView.setBackgroundColor(getResources().getColor(R.color.dark));
                    putOnTopView.setEnabled(false);
                    progressBar.setVisibility(View.VISIBLE);
                }
            });
        } else {
            putOnTopView.setText(R.string.annouc_already_put_on_top);
            putOnTopView.setEnabled(false);
        }

        progressBar = (ProgressBar)findViewById(R.id.progress_bar);

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

    private class PutOnTopTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String annoucId = params[0];
            boolean success = CourseAnnoucNetService.putOnTop(annoucId);
            return success;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if(progressBar != null) {
                progressBar.setVisibility(View.GONE);
                if(success) {
                    Toast.makeText(AnnoucDetailActivity.this, R.string.annouc_put_on_top_success,
                            Toast.LENGTH_LONG).show();
                    putOnTopView.setBackgroundColor(getResources().getColor(R.color.green));
                    putOnTopView.setText(R.string.annouc_already_put_on_top);
                    putOnTopView.setEnabled(false);
                    annouc.setOnTop(true);
                } else {
                    Toast.makeText(AnnoucDetailActivity.this, R.string.net_disconnet,
                            Toast.LENGTH_LONG).show();
                    putOnTopView.setBackgroundColor(getResources().getColor(R.color.green));
                    putOnTopView.setEnabled(true);
                }
            }
        }
    }

}
