package com.simple.neteasesimple.news.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FeedBacks {
    ArrayList<FeedBack> hot;

    boolean isTitle =false;
    String titleS ;

    public FeedBacks() {
        this.hot = new ArrayList<>();
    }

    public void add(FeedBack feedBack){
        hot.add(feedBack);
    }

    public void sort(){
        Collections.sort(hot,new FeedBackSort());
    }


    public ArrayList<FeedBack> getHot() {
        return hot;
    }

    public void setHot(ArrayList<FeedBack> hot) {
        this.hot = hot;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    public String getTitleS() {
        return titleS;
    }

    public void setTitleS(String titleS) {
        this.titleS = titleS;
    }

    @Override
    public String toString() {
        return "FeedBacks{" +
                "isTitle=" + isTitle +
                ", titleS='" + titleS + '\'' +
                '}';
    }

    public FeedBack  getLastDate(){
        return  hot.get(hot.size()-1);
    }

    //比较器
    class  FeedBackSort implements Comparator {

        @Override
        public int compare(Object lhs, Object rhs) {
            if(((FeedBack)lhs).getIndex()>((FeedBack)rhs).getIndex()){
                //大于
                return  1;
            }else if(((FeedBack)lhs).getIndex()==((FeedBack)rhs).getIndex()){
                // 相等
                return  0;
            }else {
                //小于
                return  -1;
            }

        }
    }
}
