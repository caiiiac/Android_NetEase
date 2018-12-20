package com.simple.neteasesimple.news.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.simple.neteasesimple.news.bean.FragmentInfo;

import java.util.ArrayList;

public class NewAdapter extends FragmentStatePagerAdapter {

    ArrayList<FragmentInfo> mFragments;

    public NewAdapter(FragmentManager manager, ArrayList<FragmentInfo> infos) {
        super(manager);

        this.mFragments = infos;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i).getFragment();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getTitle();
    }

    // 需要刷新ViewPager的话,我们必须重写getItemPosition->POSITION_NONE
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


    public void setDate(ArrayList<FragmentInfo> mFragments){
        this.mFragments = mFragments;
        notifyDataSetChanged();
    }
}
