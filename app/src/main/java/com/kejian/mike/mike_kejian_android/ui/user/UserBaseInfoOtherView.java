package com.kejian.mike.mike_kejian_android.ui.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;

import net.UserNetService;

import model.user.Global;
import model.user.user;

/**
 * Created by kisstheraik on 15/9/19.
 */
public class UserBaseInfoOtherView extends Activity{

    private LinearLayout course;
    private LinearLayout people;
    private LinearLayout postList;
    private Context context;
    private Button button;
    private user friend;//这个是这个界面要展示的人的信息

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_info_otherview);
        friend=(user)getIntent().getSerializableExtra("friend");
        context=this;
        initViews();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mention_me, menu);
        return true;
    }

    public void initViews(){


        course=(LinearLayout)findViewById(R.id.his_attention_course);
        people=(LinearLayout)findViewById(R.id.his_attention_people);
        postList=(LinearLayout)findViewById(R.id.his_attention_post);
        button=(Button)findViewById(R.id.attention_people);

        button.setOnClickListener(new View.OnClickListener() {

            user u=(user) Global.getObjectByName("user");
            @Override
            public void onClick(View v) {

                new attention().execute(u.getId(),"PEOPLE",friend.getId());



            }
        });

        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("userId",friend.getId());
                bundle.putString("type","course");
                intent.putExtras(bundle);
                intent.setClass(context,UserOtherState.class);
                startActivity(intent);
            }
        });
        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("userId",friend.getId());
                bundle.putString("type","people");
                intent.putExtras(bundle);
                intent.setClass(context,UserOtherState.class);
                startActivity(intent);
            }
        });
        postList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("userId",friend.getId());
                bundle.putString("type","post");
                intent.putExtras(bundle);
                intent.setClass(context,UserOtherState.class);
                startActivity(intent);
            }
        });


    }

    private  class LoadInfo extends  AsyncTask<String,Integer,String>{

        public String doInBackground(String...Para){
            return null;
        }
    }
    private class attention extends AsyncTask<String,Integer,Boolean>{

        public Boolean doInBackground(String...Para){

            boolean result=UserNetService.attention(Para[0],Para[1],Para[2]);
            return result;
        }
        @Override

        public void onPostExecute(Boolean result){

            if(result){

                Toast.makeText(context,"关注成功",Toast.LENGTH_SHORT).show();
            }

        }
    }
    private class unattention extends AsyncTask<String,Integer,Boolean>{

        public Boolean doInBackground(String...Para){

            boolean result=UserNetService.unattention(Para[0], Para[1], Para[2]);
            return result;
        }
        @Override

        public void onPostExecute(Boolean result){

            if(result){

                Toast.makeText(context,"关注成功",Toast.LENGTH_SHORT).show();
            }

        }
    }


}
