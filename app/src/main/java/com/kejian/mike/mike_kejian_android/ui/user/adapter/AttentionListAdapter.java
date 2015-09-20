package com.kejian.mike.mike_kejian_android.ui.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;

import com.kejian.mike.mike_kejian_android.R;

import java.util.List;
import java.util.Map;


/**
 * Created by kisstheraik on 15/9/20.
 */
public class AttentionListAdapter extends BaseAdapter {

    private List<Map<String,Object>> contentList;
    private Context context;
    private LayoutInflater layoutInflater;
    private int type;

    public AttentionListAdapter(int type,List<Map<String,Object>> contentList,Context context){

        this.contentList=contentList;
        this.context=context;
        this.layoutInflater=LayoutInflater.from(context);
        this.type=type;

    }

    public boolean isEmpty(){
        return false;
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
            if(type==1) {

                switch (position) {


                    case 0:
                        v = layoutInflater.inflate(R.layout.layout_user_attention_item_people, viewGroup, false);
                        break;
                    case 1:
                        v = layoutInflater.inflate(R.layout.layout_user_attention_item_people, viewGroup, false);
                        break;
                    case 2:
                        v = layoutInflater.inflate(R.layout.layout_user_attention_item_people, viewGroup, false);
                        break;
                    case 3:
                        v = layoutInflater.inflate(R.layout.layout_user_attention_item_people, viewGroup, false);
                        break;

                }
            }
            if(type==2){
                switch(position){


                    case 0:v=layoutInflater.inflate(R.layout.layout_user_attention_item_course,viewGroup,false);break;
                    case 1:v=layoutInflater.inflate(R.layout.layout_user_attention_item_course,viewGroup,false);break;
                    case 2:v=layoutInflater.inflate(R.layout.layout_user_attention_item_course,viewGroup,false);break;
                    case 3:v=layoutInflater.inflate(R.layout.layout_user_attention_item_course,viewGroup,false);break;

                }
            }
            if(type==3){
                switch(position){


                    case 0:v=layoutInflater.inflate(R.layout.layout_attention_list_item_post,viewGroup,false);break;
                    case 1:v=layoutInflater.inflate(R.layout.layout_attention_list_item_post,viewGroup,false);break;
                    case 2:v=layoutInflater.inflate(R.layout.layout_attention_list_item_post,viewGroup,false);break;
                    case 3:v=layoutInflater.inflate(R.layout.layout_attention_list_item_post,viewGroup,false);break;

                }
            }
        }

        else{
            v=view;
        }

        return v;

    }
}
