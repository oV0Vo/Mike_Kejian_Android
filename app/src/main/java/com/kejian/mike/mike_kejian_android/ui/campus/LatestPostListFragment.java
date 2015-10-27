package com.kejian.mike.mike_kejian_android.ui.campus;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.message.OnRefreshListener;
import com.kejian.mike.mike_kejian_android.ui.message.RefreshListView;


import bl.CampusBLService;
import model.campus.Post;

/**
 * Created by showjoy on 15/9/17.
 */
public class LatestPostListFragment extends Fragment implements OnRefreshListener {
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
        new InitDataTask().execute();

    }

    private void iniViews() {
        this.container = (RefreshListView)view.findViewById(R.id.post_container);
        container.setFooterDividersEnabled(false);
        container.getHeaderView().setBackgroundResource(R.color.light_grey);
        this.mInflater = ctx.getLayoutInflater();
        this.adapter = new PostAdapter(ctx, R.layout.layout_post, CampusBLService.latestPosts);
        this.container.setAdapter(adapter);
        this.container.setOnRefreshListener(this);
        this.container.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getContext(), PostDetailActivity.class);
                intent.putExtra("postId", ((Post)parent.getAdapter().getItem(position)).getPostId());
                getContext().startActivity(intent);

            }
        });
    }



    @Override
    public void onDownPullRefresh() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                CampusBLService.refreshLatestPosts();
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
        if(CampusBLService.hasNextLatestPost()) {
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    CampusBLService.getNextLatestPosts();
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    CampusBLService.moveLatestPosts();
                    adapter.notifyDataSetChanged();
                    container.hideFooterView();
                }
            }.execute(new Void[]{});
        } else {
            container.hideFooterView();
        }


    }

    private class InitDataTask extends AsyncTask<String, Integer, String> {
        @Override
        public String doInBackground(String... params) {
            CampusBLService.refreshLatestPosts();
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
