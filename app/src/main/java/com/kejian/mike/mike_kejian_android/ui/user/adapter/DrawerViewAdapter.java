package com.kejian.mike.mike_kejian_android.ui.user.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.message.CircleImageView;

import net.picture.DownloadPicture;

import java.util.Map;
import java.util.List;

import model.user.Global;
import model.user.user;
import util.GetBitmapByPinyin;

/**
 * Created by kisstheraik on 15/9/18.
 */
public class DrawerViewAdapter extends BaseAdapter{

    private List<Map<String,Object>> contentList;
    private Context context;
    private LayoutInflater layoutInflater;
    private CircleImageView circleImageView;

    public DrawerViewAdapter(List<Map<String,Object>> contentList,Context context){

        this.contentList=contentList;
        this.context=context;
        this.layoutInflater=LayoutInflater.from(context);



    }

    public int getCount(){

        return 4;

    }

    public Object getItem(int position){

        return position;

    }

    public long getItemId(int position){

        return 0;

    }

    public View getView(int position,View view,ViewGroup viewGroup){
        View v=null;


        if(view==null) {

            switch(position){


                case 0:v=layoutInflater.inflate(R.layout.layout_user_photo,viewGroup,false);
                    user u=(user)Global.getObjectByName("user");

                    final CircleImageView circleImageView=(CircleImageView)v.findViewById(R.id.photo);
                    if(u.getIcon().equals("")){
                        circleImageView.setImageResource(R.drawable.userxh);
                    }


                    DownloadPicture d=new DownloadPicture(context,circleImageView,u.getIcon(),u.getIcon());

                   // circleImageView.setImageBitmap(GetBitmapByPinyin.getBitmapByPinyin("我是谁",context));

                    TextView name=(TextView)v.findViewById(R.id.drawerName);
                    ImageView identify=(ImageView)v.findViewById(R.id.drawerIdentify);

                    if(u.getIdentify().equals("0")){

                        identify.setImageResource(R.drawable.student);
                    }
                    else{
                        identify.setImageResource(R.drawable.teacher);
                    }

                    name.setText(u.getNick_name());


                    break;
                case 1:v=layoutInflater.inflate(R.layout.layout_user_info,viewGroup,false);break;
                case 2:v=layoutInflater.inflate(R.layout.layout_user_setting,viewGroup,false);break;
                case 3:v=layoutInflater.inflate(R.layout.layout_user_attention,viewGroup,false);break;

            }
        }

        else{
            v=view;
        }

        return v;

    }
}
