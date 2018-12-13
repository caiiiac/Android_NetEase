package com.simple.neteasesimple.news.bean;

import java.util.List;

public class Hot {
    List<HotDetail> hotDetails;

    public List<HotDetail> getHotDetails() {
        return hotDetails;
    }

    public void setHotDetails(List<HotDetail> hotDetails) {
        this.hotDetails = hotDetails;
    }

    @Override
    public String toString() {
        return "Hot{" +
                "hotDetails=" + hotDetails +
                '}';
    }
}
