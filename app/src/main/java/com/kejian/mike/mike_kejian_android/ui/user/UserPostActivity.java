package com.kejian.mike.mike_kejian_android.ui.user;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

import java.util.ArrayList;

import model.user.Invitee;

/**
 * Created by kisstheraik on 15/9/18.
 */
public class UserPostActivity extends Activity{

    private TextView titlePanel;
    private TextView contentPanel;
    private ArrayList<Invitee> invitees;
    private TextView invite;
    private TextView post;

    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_post);

    }

    private void initViews(){

        this.titlePanel=(TextView)findViewById(R.id.title_panel);
        this.contentPanel=(TextView)findViewById(R.id.content_panel);
        this.post=(TextView)findViewById(R.id.post_confirm);
        this.invite=(TextView)findViewById(R.id.post_invite);





    }

    private void initDatas(){

    }

    private class InviteListener implements View.OnClickListener{

        public void onClick(View view){

        }

    }
}
