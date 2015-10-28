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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bl.course.CourseBriefFilter;
import model.course.CourseModel;
import util.DateUtil;
import util.GetBitmapByPinyin;
import util.StringUtil;
import util.TimeFormat;

import com.kejian.mike.mike_kejian_android.dataType.course.CourseBriefInfo;
import com.kejian.mike.mike_kejian_android.ui.campus.XListView;
import com.kejian.mike.mike_kejian_android.ui.message.OnRefreshListener;
import com.kejian.mike.mike_kejian_android.ui.message.RefreshListView;


public class CourseListFragment extends Fragment{

    private static final String TAG = "CourseListFragment";

    private CourseAdapter myCourseAdapter;
    private AbsListView myCourseList;
    private TextView myCourseEmptyText;

    private CourseAdapter allCourseAdapter;
    private XListView allCourseList;
    private XListView.IXListViewListener refreshListener;
    private TextView allCourseEmptyText;

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

        initMyCourseAdapter();
        initAllCourseAdapter();
    }

    private void initMyCourseAdapter() {
        if (courseModel.getMyCourseBriefs().size() == 0) {
            new InitMyCourseBriefTask().execute();
        } else {
            initMyCourseDataFinish = true;
        }
        myCourseAdapter = new CourseAdapter(getActivity(), android.R.layout.simple_list_item_1,
                courseModel.getMyCourseBriefs());
    }

    private void initAllCourseAdapter() {
        if(courseModel.getAllCourseBriefs().size() == 0) {
            new InitAllCourseBriefTask().execute();
        } else {
            initAllCourseDataFinish = true;
        }
        allCourseAdapter = new CourseAdapter(getContext(), android.R.layout.simple_list_item_1,
                courseModel.getAllCourseBriefs());
    }

    public void showMyCourse() {
        if (myCourseList == null || getContext() == null)
            return;
        isShowMyCourse = true;
        allCourseList.setVisibility(View.GONE);
        allCourseEmptyText.setVisibility(View.GONE);
        myCourseList.setVisibility(View.VISIBLE);

        if(initMyCourseDataFinish) {
            progressBar.setVisibility(View.GONE);
            errorMessageText.setVisibility(View.GONE);
            if(myCourseAdapter.getCount() != 0) {
                myCourseAdapter.notifyDataSetChanged();
            }
            else {
                myCourseList.setVisibility(View.GONE);
                myCourseEmptyText.setVisibility(View.VISIBLE);
            }

        } else if(initMyCourseDataFail) {
            progressBar.setVisibility(View.GONE);
            errorMessageText.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            errorMessageText.setVisibility(View.GONE);
        }
    }

    public void showAllCourse() {
        if (myCourseList == null)
            return;
        isShowMyCourse = false;
        myCourseList.setVisibility(View.GONE);
        myCourseEmptyText.setVisibility(View.GONE);
        allCourseList.setVisibility(View.VISIBLE);

        if(initAllCourseDataFinish) {
            progressBar.setVisibility(View.GONE);
            errorMessageText.setVisibility(View.GONE);
            if(allCourseAdapter.getCount() != 0) {
                allCourseAdapter.notifyDataSetChanged();
                allCourseList.setPullLoadEnable(true);
            } else {
                allCourseEmptyText.setVisibility(View.VISIBLE);
                allCourseList.setVisibility(View.GONE);
            }
        } else if(initAllCourseDataFail) {
            progressBar.setVisibility(View.GONE);
            errorMessageText.setVisibility(View.VISIBLE);
            allCourseList.setPullLoadEnable(false);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            errorMessageText.setVisibility(View.GONE);
        }
    }

    public void showAcademyCourseList(CharSequence academyName) {
        if(initAllCourseDataFinish) {
            if(academyName.equals(getContext().getResources().getString(
                    R.string.main_course_select_all_academy))) {
                allCourseList.setAdapter(allCourseAdapter);
                allCourseList.setPullLoadEnable(true);
            } else {
                Toast.makeText(getContext(), R.string.not_implemented, Toast.LENGTH_SHORT).show();
                ArrayList<CourseBriefInfo> allCourseBriefs = courseModel.getAllCourseBriefs();
                ArrayList<CourseBriefInfo> filterResults = CourseBriefFilter.filterByAcademyName(allCourseBriefs,
                        academyName);
                CourseAdapter adapter = new CourseAdapter(getActivity(),
                        android.R.layout.simple_list_item_1, filterResults);
                allCourseList.setAdapter(adapter);
                allCourseList.setPullLoadEnable(false);
            }
        }
    }

    public void showCourseTypeList(CharSequence courseType) {
        if(initAllCourseDataFinish) {
            Toast.makeText(getContext(), R.string.not_implemented, Toast.LENGTH_SHORT).show();
            if(courseType.equals(getContext().getResources().getString(
                    R.string.main_course_select_all_course))) {
                allCourseList.setAdapter(allCourseAdapter);
                allCourseList.setPullLoadEnable(true);
            } else {
                Toast.makeText(getContext(), R.string.not_implemented, Toast.LENGTH_SHORT).show();
                ArrayList<CourseBriefInfo> allCourseBriefs = courseModel.getAllCourseBriefs();
                ArrayList<CourseBriefInfo> filterResults = CourseBriefFilter.filterByCourseType(allCourseBriefs,
                        courseType);
                CourseAdapter adapter = new CourseAdapter(getActivity(),
                        android.R.layout.simple_list_item_1, filterResults);
                allCourseList.setAdapter(adapter);
                allCourseList.setPullLoadEnable(false);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_course_list, container, false);

        progressBar = (ProgressBar)layoutView.findViewById(R.id.progress_bar);
        if(initMyCourseDataFinish)
            progressBar.setVisibility(View.GONE);
        else {
            progressBar.setVisibility(View.VISIBLE);
        }

        errorMessageText = (TextView)layoutView.findViewById(R.id.error_message_text);

        initMyCourseView(layoutView);
        initAllCourseView(layoutView);

        return layoutView;
    }

    private void initMyCourseView(View layoutView) {
        myCourseList = (AbsListView) layoutView.findViewById(R.id.my_course_list);
        myCourseAdapter = new CourseAdapter(getContext(), android.R.layout.simple_list_item_1,
                courseModel.getMyCourseBriefs());
        myCourseList.setAdapter(myCourseAdapter);
        isShowMyCourse = true;
        myCourseEmptyText = (TextView)layoutView.findViewById(R.id.my_course_empty_text);
    }

    private void initAllCourseView(View layoutView) {
        allCourseList = (XListView)layoutView.findViewById(R.id.all_course_list);
        allCourseAdapter = new CourseAdapter(getContext(), android.R.layout.simple_list_item_1,
                courseModel.getAllCourseBriefs());
        allCourseList.setAdapter(allCourseAdapter);
        refreshListener = new AllCourseRefreshListener();
        allCourseList.setXListViewListener(refreshListener);

        allCourseList.setPullRefreshEnable(false);
        if(initAllCourseDataFinish)
            allCourseList.setPullLoadEnable(true);
        else
            allCourseList.setPullLoadEnable(false);

        allCourseEmptyText = (TextView)layoutView.findViewById(R.id.all_course_empty_text);
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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.i(TAG, "getView " + Integer.toString(position) + " " + Boolean.toString(
                    convertView == null));

            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(
                        R.layout.layout_course_brief, null);
            }

            CourseBriefInfo courseBriefInfo = getItem(position);

            ImageView imageView = (ImageView)convertView.findViewById(R.id.course_brief_image);
            GetBitmapByPinyin.getBitmapByPinyin(
                    courseBriefInfo.getCourseName(), getContext(), imageView);

            TextView nameView = (TextView)convertView.findViewById(R.id.course_brief_name);
            nameView.setText(courseBriefInfo.getCourseName());

            TextView academyView = (TextView)convertView.findViewById(R.id.course_brief_academy);
            academyView.setText(courseBriefInfo.getAcademyName());

            TextView teacherNameText = (TextView)convertView.findViewById(R.id.
                    teacher_name_text);
            ArrayList<String> teacherNames = courseBriefInfo.getTeacherNames();
            String teacherNameStr = StringUtil.toString(teacherNames, " ");
            teacherNameText.setText(teacherNameStr);

            TextView enterCourseText = (TextView)convertView.findViewById(R.id.enter_course);
            enterCourseText.setOnClickListener(new OnCourseClickListener(courseBriefInfo.getCourseId()));

            convertView.setEnabled(false);

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
            if(updateSuccess) {
                initMyCourseDataFinish = true;
            } else {
                initMyCourseDataFail = true;
            }

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
            if(updateSuccess) {
                initAllCourseDataFinish = true;
            } else {
                initAllCourseDataFail = true;
            }

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
            allCourseList.stopLoadMore();
            allCourseList.setRefreshTime(TimeFormat.toTime(new Date(System.currentTimeMillis())));
            if(updateSuccess) {
                allCourseAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), R.string.net_disconnet, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class AllCourseRefreshListener implements XListView.IXListViewListener {

        @Override
        public void onRefresh() {
            //没有刷新
        }

        @Override
        public void onLoadMore() {
            new UpdateAllCourseBriefTask().execute();
        }
    }

    private class OnCourseClickListener implements View.OnClickListener{

        private String courseId;

        public OnCourseClickListener(String courseId) {
            this.courseId = courseId;
        }

        @Override
        public void onClick(View v) {
            courseModel.setCurrentCourseId(courseId);
            listner.onCourseSelected();
        }
    }

    public interface OnCourseSelectedListener {
        void onCourseSelected();
    }

}
