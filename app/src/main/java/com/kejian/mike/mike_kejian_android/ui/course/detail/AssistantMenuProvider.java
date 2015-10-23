package com.kejian.mike.mike_kejian_android.ui.course.detail;

import android.content.Context;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by violetMoon on 2015/10/23.
 */
public class AssistantMenuProvider extends ActionProvider{

    private Context context;

    /**
     * Creates a new instance. ActionProvider classes should always implement a
     * constructor that takes a single Context parameter for inflating from menu XML.
     *
     * @param context Context for accessing resources.
     */
    public AssistantMenuProvider(Context context) {
        super(context);
    }

    @Override
    public View onCreateActionView() {
       // View v = LayoutInflater.from(context).inflate(R.layout.)
        return null;
    }
}
