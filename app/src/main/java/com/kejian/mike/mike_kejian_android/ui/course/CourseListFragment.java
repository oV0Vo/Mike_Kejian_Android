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
import android.widget.ListAdapter;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

import java.util.ArrayList;

import model.course.CourseModel;


public class CourseListFragment extends Fragment implements AbsListView.OnItemClickListener{

    private static final String ARG_STUDENT_ID = "studentId";
    private String studentId;

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

    public static CourseListFragment newInstance(String studentId) {
        Bundle args = new Bundle();
        args.putString(ARG_STUDENT_ID, studentId);
        CourseListFragment fragment = new CourseListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);//这个语句可以使得在设备配置改变比如说屏幕旋转的时候保留这个fragment实例
        if(savedInstanceState != null) {
            String studentId = savedInstanceState.getString(ARG_STUDENT_ID);
            this.studentId = studentId;
        }
        ArrayList<Course> courses = CourseModel.getAllCourses();
        if(courses == null) {
            new GetCourseTask().execute(studentId);
        } else {
            setUpAdapter(courses);
        }
    }

    private void setUpAdapter(ArrayList<Course> courses) {
        if(getActivity() == null) //因为这个方法可能是由后台任务GetCourseTask调用的，调用的时候可能Activity已经被销毁了
            return;
        if(courses != null) {
            adapter = new CourseAdapter(getActivity(), android.R.layout.simple_list_item_1, courses);
        } else {
            adapter = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);

        // Set the adapter
        listView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) listView).setAdapter(adapter);

        // Set OnItemClickListener so we can be notified on item clicks
        listView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(listner != null) {
            Course course = CourseModel.getAllCourses().get(position);
            listner.onCourseSelected(course.getCourseId());
        }
    }

    private void setEmptyText(CharSequence emptyText) {
        View emptyView = listView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    private class CourseAdapter extends ArrayAdapter<Course> {

        public CourseAdapter(Context context, int layoutId, ArrayList<Course> courses) {
            //android.R.layout。simple_list_item_1是安卓预定义的一个布局，使用其他布局文件也行，只要满足根元素是TextView就行了
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

            Course course = getItem(position);

            TextView nameView = (TextView)convertView.findViewById(R.id.main_course_brief_name);
            nameView.setText(course.getName());
            TextView academyView = (TextView)convertView.findViewById(R.id.main_course_brief_academy);
            academyView.setText(course.getAcademyName());
            TextView processWeekView = (TextView)convertView.findViewById(R.id.main_course_brief_progress_week);
            processWeekView.setText("第" + course.getProgressWeek() + "周");

            return convertView;
        }
    }

    private class GetCourseTask extends AsyncTask<String, Integer, ArrayList<Course>> {

        @Override
        protected ArrayList<Course> doInBackground(String... params) {
            String studentId = params[0];
            try {
                Thread.sleep(1000); //emulate network
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            CourseModel.mockSetCourses();
            ArrayList<Course> courses = CourseModel.getAllCourses();
            return courses;
        }

        @Override
        protected void onPostExecute(ArrayList<Course> coursesResult) {
            CourseModel.setCourses(coursesResult);
            setUpAdapter(coursesResult);
        }

    }

    public interface OnCourseSelectedListener {
        public void onCourseSelected(int courseId);
    }

}
