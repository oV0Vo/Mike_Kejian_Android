package com.kejian.mike.mike_kejian_android.ui.widget;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

/**
 * Created by violetMoon on 2015/10/9.
 */
public class TextExpandListener implements View.OnClickListener {

    private TextView contentText;
    private TextView actionText;
    private ImageView actionImage;
    private int maxLines;
    private boolean isExpanded;

    public TextExpandListener(TextView contentView, TextView zhankaiText, ImageView imageView, int maxLines) {
        this.contentText = contentView;
        this.actionText = zhankaiText;
        this.actionImage = imageView;
        this.maxLines = maxLines;
        isExpanded = false;
    }

    @Override
    public void onClick(View v) {
        if(isExpanded) {
            if(actionText != null)
                actionText.setText(R.string.zhankai);
            if(actionImage != null)
                actionImage.setImageResource(R.drawable.down);
            if(contentText != null)
                contentText.setMaxLines(maxLines);
        } else {
            if(actionText != null)
                actionText.setText(R.string.shouqi);
            if(actionImage != null)
                actionImage.setImageResource(R.drawable.up1);
            if(contentText != null)
                contentText.setMaxLines(Integer.MAX_VALUE);
        }
        isExpanded ^= true;
    }
}
