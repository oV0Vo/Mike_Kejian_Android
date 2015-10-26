package com.kejian.mike.mike_kejian_android.ui.user;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.campus.HottestPostListFragment;
import com.kejian.mike.mike_kejian_android.ui.campus.PostListContainerFragment;
import com.kejian.mike.mike_kejian_android.ui.message.CourseNoticeActivity;
import com.kejian.mike.mike_kejian_android.ui.message.MentionMeActivity;
import com.kejian.mike.mike_kejian_android.ui.message.OnRefreshListener;
import com.kejian.mike.mike_kejian_android.ui.message.RefreshListView;
import com.kejian.mike.mike_kejian_android.ui.user.adapter.AttentionListAdapter;
import com.kejian.mike.mike_kejian_android.ui.widget.AppManager;

import net.UserNetService;
import net.picture.MessagePrint;

import java.util.ArrayList;

import bl.AcademyBLService;
import model.user.CourseBrief;
import model.user.Friend;
import model.user.Global;
import model.user.PostBrief;
import model.user.UserToken;
import model.user.user;

/**
 * Created by kisstheraik on 15/9/19.
 */
public class UserAttentionListActivity extends AppCompatActivity{

    private TextView people;
    private TextView course;
    private TextView post;
    private FrameLayout container;
    private Context context;
    private LocalActivityManager activityManager;
    private UserToken userToken;
    private Bundle bundle;
    private RefreshListView attentionList;
    private ProgressBar progressBar;
    private AttentionListAdapter peopleAdapter;
    private AttentionListAdapter courseAdapter;
    private AttentionListAdapter postAdapter;
    private ArrayList<Friend> friendlist=new ArrayList<>();
    private ArrayList<CourseBrief> courseList=new ArrayList<>();
    private ArrayList<PostBrief> postList=new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_attention_list);

       // AppManager.getAppManager().addActivity(this);


        context=this;
        bundle=savedInstanceState;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userToken=(UserToken)getIntent().getSerializableExtra(UserActivityComm.USER_TOKEN.name());

        if(userToken==null){

            userToken=new UserToken();

        }


     initViews();
        initData();
        new GetData().execute("");

    }
    public void initData(){

        peopleAdapter=new AttentionListAdapter(1,friendlist,this);
        courseAdapter=new AttentionListAdapter(2,courseList,this);
        postAdapter=new AttentionListAdapter(3,postList,this);
    }

    public void initViews(){
        people=(TextView)findViewById(R.id.attention_list_people);
        course=(TextView)findViewById(R.id.attention_list_course);
        post=(TextView)findViewById(R.id.attention_list_post);
        container=(FrameLayout)findViewById(R.id.attention_container);
        attentionList=(RefreshListView)findViewById(R.id.attention_list);
        progressBar=(ProgressBar)findViewById(R.id.get_attention_progress_bar);

        attentionList.setOnGenericMotionListener(new View.OnGenericMotionListener() {
            @Override
            public boolean onGenericMotion(View v, MotionEvent event) {

               // if(event==MotionEvent.obtain(MotionEvent.EDGE_LEFT))

                return true;
            }
        });


        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  container.removeAllViews();
              //  s(UserAttentionActivity.class);
             //   ListView l = new ListView(context);

                people.setTextColor(Color.WHITE);
                course.setTextColor(Color.BLACK);
                post.setTextColor(Color.BLACK);

                attentionList.setAdapter(peopleAdapter);

                attentionList.setOnRefreshListener(new Refresh(attentionType.PEOPLE.name()));


               // container.addView(l);
               // container.addView(new HottestPostListFragment(context));


            }
        });

        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // container.removeAllViews();

               // s(UserAttentionCourse.class);
            //    ListView l = new ListView(context);

                course.setTextColor(Color.WHITE);
                people.setTextColor(Color.BLACK);
                post.setTextColor(Color.BLACK);

                attentionList.setAdapter(courseAdapter);
                attentionList.setOnRefreshListener(new Refresh(attentionType.COURSE.name()));

            //    container.addView(l);


            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //s(UserAttentionActivity.class);
               // container.removeAllViews();

                post.setTextColor(Color.WHITE);
                course.setTextColor(Color.BLACK);
                people.setTextColor(Color.BLACK);
                //ListView l = new ListView(context);
                attentionList.setAdapter(postAdapter);
                attentionList.setOnRefreshListener(new Refresh(attentionType.POST.name()));
              //  container.addView(l);

            }
        });


    }

    public void s(Class type){

        activityManager = new LocalActivityManager(this, true);
        // Bundle states = savedInstanceState != null? (Bundle) savedInstanceState.getBundle(STATES_KEY) : null;
        activityManager.dispatchCreate(bundle);


        Intent intent = new Intent(context, type);
        Bundle bundle=new Bundle();
        bundle.putSerializable(UserActivityComm.USER_TOKEN.name(),userToken);
        intent.putExtras(bundle);
        intent.setAction("android.settings.SETTINGS");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        View v = activityManager.startActivity(UserAttentionActivity.class.getName(), intent).getDecorView();
        container.removeAllViews();
        container.addView(v);
    }

    private void hideHeader(){

        attentionList.hideHeaderView();
    }

    private void hideFoot(){

        attentionList.hideFooterView();
    }

    private static enum attentionType{PEOPLE,COURSE,POST};
    private class Refresh implements OnRefreshListener{

        private  String type;

        public Refresh(String type){

            this.type=type;

        }



        @Override
        public void onDownPullRefresh() {

            switch(type){



                case "PEOPLE":new GetData().execute("");break;
                case "COURSE":new GetData().execute("");break;
                case "POST":new GetData().execute("");break;
                default:break;



            }

        }

        @Override
        public void onLoadingMore() {

            switch(type){

                case "PEOPLE":new GetData().execute("");break;
                case "COURSE":new GetData().execute("");break;
                case "POST":new GetData().execute("");break;
                default:break;

            }


        }
    }

    public ArrayList<Friend> getFrientList(){

        user u=(user) Global.getObjectByName("user");

        return UserNetService.getAttentionPeople(u.getId());

    }

    private class GetData extends AsyncTask<String,Integer,String>{


        public String doInBackground(String...para){
            user u=(user) Global.getObjectByName("user");

            friendlist=getFrientList();

            courseList=UserNetService.getAttentionCourse(u.getId());

            postList=UserNetService.getAttentionPost(u.getId());


            return null;
        }

        @Override
        public void onPostExecute(String result){

            peopleAdapter.setContentList(friendlist);
            courseAdapter.setContentList(courseList);
            postAdapter.setContentList(postList);



            postAdapter.notifyDataSetChanged();
            courseAdapter.notifyDataSetChanged();
            peopleAdapter.notifyDataSetChanged();
            hideHeader();



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
