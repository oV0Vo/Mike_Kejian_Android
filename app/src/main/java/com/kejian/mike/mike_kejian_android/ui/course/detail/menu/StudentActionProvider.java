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
public class StudentActionProvider extends ActionProvider{

    private Context context;
    private PopupWindow subMenu;
    private View view;

    public StudentActionProvider(Context context) {
        super(context);
        this.context = context;
    }
    @Override
    public View onCreateActionView() {
        if(view == null) {
            view = new ImageView(context);
            view.setBackgroundResource(R.drawable.add);
            initSubMenu();
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    subMenu.showAsDropDown(view);
                }
            });
        }
        return view;
    }

    private void initSubMenu() {
        View subMenuView = createStudentSubmenuView();
        subMenu = new PopupWindow(subMenuView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        subMenu.setTouchable(true);
        subMenu.setBackgroundDrawable(new BitmapDrawable());
    }

    private View createStudentSubmenuView() {
        View studentSubMenu = View.inflate(context, R.layout.layout_submenu_course_student, null);

        ViewGroup postPublishView = (ViewGroup)studentSubMenu.findViewById(R.id.course_submenu_student_publish_post);
        postPublishView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                OnStudentMenuSelectListener listener = getListener();
                if(listener != null) {
                    listener.onPublishPost();
                }
            }
        });

        ViewGroup signInView = (ViewGroup)studentSubMenu.findViewById(R.id.course_submenu_student_sign_in);
        signInView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                OnStudentMenuSelectListener listener = getListener();
                if(listener != null) {
                    listener.onCourseSignIn();
                }
            }
        });

        return studentSubMenu;
    }

    private OnStudentMenuSelectListener getListener() {
        if(context == null) {
            return null;
        }

        OnStudentMenuSelectListener listener = null;
        try {
            listener = (OnStudentMenuSelectListener) context;
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
        return listener;
    }

    public static interface OnStudentMenuSelectListener {
        public void onPublishPost();
        public void onCourseSignIn();
    }
}
