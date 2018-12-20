package com.simple.neteasesimple.news.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.simple.neteasesimple.R;
import com.simple.neteasesimple.news.bean.DetailWebImage;

import java.util.ArrayList;

import me.relex.photodraweeview.PhotoDraweeView;

public class DetailImageAdapter extends PagerAdapter {

    ArrayList<DetailWebImage> images;
    ArrayList<View> views;
    Context context;

    public DetailImageAdapter(ArrayList<DetailWebImage> images, ArrayList<View> views, Context context) {
        this.images = images;
        this.views = views;
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        PhotoDraweeView photoDraweeView = view.findViewById(R.id.photo);
        DetailWebImage detailWebImage = images.get(position);
        photoDraweeView.setPhotoUri(Uri.parse(detailWebImage.getSrc().replace("http","https")));
        container.addView(view);
        return  view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }
}
