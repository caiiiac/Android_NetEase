package com.simple.neteasesimple.news.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.simple.neteasesimple.R;
import com.simple.neteasesimple.news.adapter.DetailImageAdapter;
import com.simple.neteasesimple.news.bean.DetailWebImage;
import com.simple.neteasesimple.until.BaseActivity;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;


public class DetailImageActivity extends BaseActivity {

    ViewPager viewPager;
    ArrayList<View> views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_image);

        views = new ArrayList<>();

        viewPager = findViewById(R.id.viewpager);
        ArrayList<DetailWebImage> images = (ArrayList<DetailWebImage>) getIntent().getSerializableExtra("image");
        if (null != images) {
            for (DetailWebImage tmp:images) {
                View view = View.inflate(this, R.layout.item_detail_image, null);
                views.add(view);
            }
        }

        DetailImageAdapter adapter = new DetailImageAdapter(images, views,this);
        viewPager.setAdapter(adapter);
    }
}
