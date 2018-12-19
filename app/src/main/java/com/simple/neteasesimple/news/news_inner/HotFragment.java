package com.simple.neteasesimple.news.news_inner;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.simple.neteasesimple.R;
import com.simple.neteasesimple.news.activity.DetailActivity;
import com.simple.neteasesimple.news.adapter.HotAdapter;
import com.simple.neteasesimple.news.bean.Banner;
import com.simple.neteasesimple.news.bean.Hot;
import com.simple.neteasesimple.news.bean.HotDetail;
import com.simple.neteasesimple.news.adapter.BannerAdapter;
import com.simple.neteasesimple.until.Constant;
import com.simple.neteasesimple.until.HttpRespon;
import com.simple.neteasesimple.until.HttpUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;


public class HotFragment extends Fragment implements ViewPager.OnPageChangeListener, AbsListView.OnScrollListener {

    private ListView mListView;
    private ArrayList<Banner> mBanners;
    private ArrayList<HotDetail> mHotDetails;
    private ArrayList<View> views;
    private ArrayList<ImageView> dot_imgs;

    private MyHandler mHandler;
    private HotAdapter mAdapter;
    private LayoutInflater inflater;

    boolean isToend = false;
    boolean isHttpRequesting = false;

    // 刷新数据成功
    private final static int INIT_SUCCESS = 0;

    //加载更多成功
    private  final static int UPDATE_SUCCESS = 1;

    // 加载完成
    private final static int STOP_RESH = 2;

    ViewPager viewPager;
    BannerAdapter bannerAdapter;
    TextView bannerTitle;
    LinearLayout dots;
    PtrClassicFrameLayout ptr;

    int startIndex = 0;
    int endIndex = 0;
    int pageSize = 20;
    // 取页面的次数
    int count = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        mListView = view.findViewById(R.id.listView);

        ptr = view.findViewById(R.id.ptr);
        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getDate(true);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return super.checkCanDoRefresh(frame, mListView, header);
            }
        });
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCollection();

        initHead();
        // 请求
        getDate(true);

    }

    public void initCollection() {
        mBanners = new ArrayList<>();
        mHotDetails = new ArrayList<>();
        views = new ArrayList<>();
        dot_imgs = new ArrayList<>();

    }

    // 处理listView数据源
    public void initData() {
        mAdapter = new HotAdapter(mHotDetails, getActivity());
        mListView.setAdapter(mAdapter);
    }

    public void update(List<HotDetail> newDate) {
        if (mAdapter == null) {
            mHotDetails = new ArrayList<>();
            mHotDetails.addAll(newDate);
            mAdapter = new HotAdapter(mHotDetails, getActivity());
            mListView.setAdapter(mAdapter);

        } else {
            mAdapter.addDate(newDate);
        }
    }

    //isInit 来标示是否是第一次取数据
    private void getDate(final boolean isInit) {
        if(isHttpRequesting){
            return;
        }
        isHttpRequesting = true;
        if (isInit) {
            count = 0;
            dots.removeAllViews();
            dot_imgs.clear();
            views.clear();
            mHotDetails.clear();
        }
        HttpUtil util = HttpUtil.getInstance();
        calIndex();
        String url = Constant.getHotUrl(startIndex, endIndex);
        util.getDate(url, new HttpRespon<Hot>(Hot.class) {
            @Override
            public void onError(String msg) {
                isHttpRequesting = false;
                mHandler.sendEmptyMessage(STOP_RESH);
            }

            @Override
            public void onSuccess(Hot hot) {
                isHttpRequesting = false;
                mHandler.sendEmptyMessage(STOP_RESH);
                if (null != hot && null != hot.getT1348647909107()) {
                    count++;
                    List<HotDetail> details = hot.getT1348647909107();
                    //取的是第一页的数据->取出轮播图的数据->删除->显示listView
                    if (isInit) {
                        //取出第0位包含轮播图的数据
                        HotDetail tmp_baner = details.get(0);
                        List<Banner> banners = tmp_baner.getAds();
                        if (banners != null) {
                            mBanners.clear();
                            mBanners.addAll(banners);
                            //获取轮播图片成功

                            //删除轮播图片数据
                            details.remove(0);
                        }

                        mHotDetails.addAll(details);
                        //列表数据加载完成

                        //异步线程无法更改UI
                        mHandler.sendEmptyMessage(INIT_SUCCESS);
                    } else {
                        Message message = mHandler.obtainMessage(UPDATE_SUCCESS);
                        message.obj = details;
                        mHandler.sendMessage(message);

                    }

                }
            }
        });
    }

    public void initHead() {
        mHandler = new MyHandler(this);

        inflater = LayoutInflater.from(getActivity());

        View head = inflater.inflate(R.layout.include_banner, null);
        //将轮播图控件加入listview
        mListView.addHeaderView(head);
        // 滑动监听
        mListView.setOnScrollListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HotDetail detail = mAdapter.getDateByIndex(i - mListView.getHeaderViewsCount());

                Intent intent = new Intent();
                if (TextUtils.isEmpty(detail.getSpecialID())) {
                    intent.setClass(getActivity(), DetailActivity.class);
                    intent.putExtra(DetailActivity.DOCID, detail.getDocid());
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                } else {
//                    intent.setClass(getActivity(), DetailActivity.class);
                }


            }
        });
        viewPager = (ViewPager) head.findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(this);
        bannerTitle = (TextView) head.findViewById(R.id.title);
        dots = (LinearLayout)head.findViewById(R.id.dots);
    }

    public void stopResh() {
        ptr.refreshComplete();
    }

    public void initBanner() {

        if (mBanners != null && mBanners.size() > 0) {
            for (int i = 0; i < mBanners.size(); i++) {
                View view = inflater.inflate(R.layout.item_banner, null);
                views.add(view);

                ImageView dot = new ImageView(getActivity());
                dot.setImageResource(R.drawable.gray_dot);
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                p.setMargins(0, 0,10,0);
                dots.addView(dot, p);
                dot_imgs.add(dot);
            }

            bannerAdapter = new BannerAdapter(this, views, mBanners);
            viewPager.setAdapter(bannerAdapter);
            int half = Integer.MAX_VALUE/2 - (Integer.MAX_VALUE/2)%mBanners.size();
            viewPager.setCurrentItem(half);

            // 设置默认
            setImageDot(0);
            setBannerTitle(0);
        }
    }

    public void calIndex() {
        if (count == 0) {
            startIndex = 0;
            endIndex = startIndex + pageSize;

        } else {
            startIndex = endIndex;
            endIndex = startIndex + pageSize;
        }
    }
    public void setImageDot(int index){
        int size = dot_imgs.size();
        int realPosition = index%size;
        for(int i = 0;i<size;i++){
            ImageView dot = dot_imgs.get(i);
            if(i== realPosition){
                dot.setImageResource(R.drawable.white_dot);
            }else{
                dot.setImageResource(R.drawable.gray_dot);
            }
        }
    }

    public void setBannerTitle(int index){
        int size = dot_imgs.size();
        int realPosition = index%size;
        //显示默认数据
        bannerTitle.setText(mBanners.get(realPosition).getTitle());
    }

//    public void request() {
//        HttpUtil util = HttpUtil.getInstance();
//        util.getDate(Constant.HOT_URL, new HttpRespon<Hot>(Hot.class) {
//            @Override
//            public void onError(String msg) {
//                Log.v("caiiiac", msg);
//            }
//
//            @Override
//            public void onSuccess(Hot hot) {
//
//                if (hot != null && hot.getT1348647909107() != null) {
//                    List<HotDetail> details = hot.getT1348647909107();
//
//                    // 取出轮播图数据
//                    HotDetail tmp_banner = details.get(0);
//                    List<Banner> banners = tmp_banner.getAds();
//                    mBanners.addAll(banners);
//                    details.remove(0);
//
//                    // 列表数据
//                    mHotDetails.addAll(details);
//
//                    // 发送成功信号
//                    mHandler.sendEmptyMessage(INIT_SUCCESS);
//                }
//            }
//        });
//    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

        setImageDot(i);
        setBannerTitle(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    // 滑动状态回调
    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        if (i == SCROLL_STATE_IDLE && isToend) {
            getDate(false);
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        if (absListView.getLastVisiblePosition() == i2 - 1) {
            isToend = true;
        } else {
            isToend = false;
        }
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
                        hot.initBanner();
                        break;
                    case UPDATE_SUCCESS:
                        List<HotDetail> date = (List<HotDetail>) msg.obj;
                        hot.update(date);
                        break;
                    case STOP_RESH:
                        hot.stopResh();
                        break;
                }
            }
        }
    }
}
