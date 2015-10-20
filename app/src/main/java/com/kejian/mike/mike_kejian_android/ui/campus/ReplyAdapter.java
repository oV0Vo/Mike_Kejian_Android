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
import model.campus.Reply;

/**
 * Created by ShowJoy on 2015/10/17.
 */
public class ReplyAdapter extends ArrayAdapter<Reply>{
    private int layoutId;

    private static class ViewHolder {
        ImageView reply_user_icon;
        TextView reply_content;
        TextView reply_author_name;
        TextView reply_date;
        TextView reply_view_num;
        TextView reply_comment_num;
    }

    public ReplyAdapter(Context context, int layoutId, List<Reply> replys) {
        super(context, layoutId, replys);
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
            viewHolder.reply_user_icon = (ImageView) convertView.findViewById(R.id.reply_user_icon);
            viewHolder.reply_author_name = (TextView) convertView.findViewById(R.id.reply_author_name);
            viewHolder.reply_date = (TextView) convertView.findViewById(R.id.reply_date);
            viewHolder.reply_content = (TextView) convertView.findViewById(R.id.reply_content);
            viewHolder.reply_view_num = (TextView) convertView.findViewById(R.id.reply_view_num);
            viewHolder.reply_comment_num = (TextView) convertView.findViewById(R.id.reply_comment_num);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Reply reply = getItem(position);
        DownloadPicture d=new DownloadPicture(getContext()){

            @Override
            public void updateView(Bitmap bitmap) {
                viewHolder.reply_user_icon.setImageBitmap(bitmap);
            }
        };


        d.getBitMapFromNet(reply.getUserIconUrl(), "");
        viewHolder.reply_author_name.setText(reply.getAuthorName());
        viewHolder.reply_date.setText(reply.getDate());
        viewHolder.reply_content.setText(reply.getContent());
        viewHolder.reply_view_num.setText(Integer.toString(reply.getViewNum()));
        viewHolder.reply_comment_num.setText(Integer.toString(reply.getCommentNum()));

        return convertView;
    }
}
