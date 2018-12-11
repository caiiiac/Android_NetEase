package com.simple.neteasesimple.until;

import android.text.TextUtils;

public abstract class HttpRespon<T> {
    //http返回的类型的泛型
    Class<T> t;

    public HttpRespon(Class<T> t){
        this.t = t;
    }

    //失败->调用者->失败的原因
    public abstract void  onError(String msg);
    //成功->返回我需要的类型
    public abstract void  onSuccess(T t);


    public void parse(String json){
        if(TextUtils.isEmpty(json)){
            //请求失败
            onError("连接网络失败");
            return;
        }
        if(t == String.class){
            onSuccess((T)json);
            return;
        }

        //尝试转化json->需要的类型
        T result = JsonUtil.parseJson(json,t);

        //转化成功
        if(result!=null){
            onSuccess(result);
        }else{
            onError("json解析失败");
        }
    }
}

