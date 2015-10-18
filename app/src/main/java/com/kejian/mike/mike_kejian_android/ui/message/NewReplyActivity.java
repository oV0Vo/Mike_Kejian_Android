package com.kejian.mike.mike_kejian_android.ui.message;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
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

import net.picture.DownloadPicture;

import java.util.List;

import bl.MessageBLService;
import model.message.Reply;

public class NewReplyActivity extends AppCompatActivity implements View.OnClickListener ,OnRefreshListener{

    private LayoutInflater myInflater;
    private ArrayAdapter<Reply> adapter;
    private LinearLayout mainLayout;
    private RefreshListView container;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reply);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.mainLayout = (LinearLayout)findViewById(R.id.new_reply);
        this.mainLayout.setVisibility(View.GONE);

        this.progressBar = (ProgressBar)findViewById(R.id.new_reply_progress_bar);

        initData();
//        initViews();
    }
    private void initData(){
//        this.replies = MessageBLService.getReplyList();
//        this.replyNum = this.replies.size();
        new InitDataTask().execute("123");

    }

    @Override
    public void onDownPullRefresh() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                MessageBLService.refreshReplies("123");
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                adapter.notifyDataSetChanged();
                container.hideHeaderView();
                refreshReplyNumView();
            }
        }.execute(new Void[]{});
    }

    @Override
    public void onLoadingMore() {
        if(MessageBLService.totalReply > MessageBLService.replies.size()){
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    MessageBLService.addReplies("12343");
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
            MessageBLService.refreshTotalReplyNum(userId);
            MessageBLService.initReplies(userId);
            return "";
        }

        @Override
        public void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            initViews();
            mainLayout.setVisibility(View.VISIBLE);
        }
    }
    private void initViews(){
//        ImageView iv = (ImageView)this.layout_title.findViewById(R.id.image_title);
//        iv.setImageResource(R.drawable.left);
//        iv.setOnClickListener(this);
//        TextView tv = (TextView)this.layout_title.findViewById(R.id.txt_title);
//        tv.setText("新的回复");
        this.container = (RefreshListView)findViewById(R.id.reply_container);
        this.myInflater = getLayoutInflater();
        this.refreshReplyNumView();
//        for(int i = 0;i<this.replyNum;i++){
//            this.container.addView(this.genReplyLayout(this.replies.get(i)));
//            this.container.addView(this.genLineSplitView());

//        }
        this.adapter = new NewReplyAdapter(this, android.R.layout.simple_list_item_1, MessageBLService.replies);
        this.container.setAdapter(this.adapter);
        this.container.setOnRefreshListener(this);
    }
    private void refreshReplyNumView(){
        TextView replyNumText = (TextView)this.findViewById(R.id.reply_num);
        replyNumText.setText("共 "+MessageBLService.totalReply+ " 条");
    }
    static class ViewHolder{
        ImageView avatar_view;
        TextView replyer_view;
        TextView post_view;
        TextView time_view;
    }

    private class NewReplyAdapter extends ArrayAdapter<Reply> {
        public NewReplyAdapter(Context context, int layoutId, List<Reply> replies){
            super(context,layoutId,replies);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = myInflater.inflate(R.layout.layout_new_reply
                        , null);
                viewHolder = new ViewHolder();
                viewHolder.avatar_view = (ImageView)convertView.findViewById(R.id.avatar_view);
                viewHolder.replyer_view = (TextView)convertView.findViewById(R.id.replyer_view);
                viewHolder.post_view = (TextView)convertView.findViewById(R.id.post_view);
                viewHolder.time_view = (TextView)convertView.findViewById(R.id.time_view);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }
            Reply reply = getItem(position);
            DownloadPicture d=new DownloadPicture(getContext()){

                @Override
                public void updateView(Bitmap bitmap) {

                    viewHolder.avatar_view.setImageBitmap(bitmap);

                }
            };

            d.getBitMapFromNet(reply.getIconUrl(), "");
//            viewHolder.avatar_view.setImageResource(R.drawable.xiaoxin);
            viewHolder.replyer_view.setText(reply.getReplyer());
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
//    private LinearLayout genReplyLayout(Reply reply){
//        int dp15 = DensityUtil.dip2px(this,15);
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
//        layout_firstLine.setMargins(0,dp10,0,0);
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
//        reply_view.setText("回复了你的帖子");
//        reply_view.setLayoutParams(layout_reply);
//
//        TextView post_view = new TextView(this);
//        LinearLayout.LayoutParams layout_post = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layout_post.setMargins(0, dp5, 0, 0);
//        post_view.setText(reply.getPost());
//        post_view.setTextColor(getResources().getColor(R.color.green));
//        post_view.setLayoutParams(layout_post);
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
//        firstLineStr.addView(replyer_view);
//        firstLineStr.addView(reply_view);
//
//        text.addView(firstLineStr);
//        text.addView(post_view);
//        text.addView(lastLineStr);
//
//        replyLayout.addView(imageView);
//        replyLayout.addView(text);
//        return replyLayout;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_reply, menu);
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
            MessageBLService.unreadReplyNum = 0;
            NewReplyActivity.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        MessageBLService.unreadReplyNum = 0;
        NewReplyActivity.this.finish();

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            MessageBLService.unreadReplyNum = 0;
            NewReplyActivity.this.finish();
        }

        return false;

    }
}
