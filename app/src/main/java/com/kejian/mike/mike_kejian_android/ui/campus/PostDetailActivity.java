package com.kejian.mike.mike_kejian_android.ui.campus;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.user.UserBaseInfoOtherView;

import bl.CampusBLService;
import model.campus.Post;
import model.campus.Reply;
import model.user.Invitee;

/**
 * Created by showjoy on 15/9/20.
 */
public class PostDetailActivity extends AppCompatActivity {

    ActionBar actionBar;
    private TextView author;
    private Context context;
    private Post post;
    private String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        context=this;
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        iniData();
        iniView();
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
        post = CampusBLService.getPostDetail("test");
    }

    private void iniView(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LinearLayout reply_view = (LinearLayout) findViewById(R.id.reply_container);
        author=(TextView)findViewById(R.id.author_name_view);
        author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, UserBaseInfoOtherView.class);
                startActivity(intent);
            }
        });
        TextView title = (TextView)findViewById(R.id.title_view);
        title.setText(post.getTitle());
        TextView content = (TextView)findViewById(R.id.content_view);
        content.setText(post.getContent());
        TextView author = (TextView)findViewById(R.id.author_name_view);
        author.setText(post.getAuthorName());
        TextView date = (TextView)findViewById(R.id.date_view);
        date.setText(post.getDate().toString());
        TextView viewNum = (TextView)findViewById(R.id.view_num_view);
        viewNum.setText(Integer.toString(post.getPraise()));
        TextView commentNum = (TextView)findViewById(R.id.comment_num_view);
        commentNum.setText(Integer.toString(post.getReplyList().size()));
        TextView replyNumView = (TextView)findViewById(R.id.reply_num_view);
        replyNumView.setText("共(" + post.getReplyList().size() + ")条");

        for(Reply loopReply: post.getReplyList()) {
            View replyView = layoutInflater.inflate(R.layout.layout_single_reply, null);
            TextView replyContent = (TextView) replyView.findViewById(R.id.single_reply_content_view);
            replyContent.setText(loopReply.getContent());
            TextView replyAuthor = (TextView) replyView.findViewById(R.id.author_name_view);
            replyAuthor.setText(loopReply.getAuthorName());
            TextView replyDate = (TextView) replyView.findViewById(R.id.date_view);
            replyDate.setText(loopReply.getDate().toString());
            TextView replyViewNum = (TextView) replyView.findViewById(R.id.view_num_view);
            replyViewNum.setText(Integer.toString(loopReply.getPraise()));
            TextView replyCommentNum = (TextView) replyView.findViewById(R.id.comment_num_view);
            replyCommentNum.setText(Integer.toString(loopReply.getSubReplyList().size()));
            reply_view.addView(replyView);
        }



    }


}
