package com.kejian.mike.mike_kejian_android.ui.user;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.message.OnRefreshListener;
import com.kejian.mike.mike_kejian_android.ui.message.RefreshListView;
import com.kejian.mike.mike_kejian_android.ui.user.adapter.AttentionListAdapter;

import net.UserNetService;

import java.util.ArrayList;

import model.campus.Post;
import model.user.CourseBrief;
import model.user.Friend;
import model.user.PostBrief;

/**
 * Created by kisstheraik on 15/10/20.
 */
public class UserOtherState  extends AppCompatActivity{

    private RefreshListView refreshListView;
    private ArrayList list=new ArrayList<>();

    private String userId;
    private String type;

    private AttentionListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_other_state);

        refreshListView=(RefreshListView)findViewById(R.id.other_state_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



       Bundle bundle=getIntent().getExtras();
        userId=bundle.getString("userId");
        type=bundle.getString("type");

        switch(type){
            case "people":
               adapter=new AttentionListAdapter(1,list,this);
                break;
            case "course":
                adapter=new AttentionListAdapter(2,list,this);
                break;
            case "post":

                adapter=new AttentionListAdapter(3,list,this);
                break;

        }

        refreshListView.setAdapter(adapter);

        new GetData().execute(type);





    }
    private class Refresh implements OnRefreshListener {

        private  String type;

        public Refresh(String type){

            this.type=type;

        }



        @Override
        public void onDownPullRefresh() {

          new GetData().execute(type);
            refreshListView.hideFooterView();
            refreshListView.hideHeaderView();

        }

        @Override
        public void onLoadingMore() {

            new GetData().execute(type);
            refreshListView.hideFooterView();
            refreshListView.hideHeaderView();


        }
    }

    private class GetData  extends AsyncTask<String,Integer,String>{

        @Override
        public String doInBackground(String...Para){

            switch(Para[0]){

                case "people":list=UserNetService.getAttentionPeople(userId);

                    break;
                case "course":list=UserNetService.getAttentionCourse(userId);

                    break;
                case "post":list=UserNetService.getAttentionPost(userId);

                    break;
                default:break;
            }
            return null;
        }
        @Override
        public void onPostExecute(String result){
            adapter.setContentList(list);
            adapter.notifyDataSetChanged();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            this.finish();
        }

        return true;
    }
}
