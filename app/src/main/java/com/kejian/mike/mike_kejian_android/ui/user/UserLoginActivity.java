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
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.ui.broadcast.NetBroadcast;
import com.kejian.mike.mike_kejian_android.ui.main.MainActivity;
import com.kejian.mike.mike_kejian_android.ui.util.BindAction;

import net.UserNetService;
import net.picture.MessagePrint;

import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

import bl.UserBLService;
import cn.smssdk.SMSSDK;
import model.user.Global;
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
    private ProgressBar progressBar;
    private NetBroadcast netBroadcast;


    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            //do something...
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void onCreate(Bundle savedInstanceState){

        MessagePrint.print("login : start to create login activity");

        super.onCreate(savedInstanceState);







        setContentView(com.kejian.mike.mike_kejian_android.R.layout.activity_user_login);

        context=this;


//        userToken=(UserToken)getIntent().getSerializableExtra(UserActivityComm.USER_TOKEN.name());

        if(userToken!=null){


        }

//        netBroadcast=new NetBroadcast();
//
//        Global.addGlobalItem("network_listing", netBroadcast);

        initViews();








    }

    protected void onPause(Bundle savedInstanceState){

        MessagePrint.print("login : loginactivity onpause");



//        if(userToken!=null){
//
//
//
//            new LoginTask().execute(1);
//
//        }

    }



    protected  void onResume(){
        super.onResume();

//        MessagePrint.print("login : loginactivity onresume");
//
//        progressBar.setVisibility(View.INVISIBLE);


       // userToken=(UserToken)getIntent().getSerializableExtra(UserActivityComm.USER_TOKEN.name());

//        if(userToken!=null) {
//
//            new LoginTask().execute(1);
//
//        }
//        else{
//
//        }



    }

    public void timeOutOperation(LoginTask task){

        final  LoginTask loginTask=task;

        new Thread(){


            public void run(){

                try {


                    loginTask.get(4000, TimeUnit.MILLISECONDS);



                }catch(Exception e){

                    MessageHandler messageHandler=new MessageHandler(getMainLooper());

                   Message message= messageHandler.obtainMessage(1,1,1);

                    messageHandler.sendMessage(message);



                }



            }

        }.start();

    }



    public UserOperationResult login(){


        MessagePrint.print("login : login");






        if(userToken==null) {


            MessagePrint.print("login : login token is null");





            userToken = new UserToken();
            userToken.setPassword(passwordView.getText().toString().trim());

            userToken.setPhoneNumber(nameView.getText().toString().trim());



        }



        MessagePrint.print("login : name="+userToken.getName()+" password="+ userToken.getPassword());



        userToken.setPassword(userToken.getPassword());

        user=UserBLService.getInstance().login(userToken);

        if(user==null){

            MessageHandler messageHandler=new MessageHandler(getMainLooper());

            Message message=messageHandler.obtainMessage(2);

            messageHandler.sendMessage(message);



            return UserOperationResult.LOGIN_ERROR_PASSWORD;

        } else {
            Intent intent = new Intent();

            intent.setClass(this, MainActivity.class);


            Bundle bundle=new Bundle();

            bundle.putSerializable(UserActivityComm.USER_INFO.name(), user);



            intent.putExtras(bundle);

            intent.putExtra(UserActivityComm.USER_INFO.name(), user);

            //把user添加到全局变量中
            Global.addGlobalItem("user", user);


            SharedPreferences sharedPreferences=getSharedPreferences("user_map",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
           if(!user.getId().equals("")){


               editor.putString("user_name", userToken.getPhoneNumber());

               MessagePrint.print("login store user:" + user.getId());

               if(!passwordView.getText().toString().equals("")){

            editor.putString("user_password",passwordView.getText().toString().trim());}

           }
            editor.apply();

            Intent i = new Intent(BindAction.ACTION_NAME);


            i.putExtra(BindAction.ARG_IS_BIND, true);
            LocalBroadcastManager.getInstance(context).sendBroadcast(i);


            startActivity(intent);
            close();
            return UserOperationResult.LOGIN_SUCCEED;
        }
    }

    public void netErrorReport(){

        System.out.println("time out!");

        Toast.makeText(this,"请检查你的网络设置 >_<",Toast.LENGTH_SHORT).show();

       // userToken=null;

    }


    public void errorInLogInfo(String title,String errorDetail){




        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(errorDetail)
                .setPositiveButton("确定", null)
                .show();

        progressBar.setVisibility(View.INVISIBLE);

        userToken=null;


    }
    public void initViews(){

        passwordView=(EditText)findViewById(com.kejian.mike.mike_kejian_android.R.id.login_password);

        nameView=(EditText)findViewById(com.kejian.mike.mike_kejian_android.R.id.login_name);

        forget_password=(TextView)findViewById(com.kejian.mike.mike_kejian_android.R.id.forget_password);

        register=(TextView)findViewById(com.kejian.mike.mike_kejian_android.R.id.register);

        login=(Button)findViewById(com.kejian.mike.mike_kejian_android.R.id.login_confirm);

        forget_password.setOnClickListener(new AccountListener(UserPasswordCode.class));

        register.setOnClickListener(new AccountListener(UserRegisterActivity.class));

        progressBar=(ProgressBar)findViewById(com.kejian.mike.mike_kejian_android.R.id.user_login_progress_bar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                boolean net = checkNetwork();

                LoginTask loginTask = new LoginTask();

                if (net) {

                    loginTask.execute(1);
                    timeOutOperation(loginTask);

                }


            }
        });


//        loginFromLocal();





    }

//    public void loginFromLocal(){
//
//
//        System.out.println("login : try to login from local");
//        Pair<String,String> localHistory= readLocalUser();
//
//        if((!localHistory.first.equals(""))&&(!localHistory.second.equals(""))){
//
//            this.userToken=new UserToken();
//            this.userToken.setPhoneNumber(localHistory.first);
//            this.userToken.setPassword(localHistory.second);
//
////            userToken.setName("1");
////            userToken.setPassword("1");
//            System.out.println("login : 从本地登录: 用户名:"+userToken.getName() +" 密码:"+userToken.getPassword());
//
//
//            new LoginTask().execute(1);
//
//
//        }
//
//    }

    public void close(){

        this.finish();

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

    public boolean checkNetwork() {


        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();


        NetworkInfo[] networkInfo = manager.getAllNetworkInfo();

        if (networkInfo != null && networkInfo.length > 0) {
            for (int i = 0; i < networkInfo.length; i++) {

                // 判断当前网络状态是否为连接状态
                if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }

            progressBar.setVisibility(View.INVISIBLE);

            Toast.makeText(this,"请检查你的网络设置 >_<",Toast.LENGTH_SHORT).show();

            return false;
        }

        return false;


    }

    /*
    用户登录的进程
     */

    private final class LoginTask extends AsyncTask<Integer,Integer,user> {


        protected void onPreExecute(){

        }

        protected user doInBackground(Integer... params){

            System.out.println("login : start login thread");



            login();


            return null;

        }
    }
    /*
    保存或者存储用户的信息
    命名规则
     */

    public Pair<String,String> readLocalUser(){

        SharedPreferences sharedPreferences=getSharedPreferences("user_map",MODE_PRIVATE);

        String password=sharedPreferences.getString("user_password", "");
        String userName=sharedPreferences.getString("user_name","");

        MessagePrint.print("login : user password from local"+password);
        MessagePrint.print("login : user name from local"+userName);




        return new Pair<String,String>(userName,password);


    }
    /*
    进程间消息处理的类
     */

    private class MessageHandler extends android.os.Handler{


        public MessageHandler(Looper looper){
            super(looper);

        }
        public void handleMessage(Message message){
            super.handleMessage(message);
            //提示网络错误




            switch(message.what){


                case 1:netErrorReport();
                    progressBar.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    errorInLogInfo("登录错误", "请检查你的用户名或密码");
                    break;
                case 3:
                    errorInLogInfo("身份过期", "请重新登录");
                    break;

                default:break;
            }

        }

    }





}
