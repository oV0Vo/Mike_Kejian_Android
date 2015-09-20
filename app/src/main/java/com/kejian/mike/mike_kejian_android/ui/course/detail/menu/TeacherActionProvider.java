package com.kejian.mike.mike_kejian_android.ui.course.detail.menu;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ActionProvider;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.course.management.AnnoucementPublishActivity;

/**
 * Created by violetMoon on 2015/9/18.
 */
public class TeacherActionProvider extends ActionProvider {

    private Context context;
    private View view;

    public TeacherActionProvider(Context context) {
        super(context);
        this.context = context;
    }
    @Override
    public View onCreateActionView() {
        if(view == null) {
            view = new ImageView(context);
            view.setBackgroundResource(R.drawable.add);
        }
        return view;
    }

}
