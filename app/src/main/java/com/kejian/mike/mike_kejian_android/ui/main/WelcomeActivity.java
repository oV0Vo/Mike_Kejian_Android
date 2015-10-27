package com.kejian.mike.mike_kejian_android.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.broadcast.NetBroadcast;
import com.kejian.mike.mike_kejian_android.ui.user.UserActivityComm;
import com.kejian.mike.mike_kejian_android.ui.user.UserLoginActivity;
import com.kejian.mike.mike_kejian_android.ui.user.UserOperationResult;

import net.picture.MessagePrint;

import bl.UserBLService;
import model.user.Global;
import model.user.UserToken;
import model.user.user;

public class WelcomeActivity extends Activity {
    private UserToken userToken;
    private user user;
    private NetBroadcast netBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        userToken=(UserToken)getIntent().getSerializableExtra(UserActivityComm.USER_TOKEN.name());
        netBroadcast=new NetBroadcast();

        Global.addGlobalItem("network_listing", netBroadcast);
        loginFromLocal();
    }
    public void loginFromLocal(){


        System.out.println("login : try to login from local");
        Pair<String,String> localHistory= readLocalUser();

        if((!localHistory.first.equals(""))&&(!localHistory.second.equals(""))){

            this.userToken=new UserToken();
            this.userToken.setPhoneNumber(localHistory.first);
            this.userToken.setPassword(localHistory.second);

//            userToken.setName("1");
//            userToken.setPassword("1");
            System.out.println("login : 从本地登录: 用户名:"+userToken.getName() +" 密码:"+userToken.getPassword());


            new LoginTask().execute(1);


        }else{
            Intent intent = new Intent(this,UserLoginActivity.class);
            startActivity(intent);
            finish();
        }

    }
    public void login(){


        MessagePrint.print("login : login");

        MessagePrint.print("login : name=" + userToken.getName() + " password=" + userToken.getPassword());

        user= UserBLService.getInstance().login(userToken);

        if(user==null){

//            MessageHandler messageHandler=new MessageHandler(getMainLooper());
//
//            Message message=messageHandler.obtainMessage(2);
//
//            messageHandler.sendMessage(message);
            Intent intent = new Intent();
            intent.setClass(this, UserLoginActivity.class);
            startActivity(intent);
            finish();


        } else {
            Intent intent = new Intent();

            intent.setClass(this, MainActivity.class);


            Bundle bundle=new Bundle();

            bundle.putSerializable(UserActivityComm.USER_INFO.name(), user);



            intent.putExtras(bundle);

            intent.putExtra(UserActivityComm.USER_INFO.name(), user);

            //把user添加到全局变量中
            Global.addGlobalItem("user", user);
            startActivity(intent);
            finish();
//            close();
        }
    }
    private final class LoginTask extends AsyncTask<Integer,Integer,user> {


        protected void onPreExecute(){

        }

        protected user doInBackground(Integer... params){

            System.out.println("login : start login thread");



            login();


            return null;

        }
    }
    public Pair<String,String> readLocalUser(){

        SharedPreferences sharedPreferences=getSharedPreferences("user_map",MODE_PRIVATE);

        String password=sharedPreferences.getString("user_password", "");
        String userName=sharedPreferences.getString("user_name","");

        MessagePrint.print("login : user password from local" + password);
        MessagePrint.print("login : user name from local" + userName);




        return new Pair<String,String>(userName,password);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}