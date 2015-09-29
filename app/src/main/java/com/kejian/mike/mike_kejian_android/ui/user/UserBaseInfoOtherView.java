package com.kejian.mike.mike_kejian_android.ui.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

import com.kejian.mike.mike_kejian_android.R;

/**
 * Created by kisstheraik on 15/9/19.
 */
public class UserBaseInfoOtherView extends Activity{

    private LinearLayout course;
    private LinearLayout people;
    private LinearLayout postList;
    private Context context;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_info_otherview);
        context=this;
        initViews();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mention_me, menu);
        return true;
    }

    public void initViews(){


        course=(LinearLayout)findViewById(R.id.his_attention_course);
        people=(LinearLayout)findViewById(R.id.his_attention_people);
        postList=(LinearLayout)findViewById(R.id.his_attention_post);

        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(context,UserAttentionListActivity.class);
                startActivity(intent);
            }
        });
        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(context,UserAttentionListActivity.class);
                startActivity(intent);
            }
        });
        postList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(context,UserAttentionListActivity.class);
                startActivity(intent);
            }
        });


    }
}
