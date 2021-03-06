package com.kejian.mike.mike_kejian_android.ui.user;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.util.BindAction;
import com.kejian.mike.mike_kejian_android.ui.widget.AppManager;

import net.UserNetService;
import net.picture.MessagePrint;

import java.util.ArrayList;
import java.util.List;

import model.user.Global;
import model.user.user;

/**
 * Created by kisstheraik on 15/9/30.
 */
public class UserSettingActivity extends AppCompatActivity {

    private TextView schoolAccount;
    private ListView infoList;
    private Button unbind;
    private user us;
    private TextView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        schoolAccount=(TextView)findViewById(R.id.school_account_item);
        logout=(TextView)findViewById(R.id.logout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // AppManager.getAppManager().addActivity(this);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("user_map", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("user_name", "");
                editor.putString("user_password", "");
                editor.apply();

                Intent intent=new Intent();

                intent.setClass(getApplicationContext(),UserLoginActivity.class);

                startActivity(intent);

//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_HOME);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);








//                ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//                am.killBackgroundProcesses(getPackageName());
            }
        });
        schoolAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!checkNetwork()){

                    MessagePrint.print("setting check network");

                    return;
                }



                user u = (user) Global.getObjectByName("user");
                us = u;

                if (u.getSchoolAccount().equals("")) {

                    Intent intent = new Intent();

                    intent.setClass(getApplicationContext(), UserSchoolAccountActivity.class);

                    startActivity(intent);
                } else {

                    LayoutInflater layoutInflater = getLayoutInflater();

                    View view = layoutInflater.inflate(R.layout.activity_user_setting_show, null);

                    setContentView(view);

                    unbind = (Button) findViewById(R.id.unbind_school_account);
                    infoList = (ListView) findViewById(R.id.school_account_list);

                    initInfoViews();


                }
            }
        });

    }

    public boolean checkNetwork() {


        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
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



            Toast.makeText(this,"请检查你的网络设置 >_<",Toast.LENGTH_SHORT).show();

            return false;
        }

        return false;


    }


    public void initInfoViews(){

        unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AsyncTask<String,Integer,Boolean>(){

                    @Override
                public Boolean doInBackground(String...Para){



                     return   UserNetService.unbind();



                    }
                    @Override
                public void onPostExecute(Boolean result){

                        if(result){

                            Toast.makeText(getApplicationContext(),"解除绑定成功 >_<",Toast.LENGTH_SHORT).show();
                            us.setIfBind(true);
                            us.setSchoolAccount("");

                            Intent intent = new Intent(BindAction.ACTION_NAME);


                            intent.putExtra(BindAction.ARG_IS_BIND, false);
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);


                            finish();
                        }
                    }
                }.execute("");


            }
        });

        infoList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));

    }

    public List getData() {

        List list = new ArrayList();

        list.add("账号 : " + us.getSchoolAccount());

        if(us.getIdentify().equals("0")) {
            list.add("身份 : " + "学生");
        }
        else
        {
            list.add("身份 : " + "老师");
        }






        return list;
    }

    @Override

    protected  void onStop(){

        super.onStop();

        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            this.finish();
        }

        return true;
    }


}
