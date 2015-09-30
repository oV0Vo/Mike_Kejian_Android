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

import model.user.Invitee;

/**
 * Created by showjoy on 15/9/20.
 */
public class PostDetailActivity extends AppCompatActivity {

    ActionBar actionBar;
    private TextView author;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        context=this;
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
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

    }

    private void iniView(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LinearLayout reply_view = (LinearLayout) findViewById(R.id.reply_container);
        author=(TextView)findViewById(R.id.author_name_view);
        author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(context, UserBaseInfoOtherView.class);
                startActivity(intent);
            }
        });
        for(int i=0; i<10; i++) {
            View replyView = layoutInflater.inflate(R.layout.layout_single_reply, null);
            reply_view.addView(replyView);
        }



    }


}
