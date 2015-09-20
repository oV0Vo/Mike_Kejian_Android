package com.kejian.mike.mike_kejian_android.ui.user;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.campus.HottestPostListFragment;
import com.kejian.mike.mike_kejian_android.ui.campus.PostListContainerFragment;
import com.kejian.mike.mike_kejian_android.ui.user.adapter.AttentionListAdapter;

import bl.AcademyBLService;

/**
 * Created by kisstheraik on 15/9/19.
 */
public class UserAttentionListActivity extends Activity{

    private TextView people;
    private TextView course;
    private TextView post;
    private FrameLayout container;
    private Context context;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_attention_list);
        context=this;
        initViews();

    }

    public void initViews(){
        people=(TextView)findViewById(R.id.attention_list_people);
        course=(TextView)findViewById(R.id.attention_list_course);
        post=(TextView)findViewById(R.id.attention_list_post);
        container=(FrameLayout)findViewById(R.id.attention_container);
        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

ListView l=new ListView(context);
                l.setAdapter(new AttentionListAdapter(1,null,context));

                container.addView(l);
                //container.addView(new HottestPostListFragment(context));
            }
        });

        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ListView l=new ListView(context);
                l.setAdapter(new AttentionListAdapter(2,null,context));

                container.addView(l);
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ListView l=new ListView(context);
                l.setAdapter(new AttentionListAdapter(3,null,context));

                container.addView(l);

            }
        });

    }
}
