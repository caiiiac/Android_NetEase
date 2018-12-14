package com.simple.neteasesimple.news.fragment;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.simple.neteasesimple.R;
import com.simple.neteasesimple.news.bean.Banner;
import com.simple.neteasesimple.news.bean.Hot;
import com.simple.neteasesimple.news.news_inner.HotFragment;
import com.simple.neteasesimple.until.GlideApp;

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
        ImageView imageView = tmp.findViewById(R.id.img);
        Banner banner = banners.get(realPosition);

//        GlideApp.with(fragment)
//                .load(banner.getUrl())
//                .into(imageView);

        container.addView(tmp);
        return tmp;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
