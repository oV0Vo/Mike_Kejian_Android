package com.kejian.mike.mike_kejian_android.ui.course.detail;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import java.util.List;

import model.campus.Post;
import model.course.CourseDetailInfo;
import model.course.CourseModel;

public class CommentsAreaFragment extends Fragment implements AbsListView.OnItemClickListener {

    private OnPostSelectedListener mListener;

    private AbsListView mListView;

    private ListAdapter mAdapter;

    public CommentsAreaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CourseDetailInfo currentCourse = CourseModel.getInstance().getCurrentCourseDetail();
        if(currentCourse != null) {
            ArrayList<Post> posts = currentCourse.getPosts();
            mAdapter = new CommentsArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, posts);
        } else {
            mAdapter = null;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnPostSelectedListener) activity;
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mListener != null) {
            Post post = (Post)mAdapter.getItem(position);
            CourseModel.getInstance().setCurrentPost(post);
            mListener.onPostSelected();
        }
    }

    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    private class CommentsArrayAdapter extends ArrayAdapter<Post> {

        public CommentsArrayAdapter(Context context, int resource, List<Post> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            if(convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.layout_post_brief, null);
            }

            Post post = getItem(position);

            TextView titleView = (TextView)convertView.findViewById(R.id.course_detail_post_brief_title);
            titleView.setText(post.getTitle());
            /**
             * 教师参与在这设置
             */
            TextView authorView = (TextView)convertView.findViewById(R.id.course_detail_post_brief_author_name);
            authorView.setText(post.getAuthorName());

            TextView timeView = (TextView)convertView.findViewById(R.id.course_detail_post_brief_time);
            timeView.setText(post.getDate().toString());

            TextView viewNumView = (TextView)convertView.findViewById(R.id.course_detail_post_brief_view_num);
            viewNumView.setText(post.getViewNum());

            TextView replyNumView = (TextView)convertView.findViewById(R.id.course_detail_post_brief_reply_num);
            replyNumView.setText(post.getReplyList().size());

            return convertView;
        }
    }

    public interface OnPostSelectedListener {
        public void onPostSelected();
    }

}
