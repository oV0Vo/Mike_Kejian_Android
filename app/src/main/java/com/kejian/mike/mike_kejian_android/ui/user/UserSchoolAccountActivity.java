package com.kejian.mike.mike_kejian_android.ui.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.main.MainActivity;
import com.kejian.mike.mike_kejian_android.ui.widget.AppManager;

import net.UserNetService;
import net.picture.MessagePrint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import bl.UserBLResult;
import bl.UserBLService;
import model.GlobalInfoName;
import model.user.Global;
import model.user.UserToken;
import model.user.user;

/**
 * Created by kisstheraik on 15/9/30.
 */
public class UserSchoolAccountActivity extends AppCompatActivity {

    private TextView whyBind;
    private TextView skipBind;
    private Spinner cityListView;
    private Spinner schoolListView;
    private Context context;
    private UserToken userToken;
    private EditText saccountView;
    private EditText saccountPsdView;
    private Button finish;
    private boolean ifSkip=false;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_school_account);

       // AppManager.getAppManager().addActivity(this);
        context=this;
        userToken=(UserToken)getIntent().getSerializableExtra(UserActivityComm.USER_TOKEN.name());
        if(userToken==null){

            userToken=new UserToken();

        }
        else{

        }
        initViews();
        initCitySchoolData();

    }



    protected  void initViews(){

        whyBind=(TextView)findViewById(R.id.why_bind);
        skipBind=(TextView)findViewById(R.id.skip_bind);
        schoolListView=(Spinner)findViewById(R.id.school_list_view);
        cityListView=(Spinner)findViewById(R.id.city_list_view);
        saccountView=(EditText)findViewById(R.id.school_account_view);
        saccountPsdView=(EditText)findViewById(R.id.school_account_pad_view);
        finish=(Button)findViewById(R.id.complete_bind);

        finish.setOnClickListener(new BindListener());




        whyBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showInfo("为什么要绑定教务网帐号", "因为绑定了教务网帐号，您才能获取和您的课程相关的信息");


            }
        });

        skipBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                new AlertDialog.Builder(context)
                        .setTitle("跳过绑定教务网帐号")
                        .setMessage("您选择了跳过绑定教务网帐号，这样您只能浏览课程的相关信息")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                skip();
                            }
                        })
                        .show();
            }
        });
        MessagePrint.print(GlobalInfoName.BIND_SCHOOL_ACCOUNT_TIME.BIND_SCHOOL_ACCOUNT_TIME.name());

        Object tem=Global.getObjectByName(GlobalInfoName.BIND_SCHOOL_ACCOUNT_TIME.BIND_SCHOOL_ACCOUNT_TIME.name());

        if(tem!=null&&((String)tem).equals(GlobalInfoName.BIND_SCHOOL_ACCOUNT_TIME.AFTER_REGISTER.name())){

            //如果是从setting里面绑定那么就不显示跳过绑定
            skipBind.setVisibility(View.INVISIBLE);
        }

        user us=(user)Global.getObjectByName("user");
        if(us!=null)skipBind.setVisibility(View.INVISIBLE);

    }

    private void skip(){

        Global.addGlobalItem(GlobalInfoName.BIND_SCHOOL_ACCOUNT_TIME.BIND_SCHOOL_ACCOUNT_TIME.name(),GlobalInfoName.BIND_SCHOOL_ACCOUNT_TIME.FROM_SETTING.name());

        Intent intent=new Intent();
        intent.setClass(this, UserLoginActivity.class);
        startActivity(intent);

    }

    private void showInfo(String title,String detail){

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(detail)
                .setPositiveButton("确定", null)
                .show();

    }

    public void initCitySchoolData(){

        HashMap<String,ArrayList<String>> list= UserBLService.getInstance().getCityAndSchool();
        int length=list.keySet().size();

        String[] cityList=new String[length];

        Iterator iterator=list.keySet().iterator();
        int i=0;

        while(iterator.hasNext()){




            cityList[i]=((String)iterator.next()).trim();
            i++;
        }

        cityListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, cityList));

        cityListView.setOnItemSelectedListener(new CitySchoolChooseListener(list));

    }

    public void setSchoolData(String cityName,ArrayList<String> schoolList){

       schoolListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, schoolList));

    }

    private class BindListener implements View.OnClickListener{

        public void onClick(View view){

            String schoolAccount=saccountView.getText().toString().trim();
            String schoolAccountPsd=saccountPsdView.getText().toString().trim();

            if(schoolAccount==null||schoolAccountPsd==null||schoolAccount.equals("")||schoolAccountPsd.equals("")){

                new UserUIError("用户名或密码不完整","请输入用户名和密码",context);

                return;
            }

            userToken.setSchoolAccount(schoolAccount);
            userToken.setSchoolAccountPassword(schoolAccountPsd);

            UserBLResult result=UserBLService.getInstance().CheckAccount(schoolAccount,schoolAccountPsd);

            if(result!=UserBLResult.ACCOUNT_RIGHT){

                showInfo("密码或用户名错误","请输入正确的用户名和密码");

                return;
            }
            else{

                user u= (user)Global.getObjectByName("user");

                MessagePrint.print("开始绑定教务网账号");

                if(u!=null) {


                    new BindThread().execute(((user) Global.getObjectByName("user")).getId(), userToken.getSchoolAccount(), userToken.getSchoolAccountPassword());
                }
                else{


                    new AsyncTask<String,Integer,String>(){

                        @Override
                    public String doInBackground(String...Para){

                            user u=UserNetService.getUser(userToken);
                            return u.getId();
                        }

                        @Override
                    public void onPostExecute(String result){

                            new BindThread().execute(result, userToken.getSchoolAccount(), userToken.getSchoolAccountPassword());

                        }
                    }.execute("");
                }
                userToken.bindSchoolAccount();

                user us=(user)Global.getObjectByName("user");
                if(us!=null)us.setSchoolAccount(schoolAccount);
               // showInfo("绑定成功","您已经成功绑定教务网帐号");
            }

        }

    }

    public class CitySchoolChooseListener implements AdapterView.OnItemSelectedListener{

        private HashMap<String,ArrayList<String>> list;

        public CitySchoolChooseListener(HashMap<String,ArrayList<String>> list){

            this.list=list;

        }


        public void onItemSelected(AdapterView adapterView,View view,int position,long id){

            if(view.getId()==R.id.school_list_view){


            }
            else{


                String type=adapterView.getItemAtPosition(position).toString();

                setSchoolData(type,list.get(type));



            }

        }

        public void onNothingSelected(AdapterView adapterView){

        }
    }

    public void jump(){

        user us=(user)Global.getObjectByName("user");
        if(us==null){

            Intent intent=new Intent();

            intent.setClass(this,UserLoginActivity.class);

            startActivity(intent);
        }

        finish();

    }

    public boolean bindSchoolAccount(String userId,String account,String psd){

        if(ifSkip) return false;

        return UserNetService.bindSchoolAccount(userId,account,psd);

    }

    private class BindThread extends AsyncTask<String,Integer,Boolean>{

        public Boolean doInBackground(String...para){



           boolean result= bindSchoolAccount(para[0],para[1],para[2]);

            if(result){
                user u=(user)Global.getObjectByName("user");

                if(u!=null){

                    u=UserNetService.getUserInfo(u.getId());
                }
            }

            return result;

        }

        @Override
        public void onPostExecute(Boolean result){

            if(result){

                MessagePrint.print("绑定教务网账号成功");

                userToken.setIfBindSchoolAccount(true);

                Toast.makeText(getApplicationContext(),"绑定教务网账号成功 >_<",Toast.LENGTH_SHORT).show();



                //跳转到下一个activity

                jump();

            }
            else{


                user us=(user)Global.getObjectByName("user");
                if(us!=null)us.setSchoolAccount("");
                Toast.makeText(getApplicationContext(),"绑定教务网账号失败 >_<",Toast.LENGTH_SHORT).show();
            }

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
