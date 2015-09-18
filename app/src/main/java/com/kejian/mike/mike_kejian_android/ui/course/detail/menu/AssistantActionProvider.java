package com.kejian.mike.mike_kejian_android.ui.course.detail.menu;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.ActionProvider;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;

import com.kejian.mike.mike_kejian_android.R;

/**
 * Created by violetMoon on 2015/9/18.
 */
public class AssistantActionProvider extends ActionProvider{

    private Context context;
    private View view;

    public AssistantActionProvider(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View onCreateActionView() {
        if(view == null) {
            view = new ImageView(context);
            view.setBackgroundResource(R.drawable.add);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
        return view;
    }
}
