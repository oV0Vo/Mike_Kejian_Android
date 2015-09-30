package com.kejian.mike.mike_kejian_android.ui.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kejian.mike.mike_kejian_android.R;

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


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_change_password_code);
        context=this;
        userToken=(UserToken)getIntent().getSerializableExtra(UserActivityComm.USER_TOKEN.name());
        if(userToken==null){
            userToken=new UserToken();
        }
        initViews();


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
        intent.putExtras(bundle);
        intent.setClass(context,UserForgetPasswordActivity.class);
        startActivity(intent);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_forget_password, menu);
        return true;
    }

    private class CompleteOperationListener implements View.OnClickListener{

        public void onClick(View view){



            String inputCode=(String)codeView.getText().toString().trim();

            if(code!=null&&inputCode.equals(code)){

                resetPassword();
                code=null;

            }
            else{

                System.out.println(code+":"+inputCode);
                new UserUIError("验证码错误","请重新获取验证码",context);
            }



        }
    }

    private class GenerateCodeListener implements View.OnClickListener{

        public void onClick(View view){

            String phoneNumber=(String)phoneNumberView.getText().toString().trim();
            if(phoneNumber==null||phoneNumber.equals("")){

                new UserUIError("手机号错误","请输入正确的手机号",context);
                code=null;

                return;

            }
            else{

                new AlertDialog.Builder(context)
                        .setMessage("已经发送验证码")
                        .show();
            }

            code="131250114";


        }
    }

}
