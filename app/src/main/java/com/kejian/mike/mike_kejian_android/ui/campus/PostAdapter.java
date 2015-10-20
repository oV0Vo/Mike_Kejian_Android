package com.kejian.mike.mike_kejian_android.ui.campus;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

import net.picture.DownloadPicture;

import java.util.List;

import model.campus.Post;

/**
 * Created by ShowJoy on 2015/10/17.
 */
public class PostAdapter extends ArrayAdapter<Post>{
    private int layoutId;

    public static class PostViewHolder {
        ImageButton post_praise_button;
        ImageView post_user_icon;
        TextView post_title;
        TextView post_date;
        TextView post_content;
        TextView post_praise_num;
        TextView post_comment_num;
        String postId;
    }

    public PostAdapter(Context context, int layoutId, List<Post> posts) {
        super(context, layoutId, posts);
        this.layoutId = layoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
            postViewHolder.post_praise_button = (ImageButton) convertView.findViewById(R.id.post_praise_button);
            convertView.setTag(postViewHolder);
        } else {
            postViewHolder = (PostViewHolder) convertView.getTag();
        }


        DownloadPicture d=new DownloadPicture(getContext()){

            @Override
            public void updateView(Bitmap bitmap) {
                postViewHolder.post_user_icon.setImageBitmap(bitmap);
            }
        };


        d.getBitMapFromNet(post.getUserIconUrl(), "");
        postViewHolder.post_praise_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImageButton)v).setBackgroundResource(R.drawable.up_green);
            }
        });
        postViewHolder.post_title.setText(post.getTitle());
        postViewHolder.post_date.setText(post.getDate());
        postViewHolder.post_content.setText(post.getContent());
        postViewHolder.post_praise_num.setText(Integer.toString(post.getPraise()));
        postViewHolder.post_comment_num.setText(Integer.toString(post.getViewNum()));

        return convertView;
    }
}
