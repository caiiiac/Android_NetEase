package com.simple.neteasesimple.news.bean;

import java.util.List;

public class DetailWeb {

    String body;
    List<DetailWebImage> img;
    String title;
    String source;
    String ptime;
    int replyCount;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<DetailWebImage> getImg() {
        return img;
    }

    public void setImg(List<DetailWebImage> img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    @Override
    public String toString() {
        return "DetailWeb{" +
                "body='" + body + '\'' +
                ", img=" + img +
                ", title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", ptime='" + ptime + '\'' +
                ", replyCount=" + replyCount +
                '}';
    }
}
