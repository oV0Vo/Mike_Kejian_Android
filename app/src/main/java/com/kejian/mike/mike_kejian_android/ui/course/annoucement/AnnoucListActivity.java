package com.kejian.mike.mike_kejian_android.ui.course.annoucement;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;

import java.util.ArrayList;
import java.util.List;

import model.course.CourseModel;
import dataType.course.CourseAnnoucement;
import util.TimeFormat;

public class AnnoucListActivity extends AppCompatActivity {

    private CourseModel courseModel;

    private ViewGroup mainLayout;
    private ProgressBar progressBar;

    private ListView annoucListView;
    private AnnoucBriefAdapter annoucListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseModel = CourseModel.getInstance();
        setContentView(R.layout.activity_annouc_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mainLayout = (ViewGroup)findViewById(R.id.main_layout);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        mainLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        annoucListView = (ListView)findViewById(R.id.history_annouc_list_view);
        ArrayList<CourseAnnoucement> historyAnnoucs = courseModel.getAnnoucs();
        annoucListAdapter = new AnnoucBriefAdapter(this, android.R.layout.simple_list_item_1,
                historyAnnoucs);
        annoucListView.setAdapter(annoucListAdapter);

        new GetAnnoucTask().execute();
    }

    private void updateViewOnGetNewAnnoucs(ArrayList<CourseAnnoucement> newAnnoucs) {
        Log.e("AnnoucActivity", "updateViewOnGetNewAnnoucs");
        progressBar.setVisibility(View.GONE);
        mainLayout.setVisibility(View.VISIBLE);
        annoucListAdapter.notifyDataSetChanged();
        CourseAnnoucement topAnnouc = courseModel.getOnTopAnnouc();
        if(topAnnouc != null)
            setOnTopAnnoucView(topAnnouc);
    }

    private void setOnTopAnnoucView(CourseAnnoucement topAnnouc) {
        ViewGroup topAnnoucLayout = (ViewGroup)findViewById(R.id.top_annouc_layout);
        topAnnoucLayout.setVisibility(View.VISIBLE);

        TextView titleText = (TextView)topAnnoucLayout.findViewById(R.id.top_annouc_title_text);
        titleText.setText(topAnnouc.getTitle());

        TextView authorNameText = (TextView)topAnnoucLayout.findViewById(R.id.top_author_name_text);
        authorNameText.setText(topAnnouc.getPersonName());

        TextView timeText = (TextView)topAnnoucLayout.findViewById(R.id.top_time_text);
        timeText.setText(TimeFormat.toMinute(topAnnouc.getDate()));
    }

    private class AnnoucBriefAdapter extends ArrayAdapter<CourseAnnoucement> {

        public AnnoucBriefAdapter(Context context, int resource, List<CourseAnnoucement> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            if(convertView != null) {
                return convertView;
            }

            convertView = getLayoutInflater().inflate(R.layout.layout_annouc_brief, null);
            CourseAnnoucement annouc = getItem(position);

            TextView titleText = (TextView)convertView.findViewById(R.id.annouc_title_text);
            titleText.setText(annouc.getTitle());
            if(position == 0) {
                ViewGroup titleLayout = (ViewGroup)convertView.findViewById(R.id.annouc_title_layout);
                titleLayout.addView(getIsNewAnnoucImage());
            }

            TextView contentText = (TextView)convertView.findViewById(R.id.annouc_content_text);
            contentText.setText(annouc.getContent());

            TextView authorNameText = (TextView)convertView.findViewById(R.id.annouc_author_name_text);
            authorNameText.setText(annouc.getPersonName());

            TextView timeText = (TextView)convertView.findViewById(R.id.annouc_time_text);
            timeText.setText(TimeFormat.toMinute(annouc.getDate()));

            return convertView;
        }

        private ImageView getIsNewAnnoucImage() {
            ImageView image = new ImageView(AnnoucListActivity.this);
            //image.setBackgroundResource(R.drawable.);
            return image;
        }
    }

    private class GetAnnoucTask extends AsyncTask<Void, Void, ArrayList<CourseAnnoucement>> {

        @Override
        protected ArrayList<CourseAnnoucement> doInBackground(Void... params) {
            CourseModel courseModel = CourseModel.getInstance();
            return courseModel.updateAnnoucs();
        }

        @Override
        protected void onPostExecute(ArrayList<CourseAnnoucement> newAnnoucs) {
            if(newAnnoucs != null) {
                updateViewOnGetNewAnnoucs(newAnnoucs);
            } else {
                Toast.makeText(AnnoucListActivity.this, R.string.net_disconnet, Toast.LENGTH_LONG)
                        .show();
            }
        }
    }
}
