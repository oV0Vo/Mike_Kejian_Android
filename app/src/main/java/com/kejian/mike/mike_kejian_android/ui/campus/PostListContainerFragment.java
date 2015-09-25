package com.kejian.mike.mike_kejian_android.ui.campus;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

/**
 * Created by showjoy on 15/9/16.
 */
public class PostListContainerFragment extends Fragment {
    private View view;
    private FragmentTabHost mTabHost;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view == null){
            Activity ctx = this.getActivity();
            view = ctx.getLayoutInflater().inflate(R.layout.fragment_post_list_container,
                    null);
            mTabHost = (FragmentTabHost)view.findViewById(android.R.id.tabhost);
            mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
            mTabHost.addTab(mTabHost.newTabSpec("latest").setIndicator("最新"),
                    LatestPostListFragment.class, null);
            mTabHost.addTab(mTabHost.newTabSpec("hottest").setIndicator("热门"),
                    HottestPostListFragment.class, null);

            return view;

        }else{
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_campus, menu);
        MenuItem add_item = menu.findItem(R.id.publish_post);
        add_item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), PostPublishActivity.class);
                startActivity(intent);
                return true;
            }
        });
    }
}
