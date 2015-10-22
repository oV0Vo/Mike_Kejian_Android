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

import net.CampusNetService;

import java.util.ArrayList;

import bl.CampusBLService;
import model.campus.Post;
import model.campus.Reply;
import model.user.Invitee;

/**
 * Created by showjoy on 15/9/20.
 */
public class PostDetailActivity extends AppCompatActivity implements OnRefreshListener {

    ActionBar actionBar;
    private Post post;
    private ArrayList<Reply> replies;
    private String postId;
    private LinearLayout mainLayout;
    private RefreshListView container;
    private ProgressBar progressBar;
    private ReplyAdapter adapter;
    private View header;
    private boolean isFollowed;

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
        postId = getIntent().getStringExtra("postId");
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                String postId = params[0];
                isFollowed = CampusBLService.isFollowed(postId);
                post = CampusBLService.getPostDetail(postId);
                replies = post.getReplyList();
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
        header= getLayoutInflater().inflate(R.layout.layout_post_detail_header, null);
        refreshHeader();
        container.addHeaderView(header);
        this.adapter = new ReplyAdapter(this, R.layout.layout_reply, post.getReplyList());
        this.container.setAdapter(adapter);
        this.container.setOnRefreshListener(this);
    }

    private void refreshHeader() {
        TextView detail_title = (TextView) header.findViewById(R.id.detail_title);
        detail_title.setText(post.getTitle());
        TextView detail_content = (TextView) header.findViewById(R.id.detail_content);
        detail_content.setText(post.getContent());
        TextView detail_author_name = (TextView) header.findViewById(R.id.detail_author_name);
        detail_author_name.setText(post.getAuthorName());
        TextView detail_date = (TextView) header.findViewById(R.id.detail_date);
        detail_date.setText(post.getDate());
        TextView detail_view_num = (TextView) header.findViewById(R.id.detail_view_num);
        detail_view_num.setText(Integer.toString(post.getViewNum()));
        TextView detail_comment_num = (TextView) header.findViewById(R.id.detail_comment_num);
        detail_comment_num.setText(Integer.toString(post.getReplyNum()));
        TextView detail_reply_num = (TextView) header.findViewById(R.id.detail_reply_num);
        detail_reply_num.setText("共(" + post.getReplyNum() + ")条");
        TextView detail_follow_button = (TextView) header.findViewById(R.id.detail_follow_button);
        if(isFollowed) {
            detail_follow_button.setText("已关注");
            detail_follow_button.setEnabled(false);
            detail_follow_button.setBackgroundResource(R.color.white);
        } else {
            detail_follow_button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    TextView tv = (TextView) v;
                    tv.setText("已关注");
                    new AsyncTask<Void, Void, Void>() {

                        @Override
                        protected Void doInBackground(Void... params) {
                            CampusBLService.followThisPost(postId);
                            return null;
                        }
                    }.execute();
                }
            });
        }
    }


    @Override
    public void onDownPullRefresh() {
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                String postId = params[0];
                post = CampusBLService.getPostDetail(postId);
                replies = post.getReplyList();
                return null;
            }

            @Override
            public void onPostExecute(Void result) {
                adapter.notifyDataSetChanged();
                container.hideHeaderView();
                refreshHeader();
            }
        }.execute(postId);

    }

    @Override
    public void onLoadingMore() {

    }
}
