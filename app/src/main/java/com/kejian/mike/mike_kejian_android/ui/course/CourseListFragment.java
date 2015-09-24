package com.kejian.mike.mike_kejian_android.ui.course;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

import java.util.ArrayList;
import java.util.List;

import bl.CourseBLService;
import bl.UserInfoService;
import model.course.CourseBriefInfo;
import model.course.CourseModel;


public class CourseListFragment extends Fragment implements AbsListView.OnItemClickListener{

    private AbsListView listView;

    private int myCourseCurrentPos;
    private ArrayList<CourseBriefInfo> myCourses;
    private CourseAdapter myCourseAdapter;

    private int allCourseCurrentPos;
    private ArrayList<CourseBriefInfo> allCourses;
    private CourseAdapter allCourseAdapter;

    private OnCourseSelectedListener listner;

    private boolean showMyCourse;

    private CourseModel courseModel;

    private CourseBLService courseBL;

    private UserInfoService userInfoBL;

    private static final int MY_COURSE_FETCH_NUM = 50;
    private static final int ALL_COURSE_FETCH_NUM = 50;

    @Override
    public void onCreate(Bundle savedInstanceState) {Log.e("CourseListFg", "onCreate");
        super.onCreate(savedInstanceState);
        courseModel = CourseModel.getInstance();
        courseBL = CourseBLService.getInstance();
        userInfoBL = UserInfoService.getInstance();

        initMyCourse();
        myCourseAdapter = new CourseAdapter(getActivity(), android.R.layout.simple_list_item_1,
                myCourses);

        initAllCourse();
        allCourseAdapter = new CourseAdapter(getActivity(), android.R.layout.simple_expandable_list_item_1,
                allCourses);

        showMyCourse = true;
    }

    private void initMyCourse() {
        myCourseCurrentPos = 0;
        myCourses = new ArrayList<CourseBriefInfo>();
        ArrayList<CourseBriefInfo> initData = courseModel.getMyCourseBriefs(myCourseCurrentPos,
                MY_COURSE_FETCH_NUM);
        if(initData.size() == 0 && courseBL.hasMoreMyCourses(myCourseCurrentPos,
                MY_COURSE_FETCH_NUM)) {
            new GetCourseTask().execute(true);
        } else {
            myCourses.addAll(initData);
        }
    }

    private void initAllCourse() {
        allCourseCurrentPos = 0;
        allCourses = new ArrayList<CourseBriefInfo>();
        ArrayList<CourseBriefInfo> initData = courseModel.getMyCourseBriefs(allCourseCurrentPos,
                MY_COURSE_FETCH_NUM);
        if(initData.size() == 0 && courseBL.hasMoreMyCourses(allCourseCurrentPos,
                ALL_COURSE_FETCH_NUM)) {
            new GetCourseTask().execute(false);
        } else {
            allCourses.addAll(initData);
        }
    }

    public void showMyCourse() {
        if(listView == null)
            return;
        if(!showMyCourse) {
            showMyCourse = true;
            listView.setAdapter(myCourseAdapter);
            setEmptyText();
        }
    }

    public void showAllCourse() {
        if(listView == null)
            return;

        if(showMyCourse) {
            showMyCourse = false;
            listView.setAdapter(allCourseAdapter);
            setEmptyText();
        }
    }
/*
    private void setUpMyCourseAdpater() {
        if(getActivity() == null)
            return;
        myCourseAdapter = new CourseAdapter(getActivity(), android.R.layout.simple_list_item_1, myCourses);
        if(showMyCourse)
            listView.setAdapter(myCourseAdapter);
    }

    private void setUpAllCourseAdapter() {
        if(getActivity() == null)
            return;
        allCourseAdapter = new CourseAdapter(getActivity(), android.R.layout.simple_list_item_1, allCourses);
        if(!showMyCourse)
            listView.setAdapter(allCourseAdapter);
    }*/

    public void showAcademyCourseList(CharSequence academyNameList) {

    }

    public void showCourseTypeList(CharSequence courseType) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {Log.e("CourseListFg", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);

        listView = (AbsListView) view.findViewById(R.id.main_course_list);
        if(showMyCourse)
            ((AdapterView<ListAdapter>) listView).setAdapter(myCourseAdapter);
        else
            ((AdapterView<ListAdapter>) listView).setAdapter(allCourseAdapter);

        listView.setOnItemClickListener(this);
        setEmptyText();

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(listner != null) {
            CourseBriefInfo courseBrief = getCurrentAdapter().getItem(position);
            courseModel.setCurrentCourseBrief(courseBrief);
            listner.onCourseSelected();
        }
    }

    private CourseAdapter getCurrentAdapter() {
        return showMyCourse? myCourseAdapter: allCourseAdapter;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listner = (OnCourseSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnCourseSelectedListener");
        }
    }

    private void setEmptyText() {
        if(listView == null) {
            Log.i("CourseListFragment", "empty on setEmpty Text");
            return;
        }

        String emptyText = null;
        if(showMyCourse)
            emptyText = getResources().getString(R.string.main_course_no_my_course);
        else
            emptyText = getResources().getString(R.string.main_course_no_all_course);

        View emptyView = listView.getEmptyView();
        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    private class CourseAdapter extends ArrayAdapter<CourseBriefInfo> {

        public CourseAdapter(Context context, int layoutId, List<CourseBriefInfo> courses) {
            super(context, layoutId, courses);
        }

        /*
        使用自定义的View替换默认的View
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.layout_main_course_brief
                        , null);
            }

            CourseBriefInfo courseBriefInfo = getItem(position);

            ImageView imageView = (ImageView)convertView.findViewById(R.id.course_brief_image);
            imageView.setImageResource(R.drawable.default_book);
            TextView nameView = (TextView)convertView.findViewById(R.id.course_brief_name);
            nameView.setText(courseBriefInfo.getCourseName());
            TextView academyView = (TextView)convertView.findViewById(R.id.course_brief_academy);
            academyView.setText(courseBriefInfo.getAcademyName());
            TextView processWeekView = (TextView)convertView.findViewById(R.id.course_brief_progress_week);
            processWeekView.setText("第" + courseBriefInfo.getProgressWeek() + "周");

            return convertView;
        }
    }

    private class GetCourseTask extends AsyncTask<Boolean, Integer, ArrayList<CourseBriefInfo>> {

        private boolean isMyCourse;

        @Override
        protected ArrayList<CourseBriefInfo> doInBackground(Boolean... params) {
            String studentId = userInfoBL.getSid();
            isMyCourse = params[0];
            if(isMyCourse)
                return courseBL.getMyCourseBriefs(studentId, myCourseCurrentPos
                        , MY_COURSE_FETCH_NUM);
            else
                return courseBL.getAllCourseBriefs(studentId, allCourseCurrentPos
                        , ALL_COURSE_FETCH_NUM);
        }

        @Override
        protected void onPostExecute(ArrayList<CourseBriefInfo> coursesResult) {
            if(isMyCourse) {
                courseModel.addMyCourseBriefs(coursesResult);
                myCourses.addAll(coursesResult);
            } else {
                courseModel.addAllCourseBriefs(coursesResult);
                allCourses.addAll(coursesResult);
            }
        }

    }

    public interface OnCourseSelectedListener {
        public void onCourseSelected();
    }

}
