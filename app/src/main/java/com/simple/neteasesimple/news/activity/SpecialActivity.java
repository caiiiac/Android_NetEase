package com.simple.neteasesimple.news.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.simple.neteasesimple.R;
import com.simple.neteasesimple.news.adapter.SpecialAdapter;
import com.simple.neteasesimple.news.adapter.SpecialTileAdapter;
import com.simple.neteasesimple.news.bean.SpecialItem;
import com.simple.neteasesimple.until.BaseActivity;
import com.simple.neteasesimple.until.Constant;
import com.simple.neteasesimple.until.HttpRespon;
import com.simple.neteasesimple.until.HttpUtil;
import com.simple.neteasesimple.until.JsonUtil;
import com.simple.neteasesimple.until.NoScrollGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;


@ContentView(R.layout.activity_special)
public class SpecialActivity extends BaseActivity {

    public static final String SPECIAL_ID = "special_id";
    String specialId;
    ArrayList<SpecialItem> mItems;

    String banner_src;
    String sname;

    Handler mHandler ;
    SpecialAdapter mAdapter;
    ListView listView;

    SimpleDraweeView banner;
    NoScrollGridView grid;

    ArrayList<String> titles;
    ArrayList<Integer> indexs;
    SpecialTileAdapter s_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setup();
        request();
    }

    private void setup() {
        titles = new ArrayList<>();
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                s_adapter = new SpecialTileAdapter(titles, SpecialActivity.this);
                grid.setAdapter(s_adapter);
                banner.setImageURI(banner_src);

                mAdapter = new SpecialAdapter(mItems, SpecialActivity.this);
                listView.setAdapter(mAdapter);


            }
        };
        mItems = new ArrayList<>();
        indexs = new ArrayList<>();

        View head = View.inflate(this,R.layout.include_special_head,null);


        banner = head.findViewById(R.id.banner);
        grid = head.findViewById(R.id.grid);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int head =  listView.getHeaderViewsCount();
                int index = indexs.get(position);
                listView.setSelection(index+head);
            }
        });

        listView = findViewById(R.id.listView);
        listView.addHeaderView(head);
        //pauseOnScroll->拖动暂停加载图片
        //pauseOnFling->飞一下暂停图片加载
//        listView.setOnScrollListener(new PauseOnScrollListener(mImageLoader,false,true));

    }


    public void request() {
        specialId = getIntent().getStringExtra(SPECIAL_ID);

        String url = Constant.getSpecial(specialId);
        HttpUtil util = HttpUtil.getInstance();
        util.getDate(url, new HttpRespon<String>(String.class) {
            @Override
            public void onError(String msg) {

            }

            @Override
            public void onSuccess(String string) {
                try {
                    JSONObject js = new JSONObject(string);

                    JSONObject real_js = js.optJSONObject(specialId);

                    banner_src = real_js.optString("banner");

                    sname = real_js.optString("sname");

                    JSONArray topics = real_js.optJSONArray("topics");

                    for(int i = 0 ;i<topics.length();i++){
                        JSONObject tmp =  topics.optJSONObject(i);
                        //栏目的标题的数据
                        int index = tmp.optInt("index");
                        String name = tmp.optString("tname");

                        titles.add(name);


                        //封装标题栏的数据
                        SpecialItem item_title = new SpecialItem();
                        item_title.setTitle(true);
                        item_title.setTitle_name(name);
                        item_title.setIndex(index+"/"+topics.length());

                        //加了一个标题
                        mItems.add(item_title);
                        indexs.add(mItems.size()-1);

                        JSONArray docs = tmp.optJSONArray("docs");

                        for(int j = 0 ;j<docs.length();j++){
                            JSONObject item = docs.optJSONObject(j);
                            //每个栏目的对应的数据
                            SpecialItem item_bean  = JsonUtil.parseJson(item.toString(), SpecialItem.class);
                            item_bean.setTitle(false);
                            mItems.add(item_bean);
                        }

                    }

                    mHandler.sendEmptyMessage(0);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
