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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;

import java.util.ArrayList;
import java.util.List;

import bl.course.CourseBriefFilter;
import model.course.CourseModel;
import util.StringUtil;

import com.kejian.mike.mike_kejian_android.dataType.course.CourseBriefInfo;
import com.kejian.mike.mike_kejian_android.dataType.course.CourseType;
import com.kejian.mike.mike_kejian_android.ui.message.OnRefreshListener;
import com.kejian.mike.mike_kejian_android.ui.message.RefreshListView;


public class CourseListFragment extends Fragment implements AbsListView.OnItemClickListener{

    private static final String TAG = "CourseListFragment";

    private OnRefreshListener noActionRL;

    private OnRefreshListener allCourseRL;

    private RefreshListView listView;

    private ArrayList<CourseBriefInfo> listData;

    private CourseAdapter listAdapter;

    private OnCourseSelectedListener listner;

    private CourseModel courseModel;

    private boolean initMyCourseDataFinish;

    private boolean initAllCourseDataFinish;

    private boolean initMyCourseDataFail;

    private boolean initAllCourseDataFail;

    private ProgressBar progressBar;

    private TextView errorMessageText;

    private boolean isShowMyCourse;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseModel = CourseModel.getInstance();

        initListAdapter();
        initListRefreshListener();
    }

    private void initListAdapter() {
        isShowMyCourse = true;
        listData = new ArrayList(courseModel.getMyCourseBriefs());
        if(listData.size() == 0) {
            new InitMyCourseBriefTask().execute();
        } else {
            initMyCourseDataFinish = true;
        }
        listAdapter = new CourseAdapter(getActivity(), android.R.layout.simple_list_item_1,
                listData);
    }

    private void initListRefreshListener() {
        noActionRL = new NoActionRefreshListener();
        allCourseRL = new AllCourseRefreshListener();
    }

    public void showMyCourse() {
        if (listView == null)
            return;
        isShowMyCourse = true;
        listView.setOnRefreshListener(noActionRL);
        listData.clear();
        if(initMyCourseDataFinish) {
            progressBar.setVisibility(View.GONE);
            errorMessageText.setVisibility(View.GONE);
            listData.addAll(courseModel.getMyCourseBriefs());
            listAdapter.notifyDataSetChanged();
        } else if(initMyCourseDataFail) {
            progressBar.setVisibility(View.GONE);
            errorMessageText.setVisibility(View.VISIBLE);
        } else { //on progress
            progressBar.setVisibility(View.VISIBLE);
            errorMessageText.setVisibility(View.GONE);
        }
    }

    public void showAllCourse() {
        if (listView == null)
            return;
        isShowMyCourse = false;
        listData.clear();

        if(initAllCourseDataFinish) {
            progressBar.setVisibility(View.GONE);
            errorMessageText.setVisibility(View.GONE);
            listData.addAll(courseModel.getAllCourseBriefs());
            listAdapter.notifyDataSetChanged();
            listView.setOnRefreshListener(allCourseRL);
        } else if(initAllCourseDataFail) {
            progressBar.setVisibility(View.GONE);
            errorMessageText.setVisibility(View.VISIBLE);
            listView.setOnRefreshListener(noActionRL);
        } else { //on progress
            progressBar.setVisibility(View.VISIBLE);
            errorMessageText.setVisibility(View.GONE);
            listView.setOnRefreshListener(noActionRL);
        }
    }

    public void showAcademyCourseList(CharSequence academyName) {
        if(initAllCourseDataFinish) {
            ArrayList<CourseBriefInfo> allCourseBriefs = courseModel.getAllCourseBriefs();
            ArrayList<CourseBriefInfo> filterResults = CourseBriefFilter.filterByAcademyName(allCourseBriefs,
                    academyName);
            listData.clear();
            listData.addAll(filterResults);
            listAdapter.notifyDataSetChanged();
            listView.setOnRefreshListener(noActionRL);
        }
    }

    public void showCourseTypeList(CharSequence courseType) {
        if(initAllCourseDataFinish) {
            ArrayList<CourseBriefInfo> allCourseBriefs = courseModel.getAllCourseBriefs();
            ArrayList<CourseBriefInfo> filterResults = CourseBriefFilter.filterByCourseType(allCourseBriefs,
                    courseType);
            listData.clear();
            listData.addAll(filterResults);
            listAdapter.notifyDataSetChanged();
            listView.setOnRefreshListener(noActionRL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);

        progressBar = (ProgressBar)view.findViewById(R.id.progress_bar);
        if(initMyCourseDataFinish)
            progressBar.setVisibility(View.GONE);
        else
            progressBar.setVisibility(View.VISIBLE);

        errorMessageText = (TextView)view.findViewById(R.id.error_message_text);

        listView = (RefreshListView) view.findViewById(R.id.main_course_list);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
        listView.setOnRefreshListener(noActionRL);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(listner != null) {
            CourseBriefInfo courseBrief = listAdapter.getItem(position);
            courseModel.setCurrentCourseId(courseBrief.getCourseId());
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
                convertView = getActivity().getLayoutInflater().inflate(
                        R.layout.layout_main_course_brief, null);
            }

            CourseBriefInfo courseBriefInfo = getItem(position);

            ImageView imageView = (ImageView)convertView.findViewById(R.id.course_brief_image);
            imageView.setImageResource(R.drawable.default_book);

            TextView nameView = (TextView)convertView.findViewById(R.id.course_brief_name);
            nameView.setText(courseBriefInfo.getCourseName());

            TextView academyView = (TextView)convertView.findViewById(R.id.course_brief_academy);
            academyView.setText(courseBriefInfo.getAcademyName());

            TextView teacherNameText = (TextView)convertView.findViewById(R.id.
                    teacher_name_text);
            ArrayList<String> teacherNames = courseBriefInfo.getTeacherNames();
            String teacherNameStr = StringUtil.toString(teacherNames, " ");
            teacherNameText.setText(teacherNameStr);

            return convertView;
        }

    }

    private class InitMyCourseBriefTask extends AsyncTask<Void, Integer,Boolean> {

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
            //设置更新状态
            if(updateSuccess) {
                initMyCourseDataFinish = true;
            } else {
                initMyCourseDataFail = true;
            }
            //更新界面
            if(isShowMyCourse) {
                showMyCourse();
            }
        }
    }

    private class InitAllCourseBriefTask extends AsyncTask<Void, Void,Boolean> {

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
            //设置更新状态
            if(updateSuccess) { 
                initAllCourseDataFinish = true;
            } else {
                initAllCourseDataFail = true;
            }
            //更新界面
            if(!isShowMyCourse) {
                showAllCourse();
            }
        }
    }

    private class UpdateAllCourseBriefTask extends AsyncTask<Void, Void, Boolean> {

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
            listView.hideFooterView();
            if(updateSuccess) {
                listAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), R.string.net_disconnet, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class AllCourseRefreshListener implements OnRefreshListener {

        @Override
        public void onDownPullRefresh() {
            new UpdateAllCourseBriefTask().execute();
        }

        @Override
        public void onLoadingMore() {
            listView.hideHeaderView();
        }
    }

    private class NoActionRefreshListener implements OnRefreshListener {

        @Override
        public void onDownPullRefresh() {
            listView.hideFooterView();
        }

        @Override
        public void onLoadingMore() {
            listView.hideHeaderView();
        }
    }

    public interface OnCourseSelectedListener {
        void onCourseSelected();
    }

}
