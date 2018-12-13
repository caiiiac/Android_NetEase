package com.simple.neteasesimple.news.news_inner;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.simple.neteasesimple.R;
import com.simple.neteasesimple.news.bean.Banner;
import com.simple.neteasesimple.news.bean.Hot;
import com.simple.neteasesimple.news.bean.HotDetail;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class HotFragment extends Fragment {

    private ListView mListView;
    private ArrayList<Banner> mBanners;
    private ArrayList<HotDetail> mHotDetails;
    private ArrayList<View> views;
    private  ArrayList<ImageView> dot_imgs;

    // 刷新数据成功
    private final static int INIT_SUCCESS = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        mListView = view.findViewById(R.id.listView);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBanners = new ArrayList<>();
        mHotDetails = new ArrayList<>();
        views = new ArrayList<>();
        dot_imgs = new ArrayList<>();


        mHotDetails

    }
    public void initData() {

    }

    public void initBanner() {

    }


    static class MyHandler extends Handler {
        WeakReference<HotFragment> weak_fragment;

        @Override
        public void handleMessage(Message msg) {
            HotFragment hot = weak_fragment.get();
            if (hot != null) {
                switch (msg.what) {
                    case INIT_SUCCESS:
                        hot.initData();
                        hot.initBanner();
                        break;
                }
            }
        }
    }
}
