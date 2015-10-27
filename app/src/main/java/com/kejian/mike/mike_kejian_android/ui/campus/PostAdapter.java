package com.kejian.mike.mike_kejian_android.ui.campus;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.user.UserBaseInfoOtherView;

import net.UserNetService;
import net.picture.DownloadPicture;

import java.util.List;

import model.campus.Post;
import model.user.user;

/**
 * Created by ShowJoy on 2015/10/17.
 */
public class   PostAdapter extends ArrayAdapter<Post>{
    private int layoutId;

    public static class PostViewHolder {
        ImageView post_user_icon;
        TextView post_title;
        TextView post_date;
        TextView post_content;
        TextView post_praise_num;
        TextView post_comment_num;
        String postId;
        String userId;
    }

    public PostAdapter(Context context, int layoutId, List<Post> posts) {
        super(context, layoutId, posts);
        this.layoutId = layoutId;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final PostViewHolder postViewHolder;
        Post post = getItem(position);
        if(convertView == null) {
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
            convertView = vi.inflate(layoutId, null);
            postViewHolder = new PostViewHolder();
            postViewHolder.postId = post.getPostId();
            postViewHolder.post_user_icon = (ImageView) convertView.findViewById(R.id.post_user_icon);
            postViewHolder.post_title = (TextView) convertView.findViewById(R.id.publish_title);
            postViewHolder.post_date = (TextView) convertView.findViewById(R.id.post_date);
            postViewHolder.post_content = (TextView) convertView.findViewById(R.id.publish_content);
            postViewHolder.post_praise_num = (TextView) convertView.findViewById(R.id.post_praise_num);
            postViewHolder.post_comment_num = (TextView) convertView.findViewById(R.id.post_comment_num);
            postViewHolder.userId = post.getUserId();
            convertView.setTag(postViewHolder);
        } else {
            postViewHolder = (PostViewHolder) convertView.getTag();
        }

        new DownloadPicture(getContext(),postViewHolder.post_user_icon, post.getUserIconUrl(), post.getUserIconUrl());

        /*

        System.out.println("postId: " + post.getPostId() + " url: " + post.getUserIconUrl());
        postViewHolder.post_user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<String, Integer, user>() {

                    @Override
                    public user doInBackground(String... Para) {

                        user u = UserNetService.getUserInfo(Para[0]);


                        return u;

                    }

                    @Override
                    public void onPostExecute(user u) {

                        Intent intent = new Intent();

                        Bundle bundle = new Bundle();

                        bundle.putSerializable("friend", u);

                        intent.putExtras(bundle);

                        intent.setClass(getContext(), UserBaseInfoOtherView.class);
                        getContext().startActivity(intent);

                    }
                }.execute(postViewHolder.userId);
            }
        });
        */


        postViewHolder.post_title.setText(post.getTitle());
        postViewHolder.post_date.setText(post.getDate());
        postViewHolder.post_content.setText(post.getContent());
        postViewHolder.post_praise_num.setText(Integer.toString(post.getPraise()));
        postViewHolder.post_comment_num.setText(Integer.toString(post.getViewNum()));


        return convertView;
    }
}
