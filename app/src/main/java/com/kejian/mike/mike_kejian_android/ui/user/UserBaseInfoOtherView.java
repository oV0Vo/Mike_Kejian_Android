package com.kejian.mike.mike_kejian_android.ui.user;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.kejian.mike.mike_kejian_android.R;

/**
 * Created by kisstheraik on 15/9/19.
 */
public class UserBaseInfoOtherView extends Activity{

    private LinearLayout course;
    private LinearLayout people;
    private LinearLayout postList;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_info_otherview);
        initViews();

    }

    public void initViews(){


        course=(LinearLayout)findViewById(R.id.his_attention_course);
        people=(LinearLayout)findViewById(R.id.his_attention_people);
        postList=(LinearLayout)findViewById(R.id.his_attention_post);


    }
}
