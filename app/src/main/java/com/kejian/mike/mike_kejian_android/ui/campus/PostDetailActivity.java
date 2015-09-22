package com.kejian.mike.mike_kejian_android.ui.campus;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.kejian.mike.mike_kejian_android.R;

/**
 * Created by showjoy on 15/9/20.
 */
public class PostDetailActivity extends AppCompatActivity {

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
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
        for(int i=0; i<10; i++) {
            View replyView = layoutInflater.inflate(R.layout.layout_single_reply, null);
            reply_view.addView(replyView);
        }



    }


}
