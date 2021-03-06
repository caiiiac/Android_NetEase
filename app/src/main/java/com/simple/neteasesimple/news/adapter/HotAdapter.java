package com.simple.neteasesimple.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.simple.neteasesimple.R;
import com.simple.neteasesimple.news.bean.HotDetail;



import java.util.ArrayList;
import java.util.List;

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
        ViewHoder hoder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_hot, null);

            hoder = new ViewHoder();
            hoder.icon = convertView.findViewById(R.id.img);
            hoder.title = convertView.findViewById(R.id.title);
            hoder.source = convertView.findViewById(R.id.source);
            hoder.reply_count = convertView.findViewById(R.id.reply_count);
            hoder.special = convertView.findViewById(R.id.special);

            convertView.setTag(hoder);
        } else {
            hoder = (ViewHoder) convertView.getTag();
        }

        // 设置内容
        setupContent(hoder, mHotDetails.get(position));

        return convertView;
    }

    public void setupContent(ViewHoder hoder, HotDetail detail) {

        hoder.title.setText(detail.getTitle());
        hoder.source.setText(detail.getSource());
        if (detail.getReplyCount() == 0) {
            hoder.special.setText("置顶");
            hoder.special.setVisibility(View.VISIBLE);
            hoder.reply_count.setVisibility(View.GONE);
        } else {
            hoder.reply_count.setText(detail.getReplyCount() + "跟帖");
            hoder.reply_count.setVisibility(View.VISIBLE);
            hoder.special.setVisibility(View.GONE);
        }

        String imgUrl = detail.getImg();
        if (detail.getImg() != null) {
            hoder.icon.setImageURI(imgUrl.replace("http", "https"));
        } else {
            hoder.icon.setImageResource(R.drawable.base_common_default_icon_small);
        }

    }

    public void addDate(List<HotDetail> add) {
        if(mHotDetails==null){
            mHotDetails = new ArrayList<>();
        }
        mHotDetails.addAll(add);
        notifyDataSetChanged();
    }

    public HotDetail getDateByIndex(int index){
        HotDetail detail = mHotDetails.get(index);
        return  detail;
    }

    class ViewHoder {
        SimpleDraweeView icon;
        TextView title;
        TextView source;
        TextView reply_count;
        TextView special;
    }
}
