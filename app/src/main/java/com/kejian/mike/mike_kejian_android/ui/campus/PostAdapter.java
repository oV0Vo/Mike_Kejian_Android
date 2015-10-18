package com.kejian.mike.mike_kejian_android.ui.campus;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

    private static class ViewHolder {
        ImageView post_user_icon;
        TextView post_title;
        TextView post_date;
        TextView post_content;
        TextView post_praise_num;
        TextView post_comment_num;
    }

    public PostAdapter(Context context, int layoutId, List<Post> posts) {
        super(context, layoutId, posts);
        this.layoutId = layoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null) {
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
            convertView = vi.inflate(layoutId, null);
            viewHolder = new ViewHolder();
            viewHolder.post_user_icon = (ImageView) convertView.findViewById(R.id.post_user_icon);
            viewHolder.post_title = (TextView) convertView.findViewById(R.id.post_title);
            viewHolder.post_date = (TextView) convertView.findViewById(R.id.post_date);
            viewHolder.post_content = (TextView) convertView.findViewById(R.id.post_content);
            viewHolder.post_praise_num = (TextView) convertView.findViewById(R.id.post_praise_num);
            viewHolder.post_comment_num = (TextView) convertView.findViewById(R.id.post_comment_num);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Post post = getItem(position);
        DownloadPicture d=new DownloadPicture(getContext()){

            @Override
            public void updateView(Bitmap bitmap) {
                viewHolder.post_user_icon.setImageBitmap(bitmap);
            }
        };


        d.getBitMapFromNet(post.getUserIconUrl(), "");
        viewHolder.post_title.setText(post.getTitle());
        viewHolder.post_date.setText(post.getDate());
        viewHolder.post_content.setText(post.getContent());
        viewHolder.post_praise_num.setText(Integer.toString(post.getPraise()));
        viewHolder.post_comment_num.setText(Integer.toString(post.getViewNum()));

        return convertView;
    }
}
