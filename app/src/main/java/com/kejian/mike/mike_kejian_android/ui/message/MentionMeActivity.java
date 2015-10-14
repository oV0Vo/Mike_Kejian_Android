package com.kejian.mike.mike_kejian_android.ui.message;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

import java.util.List;

import bl.MessageBLService;
import model.message.MentionMe;
import model.message.Reply;

public class MentionMeActivity extends AppCompatActivity implements View.OnClickListener,OnRefreshListener{
//    private View layout_title;
//    private ArrayList<MentionMe> mentionMes = new ArrayList<MentionMe>();
    private LinearLayout mainLayout;
    private RefreshListView container;
    private LayoutInflater myInflater;
    private ProgressBar progressBar;
    private ArrayAdapter<MentionMe> adapter;
//    private int mention_num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mention_me);
//        this.layout_title = findViewById(R.id.layout_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.mainLayout = (LinearLayout)findViewById(R.id.mention_me);
        this.mainLayout.setVisibility(View.GONE);

        this.progressBar = (ProgressBar)findViewById(R.id.mention_me_progress_bar);

        initData();
//        initViews();
    }
    private void initData(){
//        this.mentionMes = MessageBLService.getMentionMeList();
//        this.mention_num = this.mentionMes.size();
        new InitDataTask().execute("1234");
    }
    private void initViews(){
//        ImageView iv = (ImageView)this.layout_title.findViewById(R.id.image_title);
//        iv.setImageResource(R.drawable.left);
//        iv.setOnClickListener(this);
//        TextView tv = (TextView)this.layout_title.findViewById(R.id.txt_title);
//        tv.setText("提到我的");
        this.container = (RefreshListView)findViewById(R.id.mention_container);
        this.myInflater = getLayoutInflater();
        this.refreshMentionMeNumView();
//        for(int i = 0;i<this.mention_num;i++){
//            this.container.addView(this.genMentionLayout(this.mentionMes.get(i)));
//            this.container.addView(this.genLineSplitView());
//        }
        this.adapter = new MentionMeAdapter(this,android.R.layout.simple_list_item_1,MessageBLService.mentionMes);
        this.container.setAdapter(adapter);
        this.container.setOnRefreshListener(this);

    }
    private void refreshMentionMeNumView(){
        TextView mention_num_view = (TextView)findViewById(R.id.mention_num);
        mention_num_view.setText("共 " + MessageBLService.totalMentionMe + " 条");
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
                refreshMentionMeNumView();
            }
        }.execute(new Void[]{});

    }

    @Override
    public void onLoadingMore() {
        if(MessageBLService.totalMentionMe > MessageBLService.mentionMes.size()){
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
        }else{
            container.hideFooterView();
        }

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
    static class ViewHolder{
        ImageView avatar_view;
        TextView mentioner_view;
        TextView post_view;
        TextView time_view;
    }

    private class MentionMeAdapter extends ArrayAdapter<MentionMe> {
        public MentionMeAdapter(Context context, int layoutId, List<MentionMe> mentionMes){
            super(context,layoutId,mentionMes);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = myInflater.inflate(R.layout.layout_mention_me
                        , null);
                viewHolder = new ViewHolder();
                viewHolder.avatar_view = (ImageView)convertView.findViewById(R.id.avatar_view);
                viewHolder.mentioner_view = (TextView)convertView.findViewById(R.id.mentioner_view);
                viewHolder.post_view = (TextView)convertView.findViewById(R.id.post_view);
                viewHolder.time_view = (TextView)convertView.findViewById(R.id.time_view);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }
            Reply reply = getItem(position);
            viewHolder.avatar_view.setImageResource(R.drawable.xiaoxin);
            viewHolder.mentioner_view.setText(reply.getReplyer());
            viewHolder.post_view.setText(reply.getPost());
            viewHolder.time_view.setText(reply.getAdjustTime());
            return convertView;
        }
    }
//    private View genLineSplitView(){
//        View lineView = new View(this);
//        LinearLayout.LayoutParams layout_line = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,2);
//        lineView.setBackgroundResource(R.color.black2);
//        lineView.setLayoutParams(layout_line);
//        return lineView;
//    }
//
//    private LinearLayout genMentionLayout(Reply reply){
//        int dp15 = DensityUtil.dip2px(this, 15);
//        int dp5 = DensityUtil.dip2px(this,5);
//        int dp40 = DensityUtil.dip2px(this,40);
//        int dp10 = DensityUtil.dip2px(this,10);
//
//        LinearLayout replyLayout = new LinearLayout(this);
//        replyLayout.setOrientation(LinearLayout.HORIZONTAL);
//        replyLayout.setBackgroundResource(R.drawable.setting_item_selector);
//        LinearLayout.LayoutParams layout_linear = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        replyLayout.setLayoutParams(layout_linear);
//
//        ImageView imageView = new ImageView(this);
//        LinearLayout.LayoutParams layout_image = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layout_image.gravity = Gravity.CENTER;
//        layout_image.setMargins(dp15, dp10, 0, dp10);
//        imageView.setImageResource(R.drawable.mail);
//        imageView.setMaxHeight(dp40);
//        imageView.setMaxWidth(dp40);
//        imageView.setAdjustViewBounds(true);
//        imageView.setLayoutParams(layout_image);
//
//        LinearLayout text = new LinearLayout(this);
//        LinearLayout.LayoutParams layout_text = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layout_text.gravity = Gravity.RIGHT;
//        layout_text.setMargins(dp15, 0, 0, dp10);
//        text.setOrientation(LinearLayout.VERTICAL);
//        text.setLayoutParams(layout_text);
//
//        LinearLayout firstLineStr = new LinearLayout(this);
//        LinearLayout.LayoutParams layout_firstLine = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        firstLineStr.setOrientation(LinearLayout.HORIZONTAL);
//        layout_firstLine.setMargins(0, dp10, 0, 0);
//        firstLineStr.setLayoutParams(layout_firstLine);
//
//        TextView replyer_view = new TextView(this);
//        LinearLayout.LayoutParams layout_replyer = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        replyer_view.setText(reply.getReplyer());
//        replyer_view.setTextColor(getResources().getColor(R.color.green));
//        replyer_view.setLayoutParams(layout_replyer);
//
//        TextView reply_view = new TextView(this);
//        LinearLayout.LayoutParams layout_reply = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layout_reply.setMargins(dp10, 0, 0, 0);
//        reply_view.setText("在帖子:");
//        reply_view.setLayoutParams(layout_reply);
//
//        LinearLayout secondLineStr = new LinearLayout(this);
//        LinearLayout.LayoutParams layout_secondLine = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layout_secondLine.setMargins(0, dp10, 0, 0);
//        secondLineStr.setOrientation(LinearLayout.HORIZONTAL);
//        secondLineStr.setLayoutParams(layout_secondLine);
//
//        TextView postContent = new TextView(this);
//        LinearLayout.LayoutParams layout_post = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        postContent.setText(reply.getPost());
//        postContent.setTextColor(getResources().getColor(R.color.green));
//        postContent.setLayoutParams(layout_post);
//
//        TextView toYourPost = new TextView(this);
//        LinearLayout.LayoutParams layout_toPost = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layout_toPost.setMargins(dp10,0,0,0);
//        toYourPost.setText("中@了你");
//        toYourPost.setLayoutParams(layout_toPost);
//
//
//        LinearLayout lastLineStr = new LinearLayout(this);
//        LinearLayout.LayoutParams layout_lastLine = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layout_lastLine.setMargins(0,dp5,0,0);
//        lastLineStr.setLayoutParams(layout_lastLine);
//
//        TextView time_view = new TextView(this);
//        LinearLayout.LayoutParams layout_time = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layout_time.gravity = Gravity.LEFT;
//        time_view.setText(reply.getReplyTime());
//        time_view.setLayoutParams(layout_time);
//
//        TextView arrow_view = new TextView(this);
//        LinearLayout.LayoutParams layout_arrow = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layout_arrow.gravity = Gravity.RIGHT;
//        layout_arrow.setMargins(0,0,dp15,0);
//        arrow_view.setText(">");
//        arrow_view.setTextColor(getResources().getColor(R.color.green));
//        arrow_view.setLayoutParams(layout_arrow);
//        arrow_view.setGravity(Gravity.RIGHT);
//
//        lastLineStr.addView(time_view);
//        lastLineStr.addView(arrow_view);
//
//        secondLineStr.addView(postContent);
//        secondLineStr.addView(toYourPost);
//
//        firstLineStr.addView(replyer_view);
//        firstLineStr.addView(reply_view);
//
//        text.addView(firstLineStr);
//        text.addView(secondLineStr);
//        text.addView(lastLineStr);
//
//        replyLayout.addView(imageView);
//        replyLayout.addView(text);
//        return replyLayout;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mention_me, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == android.R.id.home){
            MessageBLService.unreadMentionNum = 0;
            MentionMeActivity.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        MessageBLService.unreadMentionNum = 0;
        MentionMeActivity.this.finish();

    }
}
