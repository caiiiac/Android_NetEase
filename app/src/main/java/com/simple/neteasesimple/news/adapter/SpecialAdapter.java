package com.simple.neteasesimple.news.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.simple.neteasesimple.R;
import com.simple.neteasesimple.news.bean.SpecialItem;

import java.util.ArrayList;

public class SpecialAdapter extends BaseAdapter {
    int title =0;
    int content =1;

    ArrayList<SpecialItem> date;
    Context mContext;
    LayoutInflater mInflater;

    public SpecialAdapter(ArrayList<SpecialItem> date, Context context) {
        this.date = date;
        mInflater =LayoutInflater.from(context) ;
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
        int type = getItemViewType(position);
        SpecialItem itemt  = date.get(position);
        if(type == title){
            //返回一个标题类
            TitleViewHolder viewHolder;
            if(convertView==null){
                convertView = mInflater.inflate(R.layout.item_special_title,null);
                viewHolder = new TitleViewHolder();
                viewHolder.title =  convertView.findViewById(R.id.title);
                convertView.setTag(viewHolder);
            }
            else{
                viewHolder = (TitleViewHolder) convertView.getTag();
            }
            viewHolder.title.setText(itemt.getIndex()+" "+itemt.getTitle_name());
        }else{
            ContentViewHolder viewHolder;

            if(convertView==null){
                convertView = mInflater.inflate(R.layout.item_special,null);
                viewHolder = new ContentViewHolder();
                viewHolder.icon = convertView.findViewById(R.id.img);
                viewHolder.name = convertView.findViewById(R.id.title);
                viewHolder.vote = convertView.findViewById(R.id.reply_count);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ContentViewHolder) convertView.getTag();
            }

            initHoder(viewHolder,itemt);
        }
        return convertView;
    }

    public void initHoder(ContentViewHolder holder,SpecialItem backs ){

        holder.name.setText(backs.getLtitle());
        holder.vote.setText(String.valueOf(backs.getVotecount()));

        holder.icon.setImageURI(backs.getImgsrc());
    }

    @Override
    public int getItemViewType(int position) {
        return date.get(position).isTitle() ? title : content;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    class TitleViewHolder {
        TextView title;
    }
    class ContentViewHolder{
        SimpleDraweeView icon;
        TextView name;
        TextView vote;

    }
}
