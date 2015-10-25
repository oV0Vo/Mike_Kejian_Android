package com.kejian.mike.mike_kejian_android.ui.course.annoucement;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.kejian.mike.mike_kejian_android.dataType.course.CourseAnnoucement;
import util.TimeFormat;

public class AnnoucListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private CourseModel courseModel;

    private ViewGroup mainLayout;

    private ListView annoucListView;
    private AnnoucBriefAdapter annoucListAdapter;

    private CourseAnnoucement onTopAnnouc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseModel = CourseModel.getInstance();
        setContentView(R.layout.activity_annouc_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mainLayout = (ViewGroup)findViewById(R.id.main_layout);

        annoucListView = (ListView)findViewById(R.id.history_annouc_list_view);
        ArrayList<CourseAnnoucement> historyAnnoucs = courseModel.getAnnoucs();
        annoucListAdapter = new AnnoucBriefAdapter(this, android.R.layout.simple_list_item_1,
                historyAnnoucs);
        annoucListView.setAdapter(annoucListAdapter);
        annoucListView.setOnItemClickListener(this);

        CourseAnnoucement topAnnouc = courseModel.getOnTopAnnouc();
        if(topAnnouc != null)
            setOnTopAnnoucView(topAnnouc);
        CourseModel d;
    }

    private void setOnTopAnnoucView(CourseAnnoucement topAnnouc) {
        onTopAnnouc = topAnnouc;

        ViewGroup topAnnoucLayout = (ViewGroup)findViewById(R.id.top_annouc_layout);
        topAnnoucLayout.setVisibility(View.VISIBLE);

        TextView titleText = (TextView)topAnnoucLayout.findViewById(R.id.top_annouc_title_text);
        titleText.setText(topAnnouc.getTitle());

        TextView authorNameText = (TextView)topAnnoucLayout.findViewById(R.id.top_author_name_text);
        authorNameText.setText(topAnnouc.getPersonName());

        TextView timeText = (TextView)topAnnoucLayout.findViewById(R.id.top_time_text);
        timeText.setText(TimeFormat.toMinute(topAnnouc.getDate()));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CourseAnnoucement annouc = (CourseAnnoucement)parent.getItemAtPosition(position);
        CourseModel courseModel = CourseModel.getInstance();
        courseModel.setCurrentFocusAnnouc(annouc);
        Intent i = new Intent(this, AnnoucDetailActivity.class);
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        CourseModel courseModel = CourseModel.getInstance();
        CourseAnnoucement courseAnnouc = courseModel.getCurrentFocusAnnouc();
        if(courseAnnouc != onTopAnnouc) {
            courseAnnouc.setOnTop(false);
            setOnTopAnnoucView(courseAnnouc);
        }
    }

    private class AnnoucBriefAdapter extends ArrayAdapter<CourseAnnoucement> {

        public AnnoucBriefAdapter(Context context, int resource, List<CourseAnnoucement> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            if(convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.layout_annouc_brief, null);
            }

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
            image.setBackgroundResource(R.drawable.new_img);
            return image;
        }
    }
}
