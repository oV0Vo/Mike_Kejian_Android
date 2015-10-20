package com.kejian.mike.mike_kejian_android.ui.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by kisstheraik on 15/10/15.
 */
public class NetBroadcast extends BroadcastReceiver{

    public void onReceive(Context context,Intent intent){

        ConnectivityManager manager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] networkInfos=manager.getAllNetworkInfo();

        int size=networkInfos.length;
        int index=0;

        if (networkInfos != null && size > 0) {
            for (int i = 0; i < size; i++) {

                if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {

                    index++;

                }

            }
        }

       if(index==0) {

           Toast.makeText(context, "请检查你的网络连接", Toast.LENGTH_LONG).show();

       }

        else{

       }

    }
}
