package com.kejian.mike.mike_kejian_android.ui.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.campus.PostDetailActivity;
import com.kejian.mike.mike_kejian_android.ui.message.OnRefreshListener;
import com.kejian.mike.mike_kejian_android.ui.message.RefreshListView;

import java.util.List;

import bl.MessageBLService;
import model.message.MentionMe;
import model.message.Reply;
import model.user.UserToken;


public class UserAttentionActivity extends AppCompatActivity implements View.OnClickListener,OnRefreshListener {

    private LinearLayout mainLayout;
    private RefreshListView container;
    private LayoutInflater myInflater;
    private ProgressBar progressBar;
    private BaseAdapter adapter;
    public Context context;
    private UserToken userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention_people);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.context=this;
        this.mainLayout = (LinearLayout)findViewById(R.id.attention_people_layout);
        this.mainLayout.setVisibility(View.GONE);
        this.progressBar = (ProgressBar)findViewById(R.id.attention_people_progress_bar);
        System.out.println("in userAttentionActivity3");
        initData();
        System.out.println("in userAttentionActivity4");

    }
    private void initData(){

        new InitDataTask().execute("1234");
    }
    private void initViews(){

        this.container = (RefreshListView)findViewById(R.id.attention_people_container);
        this.myInflater = getLayoutInflater();

        System.out.println("in userAttentionActivity1");
        //this.adapter=new AttentionListAdapter(0,null,context);
        System.out.println("in userAttentionActivity2");
        this.adapter = new MentionMeAdapter(this,android.R.layout.simple_list_item_1,MessageBLService.mentionMes);
        this.container.setAdapter(adapter);
        this.container.setOnRefreshListener(this);
        container.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.setClass(context, PostDetailActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onDownPullRefresh() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                MessageBLService.refreshMentionMes("123");
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                adapter.notifyDataSetChanged();
                container.hideHeaderView();
            }
        }.execute(new Void[]{});

    }

    @Override
    public void onLoadingMore() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                MessageBLService.addMentionMes("12343");
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                adapter.notifyDataSetChanged();

                // 控制脚布局隐藏
                container.hideFooterView();
            }
        }.execute(new Void[]{});
    }

    private class InitDataTask extends AsyncTask<String, Integer, String> {
        @Override
        public String doInBackground(String... params) {
            String userId = params[0];
            MessageBLService.refreshTotalMentionMeNum(userId);
            MessageBLService.initMentionMes(userId);
            return "";
        }

        @Override
        public void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            initViews();
            mainLayout.setVisibility(View.VISIBLE);
        }
    }
    public  class ViewHolder{
        ImageView avatar_view;
        TextView mentioner_view;
        TextView post_view;
        TextView time_view;
        Context activityContext;
        public ViewHolder(){

//            avatar_view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });

//            avatar_view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Intent intent=new Intent();
//                    intent.setClass(context,UserBaseInfoOtherView.class);
//
//                    startActivity(intent);
//                }
//            });
        }
    }

    private class MentionMeAdapter extends ArrayAdapter<MentionMe> {
        public MentionMeAdapter(Context context, int layoutId, List<MentionMe> mentionMes){
            super(context,layoutId,mentionMes);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = myInflater.inflate(R.layout.layout_user_attention_item_people
                        , null);
                viewHolder = new ViewHolder();
                viewHolder.avatar_view = (ImageView)convertView.findViewById(R.id.photoView);
                viewHolder.mentioner_view = (TextView)convertView.findViewById(R.id.nameView);
                viewHolder.post_view = (TextView)convertView.findViewById(R.id.signView);
                //viewHolder.time_view = (TextView)convertView.findViewById(R.id.timeView);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }
            Reply reply = getItem(position);
            viewHolder.avatar_view.setImageResource(R.drawable.photo);
            viewHolder.mentioner_view.setText(reply.getReplyer());
            viewHolder.post_view.setText(reply.getPost());
            viewHolder.time_view.setText(reply.getAdjustTime());
            viewHolder.avatar_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

            return convertView;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_mention_me, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        //MessageBLService.unreadMentionNum = 0;
        //UserAttentionActivity.this.finish();

    }

    /**
     * Created by kisstheraik on 15/9/30.
     */


}
