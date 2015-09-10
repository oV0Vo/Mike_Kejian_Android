package com.kejian.mike.mike_kejian_android.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.message.CourseNoticeActivity;
import com.kejian.mike.mike_kejian_android.ui.message.MentionMeActivity;
import com.kejian.mike.mike_kejian_android.ui.message.NewPraiseActivity;
import com.kejian.mike.mike_kejian_android.ui.message.NewReplyActivity;

import bl.MessageBLService;

public class Fragment_Msg extends Fragment implements View.OnClickListener{
    private View layout;
    private Activity ctx;

    private TextView unreadPraiseLabel;
    private TextView unreadCourseNoticeLabel;
    private TextView unreadReplyLabel;
    private TextView unreadMentionLabel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(layout == null){
            ctx = this.getActivity();
            layout = ctx.getLayoutInflater().inflate(R.layout.fragment_msg,
                    null);
            initViews();
            initData();
            setOnListener();
        }else{
            ViewGroup parent = (ViewGroup) layout.getParent();
            if (parent != null) {
                parent.removeView(layout);
            }
        }
        return layout;
    }

    private void initViews(){
        this.unreadCourseNoticeLabel = (TextView)layout.findViewById(R.id.unread_courseNotice_number);
        this.unreadReplyLabel = (TextView)layout.findViewById(R.id.unread_newReply_number);
        this.unreadPraiseLabel = (TextView) layout.findViewById(R.id.unread_newPraise_number);
        this.unreadMentionLabel = (TextView)layout.findViewById(R.id.unread_mentionMe_number);

        showUnreadLabel(MessageBLService.unreadCourseNoticeNum,this.unreadCourseNoticeLabel);
        showUnreadLabel(MessageBLService.unreadReplyNum,this.unreadReplyLabel);
        showUnreadLabel(MessageBLService.unreadPraiseNum,this.unreadPraiseLabel);
        showUnreadLabel(MessageBLService.unreadMentionNum,this.unreadMentionLabel);

    }
    private void initData(){

    }
    private void showUnreadLabel(int count,TextView tv){
        if(count > 0){
            tv.setText(String.valueOf(count));
            tv.setVisibility(View.VISIBLE);
        }else{
            tv.setVisibility(View.INVISIBLE);
        }
    }
    private void setOnListener(){
        layout.findViewById(R.id.txt_courseNotice).setOnClickListener(this);
        layout.findViewById(R.id.txt_newReply).setOnClickListener(this);
        layout.findViewById(R.id.txt_newPraise).setOnClickListener(this);
        layout.findViewById(R.id.txt_mentionMe).setOnClickListener(this);

    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.txt_courseNotice:
                Intent intent = new Intent();
                intent.setClass(ctx,CourseNoticeActivity.class);
                startActivity(intent);
                break;
            case R.id.txt_newReply:
                intent = new Intent();
                intent.setClass(ctx,NewReplyActivity.class);
                startActivity(intent);
                break;
            case R.id.txt_newPraise:
                intent = new Intent();
                intent.setClass(ctx,NewPraiseActivity.class);
                startActivity(intent);
                break;
            case R.id.txt_mentionMe:
                intent = new Intent();
                intent.setClass(ctx,MentionMeActivity.class);
                startActivity(intent);
                break;
        }
    }

}
