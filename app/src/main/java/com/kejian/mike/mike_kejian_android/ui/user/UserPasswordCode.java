package com.kejian.mike.mike_kejian_android.ui.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.widget.AppManager;

import java.util.Date;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import model.user.Global;
import model.user.UserToken;

/**
 * Created by kisstheraik on 15/9/29.
 */
public class UserPasswordCode extends AppCompatActivity {

    private Button sendCode;
    private Context context;
    private EditText phoneNumberView;
    private EditText codeView;
    private Button finish;
    private UserToken userToken;
    private String code;
    private boolean state=false;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_change_password_code);

      //  AppManager.getAppManager().addActivity(this);
        context=this;
        userToken=(UserToken)getIntent().getSerializableExtra(UserActivityComm.USER_TOKEN.name());
        if(userToken==null){
            userToken=new UserToken();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
        SMSSDK.initSDK(this, "ab3e9212084e", "5fbc8aeaf6291b8d97647a5972905456");
        EventHandler eventHandler=new EventHandler(){


            @Override
            public void afterEvent(int event, int result, Object data) {
                System.out.println("finish get countries");

                if (result == SMSSDK.RESULT_COMPLETE) {

                    System.out.println(data.toString());
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        state=true;
                        System.out.println("验证成功！");
                        resetPassword();

                        Looper.prepare();

                        Toast.makeText(context, "验证成功！", Toast.LENGTH_SHORT).show();
                        System.out.println("add to global " + data.toString());


                        Global.addGlobalItem("code", data.toString());
                        //提交验证码成功
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){




                        System.out.println(data.toString());
                        //获取验证码成功
                    }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        //返回支持发送验证码的国家列表
                        Looper.prepare();

                        Toast.makeText(context, "获取国家列表成功", Toast.LENGTH_SHORT).show();

                        System.out.println(data.toString());






                    }
                }else{

                  //  new UserUIError("验证码错误","请重新获取验证码",context);
                    ((Throwable)data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler);


    }



    public void initViews(){

        phoneNumberView=(EditText)findViewById(R.id.phone_number);
        codeView=(EditText)findViewById(R.id.change_psd_code);
        sendCode=(Button)findViewById(R.id.send_change_psd_code);
        finish=(Button)findViewById(R.id.complete_set_code);

        finish.setOnClickListener(new CompleteOperationListener());
        sendCode.setOnClickListener(new GenerateCodeListener());

    }
    public void close(){
        this.finish();
    }

    public void resetPassword(){


        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        userToken.setIsGetCode(true);
        bundle.putSerializable(UserActivityComm.USER_TOKEN.name(),userToken);
        userToken.setPhoneNumber(phoneNumberView.getText().toString().trim());
        intent.putExtras(bundle);
        intent.setClass(context, UserForgetPasswordActivity.class);
        startActivity(intent);
        this.finish();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_forget_password, menu);
        return true;
    }

    private class CompleteOperationListener implements View.OnClickListener{

        public void onClick(final View view){



            String inputCode=null;

            if(codeView.getText()!=null){ inputCode=(String)codeView.getText().toString().trim();}

            if(phoneNumberView.getText()!=null)SMSSDK.submitVerificationCode("86",(String)phoneNumberView.getText().toString().trim(),inputCode);
//
//            if(state){
//
//
//                resetPassword();
//
//
//                code=null;
//
//            }
//            else{
//
//
//                System.out.println(code+":"+inputCode);
//
//            }



        }
    }

    private class GenerateCodeListener implements View.OnClickListener{

        public void onClick(View view){

            new CountDownTimer(60000,1000){

                @Override
                public void onFinish(){

                    sendCode.setText("发送验证码");
                    sendCode.setClickable(true);


                }

                @Override
                public void onTick(long m){

                    sendCode.setClickable(false);
                    sendCode.setBackgroundColor(Color.GRAY);

                    sendCode.setText(( m / 1000) + "秒后重新发送");


                }

            }.start();


            String phoneNumber=(String)phoneNumberView.getText().toString().trim();
            if(phoneNumber==null||phoneNumber.equals("")){

                new UserUIError("手机号错误","请输入正确的手机号",context);
                code=null;

                return;

            }
            else{

                Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
            }

            SMSSDK.getVerificationCode("86",phoneNumber);
            code="131250114";


        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            this.finish();
        }

        return true;
    }

}
