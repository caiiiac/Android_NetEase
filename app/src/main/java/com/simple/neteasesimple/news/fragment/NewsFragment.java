package com.simple.neteasesimple.news.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.simple.neteasesimple.R;
import com.simple.neteasesimple.news.adapter.NewAdapter;
import com.simple.neteasesimple.news.bean.FragmentInfo;

import java.util.ArrayList;


public class NewsFragment extends Fragment {
    ArrayList<FragmentInfo> pages;
    NewAdapter mNewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        pages = new ArrayList<>();

        FrameLayout layout = getActivity().findViewById(R.id.tabs);
        layout.addView(View.inflate(getActivity(), R.layout.include_tab,null));

        SmartTabLayout smartTabLayout = getActivity().findViewById(R.id.smart_tab);
        ViewPager viewPager = getActivity().findViewById(R.id.viewpager);


        String[] titles = getResources().getStringArray(R.array.news_titles);
        for (int i = 0; i < titles.length; i++) {
            FragmentInfo info;
            if (i == 0) {
//                info = new FragmentInfo(new )
            }
            info = new FragmentInfo(new EmptyFragment(), titles[i]);
            pages.add(info);
        }

        mNewAdapter = new NewAdapter(getFragmentManager(), pages);
        viewPager.setAdapter(mNewAdapter);


        // 自动绑定数据
        smartTabLayout.setViewPager(viewPager);

    }
}
