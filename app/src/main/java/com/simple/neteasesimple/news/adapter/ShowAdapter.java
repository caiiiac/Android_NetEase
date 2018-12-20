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

public class ShowAdapter extends BaseAdapter {

    ArrayList<String> titles;
    Context mContext;
    boolean isShowDel = false;

    public ShowAdapter(ArrayList<String> titles, Context mContext) {
        this.titles = titles;
        this.mContext = mContext;
    }

    public ShowAdapter(String[] title , Context context){
        this.titles = new ArrayList<>();
        titles.addAll(Arrays.asList(title));
        mContext = context;
    }

    public  ShowAdapter(Context context){
        this.titles = new ArrayList<>();
        mContext = context;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int i) {
        return titles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String name = titles.get(i);
        ViewHoder hoder;
        if(view==null){
            view = View.inflate(mContext, R.layout.item_show,null);
            hoder = new ViewHoder();
            hoder.title = (TextView) view.findViewById(R.id.title);
            hoder.del = (ImageView) view.findViewById(R.id.del);
            view.setTag(hoder);
        }else{
            hoder = (ViewHoder) view.getTag();
        }
        hoder.title.setText(name);

        if(isShowDel){
            hoder.del.setVisibility(View.VISIBLE);
        }else{
            hoder.del.setVisibility(View.GONE);
        }
        return view;
    }

    public boolean isShowDel(){
        return  isShowDel;
    }

    public void addADate(String title){
        if(null==titles){
            titles = new ArrayList<>();
        }
        titles.add(title);
        notifyDataSetChanged();
    }

    public String getContent(){
        StringBuilder builder = new StringBuilder();
        for(int i =0;i<titles.size();i++){
            String tmp = titles.get(i);
            builder.append(tmp);
            if(i!=titles.size()-1){
                builder.append("-");
            }
        }
        return  builder.toString();
    }

    public String  delATitle(int index){
        String title = titles.get(index);
        titles.remove(index);
        notifyDataSetChanged();
        return title;
    }

    public void setShowDel(){
        this.isShowDel = !isShowDel;
        notifyDataSetChanged();
    }

    public void setShowDelUnable(){
        this.isShowDel = false;
        notifyDataSetChanged();
    }

    class ViewHoder{
        TextView title;
        ImageView del;
    }
}
