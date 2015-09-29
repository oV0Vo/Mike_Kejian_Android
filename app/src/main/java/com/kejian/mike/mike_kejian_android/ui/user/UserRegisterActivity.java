package com.kejian.mike.mike_kejian_android.ui.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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
    private EditText nameView;
    private EditText codeInputView;
    private Register register;
    private UserToken userToken;
    private Button sendCode;
    private String code;


    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        //
        getIntent().getSerializableExtra(UserActivityComm.USER_TOKEN.name());
        initViews();

        if(userToken==null){

            userToken=new UserToken();

        }

        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNumber=phoneNumberView.getText().toString().trim();
                if(phoneNumber==null){
                    notifyError("请输入正确的电话号码");
                    return;
                }



                code="131250";


            }
        });


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

        phoneNumberView=(EditText)findViewById(R.id.phone_number_view);
        passwordView=(EditText)findViewById(R.id.password_view);
        nameView=(EditText)findViewById(R.id.name_view);
        codeInputView=(EditText)findViewById(R.id.code_input);
        sendCode=(Button)findViewById(R.id.code_send);


    }

    public void notifyError(String message){

        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show();



    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mention_me, menu);
        return true;
    }



    public void register(){

        String phoneNumber=phoneNumberView.getText().toString().trim();
        String password=passwordView.getText().toString().trim();
        String inputCode=codeInputView.getText().toString().trim();
        String name=nameView.getText().toString().trim();

        if(phoneNumber==null||password==null||inputCode==null||name==null){

            new UserUIError("信息不完整","请填写完整的信息",this);

        }
        userToken.setPhoneNumber(phoneNumber);
        userToken.setName(name);
        userToken.setPassword(password);
        System.out.println("注册名字："+userToken.getName());



        if(!inputCode.equals(code)){

            new UserUIError("验证码错误","请重新获取验证码",this);
            code=null;
            return ;

        }




        System.out.println("准备登录");


        UserBLResult userBLResult=UserBLService.getInstance().register(userToken);

        if(userBLResult.equals(UserBLResult.REGISTER_SUCCEED)){

            Intent intent = new Intent();

            Bundle bundle = new Bundle();


            bundle.putSerializable(UserActivityComm.USER_TOKEN.name(), userToken);

            intent.putExtra(UserActivityComm.USER_TOKEN.name(),userToken);

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
