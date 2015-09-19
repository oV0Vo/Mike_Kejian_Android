package com.kejian.mike.mike_kejian_android.ui.user;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

import java.util.ArrayList;

import model.user.Invitee;
import model.user.UserPost;

/**
 * Created by kisstheraik on 15/9/17.
 */
public class UserPostFragment extends Fragment {


    private TextView titlePanel;
    private TextView contentPanel;
    private ArrayList<Invitee> invitees;

    public View onCreateView(LayoutInflater layoutInflater,ViewGroup container,Bundle savedInstanceState){

        return layoutInflater.inflate(R.layout.fragment_user_post,container,false);




    }

    public void initView(){

        this.titlePanel=(TextView)this.getActivity().findViewById(R.id.title_panel);
        this.contentPanel=(TextView)this.getActivity().findViewById(R.id.content_panel);

    }

    public void initViews(){

    }

    public void post(){

        UserPost post=new UserPost(null);
        post.addTitle((String)this.titlePanel.getText());
        post.addContent((String)this.contentPanel.getText());

    }


}
