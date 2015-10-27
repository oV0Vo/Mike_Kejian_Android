package com.kejian.mike.mike_kejian_android.ui.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.campus.PostDetailActivity;
import com.kejian.mike.mike_kejian_android.ui.course.detail.CourseActivity;
import com.kejian.mike.mike_kejian_android.ui.message.CircleImageView;
import com.kejian.mike.mike_kejian_android.ui.user.UserBaseInfoOtherView;

import net.UserNetService;
import net.picture.DownloadPicture;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.course.CourseModel;
import model.user.CourseBrief;
import model.user.Friend;
import model.user.PostBrief;


/**
 * Created by kisstheraik on 15/9/20.
 */
public class AttentionListAdapter extends BaseAdapter {

    private ArrayList contentList;
    private Context context;
    private LayoutInflater layoutInflater;
    private int type;

    /*
     * 构造方法 传入type和数据
     * type 1，2，3  对应 用户 课程 帖子
     */

    public AttentionListAdapter(int type,ArrayList contentList,Context context){

        this.contentList=contentList;
        this.context=context;
        this.layoutInflater=LayoutInflater.from(context);
        this.type=type;

    }

    public void setContentList(ArrayList contentList){
        this.contentList=contentList;
    }

    public boolean isEmpty(){
        return false;
    }

    //返回view item的数目
    public int getCount(){



        if(contentList==null)return 0;

        return contentList.size();

    }

    public Object getItem(int position){

        return position;

    }

    public long getItemId(int position){

        return position;

    }

    public View getView(int position,View view,ViewGroup viewGroup){
        View v=null;
      //  v = layoutInflater.inflate(R.layout.layout_user_attention_item_people, viewGroup, false);
//


        if(view==null) {
           // v = layoutInflater.inflate(R.layout.layout_user_attention_item_people, viewGroup, false);
//
            if(type==1) {


                v = layoutInflater.inflate(R.layout.layout_user_attention_item_people, viewGroup, false);

                final Friend friend=(Friend)contentList.get(position);

                CircleImageView circleImageView=(CircleImageView)v.findViewById(R.id.photoView);

                DownloadPicture downloadPicture=new DownloadPicture(context,circleImageView,friend.getIcon(),friend.getIcon());

                TextView textView=(TextView)v.findViewById(R.id.nameView);

                textView.setText(friend.getUserName());

                TextView sign=(TextView)v.findViewById(R.id.signView);

                sign.setText(friend.getSign());

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        System.out.println("jump to :"+friend.getId());

                        new jump().execute(friend.getId());
                    }


                });

            }
            if(type==2){
                 v=layoutInflater.inflate(R.layout.layout_user_attention_item_course,viewGroup,false);

                final CourseBrief courseBrief=(CourseBrief)contentList.get(position);

                CircleImageView circleImageView=(CircleImageView)v.findViewById(R.id.coursephotoView);
                TextView name=(TextView)v.findViewById(R.id.coursenameView);

                name.setText(courseBrief.getName());
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        CourseModel.getInstance().setCurrentCourseId(courseBrief.getId());

                        Intent intent=new Intent();

                        intent.setClass(context, CourseActivity.class);

                        context.startActivity(intent);
                    }
                });


                DownloadPicture downloadPicture=new DownloadPicture(context,circleImageView,courseBrief.getIcon(),courseBrief.getIcon());


            }
            if(type==3){

                    v=layoutInflater.inflate(R.layout.layout_attention_list_item_post,viewGroup,false);

                final PostBrief postBrief=(PostBrief)contentList.get(position);

                TextView title=(TextView)v.findViewById(R.id.titleView);
                TextView name=(TextView)v.findViewById(R.id.userName);
                CircleImageView circleImageView=(CircleImageView)v.findViewById(R.id.userView);

                name.setText(postBrief.getUserName());
                title.setText(postBrief.getPostName());

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(context, PostDetailActivity.class);
                        intent.putExtra("postId", postBrief.getId());
                        context.startActivity(intent);
                    }
                });


                DownloadPicture downloadPicture=new DownloadPicture(context,circleImageView,postBrief.getUserIcon(),postBrief.getUserIcon());

                }
            }


        else{

            v=view;
        }

        return v;

    }

    private  class jump extends AsyncTask<String,Integer,String>{
        public String doInBackground(String...Para){


            Intent intent=new Intent();

            Bundle bundle=new Bundle();



            bundle.putSerializable("friend",UserNetService.getUserInfo(Para[0]));

            intent.putExtras(bundle);

            intent.setClass(context, UserBaseInfoOtherView.class);

            context.startActivity(intent);

            return null;
        }
    }



}
