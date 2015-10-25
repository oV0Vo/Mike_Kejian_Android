package com.kejian.mike.mike_kejian_android.ui.course.detail;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

import com.kejian.mike.mike_kejian_android.dataType.course.CourseAnnoucement;

import net.course.CourseAnnoucNetService;

import java.util.ArrayList;

import model.course.CourseModel;
import util.TimeFormat;


public class LatestAnnoucFragment extends Fragment {

    private static final String TAG = "LatestAnnoucFg";

    private OnAnnoucementClickListener mListener;

    private ProgressBar progressBar;

    private TextView errorMessageText;

    private ViewGroup mainLayout;

    private CourseAnnoucement annouc;

    public LatestAnnoucFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_annoucement, container, false);
        progressBar = (ProgressBar)v.findViewById(R.id.progress_bar);
        errorMessageText = (TextView)v.findViewById(R.id.error_message_text);
        mainLayout = (ViewGroup)v.findViewById(R.id.main_layout);
        v.setOnClickListener(new OnAnnoucClickListener());

        String courseId = CourseModel.getInstance().getCurrentCourseId();
        new GetAnnoucsTask().execute(courseId);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnAnnoucementClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void updateViewOnGetAnnouc() {
        if(mainLayout == null)
            return;

        mainLayout.setVisibility(View.VISIBLE);
        TextView contentView = (TextView) mainLayout.findViewById(R.id.
                course_detail_annoucement_content);
        if(annouc != null) {
            contentView.setText(annouc.getContent());
            TextView authorView = (TextView) mainLayout.findViewById(R.id.
                    course_detail_annoucement_author_name);
            authorView.setText(annouc.getPersonName());
            TextView dateView = (TextView) mainLayout.findViewById(R.id.
                    course_detail_annoucement_date);
            dateView.setText(TimeFormat.toMinute(annouc.getDate()));
            Log.i(TAG, annouc.getDate().toString());
        } else {
            contentView.setText((R.string.annoucement_no_annoucement));
            contentView.setGravity(Gravity.CENTER);
            contentView.setTextColor(getResources().getColor(R.color.dark));
            ViewGroup annoucTimeLayout = (ViewGroup)mainLayout.findViewById(R.id.annouc_time_layout);
            annoucTimeLayout.setVisibility(View.GONE);
        }
    }

    public interface OnAnnoucementClickListener {
        void onAnnoucementClick();
    }

    private class GetAnnoucsTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            CourseModel courseModel = CourseModel.getInstance();
            boolean updateSuccess = courseModel.updateAnnoucs();
            if(updateSuccess)
                annouc = courseModel.getLatestAnnouc();
            return updateSuccess;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            progressBar.setVisibility(View.GONE);
            if(success) {
                updateViewOnGetAnnouc();
            } else {
                errorMessageText.setVisibility(View.VISIBLE);
                Log.e(TAG, "update annoucs Fail");
            }
        }
    }

    private class OnAnnoucClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(mListener != null)
                mListener.onAnnoucementClick();
        }
    }

}
