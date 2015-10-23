package com.kejian.mike.mike_kejian_android.ui.campus;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import com.kejian.mike.mike_kejian_android.R;

/**
 * Created by showjoy on 15/9/16.
 */
public class PostListContainerFragment extends Fragment implements CompoundButton.OnCheckedChangeListener{
    private View view;
    private FragmentTabHost mTabHost;
    RadioButton latestButton;
    RadioButton hottestButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view == null){
            Activity ctx = this.getActivity();
            view = ctx.getLayoutInflater().inflate(R.layout.fragment_post_list_container, null);
            mTabHost = (FragmentTabHost)view.findViewById(android.R.id.tabhost);
            mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
            latestButton = (RadioButton) view.findViewById(R.id.campus_main_latest_button);
            latestButton.setOnCheckedChangeListener(this);
            latestButton.setChecked(true);
            hottestButton = (RadioButton) view.findViewById(R.id.campus_main_hottest_button);
            hottestButton.setOnCheckedChangeListener(this);
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
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked && mTabHost != null) {
            if(buttonView == latestButton)
                mTabHost.setCurrentTabByTag("latest");
            else
                mTabHost.setCurrentTabByTag("hottest");

        }

    }
}
