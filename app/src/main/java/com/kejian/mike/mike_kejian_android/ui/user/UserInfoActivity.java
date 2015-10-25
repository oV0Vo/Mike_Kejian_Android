package com.kejian.mike.mike_kejian_android.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.message.CircleImageView;

import net.UserNetService;
import net.picture.DownloadPicture;
import net.picture.MessagePrint;
import net.picture.PictureToFile;
import net.picture.PictureUploadUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import bl.UserBLService;
import model.user.Global;
import model.user.UserToken;
import model.user.department;
import model.user.school;
import model.user.user;

/**
 * Created by kisstheraik on 15/9/18.
 */


public class UserInfoActivity extends AppCompatActivity{

    private user user;
    private LinearLayout userInfoLayout;
    private TableLayout userBaseInfoView;
    private TableLayout userSchoolInfoView;
    private EditText baseInfoName;
    private EditText baseInfoGender;
    private EditText baseInfoGrade;
    private EditText baseInfoIdentify;
    private EditText baseInfoSign;
    private EditText baseInfoNickname;
    private EditText schoolAccountView;
    private EditText schoolMajorView;
    private EditText schoolDepartmentView;
    private Menu menu;

    private CircleImageView photo;






    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_info, menu);
        this.menu=menu;
        MenuItem menuItem=(MenuItem)menu.findItem(R.id.save_user_info);
        menuItem.setVisible(false);
        return true;
    }

    public void initViews(){

        user= (user)Global.getObjectByName("user");
//        UserToken u=new UserToken();
//        u.setName("义薄云天");
//        u.setPassword("123456");
//        user= UserBLService.getInstance().login(u);
        baseInfoNickname=(EditText)findViewById(R.id.base_info_nickname);
        schoolAccountView=(EditText)findViewById(R.id.user_school_info_number);
        schoolMajorView=(EditText)findViewById(R.id.user_school_info_major);
        schoolDepartmentView=(EditText)findViewById(R.id.user_school_info_department);
        userBaseInfoView=(TableLayout)findViewById(R.id.user_base_info_view);
        baseInfoName=(EditText)findViewById(R.id.base_info_name);
        baseInfoGender=(EditText)findViewById(R.id.base_info_gender);
        baseInfoGrade=(EditText)findViewById(R.id.user_school_info_grade);
        baseInfoIdentify=(EditText)findViewById(R.id.user_school_info_identify);
        baseInfoSign=(EditText)findViewById(R.id.base_info_sign);
        photo=(CircleImageView)findViewById(R.id.user_photo_view);




        DownloadPicture d=new DownloadPicture(this,photo, user.getIcon(),user.getIcon());

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
//                intent.putExtra("outputX", 80);
//                intent.putExtra("outputY", 80);
                intent.putExtra("return-data", true);

                startActivityForResult(intent, 0);


            }
        });

        assert user==null:"do not get user";


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        Bitmap cameraBitmap = null;

       if(data!=null) {cameraBitmap=(Bitmap) data.getExtras().get("data");}
        super.onActivityResult(requestCode, resultCode, data);



        if(cameraBitmap!=null) {

            Global.addGlobalItem("bitmap", cameraBitmap);


            photo.setImageBitmap(cameraBitmap);
        }

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

//
//            name=(String)(infoSet.get("name"));
//            gender=(String)(infoSet.get("gender"));
//            grade=(String)(infoSet.get("grade"));
//            icon=(String)(infoSet.get("icon"));
//            sign=(String)(infoSet.get("signal"));
//            identify=(String)(infoSet.get("identify"));
//            id=infoSet.get("id").toString();
//            nick_name=(String)infoSet.get("nick_name");
//            background_icon_path=(String)infoSet.get("background");
//            identify=(String)infoSet.get("school_identify");
//
//            schoolInfo=new school();
//            schoolInfo.setId((String)infoSet.get("school_id"));
////        schoolInfo.setName();
////        schoolInfo.setNumber();
////
//
//            departmentInfo=new department();
////        departmentInfo.setNumber();
////        departmentInfo.setName();
//            departmentInfo.setId((String)infoSet.get("department_id"));
//            departmentInfo.setSchoolId(schoolInfo.getId());


            System.out.println("Begin download icon!");

           // new DownLoadIcon().execute("http://d.hiphotos.baidu.com/zhidao/pic/item/730e0cf3d7ca7bcbb9177b55b8096b63f624a858.jpg");



            String name=user.getName();
            String gender=user.getGender();
            String grade=user.getGrade();
            String sign=user.getSign();
            String identify=user.getIdentify();

//




//            userBaseInfoView=(TableLayout)findViewById(R.id.user_base_info_view);



            if(gender.equals("0")) {
                baseInfoGender.setText("男生");
            }
            else{
                baseInfoGender.setText("菇凉");
            }
            baseInfoGender.setEnabled(false);

            baseInfoGrade.setText(grade);
            baseInfoGrade.setEnabled(false);


            if(identify.equals("0")) {
                baseInfoIdentify.setText("学生");
            }
            else{

                baseInfoIdentify.setText("教师");
            }
            baseInfoIdentify.setEnabled(false);


            baseInfoSign.setText(sign);
            baseInfoSign.setEnabled(false);


            baseInfoName.setText(name);
            baseInfoName.setEnabled(false);

            baseInfoNickname.setText(user.getNick_name());
            baseInfoNickname.setEnabled(false);

            schoolDepartmentView.setText(user.getDepartmentInfo().getName());
            schoolDepartmentView.setEnabled(false);

            schoolMajorView.setText(user.getMajorName());
            schoolMajorView.setEnabled(false);


            schoolAccountView.setText(user.getSchoolAccount());
            schoolAccountView.setEnabled(false);


        }

    }

    public void setPhotoFromNetWork(Bitmap bitmap){

        System.out.println("set the photo from the net work!");

        photo.setImageBitmap(bitmap);

    }

    private class resetUserInfo extends AsyncTask<HashMap,Integer,String>{

        protected void onPreExecute(){

        }

        protected String doInBackground(HashMap...Para){


            HashMap info=Para[0];

           /*

            这里调用修改用户信息的接口
            */


            return "";

        }


    }

    private class uploadpicture extends AsyncTask<Bitmap ,Integer,String>{


        protected void onPreExecute(){

        }

        protected String doInBackground(Bitmap...Para){

            System.out.println(PictureUploadUtil.upload(PictureToFile.bitmapToFile(Para[0], user.getIcon())) == null);


           String path=PictureUploadUtil.upload(PictureToFile.bitmapToFile(Para[0],"temp")).getLinkurl();



            UserNetService.setUserInfo(Integer.parseInt(user.getId()),"ICON",path);



            user.setIcon(path);

          //  UserNetService.setUserInfo(Integer.parseInt(user.getId()), "SIGN_TEXT", signal);
            return "";

        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.edit_user_info:
                item.setVisible(false);

                MenuItem menuItem=(MenuItem)menu.findItem(R.id.save_user_info);
                menuItem.setVisible(true);


                setEditAble();

                return true;
            case R.id.save_user_info:
                item.setVisible(false);
                saveUserInfo();

                MenuItem menuItem2=(MenuItem)menu.findItem(R.id.edit_user_info);
                menuItem2.setVisible(true);

                return true;
            case android.R.id.home:

                this.finish();

                return true;


            default:

                return super.onOptionsItemSelected(item);
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

    public void saveUserInfo(){

        /*
        the info that can be save

        SIGN_TEXT|ICON|NICKNAME
         */

      final  String nickName=(String)baseInfoNickname.getText().toString().trim();

        final String signal=(String)baseInfoSign.getText().toString().trim();

        new Thread(){

            public void run(){

                UserNetService.setUserInfo(Integer.parseInt(user.getId()),"NICK_NAME",nickName);
                UserNetService.setUserInfo(Integer.parseInt(user.getId()), "SIGN_TEXT", signal);

                Bitmap bitmap=(Bitmap)Global.getObjectByName("bitmap");
                if(bitmap!=null)new uploadpicture().execute(bitmap);

            }
        }.start();

        Toast.makeText(this,"保存成功 >_<",Toast.LENGTH_SHORT).show();

        setUnable();

        user.setNickName(nickName);
        user.setSign(signal);

    }
    public void setUnable(){

        baseInfoGender.setEnabled(false);

        baseInfoGender.setEnabled(false);

        baseInfoSign.setEnabled(false);


        baseInfoName.setEnabled(false);


        baseInfoNickname.setEnabled(false);

    }
    public void setEditAble(){


       // baseInfoGender.setEnabled(true);

       // baseInfoGender.setEnabled(true);

        baseInfoSign.setEnabled(true);


       // baseInfoName.setEnabled(true);


        baseInfoNickname.setEnabled(true);


       // baseInfoGrade.setEnabled(false);



        //baseInfoIdentify.setEnabled(false);






        //schoolDepartmentView.setEnabled(false);


        //schoolMajorView.setEnabled(false);



      //  schoolAccountView.setEnabled(false);

//        private TextView baseInfoName;

       // baseInfoGender.setBackgroundColor(Color.GRAY);

//        private TextView baseInfoGrade;
//        private TextView baseInfoIdentify;
//        private TextView baseInfoSign;
//        private TextView baseInfoNickname;
//        private TextView schoolAccountView;
//        private TextView schoolMajorView;
//        private TextView schoolDepartmentView;

    }



}
