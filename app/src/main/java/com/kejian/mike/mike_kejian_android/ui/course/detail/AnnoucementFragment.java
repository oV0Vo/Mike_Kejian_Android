package com.kejian.mike.mike_kejian_android.ui.course.detail;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

import model.course.CourseAnnoucement;
import model.course.CourseBriefInfo;
import model.course.CourseDetailInfo;
import model.course.CourseModel;


public class AnnoucementFragment extends Fragment {

    private OnAnnoucementClickListener mListener;

    public AnnoucementFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_annoucement, container, false);

        CourseDetailInfo courseDetail = CourseModel.getInstance().getCurrentCourseDetail();

        if(courseDetail == null) {
            Log.i("AnnoucementFragment", "courseDetail null!");
            return v;
        }

        CourseAnnoucement annoucement = courseDetail.getAnnoucement();
        TextView contentView = (TextView)v.findViewById(R.id.annoucement_content);
        contentView.setText(annoucement.getContent());
        TextView authorView = (TextView)v.findViewById(R.id.annoucement_author_name);
        authorView.setText(annoucement.getPersonName());
        TextView dateView = (TextView)v.findViewById(R.id.annoucement_date);
        dateView.setText(annoucement.getDate().toString());

        v.setOnClickListener(new OnViewClickListener());

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnAnnoucementClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnAnnoucementClickListener {
        public void onAnnoucementClick();
    }

    private class OnViewClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if(mListener != null)
                mListener.onAnnoucementClick();
        }
    }

}
