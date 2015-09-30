package com.kejian.mike.mike_kejian_android.ui.course;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
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

import com.kejian.mike.mike_kejian_android.R;

import java.util.ArrayList;

import bl.AcademyBLService;
import bl.UserInfoService;
import model.course.CourseModel;
import model.course.data.CourseType;

/**
 *
 */
public class CourseListContainerFragment extends Fragment {

    private RadioButton myCourseButton;
    private RadioButton allCourseButton;
    private RadioButton currentButton;

    private LinearLayout allCourseSelectLayout;

    private TextView academySelectText;
    private ViewGroup academySelectLayout;
    private PopupMenu academySelectMenu;

    private TextView courseTypeSelectText;
    private ViewGroup courseTypeSelectLayout;
    private PopupMenu courseTypeSelectMenu;

    private CourseListFragment courseListFg;

    private boolean isShowMyCourse;
    private boolean isSelectAcademy;
    private CharSequence selectText;

    public CourseListContainerFragment() {
        // Required empty public constructor
    }

    private void createListFragment() {
        FragmentManager fm = getChildFragmentManager();
        CourseListFragment fg= (CourseListFragment)fm.findFragmentById(R.id.main_course_course_list);
        if(fg == null) {
            String studentId = UserInfoService.getInstance().getSid();
            fg = new CourseListFragment();
            fm.beginTransaction().add(R.id.main_course_course_list, fg).commit();
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
        View v = inflater.inflate(R.layout.fragment_course_list_container, container, false);
        allCourseButton = (RadioButton)v.findViewById(R.id.main_course_all_course_button);
        myCourseButton = (RadioButton)v.findViewById(R.id.main_course_my_course_button);
        initCourseButtonListner();
        initAllCourseSelectLayout(v);
        myCourseButton.setChecked(true);
        isShowMyCourse = true;
        return v;
    }

    private void initAllCourseSelectLayout(View contentView) {
        allCourseSelectLayout = (LinearLayout)contentView.findViewById(R.id.main_course_all_course_select_layout);
        initAcademySelectView(allCourseSelectLayout);
        initCourseTypeSelectView(allCourseSelectLayout);
        allCourseSelectLayout.setVisibility(View.GONE);
    }

    private void initAcademySelectView(View contentView) {
        academySelectText = (TextView)contentView.findViewById(R.id.main_course_academy_select_text);
        academySelectLayout = (ViewGroup)contentView.findViewById(R.id.course_select_academy_layout);

        AcademyBLService academyBL = AcademyBLService.getInstance();
        ArrayList<String> allNames = academyBL.getAllAcademyNamesMock();
        academySelectMenu = new PopupMenu(this.getActivity(), academySelectText);
        Menu menu = academySelectMenu.getMenu();
        for(String academyName: allNames) {
            menu.add(academyName).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    CharSequence academyName = item.getTitle();
                    academySelectText.setText(academyName);
                    showAcademyCourseList(academyName);
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

    private void showAcademyCourseList(CharSequence academyNameList) {
        courseListFg.showAcademyCourseList(academyNameList);
    }

    private void showCourseTypeList(CharSequence courseType) {
        courseListFg.showCourseTypeList(CourseType.valueOf(courseType.toString()));
    }

    private void initCourseTypeSelectView(View contentView) {
        courseTypeSelectText = (TextView)contentView.findViewById(R.id.main_course_course_type_select_text);
        courseTypeSelectText.setText(R.string.main_course_select_all_course);
        courseTypeSelectLayout = (ViewGroup)contentView.findViewById(R.id.course_select_type_layout);

        CourseModel courseModel = CourseModel.getInstance();
        ArrayList<String> allNames = courseModel.getAllCourseTypeNamesMock();
        courseTypeSelectMenu = new PopupMenu(this.getActivity(), courseTypeSelectText);
        Menu menu = courseTypeSelectMenu.getMenu();
        for(String courseTypeName: allNames) {
            menu.add(courseTypeName).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    CharSequence courseTypeName = item.getTitle();
                    courseTypeSelectText.setText(courseTypeName);
                    showCourseTypeList(courseTypeName);
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

    private void initCourseButtonListner() {

        CompoundButton.OnCheckedChangeListener checkChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if(currentButton != null)
                        currentButton.setChecked(false);
                    currentButton = (RadioButton)buttonView;
                    buttonView.setTextColor(getResources().getColor(R.color.dark_blue));
                    //buttonView.setBackgroundResource(R.drawable.green_bottom_border);
                } else {
                   // buttonView.setBackgroundDrawable(null);
                    buttonView.setTextColor(getResources().getColor(R.color.black));
                }
            }
        };
        allCourseButton.setOnCheckedChangeListener(checkChangeListener);
        myCourseButton.setOnCheckedChangeListener(checkChangeListener);
        myCourseButton.setChecked(true);
//
        allCourseButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(isShowMyCourse) {
                    isShowMyCourse = false;
                    Log.e("CourseList", "" + Boolean.toString(isShowMyCourse));
                    allCourseSelectLayout.setVisibility(View.VISIBLE);
                    /*if(isSelectAcademy) {
                        courseListFg.showAcademyCourseList(selectText);
                    } else {
                        courseListFg.showCourseTypeList(CourseType.valueOf(selectText.toString()));
                    }*/
                }
            }
        });

        myCourseButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(!isShowMyCourse) {
                    isShowMyCourse = true;
                    allCourseSelectLayout.setVisibility(View.GONE);
                    courseListFg.showMyCourse();
                }
            }
        });
    }




}
