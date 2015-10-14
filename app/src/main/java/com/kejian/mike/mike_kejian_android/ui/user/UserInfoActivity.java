package com.kejian.mike.mike_kejian_android.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.message.CircleImageView;

import net.picture.PictureToFile;
import net.picture.PictureUploadUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import bl.UserBLService;
import model.user.Global;
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

        user= (user)Global.getObjectByName("user");
//        UserToken u=new UserToken();
//        u.setName("义薄云天");
//        u.setPassword("123456");
//        user= UserBLService.getInstance().login(u);
        userBaseInfoView=(TableLayout)findViewById(R.id.user_base_info_view);
        baseInfoName=(TextView)findViewById(R.id.base_info_name);
        baseInfoGender=(TextView)findViewById(R.id.base_info_gender);
        baseInfoGrade=(TextView)findViewById(R.id.user_school_info_grade);
        baseInfoIdentify=(TextView)findViewById(R.id.user_school_info_identify);
        baseInfoSign=(TextView)findViewById(R.id.base_info_sign);
        photo=(CircleImageView)findViewById(R.id.user_photo_view);

        System.out.println("user in userinfo view:"+user);

        String iconPath=user.getIcon();
       // iconPath="http://images.csdn.net/20150821/u=2293639569,2369491550&fm=58%20(1).jpg";

//        try {
//            URL url = new URL(iconPath);
//            photo.setImageBitmap(BitmapFactory.decodeStream(url.openStream()));
//        } catch (Exception e) {
//
//
//        }
////            photo.setImageBitmap(getBitmap(iconPath));
//        System.out.println("set photo :"+iconPath);
//       // photo.setImageDrawable(D);
        setUserInfo();

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("outputX", 80);
                intent.putExtra("outputY", 80);
                intent.putExtra("return-data", true);

                startActivityForResult(intent, 0);


            }
        });

        assert user==null:"do not get user";


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println(resultCode);
        Bitmap cameraBitmap = (Bitmap) data.getExtras().get("data");
        super.onActivityResult(requestCode, resultCode, data);

        new uploadpicture().execute(cameraBitmap);



        photo.setImageBitmap(cameraBitmap);

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



            System.out.println("Begin download icon!");

            new DownLoadIcon().execute("http://d.hiphotos.baidu.com/zhidao/pic/item/730e0cf3d7ca7bcbb9177b55b8096b63f624a858.jpg");



            String name=user.getName();
            String gender=user.getGender();
            String grade=user.getGrade();
            String sign=user.getSign();
            String identify=user.getIdentify();

            baseInfoGender.setText(gender);
            baseInfoGrade.setText(grade);
            baseInfoIdentify.setText(identify);
            baseInfoSign.setText(sign);
            baseInfoName.setText(name);

        }

    }

    public void setPhotoFromNetWork(Bitmap bitmap){

        System.out.println("set the photo from the net work!");

        photo.setImageBitmap(bitmap);

    }

    private class uploadpicture extends AsyncTask<Bitmap ,Integer,String>{


        protected void onPreExecute(){

        }

        protected String doInBackground(Bitmap...Para){

            System.out.println("net photo paht:"+PictureUploadUtil.upload(PictureToFile.bitmapToFile(Para[0],Para[0].toString())).getLinkurl());

            return "";

        }

    }

    private class DownLoadIcon extends AsyncTask<String,Integer,String>{


        protected void onPreExecute(){

        }

        protected String doInBackground(String...Para){

            InputStream in=null;

            try {

                System.out.println(Para[0]);

                URL url = new URL(Para[0]);
                URLConnection urlConnection=url.openConnection();
                urlConnection.setRequestProperty("accept", "*/*");//接受什么类型的介质，此处为任何类型，*为通配符
                urlConnection.setRequestProperty("connection","Keep-Alive");//
                urlConnection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");//表明客户端是哪种浏览器


                urlConnection.connect();
                in=urlConnection.getInputStream();

                Bitmap bitmap=BitmapFactory.decodeStream(in);

                setPhotoFromNetWork(bitmap);


            }catch(Exception e){
                e.printStackTrace();

            }

            return null;

        }
    }



}
