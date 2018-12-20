package com.simple.neteasesimple.news.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

import com.simple.neteasesimple.R;
import com.simple.neteasesimple.news.adapter.FeedBackAdapter;
import com.simple.neteasesimple.news.bean.FeedBack;
import com.simple.neteasesimple.news.bean.FeedBacks;
import com.simple.neteasesimple.until.BaseActivity;
import com.simple.neteasesimple.until.Constant;
import com.simple.neteasesimple.until.HttpRespon;
import com.simple.neteasesimple.until.HttpUtil;
import com.simple.neteasesimple.until.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

@ContentView(R.layout.activity_feed_back)
public class FeedBackActivity extends BaseActivity {

    ListView listView;
    ArrayList<FeedBacks> backs;
    FeedBackAdapter mAdapter;
    InnerHander mInnerHander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInnerHander = new InnerHander(this);


        listView = (ListView) findViewById(R.id.listView);

        String docid = getIntent().getStringExtra(DetailActivity.DOCID);
        String url = Constant.getFeedBackUrl(docid);
        backs = new ArrayList<>();
        HttpUtil util = HttpUtil.getInstance();
        util.getDate(url, new HttpRespon<String>(String.class) {
            @Override
            public void onError(String msg) {

            }

            @Override
            public void onSuccess(String string) {
                try {
                    //获取到所有的数据
                    JSONObject js = new JSONObject(string);
                    //取出hotPosts对应的JSONArray
                    JSONArray array = js.optJSONArray("hotPosts");

                    //生成一个标题的数据
                    FeedBacks title = new FeedBacks();
                    title.setTitle(true);
                    title.setTitleS("热门跟帖");
                    backs.add(title);

                    for (int i = 0; i < array.length(); i++) {
                        //生成每一条回复的数据
                        FeedBacks feedBacks = new FeedBacks();


                        //逐条解析数组中的jsonObject
                        JSONObject tmp = array.optJSONObject(i);
                        //迭代器->
                        Iterator<String> keys = tmp.keys();
                        while (keys.hasNext()) {
                            //迭代器-> key->1,2,3,4,5
                            String key = keys.next();
                            //
                            JSONObject everyJson = tmp.optJSONObject(key);
                            FeedBack feedBack = JsonUtil.parseJson(everyJson.toString(), FeedBack.class);
                            //每一个回帖的序号记录下来
                            feedBack.setIndex(Integer.valueOf(key));


                            feedBacks.add(feedBack);
                        }
                        //根据key排序
                        feedBacks.sort();
                        backs.add(feedBacks);
                    }

                    mInnerHander.sendEmptyMessage(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void init() {
        mAdapter = new FeedBackAdapter(backs,this);
        listView.setAdapter(mAdapter);
    }

    static class InnerHander extends Handler {
        WeakReference<FeedBackActivity> act;

        public InnerHander(FeedBackActivity activity) {
            this.act = new WeakReference<FeedBackActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            FeedBackActivity feed = act.get();
            if (feed == null) {
                return;
            }
            feed.init();
        }
    }
}
