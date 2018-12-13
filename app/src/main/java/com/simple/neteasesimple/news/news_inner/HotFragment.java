package com.simple.neteasesimple.news.news_inner;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.simple.neteasesimple.R;
import com.simple.neteasesimple.news.adapter.HotAdapter;
import com.simple.neteasesimple.news.bean.Banner;
import com.simple.neteasesimple.news.bean.FragmentInfo;
import com.simple.neteasesimple.news.bean.Hot;
import com.simple.neteasesimple.news.bean.HotDetail;
import com.simple.neteasesimple.until.Constant;
import com.simple.neteasesimple.until.HttpRespon;
import com.simple.neteasesimple.until.HttpUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class HotFragment extends Fragment {

    private ListView mListView;
    private ArrayList<Banner> mBanners;
    private ArrayList<HotDetail> mHotDetails;
    private ArrayList<View> views;
    private ArrayList<ImageView> dot_imgs;

    private MyHandler mHandler;
    private HotAdapter mAdapter;
    private LayoutInflater inflater;

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


        mHandler = new MyHandler(this);

        inflater = LayoutInflater.from(getActivity());


        // 请求
        request();

    }
    public void initData() {
        mAdapter = new HotAdapter(mHotDetails, getActivity());
        mListView.setAdapter(mAdapter);
    }

    public void initBanner() {

        if (mBanners != null && mBanners.size() > 0) {
            for (int i = 0; i < mBanners.size(); i++) {
//                View view = inflater.inflate(R.layout.)
            }
        }
    }

    public void request() {
        HttpUtil util = HttpUtil.getInstance();
        util.getDate(Constant.HOT_URL, new HttpRespon<Hot>(Hot.class) {
            @Override
            public void onError(String msg) {
                Log.v("caiiiac", msg);
            }

            @Override
            public void onSuccess(Hot hot) {

                if (hot != null && hot.getT1348647909107() != null) {
                    List<HotDetail> details = hot.getT1348647909107();

                    // 取出轮播图数据
                    HotDetail tmp_banner = details.get(0);
                    List<Banner> banners = tmp_banner.getAds();
                    mBanners.addAll(banners);
                    details.remove(0);

                    // 列表数据
                    mHotDetails.addAll(details);

                    // 发送成功信号
                    mHandler.sendEmptyMessage(INIT_SUCCESS);
                }
            }
        });
    }


    static class MyHandler extends Handler {
        WeakReference<HotFragment> weak_fragment;

        public MyHandler(HotFragment fragment) {
            this.weak_fragment = new WeakReference(fragment);
        }
        @Override
        public void handleMessage(Message msg) {
            HotFragment hot = weak_fragment.get();
            if (hot != null) {
                switch (msg.what) {
                    case INIT_SUCCESS:
                        hot.initData();
//                        hot.initBanner();
                        break;
                }
            }
        }
    }
}
