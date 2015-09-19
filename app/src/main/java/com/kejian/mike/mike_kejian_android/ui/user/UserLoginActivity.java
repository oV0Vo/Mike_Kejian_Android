package com.kejian.mike.mike_kejian_android.ui.user;

import android.app.Activity;
import android.R;
/**
 * Created by kisstheraik on 15/9/13.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserLoginActivity extends Activity {

    private TextView forget_password;
    private TextView register;
    private Context context;
    private Button login;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(com.kejian.mike.mike_kejian_android.R.layout.activity_user_login);
        context=this;



    }

    protected void onPause(Bundle savedInstanceState){


    }

    public UserOperationResult login(){
        return null;
    }

    public void errorInLogInfo(){


        new AlertDialog.Builder(context)
                .setTitle("登录错误")
                .setMessage("请检查你的用户名或者密码")
                .setPositiveButton("确定", null)
                .show();


    }
    public void initViews(){
        forget_password=(TextView)findViewById(com.kejian.mike.mike_kejian_android.R.id.forget_password);
        register=(TextView)findViewById(com.kejian.mike.mike_kejian_android.R.id.register);

        login=(Button)findViewById(com.kejian.mike.mike_kejian_android.R.id.login_confirm);

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(context,UserForgetPasswordActivity.class);
                startActivity(intent);


            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(context,UserSignUpActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();

            }
        });


    }



}
