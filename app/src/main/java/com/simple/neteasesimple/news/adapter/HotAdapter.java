package com.simple.neteasesimple.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.simple.neteasesimple.news.bean.HotDetail;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HotAdapter extends BaseAdapter {

    ArrayList<HotDetail> mHotDetails;
    LayoutInflater mInflater;

    public HotAdapter(ArrayList<HotDetail> hotDetails, Context context) {
        mHotDetails = hotDetails;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mHotDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return mHotDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }


    class ViewHoder {
        ImageView icon;
        TextView title;
        TextView source;
        TextView reply_count;
        TextView special;
    }
}
