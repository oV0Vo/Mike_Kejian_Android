package com.kejian.mike.mike_kejian_android.ui.campus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.message.OnRefreshListener;
import com.kejian.mike.mike_kejian_android.ui.message.RefreshListView;


import bl.CampusBLService;
import model.campus.Post;

/**
 * Created by showjoy on 15/9/17.
 */
public class HottestPostListFragment extends Fragment implements XListView.IXListViewListener {
    private View view;
    private LinearLayout mainLayout;
    private XListView container;
    private Activity ctx;
    private LayoutInflater mInflater;
    private ProgressBar progressBar;
    private PostAdapter adapter;

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
        this.container = (XListView)view.findViewById(R.id.post_container);
        container.setPullLoadEnable(true);
        this.mInflater = ctx.getLayoutInflater();
        this.adapter = new PostAdapter(ctx, R.layout.layout_post, CampusBLService.hottestPosts);
        this.container.setAdapter(adapter);
        this.container.setXListViewListener(this);
        this.container.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0 &&checkNetwork()) {
                    Intent intent = new Intent();
                    intent.setClass(getContext(), PostDetailActivity.class);
                    intent.putExtra("postId", ((Post) parent.getAdapter().getItem(position)).getPostId());
                    getContext().startActivity(intent);
                }

            }
        });

    }


    @Override
    public void onRefresh() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                CampusBLService.refreshHottestPosts();
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                adapter.notifyDataSetChanged();
                onLoad();
            }
        }.execute(new Void[]{});
    }

    @Override
    public void onLoadMore() {
        if(CampusBLService.hasNextHottestPost()) {
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    CampusBLService.getNextHottestPosts();
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    CampusBLService.moveHottestPosts();
                    adapter.notifyDataSetChanged();
                    onLoad();

                }
            }.execute(new Void[]{});
        } else {
            onLoad();
            container.setFooterState(XListViewFooter.STATE_NOMORE);
        }

    }

    private class InitDataTask extends AsyncTask<String, Integer, String> {
        @Override
        public String doInBackground(String... params) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            CampusBLService.refreshHottestPosts();
            return "";
        }

        @Override
        public void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            iniViews();
            mainLayout.setVisibility(View.VISIBLE);
        }
    }

    private void onLoad() {
        container.stopRefresh();
        container.stopLoadMore();
        container.setRefreshTime("刚刚");
    }

    public boolean checkNetwork() {


        ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();


        NetworkInfo[] networkInfo = manager.getAllNetworkInfo();

        if (networkInfo != null && networkInfo.length > 0) {
            for (int i = 0; i < networkInfo.length; i++) {

                // 判断当前网络状态是否为连接状态
                if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }

            progressBar.setVisibility(View.INVISIBLE);

            Toast.makeText(getContext(), "请检查你的网络设置 >_<", Toast.LENGTH_SHORT).show();

            return false;
        }

        return false;


    }
}
