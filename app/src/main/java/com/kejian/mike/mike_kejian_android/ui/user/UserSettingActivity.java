package com.kejian.mike.mike_kejian_android.ui.user;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("user_map", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("user_name", "");
                editor.putString("user_password", "");
                editor.apply();

                finish();


                ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                am.killBackgroundProcesses(getPackageName());
            }
        });
        schoolAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    public void initInfoViews(){

        unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AsyncTask<String,Integer,Boolean>(){

                    @Override
                public Boolean doInBackground(String...Para){



                     return   UserNetService.setUserInfo(Integer.parseInt(us.getId()), "SCHOOL_NUMBER", "");



                    }
                    @Override
                public void onPostExecute(Boolean result){

                        if(result){

                            Toast.makeText(getApplicationContext(),"解除绑定成功 >_<",Toast.LENGTH_SHORT).show();
                            us.setIfBind(true);
                            us.setSchoolAccount("");
                            us.setSchoolAccount("");

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
        list.add("身份 : "+us.getIdentify());






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
