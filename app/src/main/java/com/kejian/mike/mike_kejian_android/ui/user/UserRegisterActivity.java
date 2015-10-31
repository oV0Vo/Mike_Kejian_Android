package com.kejian.mike.mike_kejian_android.ui.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.CursorJoiner;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.widget.AppManager;

import net.UserNetService;

import java.util.HashMap;

import bl.User.Register;
import bl.UserBLResult;
import bl.UserBLService;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import model.user.Global;
import model.user.UserToken;
import model.user.user;

/**
 * Created by kisstheraik on 15/9/20.
 */
public class UserRegisterActivity extends AppCompatActivity{

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
    private TextView countryNumberView;
    private Spinner countryChooseView;



    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      //  AppManager.getAppManager().addActivity(this);

        getIntent().getSerializableExtra(UserActivityComm.USER_TOKEN.name());
        setCodeMechine();
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



                getCode("86",phoneNumberView.getText().toString().trim());

                code="131250";


            }
        });


        context=this;
        confirm=(Button)findViewById(R.id.register_confirm);
        confirm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        
                        new RegisterThread().execute(new UserToken());

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
        countryNumberView=(TextView)findViewById(R.id.country_number);

        getSupportCountries();
       // countryChooseView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, cityList));


    }

    public void notifyError(String message){

        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show();



    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user_register, menu);
        return true;

    }



    public boolean register(){

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
        System.out.println("注册名字：" + userToken.getName());
        System.out.println("注册电话："+userToken.getPhoneNumber());

        checkCode("86", phoneNumberView.getText().toString().trim(), codeInputView.getText().toString().trim());




//        if(((String)Global.getObjectByName("code")).equals("true")){
//
//            new UserUIError("验证码错误","请重新获取验证码",this);
//            code=null;
//            return ;
//
//        }




        System.out.println("准备登录");


        UserBLResult userBLResult=UserNetService.register(userToken);

        if(userBLResult.equals(UserBLResult.REGISTER_SUCCEED)){



            return true;


        }

        else{

           return false;

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

    public void setCodeMechine(){


        SMSSDK.initSDK(this, "ab3e9212084e", "5fbc8aeaf6291b8d97647a5972905456");
        EventHandler eventHandler=new EventHandler(){


            @Override
            public void afterEvent(int event, int result, Object data) {
                System.out.println("finish get countries");

                if (result == SMSSDK.RESULT_COMPLETE) {

                    System.out.println(data.toString());
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        System.out.println("验证成功！");

                        Looper.prepare();

                        Toast.makeText(context, "验证成功！", Toast.LENGTH_SHORT).show();
                        System.out.println("add to global " + data.toString());


                        Global.addGlobalItem("code",data.toString());
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
                    ((Throwable)data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
       // System.out.println("hello");

        //SMSSDK.getGroupedCountryList();

    }

    public void test(){
        Looper.prepare();
        Toast.makeText(context, "获取国家列表成功", Toast.LENGTH_SHORT).show();

    }

    public void checkCode(String country,String phoneNumber,String code){

        SMSSDK.submitVerificationCode(country, phoneNumber, code);




    }
    public void getSupportCountries(){



        SMSSDK.getSupportedCountries();

    }

    public void getCode(String country,String phoneNumber){

        SMSSDK.getVerificationCode(country, phoneNumber);
        Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();






//        //打开注册页面
//        RegisterPage registerPage = new RegisterPage();
//        registerPage.setRegisterCallback(new EventHandler() {
//            public void afterEvent(int event, int result, Object data) {
//// 解析注册结果
//                if (result == SMSSDK.RESULT_COMPLETE) {
//                    @SuppressWarnings("unchecked")
//                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
//                    String country = (String) phoneMap.get("country");
//                    String phone = (String) phoneMap.get("phone");
//
//// 提交用户信息
//                    //registerUser(country, phone);
//                }
//            }
//        });
//
//        registerPage.show(context);
        //

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){


            this.finish();
        }

        return true;
    }



    public void close(){
        this.finish();
    }

    private class RegisterThread extends AsyncTask<UserToken,Integer,Boolean>{

        public Boolean doInBackground(UserToken...para){

            return register();



        }

        @Override
        public void onPostExecute(Boolean result){

            if(!result){

                Toast.makeText(context,"手机已经被注册，或者没有网络连接",Toast.LENGTH_SHORT).show();
            }else{

                Toast.makeText(context,"注册成功 >_<",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();

                Bundle bundle = new Bundle();


                bundle.putSerializable(UserActivityComm.USER_TOKEN.name(), userToken);

                intent.putExtra(UserActivityComm.USER_TOKEN.name(), userToken);

                //跳转到绑定教务网账号的界面

                //把token压入全局数组

                Global.addGlobalItem("userToken", userToken);

                intent.setClass(context, UserSchoolAccountActivity.class);

                startActivity(intent);

                close();



                finish();

            }



        }


    }
}
