package com.kejian.mike.mike_kejian_android.ui.course;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
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
import bl.UserAccountBLService;
import model.course.CourseBriefInfo;
import model.course.CourseModel;


public class CourseListFragment extends Fragment implements AbsListView.OnItemClickListener{
    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView listView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter adapter;

    private OnCourseSelectedListener listner;

    private boolean showMyCourse;

    private static final int BREIF_COURSE_FETCH_NUM = 50;
    private static final int DETAIL_COURSE_FETCH_NUM = 50;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CourseModel courseModel = getCourseModel();
        List<CourseBriefInfo> myCourseBriefs = courseModel.getMyCourseBriefs(0, BREIF_COURSE_FETCH_NUM);
        if(myCourseBriefs.size() == 0) {
            new GetCourseTask().execute(true);
        } else {
            setUpAdapter(myCourseBriefs);
        }
    }

    private CourseModel getCourseModel() {
        final CourseModel courseModel = CourseModel.getInstance();
        return courseModel;
    }

    public void showMyCourse() {
        showMyCourse = true;
    }

    public void showAllCourse() {
        showMyCourse = false;
    }

    private void setUpAdapter(List<CourseBriefInfo> courses) {
        if(getActivity() == null) //因为这个方法可能是由后台任务GetCourseTask调用的，调用的时候可能Activity已经被销毁了
            return;
        if(courses != null) {
            adapter = new CourseAdapter(getActivity(), android.R.layout.simple_list_item_1, courses);
        } else {
            adapter = null;
        }

        //notify on data change
        if(listView != null) {
            listView.setAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);

        listView = (AbsListView) view.findViewById(R.id.main_course_list);
        ((AdapterView<ListAdapter>) listView).setAdapter(adapter);
        listView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(listner != null) {
            CourseBriefInfo courseBrief = (CourseBriefInfo)adapter.getItem(position);
            CourseModel.getInstance().setCurrentCourseBrief(courseBrief);
            listner.onCourseSelected();
        }
    }

    private void setEmptyText(CharSequence emptyText) {
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
                convertView = getActivity().getLayoutInflater().inflate(R.layout.layout_course_brief, null);
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
            String studentId = UserAccountBLService.getInstance().getSid();
            isMyCourse = params[0];
            if(isMyCourse)
                return CourseBLService.getInstance().getMyCourseBriefs(studentId, 0, 0);
            else
                return CourseBLService.getInstance().getAllCourseBriefs(studentId, 0, 0);

        }

        @Override
        protected void onPostExecute(ArrayList<CourseBriefInfo> coursesResult) {
            if(isMyCourse)
                CourseModel.getInstance().setMyCourseBriefs(coursesResult);
            else
                CourseModel.getInstance().setAllCourseBriefs(coursesResult);
            setUpAdapter(coursesResult);
        }

    }

    public interface OnCourseSelectedListener {
        public void onCourseSelected();
    }

}
