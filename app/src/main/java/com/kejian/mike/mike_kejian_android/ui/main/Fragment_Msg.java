package com.kejian.mike.mike_kejian_android.ui.main;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.broadcast.ReceiverActions;
import com.kejian.mike.mike_kejian_android.ui.message.CourseNoticeActivity;
import com.kejian.mike.mike_kejian_android.ui.message.MentionMeActivity;
import com.kejian.mike.mike_kejian_android.ui.message.NewPraiseActivity;
import com.kejian.mike.mike_kejian_android.ui.message.NewReplyActivity;

import net.picture.MessagePrint;

import bl.MessageBLService;
import model.message.MessageType;

public class Fragment_Msg extends Fragment implements View.OnClickListener{
    private View layout;
    private Activity ctx;

    private TextView unreadPraiseLabel;
    private TextView unreadCourseNoticeLabel;
    private TextView unreadReplyLabel;
    private TextView unreadMentionLabel;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ReceiverActions.increment_action);//建议把它写一个公共的变量，这里方便阅读就不写了。
        BroadcastReceiver messageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent){
                MessageType type = (MessageType)intent.getSerializableExtra("messageType");
                MessageBLService.incrementUnReadMessageNum(type);
                refreshUnReadLabel(type);
            }
        };
        broadcastManager.registerReceiver(messageReceiver, intentFilter);
        MessageBLService.initUnreadMessageNum(getContext());
        MessagePrint.print("-----------------------------Message fragment onActivityCreated--------------------------------------");
    }

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
        MessagePrint.print("---------------message fragment onCreateView-----------------------------");
        return layout;
    }

    public void onStart(){
        super.onStart();
        MessagePrint.print("---------------message fragment onStart-----------------------------");
        initViews();
        initData();
    }

    private void initViews(){
        this.unreadCourseNoticeLabel = (TextView)layout.findViewById(R.id.unread_courseNotice_number);
        this.unreadReplyLabel = (TextView)layout.findViewById(R.id.unread_newReply_number);
        this.unreadPraiseLabel = (TextView) layout.findViewById(R.id.unread_newPraise_number);
        this.unreadMentionLabel = (TextView)layout.findViewById(R.id.unread_mentionMe_number);

        refreshUnReadLabel(MessageType.courseNotice);
        refreshUnReadLabel(MessageType.reply);
        refreshUnReadLabel(MessageType.praise);
        refreshUnReadLabel(MessageType.mentionMe);

    }
    private void initData(){

    }
    public void refreshUnReadLabel(MessageType type){
        switch (type){
            case courseNotice:
                if(MessageBLService.unreadCourseNoticeNum > 0){
                    this.unreadCourseNoticeLabel.setText(String.valueOf(MessageBLService.unreadCourseNoticeNum));
                    this.unreadCourseNoticeLabel.setVisibility(View.VISIBLE);
                }else{
                    this.unreadCourseNoticeLabel.setVisibility(View.INVISIBLE);
                }
                break;
            case reply:
                if(MessageBLService.unreadReplyNum > 0){
                    this.unreadReplyLabel.setText(String.valueOf(MessageBLService.unreadReplyNum));
                    this.unreadReplyLabel.setVisibility(View.VISIBLE);
                }else {
                    this.unreadReplyLabel.setVisibility(View.INVISIBLE);
                }
                break;
            case praise:
                if(MessageBLService.unreadPraiseNum > 0){
                    this.unreadPraiseLabel.setText(String.valueOf(MessageBLService.unreadPraiseNum));
                    this.unreadPraiseLabel.setVisibility(View.VISIBLE);
                }else{
                    this.unreadPraiseLabel.setVisibility(View.INVISIBLE);
                }
                break;
            case mentionMe:
                if(MessageBLService.unreadMentionNum > 0){
                    this.unreadMentionLabel.setText(String.valueOf(MessageBLService.unreadMentionNum));
                    this.unreadMentionLabel.setVisibility(View.VISIBLE);
                }else {
                    this.unreadMentionLabel.setVisibility(View.INVISIBLE);
                }
                break;
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        MessagePrint.print("------------------------------------message fragment is destroyed-----------------------------------------");
        MessageBLService.persistentUnreadMessageNum(getContext());
    }
}
