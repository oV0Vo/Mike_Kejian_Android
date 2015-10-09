package com.kejian.mike.mike_kejian_android.ui.user;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.message.CircleImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import bl.UserBLService;
import model.user.UserToken;
import model.user.user;

/**
 * Created by kisstheraik on 15/9/18.
 */


public class UserInfoActivity extends AppCompatActivity{

    private user user;
    private LinearLayout userInfoLayout;
    private TableLayout userBaseInfoView;
    private TableLayout userSchoolInfoView;
    private TextView baseInfoName;
    private TextView baseInfoGender;
    private TextView baseInfoGrade;
    private TextView baseInfoIdentify;
    private TextView baseInfoSign;
    private CircleImageView photo;






    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_info);
        initViews();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_info, menu);
        return true;
    }

    public void initViews(){

        user=(user)getIntent().getSerializableExtra(UserActivityComm.USER_INFO.name());
        UserToken u=new UserToken();
        u.setName("义薄云天");
        u.setPassword("123456");
        user= UserBLService.getInstance().login(u);
        userBaseInfoView=(TableLayout)findViewById(R.id.user_base_info_view);
        baseInfoName=(TextView)findViewById(R.id.base_info_name);
        baseInfoGender=(TextView)findViewById(R.id.base_info_gender);
        baseInfoGrade=(TextView)findViewById(R.id.user_school_info_grade);
        baseInfoIdentify=(TextView)findViewById(R.id.user_school_info_identify);
        baseInfoSign=(TextView)findViewById(R.id.base_info_sign);
        photo=(CircleImageView)findViewById(R.id.user_photo_view);

        String iconPath=user.getIcon();
       // iconPath="http://images.csdn.net/20150821/u=2293639569,2369491550&fm=58%20(1).jpg";

        try {
            URL url = new URL(iconPath);
            photo.setImageBitmap(BitmapFactory.decodeStream(url.openStream()));
        } catch (Exception e) {


        }
//            photo.setImageBitmap(getBitmap(iconPath));
        System.out.println("set photo :"+iconPath);
       // photo.setImageDrawable(D);
        setUserInfo();

        assert user==null:"do not get user";


    }

    public Bitmap getBitmap(String iconPath){
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(iconPath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setConnectTimeout(6000);
            //连接设置获得数据流
            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(false);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public void setUserInfo(){



        if(user==null){

            System.out.println("no user");



            return ;
        }

        else{

            System.out.println("get user " + user.getName());



            String name="姓名："+user.getName();
            String gender="性别："+user.getGender();
            String grade="年级："+user.getGrade();
            String sign=""+user.getSign();
            String identify="身份："+user.getIdentify();

            baseInfoGender.setText(gender);
            baseInfoGrade.setText(grade);
            baseInfoIdentify.setText(identify);
            baseInfoSign.setText(sign);
            baseInfoName.setText(name);

        }

    }



}
