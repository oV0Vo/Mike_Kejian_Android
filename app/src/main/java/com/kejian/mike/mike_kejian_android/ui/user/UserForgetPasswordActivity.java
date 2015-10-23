package com.kejian.mike.mike_kejian_android.ui.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;

import net.UserNetService;

import model.user.UserToken;

/**
 * Created by kisstheraik on 15/9/19.
 */
public class UserForgetPasswordActivity extends AppCompatActivity{

    private Button reset;
    private Context context;
    private EditText newPassword;
    private EditText confirmNewPassword;
    private String phoneNumber;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_forget_password);
        Intent intent=getIntent();
        UserToken userToken=(UserToken)intent.getSerializableExtra(UserActivityComm.USER_TOKEN.name());
        phoneNumber=userToken.getPhoneNumber();
        context=this;
        initViews();
        reset=(Button)findViewById(R.id.complete_reset_passport);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String psd=newPassword.getText().toString().trim();
                String cpsd=confirmNewPassword.getText().toString().trim();

                if(!psd.equals(cpsd)){

                    Toast.makeText(getApplicationContext(),"两次输入的密码不一致，请重新输入 >_<",Toast.LENGTH_SHORT).show();

                    return;
                }


                resetPassword(phoneNumber,psd);



            }
        });

    }

    public void initViews(){

        newPassword=(EditText)findViewById(R.id.newPasswordView);
        confirmNewPassword=(EditText)findViewById(R.id.confirmNewPasswordView);

    }

    public void resetPassword(final String phoneNumber, final String npsd){

        new AsyncTask<String,Integer,Boolean>(){

            public Boolean doInBackground(String...Para){

                boolean result=UserNetService.resetPassword(phoneNumber,npsd);

                return result;

            }

            @Override
        public void onPostExecute(Boolean result){

                if(result){

                    Toast.makeText(getApplicationContext(),"修改密码成功！ >_<",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    intent.setClass(context, UserLoginActivity.class);
                    startActivity(intent);
                    close();
                }
                else{

                    Toast.makeText(getApplicationContext(),"修改密码失败请稍后尝试！ >_<",Toast.LENGTH_SHORT).show();

                }

            }
        }.execute("");
    }


    public void close(){
        this.finish();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_user_forget_password, menu);
        return true;
    }


}
