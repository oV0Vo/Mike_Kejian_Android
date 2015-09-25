package com.kejian.mike.mike_kejian_android.ui.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kejian.mike.mike_kejian_android.R;

import bl.User.Register;
import bl.UserBLResult;
import bl.UserBLService;
import model.user.UserToken;

/**
 * Created by kisstheraik on 15/9/20.
 */
public class UserRegisterActivity extends Activity{

    private Button confirm;
    private Context context;
    private EditText phoneNumberView;
    private EditText passwordView;
    private EditText codeInputView;
    private Register register;
    private UserToken userToken;

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        //
        getIntent().getSerializableExtra(UserActivityComm.USER_TOKEN.name());

        if(userToken==null){

            userToken=new UserToken();

        }


        context=this;
        confirm=(Button)findViewById(R.id.register_confirm);
        confirm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        
                        register();

                    }
                }
        );

    }

    public void initViews(){

    }

    public void notifyError(String message){

        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show();



    }



    public void register(){

//        String phoneNumber=phoneNumberView.getText().toString().trim();
//        String password=getSha1Value(passwordView.getText().toString().trim());
//        String inputCode=codeInputView.getText().toString().trim();
//        register.setPhoneNumber(phoneNumber);
//        register.setPassword(password);
        userToken.setPhoneNumber("15951931083");
        userToken.setName("十里长亭");
        userToken.setPassword("asfd");


        UserBLResult userBLResult=UserBLService.getInstance().register(userToken);

        if(userBLResult.equals(UserBLResult.REGISTER_SUCCEED)){

            Intent intent = new Intent();

            Bundle bundle = new Bundle();


            bundle.putSerializable(UserActivityComm.USER_TOKEN.name(), userToken);

            intent.setClass(context, UserLoginActivity.class);

            startActivity(intent);

            close();


        }

        else{

            new UserUIError("注册失败",userBLResult.name(),this);

        }



    }

    public boolean checkPhoneNumber(){

        return false;

    }

    public boolean checkPassword(){

        return false;
    }

    public boolean checkInputCode(){

        return false;
    }

    public String getSha1Value(String code){

        return null;

    }


    public void close(){
        this.finish();
    }
}
