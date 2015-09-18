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
public class TeacherActionProvider extends ActionProvider{

    private Context context;
    private PopupWindow subMenu;
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
        View subMenuView = createTeacherSubMenu();
        subMenu = new PopupWindow(subMenuView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        subMenu.setTouchable(true);
        subMenu.setBackgroundDrawable(new BitmapDrawable());
    }


    private View createTeacherSubMenu() {
        View teacherSubMenu = View.inflate(context, R.layout.layout_submenu_course_teacher, null);

        ViewGroup postPublishView = (ViewGroup)teacherSubMenu.findViewById(R.id.course_submenu_teacher_publish_post);
        postPublishView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                OnTeacherMenuSelectListener listener = getListener();
                if(listener != null)
                    listener.onTeacherPublishPost();
            }
        });

        ViewGroup namingView = (ViewGroup)teacherSubMenu.findViewById(R.id.course_submenu_teacher_naming);
        namingView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                OnTeacherMenuSelectListener listener = getListener();
                if(listener != null)
                    listener.onCourseNaming();
            }
        });

        ViewGroup annoucPublishView = (ViewGroup)teacherSubMenu.findViewById(R.id.course_submenu_teacher_publish_annouc);
        annoucPublishView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                OnTeacherMenuSelectListener listener = getListener();
                if(listener != null)
                    listener.onTeacherPublishAnnouc();
            }
        });

        ViewGroup questionView = (ViewGroup)teacherSubMenu.findViewById(R.id.course_submenu_teacher_question);
        questionView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                OnTeacherMenuSelectListener listener = getListener();
                if(listener != null)
                    listener.onCourseQuestion();
            }
        });

        return teacherSubMenu;
    }

    private OnTeacherMenuSelectListener getListener() {
        if(context == null) {
            return null;
        }

        OnTeacherMenuSelectListener listener = null;
        try {
            listener = (OnTeacherMenuSelectListener) context;
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
        return listener;
    }

    public static interface OnTeacherMenuSelectListener {
        public void onTeacherPublishPost();
        public void onTeacherPublishAnnouc();
        public void onCourseNaming();
        public void onCourseQuestion();
    }
}
