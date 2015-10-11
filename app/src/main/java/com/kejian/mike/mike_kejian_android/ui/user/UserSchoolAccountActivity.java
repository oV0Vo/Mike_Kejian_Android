package com.kejian.mike.mike_kejian_android.ui.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import bl.UserBLResult;
import bl.UserBLService;
import model.user.UserToken;

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


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_school_account);
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

                showInfo("跳过绑定教务网帐号", "您选择了跳过绑定教务网帐号，这样您只能浏览课程的相关信息");
            }
        });

    }

    private void skip(){

        Intent intent=new Intent();
        intent.setClass(this, MainActivity.class);
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

                userToken.bindSchoolAccount();
                showInfo("绑定成功","您已经成功绑定教务网帐号");
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


}
