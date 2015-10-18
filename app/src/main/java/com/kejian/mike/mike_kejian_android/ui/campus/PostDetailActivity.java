package com.kejian.mike.mike_kejian_android.ui.campus;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.message.OnRefreshListener;
import com.kejian.mike.mike_kejian_android.ui.message.RefreshListView;
import com.kejian.mike.mike_kejian_android.ui.user.UserBaseInfoOtherView;

import bl.CampusBLService;
import model.campus.Post;
import model.campus.Reply;
import model.user.Invitee;

/**
 * Created by showjoy on 15/9/20.
 */
public class PostDetailActivity extends AppCompatActivity implements OnRefreshListener{

    ActionBar actionBar;
    private Post post;
    private String postId;
    private LinearLayout mainLayout;
    private RefreshListView container;
    private LayoutInflater myInflater;
    private ProgressBar progressBar;
    private ReplyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        this.mainLayout = (LinearLayout)findViewById(R.id.reply);
        this.mainLayout.setVisibility(View.GONE);
        this.progressBar = (ProgressBar)findViewById(R.id.post_detail_progress_bar);
        iniData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void iniData() {
        postId = "1234";
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                String postId = params[0];
                post = CampusBLService.getPostDetail("test");
                return null;
            }

            @Override
            public void onPostExecute(Void result) {
                progressBar.setVisibility(View.GONE);
                iniView();
                mainLayout.setVisibility(View.VISIBLE);
            }
        }.execute(postId);


    }

    private void iniView(){
        this.container = (RefreshListView)findViewById(R.id.reply_container);
        this.adapter = new ReplyAdapter(this, R.layout.layout_reply, post.getReplyList());
        this.container.setAdapter(adapter);
        this.container.setOnRefreshListener(this);
    }


    @Override
    public void onDownPullRefresh() {

    }

    @Override
    public void onLoadingMore() {

    }
}
