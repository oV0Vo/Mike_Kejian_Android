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
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;

import java.util.ArrayList;
import java.util.List;

import bl.UserInfoService;
import model.course.data.CourseBriefInfo;
import model.course.CourseModel;


public class CourseListFragment extends Fragment implements AbsListView.OnItemClickListener{

    private AbsListView listView;

    private CourseAdapter myCourseAdapter;

    private CourseAdapter allCourseAdapter;

    private OnCourseSelectedListener listner;

    private CourseModel courseModel;

    private boolean showMyCourse;

    private static final int MY_COURSE_FETCH_NUM = 50;
    private static final int ALL_COURSE_FETCH_NUM = 50;

    @Override
    public void onCreate(Bundle savedInstanceState) {Log.e("CourseListFg", "onCreate");
        super.onCreate(savedInstanceState);
        courseModel = CourseModel.getInstance();

        initMyCourseAdapter();
        initAllCourseAdapter();
        showMyCourse = true;
    }

    private void initMyCourseAdapter() {
        ArrayList<CourseBriefInfo> myCourseBriefs = courseModel.getMyCourseBriefs();
        if(myCourseBriefs.size() == 0)
            new UpdateMyCourseBriefTask().execute();
        myCourseAdapter = new CourseAdapter(getActivity(), android.R.layout.simple_list_item_1,
                myCourseBriefs);
    }

    private void initAllCourseAdapter() {
        ArrayList<CourseBriefInfo> allCourseBriefs =courseModel.getAllCourseBriefs();
        if(allCourseBriefs.size() == 0)
            new UpdateAllCourseBriefTask().execute();
        allCourseAdapter = new CourseAdapter(getActivity(), android.R.layout.simple_list_item_1,
                allCourseBriefs);
    }

    public void showMyCourse() {
        if(listView == null)
            return;
        if(!showMyCourse) {
            showMyCourse = true;
            listView.setAdapter(myCourseAdapter);
            //setEmptyText();
        }
    }

    public void showAllCourse() {
        if(listView == null)
            return;

        if(showMyCourse) {
            showMyCourse = false;
            listView.setAdapter(allCourseAdapter);
            //setEmptyText();
        }
    }

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
        //setEmptyText();

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

    @Override
    public void onDetach() {
        super.onDetach();
        listner = null;
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

    private class UpdateMyCourseBriefTask extends AsyncTask<Void, Integer,Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            CourseModel courseModel = CourseModel.getInstance();
            ArrayList<CourseBriefInfo> updateInfos = courseModel.updateMyCourseBriefs();
            if(updateInfos != null)
                return true;
            else
                return false;
        }

        @Override
        protected void onPostExecute(Boolean updateSuccess) {
            if(updateSuccess) {
                myCourseAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getActivity(), R.string.net_disconnet, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class UpdateAllCourseBriefTask extends AsyncTask<Void, Integer,Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            CourseModel courseModel = CourseModel.getInstance();
            ArrayList<CourseBriefInfo> updateInfos = courseModel.updateAllCourseBriefs();
            if(updateInfos != null)
                return true;
            else
                return false;
        }

        @Override
        protected void onPostExecute(Boolean updateSuccess) {
            if(updateSuccess) {
                allCourseAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getActivity(), R.string.net_disconnet, Toast.LENGTH_LONG).show();
            }
        }
    }
    public interface OnCourseSelectedListener {
        public void onCourseSelected();
    }

}
