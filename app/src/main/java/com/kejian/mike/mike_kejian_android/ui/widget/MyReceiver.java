package com.kejian.mike.mike_kejian_android.ui.widget;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.kejian.mike.mike_kejian_android.ui.broadcast.ReceiverActions;
import com.kejian.mike.mike_kejian_android.ui.campus.PostDetailActivity;

import net.picture.MessagePrint;

import org.json.JSONObject;

import java.util.logging.Logger;

import cn.jpush.android.api.JPushInterface;
import model.message.MessageType;

/**
 * Created by kisstheraik on 15/10/27.
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    private NotificationManager nm;
    private Context context;


    public void sendBroadcast(MessageType messageType){

        Intent intent = new Intent(ReceiverActions.increment_action);


        intent.putExtra("messageType",messageType);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }



    @Override
    public void onReceive(Context context, Intent intent) {

        this.context=context;

        String message=intent.getExtras().getString(JPushInterface.EXTRA_EXTRA);

        MessagePrint.print("receive push message :"+intent.getExtras().getString(JPushInterface.EXTRA_EXTRA));

        try {

            JSONObject jsonObject = new JSONObject(message);

            String action =jsonObject.getString("inf_type");

            switch(action){


                case "AT":
                    sendBroadcast(MessageType.mentionMe);
                    break;
                case "REPLY":
                    sendBroadcast(MessageType.reply);
                    break;
                case "PRAISE":
                    sendBroadcast(MessageType.praise);
                    break;
                case "ANNOUNCE_TE":

                    sendBroadcast(MessageType.courseNotice);break;
//                case "INVITE":openNotification(context,intent.getExtras());

            }

        }catch (Exception e){

            e.printStackTrace();
        }


        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();
       // Logger.d(TAG, "onReceive - " + intent.getAction() + ", extras: " + AndroidUtil.printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
           // Logger.d(TAG, "JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {

           Bundle bundle1= intent.getExtras();

            System.out.println("key : "+bundle1.getString("key"));
           // Logger.d(TAG, "接受到推送下来的自定义消息");

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
           // Logger.d(TAG, "接受到推送下来的通知");

            receivingNotification(context,bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
           // Logger.d(TAG, "用户点击打开了通知");

            openNotification(context,bundle);

        } else {
            //Logger.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    private void receivingNotification(Context context, Bundle bundle){
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
      //  Logger.d(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
      //  Logger.d(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
     //   Logger.d(TAG, "extras : " + extras);
    }

    private void openNotification(Context context, Bundle bundle){


           String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String myValue = "";
        JSONObject extrasJson=null;
        try {
             extrasJson= new JSONObject(extras);
            myValue = extrasJson.getString("inf_type");

            switch(myValue){

                case "INVITE":

                    String postId=extrasJson.getString("postId");

                    jumpToPost(postId);

                    break;
                case "UPDATE":
                    jumpToUpdate();
                    break;
            }
        } catch (Exception e) {
            //Logger.w(TAG, "Unexpected: extras is not a valid json", e);
            return;
        }


//        if (TYPE_THIS.equals(myValue)) {
//            Intent mIntent = new Intent(context, ThisActivity.class);
//            mIntent.putExtras(bundle);
//            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(mIntent);
//        } else if (TYPE_ANOTHER.equals(myValue)){
//            Intent mIntent = new Intent(context, AnotherActivity.class);
//            mIntent.putExtras(bundle);
//            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(mIntent);
//        }
    }


    public void jumpToUpdate(){

    }
    public void jumpToPost(String postId){

        MessagePrint.print("打开通知");






        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(context, PostDetailActivity.class);
        intent.putExtra("postId", postId);
        context.startActivity(intent);

    }


}
