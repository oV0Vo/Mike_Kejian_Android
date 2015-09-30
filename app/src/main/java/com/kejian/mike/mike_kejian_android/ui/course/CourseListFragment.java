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
import bl.course.CourseBriefFilter;
import model.course.data.CourseBriefInfo;
import model.course.CourseModel;
import model.course.data.CourseType;


public class CourseListFragment extends Fragment implements AbsListView.OnItemClickListener{

    private AbsListView listView;

    private ArrayList<CourseBriefInfo> listData;

    private CourseAdapter listAdapter;

    private OnCourseSelectedListener listner;

    private CourseModel courseModel;

    private static final int MY_COURSE_FETCH_NUM = 50;
    private static final int ALL_COURSE_FETCH_NUM = 50;

    @Override
    public void onCreate(Bundle savedInstanceState) {Log.e("CourseListFg", "onCreate");
        super.onCreate(savedInstanceState);
        courseModel = CourseModel.getInstance();

        listData = new ArrayList(courseModel.getMyCourseBriefs());
        if(listData.size() == 0)
            new UpdateMyCourseBriefTask().execute();
        listAdapter = new CourseAdapter(getActivity(), android.R.layout.simple_list_item_1,
                listData);
        new UpdateAllCourseBriefTask().execute();
    }

    public void showMyCourse() {
        if (listView == null)
            return;
        listData.clear();
        listData.addAll(courseModel.getMyCourseBriefs());
        listAdapter.notifyDataSetChanged();
    }

    public void showAllCourse() {
        if (listView == null)
            return;
        listData.clear();
        listData.addAll(courseModel.getAllCourseBriefs());
        listAdapter.notifyDataSetChanged();
    }

    //@需要一个progressBar
    public void showAcademyCourseList(CharSequence academyName) {
        ArrayList<CourseBriefInfo> allCourseBriefs = courseModel.getAllCourseBriefs();
        ArrayList<CourseBriefInfo> filterResults = CourseBriefFilter.filterByAcademyName(allCourseBriefs,
                academyName);
        listData.clear();
        listData.addAll(filterResults);
        listAdapter.notifyDataSetChanged();
    }

    public void showCourseTypeList(CourseType courseType) {
        ArrayList<CourseBriefInfo> allCourseBriefs = courseModel.getAllCourseBriefs();
        ArrayList<CourseBriefInfo> filterResults = CourseBriefFilter.filterByCourseType(allCourseBriefs,
                courseType);
        listData.clear();
        listData.addAll(filterResults);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {Log.e("CourseListFg", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);

        listView = (AbsListView) view.findViewById(R.id.main_course_list);
        ((AdapterView<ListAdapter>) listView).setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
        //setEmptyText();

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(listner != null) {
            CourseBriefInfo courseBrief = listAdapter.getItem(position);
            courseModel.setCurrentCourseBrief(courseBrief);
            listner.onCourseSelected();
        }
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
                //@ mock here
                listData.clear();
                listData.addAll(courseModel.getMyCourseBriefs());
                listAdapter.notifyDataSetChanged();
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
        }
    }
    public interface OnCourseSelectedListener {
        public void onCourseSelected();
    }

}
