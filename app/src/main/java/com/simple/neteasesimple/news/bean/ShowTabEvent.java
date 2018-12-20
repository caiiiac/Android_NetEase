package com.simple.neteasesimple.news.bean;

public class ShowTabEvent {
    boolean isShow = false;

    public ShowTabEvent(boolean isShow) {
        this.isShow = isShow;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
