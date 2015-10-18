package com.kejian.mike.mike_kejian_android.ui.campus;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.message.OnRefreshListener;
import com.kejian.mike.mike_kejian_android.ui.message.RefreshListView;

import java.util.ArrayList;

import bl.CampusBLService;
import model.campus.Post;
import model.message.Reply;
import util.DensityUtil;

/**
 * Created by showjoy on 15/9/17.
 */
public class LatestPostListFragment extends Fragment implements View.OnClickListener,OnRefreshListener {
    private View view;
    private LinearLayout mainLayout;
    private RefreshListView container;
    private Activity ctx;
    private LayoutInflater mInflater;
    private ProgressBar progressBar;
    private PostAdapter adapter;
    private int post_num = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_latest_post_list, null);
        ctx = this.getActivity();
        this.mainLayout = (LinearLayout) view.findViewById(R.id.post);
        this.mainLayout.setVisibility(View.GONE);
        this.progressBar = (ProgressBar)view.findViewById(R.id.post_list_progress_bar);
        iniData();
        return view;

    }

    private void iniData() {
        new InitDataTask().execute("1234");

    }

    private void iniViews() {
        this.container = (RefreshListView)view.findViewById(R.id.post_container);
        this.mInflater = ctx.getLayoutInflater();
        this.adapter = new PostAdapter(ctx, R.layout.layout_post, CampusBLService.getLatestPostList());
        this.container.setAdapter(adapter);
        this.container.setOnRefreshListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(ctx, PostDetailActivity.class);
        intent.putExtra("postId", (String) v.getTag());
        startActivity(intent);

    }

    @Override
    public void onDownPullRefresh() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                adapter.notifyDataSetChanged();
                container.hideHeaderView();
            }
        }.execute(new Void[]{});

    }

    @Override
    public void onLoadingMore() {

            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    adapter.notifyDataSetChanged();

                    // 控制脚布局隐藏
                    container.hideFooterView();
                }
            }.execute(new Void[]{});


    }

    private class InitDataTask extends AsyncTask<String, Integer, String> {
        @Override
        public String doInBackground(String... params) {
            String userId = params[0];
            return "";
        }

        @Override
        public void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            iniViews();
            mainLayout.setVisibility(View.VISIBLE);
        }
    }
}
