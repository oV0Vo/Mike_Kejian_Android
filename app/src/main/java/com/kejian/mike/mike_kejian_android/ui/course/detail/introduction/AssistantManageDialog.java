package com.kejian.mike.mike_kejian_android.ui.course.detail.introduction;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.kejian.mike.mike_kejian_android.R;

import java.util.ArrayList;

/**
 * Created by violetMoon on 2015/10/26.
 */
public class AssistantManageDialog extends Dialog {
    public AssistantManageDialog(Context context, ArrayList<String> userIds, ArrayList<String> userNames,
                                 ArrayList<String> userIcons) {
        super(context);
        View frameView = getLayoutInflater().inflate(R.layout.layout_default_dialog, null);
        View contentView = getContentView(context);
        ViewGroup contentContainer = (ViewGroup)frameView.findViewById(R.id.dialog_content_container);
        contentContainer.addView(contentView);
    }

    private View getContentView(Context context) {
        View contentView = getLayoutInflater().inflate(R.layout.layout_user_search_dialog_content,
            null);
        EditText searchText = (EditText)contentView.findViewById(R.id.search_text);
        PopupMenu resultPopup = new PopupMenu(context, searchText);
        searchText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                EditText inputText = (EditText)v;
                String searchText = inputText.getText().toString();
                if(searchText.length() != 0)
                    return true;
                return true;
            }
        });

        return null;
    }
}
