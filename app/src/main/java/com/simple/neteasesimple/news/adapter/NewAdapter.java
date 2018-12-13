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
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getTitle();
    }
}