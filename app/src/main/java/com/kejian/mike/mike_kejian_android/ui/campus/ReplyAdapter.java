package com.kejian.mike.mike_kejian_android.ui.campus;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;


import net.picture.DownloadPicture;

import java.util.List;

import model.campus.Reply;

/**
 * Created by ShowJoy on 2015/10/17.
 */
public class ReplyAdapter extends ArrayAdapter<Reply>{
    private int layoutId;

    public static class ReplyViewHolder {
        ImageView reply_user_icon;
        TextView reply_content;
        TextView reply_author_name;
        TextView reply_date;
        TextView reply_view_num;
        TextView reply_comment_num;
        String postId;
    }

    public ReplyAdapter(Context context, int layoutId, List<Reply> replys) {
        super(context, layoutId, replys);
        this.layoutId = layoutId;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ReplyViewHolder replyViewHolder;
        if(convertView == null) {
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
            convertView = vi.inflate(layoutId, null);
            replyViewHolder = new ReplyViewHolder();
            replyViewHolder.reply_user_icon = (ImageView) convertView.findViewById(R.id.reply_user_icon);
            replyViewHolder.reply_author_name = (TextView) convertView.findViewById(R.id.reply_author_name);
            replyViewHolder.reply_date = (TextView) convertView.findViewById(R.id.reply_date);
            replyViewHolder.reply_content = (TextView) convertView.findViewById(R.id.reply_content);
            replyViewHolder.reply_view_num = (TextView) convertView.findViewById(R.id.reply_view_num);
            replyViewHolder.reply_comment_num = (TextView) convertView.findViewById(R.id.reply_comment_num);
            convertView.setTag(replyViewHolder);
        } else {
            replyViewHolder = (ReplyViewHolder) convertView.getTag();
        }

        final Reply reply = getItem(position);
        DownloadPicture d=new DownloadPicture(getContext(),replyViewHolder.reply_user_icon, reply.getUserIconUrl(), reply.getUserIconUrl());


        //d.getBitMapFromNet(reply.getUserIconUrl(), "");
        replyViewHolder.postId = reply.getPostId();
        replyViewHolder.reply_author_name.setText(reply.getAuthorName());
        replyViewHolder.reply_date.setText(reply.getDate());
        replyViewHolder.reply_content.setText(reply.getContent());
        replyViewHolder.reply_view_num.setText(Integer.toString(reply.getViewNum()));
        replyViewHolder.reply_comment_num.setText(Integer.toString(reply.getCommentNum()));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), ReplyDetailActivity.class);
                intent.putExtra("title", reply.getTitle());
                intent.putExtra("activity_title", (position + 2) + "楼");
                intent.putExtra("postId", replyViewHolder.postId);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
