package com.simple.neteasesimple.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.simple.neteasesimple.R;
import com.simple.neteasesimple.news.bean.FeedBack;
import com.simple.neteasesimple.news.bean.FeedBacks;

import java.util.ArrayList;

public class FeedBackAdapter extends BaseAdapter {
    int type_title = 0;
    int type_content = 1;

    ArrayList<FeedBacks> date;
    LayoutInflater mInflater;

    public FeedBackAdapter(ArrayList<FeedBacks> date, Context context) {
        this.date = date;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return date.size();
    }

    @Override
    public Object getItem(int i) {
        return date.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        //类型
        int type = getItemViewType(position);
        if(type == type_title){
            //返回一个标题类
            TitleViewHolder viewHolder;
            if(convertView==null){
                convertView = mInflater.inflate(R.layout.item_feed_title,null);
                viewHolder = new TitleViewHolder();
                viewHolder.title = (TextView) convertView.findViewById(R.id.title);
                convertView.setTag(viewHolder);
            }
            else{
                viewHolder = (TitleViewHolder) convertView.getTag();

            }
        }else{
            ContentViewHolder viewHolder;
            FeedBacks feedBacks = date.get(position);
            if(convertView==null){
                convertView = mInflater.inflate(R.layout.item_feedback,null);
                viewHolder = new ContentViewHolder();
                viewHolder.icon = convertView.findViewById(R.id.profile_image);
                viewHolder.name = convertView.findViewById(R.id.net_name);
                viewHolder.from = convertView.findViewById(R.id.net_from);
                viewHolder.content = convertView.findViewById(R.id.content);
                viewHolder.vote = convertView.findViewById(R.id.like);

                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ContentViewHolder) convertView.getTag();
            }

            initHoder(viewHolder,feedBacks);
        }
        return convertView;
    }
    public void initHoder(ContentViewHolder holder,FeedBacks backs ){
        FeedBack back =  backs.getLastDate();
        holder.name.setText(back.getN());
        holder.from.setText(back.getF());
        holder.content.setText(back.getB());
        holder.vote.setText(back.getV());

        holder.icon.setImageURI(back.getTimg());
    }
    //返回页面数据的类型
    @Override
    public int getViewTypeCount() {
        return 2;
    }


    //返回什么类型的view
    @Override
    public int getItemViewType(int position) {
        //根据每一条数据的isTitle()进行判断,如果是true
        FeedBacks feedBacks =  date.get(position);
        if(feedBacks.isTitle()){
            return   type_title;
        }else{
            return  type_content;
        }
    }

    class TitleViewHolder {
        TextView title;
    }
    class ContentViewHolder{
        SimpleDraweeView icon;
        TextView name;
        TextView from;
        TextView time;
        TextView content;
        TextView vote;

    }
}
