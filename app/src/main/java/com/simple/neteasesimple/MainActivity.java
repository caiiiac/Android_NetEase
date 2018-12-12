package com.simple.neteasesimple;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;

import com.simple.neteasesimple.news.fragment.EmptyFragment;
import com.simple.neteasesimple.news.fragment.NewsFragment;
import com.simple.neteasesimple.until.BaseActivity;

import org.xutils.view.annotation.ContentView;


@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FragmentTabHost tabHost = (FragmentTabHost) findViewById(R.id.tab_Host);

        // 获取tab的标题
        String[] titles = getResources().getStringArray(R.array.tab_title);
        // 背景图
        int[] icons = new int[]{
                R.drawable.news_selector,
                R.drawable.reading_selector,
                R.drawable.video_selector,
                R.drawable.topic_selector,
                R.drawable.mine_selector
        };

        Class[] classes = new Class[]{
                NewsFragment.class,
                EmptyFragment.class,
                EmptyFragment.class,
                EmptyFragment.class,
                EmptyFragment.class
        };

        //绑定fragment
        for (int i = 0; i < titles.length; i++) {
            TabHost.TabSpec tmp = tabHost.newTabSpec("" + i);
            tmp.setIndicator(getE)
        }
    }

    public void getEveryView(Context context, String[] titles, int[] icons, int index) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View t_Viwe = inflater.inflate(R.layout.)
    }
}
