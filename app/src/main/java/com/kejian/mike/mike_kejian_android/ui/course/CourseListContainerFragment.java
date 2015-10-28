package com.kejian.mike.mike_kejian_android.ui.course;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;

import java.util.ArrayList;

import model.course.CourseModel;

/**
 *
 */
public class CourseListContainerFragment extends Fragment {

    private View contentView;

    private RadioButton myCourseButton;
    private RadioButton allCourseButton;

    private LinearLayout allCourseSelectLayout;

    private TextView academySelectText;
    private ViewGroup academySelectLayout;

    private TextView courseTypeSelectText;
    private ViewGroup courseTypeSelectLayout;

    private CourseListFragment courseListFg;

    public CourseListContainerFragment() {

    }

    private void createListFragment() {
        FragmentManager fm = getChildFragmentManager();
        CourseListFragment fg= (CourseListFragment)fm.findFragmentById(R.id.main_course_list_container);
        if(fg == null) {
            fg = new CourseListFragment();
            fm.beginTransaction().add(R.id.main_course_list_container, fg).commit();
        }
        courseListFg = fg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_course_list_container, container, false);
        allCourseButton = (RadioButton)contentView.findViewById(R.id.main_course_all_course_button);
        myCourseButton = (RadioButton)contentView.findViewById(R.id.main_course_my_course_button);
        initCourseButtonListener();
        myCourseButton.setChecked(true);
        CourseModel.getInstance();
        initAllCourseSelectLayout(contentView);
        myCourseButton.setChecked(true);
        return contentView;
    }

    private void initAllCourseSelectLayout(View contentView) {
        allCourseSelectLayout = (LinearLayout)contentView.findViewById(R.id.main_course_all_course_select_layout);
        initAcademySelectView(allCourseSelectLayout);
        initCourseTypeSelectLayout(allCourseSelectLayout);
    }

    private void initAcademySelectView(View contentView) {
        if(getContext() == null)
            return;

        CourseModel courseModel = CourseModel.getInstance();
        ArrayList<String> allAcademyNames = courseModel.getAllAcademyNames();
        if(allAcademyNames == null) {
            new InitAllAcademyNamesTask().execute();
            return;
        }

        ArrayList<String> allNames = new ArrayList<String>(allAcademyNames.size() + 1);
        allNames.add(getContext().getResources().getString(R.string.
                main_course_select_all_academy));
        allNames.addAll(allAcademyNames);

        academySelectText = (TextView)contentView.findViewById(R.id.main_course_academy_select_text);
        academySelectLayout = (ViewGroup)contentView.findViewById(R.id.course_select_academy_layout);
        final PopupMenu academySelectMenu = new PopupMenu(this.getActivity(), academySelectText);
        Menu menu = academySelectMenu.getMenu();
        for(String academyName: allNames) {
            menu.add(academyName).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    CharSequence academyName = item.getTitle();
                    academySelectText.setText(academyName);
                    showCourseByCondition();
                    academySelectMenu.dismiss();
                    return true;
                }
            });
        }

        academySelectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                academySelectMenu.show();
            }
        });

        MenuInflater menuInflater = academySelectMenu.getMenuInflater();
        menuInflater.inflate(R.menu.menu_empty, menu);
    }

    private void showCourseByCondition() {
        String courseType = courseTypeSelectText.getText().toString();
        String academyName = academySelectText.getText().toString();
        courseListFg.showCourseByCondition(academyName, courseType);
    }

    private void initCourseTypeSelectLayout(View contentView) {
        if(getContext() == null)
            return;

        CourseModel courseModel = CourseModel.getInstance();
        ArrayList<String> allCourseTypes = courseModel.getAllCourseTypes();
        if(allCourseTypes == null) {
            new InitAllCourseTypeTask().execute();
            return;
        }

        ArrayList<String> allNames = new ArrayList<String>(allCourseTypes.size() + 1);
        allNames.add(getContext().getResources().getString(R.string.
                main_course_select_all_course));
        allNames.addAll(allCourseTypes);

        courseTypeSelectText = (TextView)contentView.findViewById(R.id.main_course_course_type_select_text);
        courseTypeSelectLayout = (ViewGroup)contentView.findViewById(R.id.course_select_type_layout);
        final PopupMenu courseTypeSelectMenu = new PopupMenu(this.getActivity(), courseTypeSelectText);
        Menu menu = courseTypeSelectMenu.getMenu();
        for(String courseTypeName: allNames) {
            menu.add(courseTypeName).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    CharSequence courseTypeName = item.getTitle();
                    courseTypeSelectText.setText(courseTypeName);
                    showCourseByCondition();
                    courseTypeSelectMenu.dismiss();
                    return true;
                }
            });
        }

        courseTypeSelectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseTypeSelectMenu.show();
            }
        });

        MenuInflater menuInflater = courseTypeSelectMenu.getMenuInflater();
        menuInflater.inflate(R.menu.menu_empty, menu);
    }

    private void initCourseButtonListener() {
        myCourseButton.setChecked(true);

        allCourseButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    allCourseSelectLayout.setVisibility(View.VISIBLE);
                    getActivity().setTitle(R.string.all_course_title);
                    academySelectText.setText(R.string.main_course_select_all_academy);
                    courseTypeSelectText.setText(R.string.main_course_select_all_course);
                    courseListFg.showAllCourse();
                }
            }
        });

        myCourseButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    allCourseSelectLayout.setVisibility(View.GONE);
                    getActivity().setTitle(R.string.my_course_title);
                    courseListFg.showMyCourse();
                }
            }
        });
    }

    private class InitAllAcademyNamesTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean initSuccess = CourseModel.getInstance().initAllAcademyInfos();
            return initSuccess;
        }

        @Override
        protected  void onPostExecute(Boolean initSuccess) {
            if(getContext() == null)
                return;
            if(initSuccess) {
                initCourseTypeSelectLayout(contentView);
            } else {
                Toast.makeText(getContext(), R.string.net_disconnet, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class InitAllCourseTypeTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean initSuccess = CourseModel.getInstance().initAllCourseTypes();
            return initSuccess;
        }

        @Override
        protected  void onPostExecute(Boolean initSuccess) {
            if(getContext() == null)
                return;
            if(initSuccess) {
                initAllCourseSelectLayout(contentView);
            } else {
                Toast.makeText(getContext(), R.string.net_disconnet, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
