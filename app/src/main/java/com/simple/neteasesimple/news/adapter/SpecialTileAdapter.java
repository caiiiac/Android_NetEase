package com.simple.neteasesimple.news.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.simple.neteasesimple.R;

import java.util.ArrayList;
import java.util.Arrays;

public class SpecialTileAdapter extends BaseAdapter {
    ArrayList<String> titles;
    Context mContext;
    boolean isShowDel = false;


    public SpecialTileAdapter(ArrayList<String> titles, Context context) {
        this.titles = titles;
        mContext = context;
    }
    public SpecialTileAdapter(String[] title , Context context){
        this.titles = new ArrayList<>();
        titles.addAll(Arrays.asList(title));
        mContext = context;
    }

    public SpecialTileAdapter(Context context){
        this.titles = new ArrayList<>();
        mContext = context;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = titles.get(position);
        ViewHoder hoder;
        if(convertView==null){
            convertView = View.inflate(mContext, R.layout.item_show,null);
            hoder = new ViewHoder();
            hoder.title =  convertView.findViewById(R.id.title);
            hoder.del =  convertView.findViewById(R.id.del);
            convertView.setTag(hoder);
        }else{
            hoder = (ViewHoder) convertView.getTag();
        }
        hoder.title.setText(name);

        if(isShowDel){
            hoder.del.setVisibility(View.VISIBLE);
        }else{
            hoder.del.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHoder{
        TextView title;
        ImageView del;
    }
}
