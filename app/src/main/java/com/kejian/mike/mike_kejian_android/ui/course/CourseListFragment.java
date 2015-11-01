package com.kejian.mike.mike_kejian_android.ui.course;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.course.CourseModel;
import model.user.Global;
import model.user.user;
import util.GetBitmapByPinyin;
import util.StringUtil;
import util.TimeFormat;

import com.kejian.mike.mike_kejian_android.dataType.course.CourseBriefInfo;
import com.kejian.mike.mike_kejian_android.ui.campus.XListView;
import com.kejian.mike.mike_kejian_android.ui.util.BindAction;
import com.kejian.mike.mike_kejian_android.ui.util.UmengMessageAction;

import net.course.CourseInfoNetService;


public class CourseListFragment extends Fragment{

    private static final String TAG = "CourseListFragment";

    private CourseAdapter myCourseAdapter;
    private AbsListView myCourseList;
    private TextView myCourseEmptyText;

    private CourseAdapter allCourseAdapter;
    private XListView allCourseList;
    private XListView.IXListViewListener refreshListener;
    private TextView allCourseEmptyText;

    private TextView allCourseFilterEmptyText;

    private OnCourseSelectedListener courseSelectListener;

    private CourseModel courseModel;

    private boolean initMyCourseDataFinish;

    private boolean initAllCourseDataFinish;

    private boolean initMyCourseDataFail;

    private boolean initAllCourseDataFail;

    private ProgressBar progressBar;

    private TextView errorMessageText;

    private boolean isShowMyCourse;

    private boolean isShowFilterResult;

    private int currentFilterVersion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseModel = CourseModel.getInstance();

        initMyCourseAdapter();
        initAllCourseAdapter();
        registerBindBroacast();
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
        allCourseFilterEmptyText.setVisibility(View.GONE);
        isShowFilterResult = false;
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
        allCourseFilterEmptyText.setVisibility(View.GONE);
        isShowFilterResult = false;
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

    public void showCourseByCondition(String academyName, String courseType) {
        if(initAllCourseDataFinish) {
            boolean showAllAcademy = academyName.equals(getContext().getResources().getString(
                    R.string.main_course_select_all_academy));
            boolean showAllCourseType = courseType.equals(getContext().getResources().getString(
                    R.string.main_course_select_all_course));

            String academyId = null;
            if(showAllAcademy && showAllCourseType) {
                allCourseList.setAdapter(allCourseAdapter);
                allCourseList.setPullLoadEnable(true);
                return;
            } else if(showAllAcademy){
                academyId = "";
            } else if(showAllCourseType) {
                courseType = "";//java的string是不变的，不会改变传进来的值
                academyId = courseModel.getAcademyIdByName(academyName);
            } else {
                academyId = courseModel.getAcademyIdByName(academyName);
            }

            isShowFilterResult = true;
            String schoolId = ((user)Global.getObjectByName("user")).getSchoolInfo().getId();
            new GetCourseByConditionTask(++currentFilterVersion).execute(schoolId, academyId,
                    courseType);
            progressBar.setVisibility(View.VISIBLE);
            errorMessageText.setVisibility(View.GONE);
            allCourseFilterEmptyText.setVisibility(View.GONE);
        }
    }

    private class GetCourseByConditionTask extends AsyncTask<String, Void,
            ArrayList<CourseBriefInfo>> {

        private int filterVersion;

        public GetCourseByConditionTask(int filterVersion) {
            this.filterVersion = filterVersion;
        }

        @Override
        protected ArrayList<CourseBriefInfo> doInBackground(String... params) {
            String schoolId = params[0];
            String academyId = params[1];
            String courseType = params[2];
            ArrayList<CourseBriefInfo> results = CourseInfoNetService.getCourseByCondition(schoolId,
                    academyId, courseType);
            return results;
        }

        @Override
        protected void onPostExecute(ArrayList<CourseBriefInfo> results) {
            if(getContext() == null)
                return;

            if(!(isShowFilterResult && filterVersion == currentFilterVersion)) {
                Log.i(TAG, Boolean.toString(isShowFilterResult));
                return;
            }

            progressBar.setVisibility(View.GONE);
            if(results != null) {
                if(results.size() != 0) {
                    CourseAdapter resultAdapter = new CourseAdapter(getContext(),
                            android.R.layout.simple_list_item_1, results);
                    allCourseList.setAdapter(resultAdapter);
                } else {
                    allCourseList.setVisibility(View.GONE);
                    allCourseFilterEmptyText.setVisibility(View.VISIBLE);
                }
            } else {
                errorMessageText.setVisibility(View.VISIBLE);
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
        myCourseList.setOnItemClickListener(new AbsListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CourseBriefInfo selectedCourseBrief = (CourseBriefInfo)
                        ((AbsListView) parent).getItemAtPosition(position);
                courseModel.setCurrentCourseId(selectedCourseBrief.getCourseId());
                courseSelectListener.onCourseSelected();
            }
        });
        myCourseAdapter = new CourseAdapter(getContext(), android.R.layout.simple_list_item_1,
                courseModel.getMyCourseBriefs());
        myCourseList.setAdapter(myCourseAdapter);
        isShowMyCourse = true;
        myCourseEmptyText = (TextView)layoutView.findViewById(R.id.my_course_empty_text);
    }

    private void initAllCourseView(View layoutView) {
        allCourseList = (XListView)layoutView.findViewById(R.id.all_course_list);
        allCourseList.setOnItemClickListener(new AbsListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CourseBriefInfo selectedCourseBrief = (CourseBriefInfo)
                        ((AbsListView) parent).getItemAtPosition(position);
                courseModel.setCurrentCourseId(selectedCourseBrief.getCourseId());
                courseSelectListener.onCourseSelected();
            }
        });
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
        allCourseFilterEmptyText = (TextView)layoutView.findViewById(R.id.filter_empty_text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            courseSelectListener = (OnCourseSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnCourseSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        courseSelectListener = null;
    }

    private class CourseAdapter extends ArrayAdapter<CourseBriefInfo> {

        public CourseAdapter(Context context, int layoutId, List<CourseBriefInfo> courses) {
            super(context, layoutId, courses);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CourseBriefViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(
                        R.layout.layout_course_brief, null);
                viewHolder = new CourseBriefViewHolder();
                ImageView bookImage = (ImageView)convertView.findViewById(R.id.course_brief_image);
                viewHolder.bookImage = bookImage;
                TextView titleView = (TextView)convertView.findViewById(R.id.course_brief_name);
                viewHolder.titleText = titleView;
                TextView academyNameView = (TextView)convertView.findViewById(R.id.course_brief_academy);
                viewHolder.academyNameText = academyNameView;
                TextView teacherNameText = (TextView)convertView.findViewById(R.id.
                        teacher_name_text);
                viewHolder.teacherNameText = teacherNameText;
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (CourseBriefViewHolder)convertView.getTag();
            }

            CourseBriefInfo courseBriefInfo = getItem(position);

            GetBitmapByPinyin.getBitmapByPinyin(
                    courseBriefInfo.getCourseName(), getContext(), viewHolder.bookImage);

            viewHolder.titleText.setText(courseBriefInfo.getCourseName());

            viewHolder.academyNameText.setText(courseBriefInfo.getAcademyName());


            ArrayList<String> teacherNames = courseBriefInfo.getTeacherNames();
            String teacherNameStr = StringUtil.toString(teacherNames, " ");
            viewHolder.teacherNameText.setText(teacherNameStr);
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

    public interface OnCourseSelectedListener {
        void onCourseSelected();
    }

    static class CourseBriefViewHolder {
        private ImageView bookImage;
        private TextView titleText;
        private TextView academyNameText;
        private TextView teacherNameText;
    }

    private void registerBindBroacast() {
        BroadcastReceiver bindBR = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i(TAG, "bind broacast: bind:" + Boolean.toString(intent.getBooleanExtra(
                        BindAction.ARG_IS_BIND, false)));
                new InitMyCourseBriefTask().execute();
            }
        };
        IntentFilter bindIntentFilter = new IntentFilter(BindAction.ACTION_NAME);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(bindBR, bindIntentFilter);
    }

}
