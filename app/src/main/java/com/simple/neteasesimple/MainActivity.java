package com.simple.neteasesimple;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.simple.neteasesimple.news.fragment.EmptyFragment;
import com.simple.neteasesimple.news.fragment.NewsFragment;
import com.simple.neteasesimple.until.BaseActivity;
import com.simple.neteasesimple.until.FragmentTabHost;

import org.xutils.view.annotation.ContentView;


@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    long lastBacktime;

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

        // 绑定fragment
        tabHost.setup(this, getSupportFragmentManager(), R.id.content);

        for (int i = 0; i < titles.length; i++) {
            TabHost.TabSpec tmp = tabHost.newTabSpec("" + i);
            tmp.setIndicator(getEveryView(this, titles, icons, i));
            tabHost.addTab(tmp, classes[i], null);
        }

        // 监听tab的切换
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Log.i("caiiiac", "tab ==" + tabId);
            }
        });
    }

    public View getEveryView(Context context, String[] titles, int[] icons, int index) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View t_Viwe = inflater.inflate(R.layout.item_title, null);
        TextView textView = (TextView) t_Viwe.findViewById(R.id.title);
        ImageView icon = (ImageView) t_Viwe.findViewById(R.id.icon);

        // 设置标签内容
        textView.setText(titles[index]);
        icon.setImageResource(icons[index]);
        return t_Viwe;
    }

    @Override
    public void onBackPressed() {
        long now = System.currentTimeMillis();
        if (now - lastBacktime < 1000) {
            finish();
        } else {
            Toast.makeText(this, "再次点击退出应用", Toast.LENGTH_SHORT).show();
        }
        lastBacktime = now;
    }
}
