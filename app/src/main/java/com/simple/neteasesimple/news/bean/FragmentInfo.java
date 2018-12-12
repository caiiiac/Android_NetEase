package com.simple.neteasesimple.news.bean;

import android.support.v4.app.Fragment;

public class FragmentInfo {
    Fragment mFragment;
    String title;

    public FragmentInfo(Fragment fragment, String title) {
        mFragment = fragment;
        this.title = title;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "FragmentInfo{" +
                "mFragment=" + mFragment +
                ", title='" + title + '\'' +
                '}';
    }
}
