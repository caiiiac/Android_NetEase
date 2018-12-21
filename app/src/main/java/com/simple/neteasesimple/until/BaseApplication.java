package com.simple.neteasesimple.until;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.xutils.x;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import cn.jpush.android.api.JPushInterface;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 网络库
        x.Ext.init(this);
        // 图片加载库
        Fresco.initialize(this);

        JPushInterface.init(this);
        JPushInterface.setDebugMode(true);
//        x.Ext.setDefaultHostnameVerifier(new HostnameVerifier() {
//            @Override
//            public boolean verify(String hostname, SSLSession session) {
//                return true;
//            }
//        });
    }
}
