package com.simple.neteasesimple.news.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.simple.neteasesimple.R;
import com.simple.neteasesimple.news.bean.Banner;
import com.simple.neteasesimple.news.bean.Hot;
import com.simple.neteasesimple.news.news_inner.HotFragment;


import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {

    HotFragment fragment;
    ArrayList<View> views;
    ArrayList<Banner> banners;
    int size;

    public BannerAdapter(HotFragment fragment, ArrayList<View> view, ArrayList<Banner> banner) {

        this.fragment = fragment;
        this.views = view;
        this.banners = banner;
        size = view.size();

    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realPosition = position%size;

        View tmp = views.get(realPosition);
        SimpleDraweeView imageView = tmp.findViewById(R.id.img);
        Banner banner = banners.get(realPosition);

        imageView.setImageURI("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545045516383&di=433465e23c1159127e2dc3c9bbb89dbd&imgtype=0&src=http%3A%2F%2Fwww.qiaoxun.org%2Fupload_files%2Farticle%2F357%2F12484_1ommw__831cc5feddef475_size24_w926_h435.jpg");

        container.addView(tmp);
        return tmp;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
