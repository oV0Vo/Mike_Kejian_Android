package com.kejian.mike.mike_kejian_android.ui.user;

import android.app.Activity;
import android.R;
/**
 * Created by kisstheraik on 15/9/13.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import bl.UserBLService;
import model.user.UserToken;
import model.user.user;

public class UserLoginActivity extends Activity {

    private TextView forget_password;
    private TextView register;
    private Context context;
    private Button login;
    private UserToken userToken;
    private user user;
    private EditText passwordView;
    private EditText nameView;

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(com.kejian.mike.mike_kejian_android.R.layout.activity_user_login);
        context=this;
        initViews();
        userToken=(UserToken)getIntent().getSerializableExtra(UserActivityComm.USER_TOKEN.name());
        if(userToken!=null){

            login();

        }



    }

    protected void onPause(Bundle savedInstanceState){
        if(userToken!=null){

            System.out.println("直接登录");

            login();

        }

    }



    protected  void onResume(){
        super.onResume();



        userToken=(UserToken)getIntent().getSerializableExtra(UserActivityComm.USER_TOKEN.name());

        if(userToken!=null) {

            login();

        }
        else{

        }



    }


    public UserOperationResult login(){

        if(userToken==null) {



            userToken = new UserToken();


            userToken.setPassword(passwordView.getText().toString().trim());

            userToken.setName(nameView.getText().toString().trim());
        }





        user=UserBLService.getInstance().login(userToken);

        if(user==null){

            errorInLogInfo("登录错误","请检查你的用户名或密码");

            return UserOperationResult.LOGIN_ERROR_PASSWORD;

        }

        else {
            Intent intent = new Intent();

            intent.setClass(this, UserInfoActivity.class);
            System.out.println();

            Bundle bundle=new Bundle();

            bundle.putSerializable(UserActivityComm.USER_INFO.name(), user);

            intent.putExtras(bundle);

            //intent.putExtra(UserActivityComm.USER_INFO.name(),user);



            startActivity(intent);

            return UserOperationResult.LOGIN_SUCCEED;
        }


    }




    public void errorInLogInfo(String title,String errorDetail){


        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(errorDetail)
                .setPositiveButton("确定", null)
                .show();


    }
    public void initViews(){

        passwordView=(EditText)findViewById(com.kejian.mike.mike_kejian_android.R.id.login_password);

        nameView=(EditText)findViewById(com.kejian.mike.mike_kejian_android.R.id.login_name);

        forget_password=(TextView)findViewById(com.kejian.mike.mike_kejian_android.R.id.forget_password);

        register=(TextView)findViewById(com.kejian.mike.mike_kejian_android.R.id.register);

        login=(Button)findViewById(com.kejian.mike.mike_kejian_android.R.id.login_confirm);

        forget_password.setOnClickListener(new AccountListener(UserForgetPasswordActivity.class));

        register.setOnClickListener(new AccountListener(UserRegisterActivity.class));

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               login();

            }
        });


    }

    public void close(){

        this.close();

    }

    private class AccountListener implements View.OnClickListener{

        private Class target;

        public AccountListener(Class target){

            this.target=target;

        }

        public void onClick(View v){

            Intent intent=new Intent();


            intent.setClass(context,target);
            startActivity(intent);

        }
    }




}
