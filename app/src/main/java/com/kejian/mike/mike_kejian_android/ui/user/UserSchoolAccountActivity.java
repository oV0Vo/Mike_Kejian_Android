package com.kejian.mike.mike_kejian_android.ui.user;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.main.MainActivity;

/**
 * Created by kisstheraik on 15/9/30.
 */
public class UserSchoolAccountActivity extends AppCompatActivity {

    private TextView whyBind;
    private TextView skipBind;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_school_account);
        initViews();

    }

    protected  void initViews(){

        whyBind=(TextView)findViewById(R.id.why_bind);
        skipBind=(TextView)findViewById(R.id.skip_bind);

        whyBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showInfo("为什么要绑定教务网帐号", "因为绑定了教务网帐号，您才能获取和您的课程相关的信息");


            }
        });

        skipBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showInfo("跳过绑定教务网帐号", "您选择了跳过绑定教务网帐号，这样您只能浏览课程的相关信息");
            }
        });

    }

    private void skip(){

        Intent intent=new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);

    }

    private void showInfo(String title,String detail){

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(detail)
                .setPositiveButton("确定", null)
                .show();

    }


}
