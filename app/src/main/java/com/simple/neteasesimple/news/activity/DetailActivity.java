package com.simple.neteasesimple.news.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simple.neteasesimple.R;
import com.simple.neteasesimple.news.bean.DetailWeb;
import com.simple.neteasesimple.news.bean.DetailWebImage;
import com.simple.neteasesimple.until.BaseActivity;
import com.simple.neteasesimple.until.Constant;
import com.simple.neteasesimple.until.HttpRespon;
import com.simple.neteasesimple.until.HttpUtil;
import com.simple.neteasesimple.until.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class DetailActivity extends BaseActivity {
    public static final String DOCID = "doc";

    public String doc_Id;

    // html内容
    String body;
    MyHandler mHandler;

    private static int INITSUCCESS = 0;

    WebView mWebView;

    int replayCount;

    TextView replayCountTextView;
    LinearLayout share_outer;
    EditText feeback;
    TextView send;
    RelativeLayout parent;
    boolean hasFocus = false;

    ArrayList<DetailWebImage> images;


    @JavascriptInterface
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();
        setup();
        request();
    }

    public void init() {
        mWebView = findViewById(R.id.webView);
        feeback = findViewById(R.id.feeback);
        share_outer = findViewById(R.id.share_outer);
        send = findViewById(R.id.send);
        parent = findViewById(R.id.parent);
    }

    public void setup() {
        final Drawable left = getResources().getDrawable(R.drawable.biz_pc_main_tie_icon);
        left.setBounds(0, 0, 30, 30);
        feeback.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                hasFocus = b;
                if (b) {
                    // 有焦点
                    feeback.setCompoundDrawables(null, null, null, null);
                    feeback.setHint("");
                    share_outer.setVisibility(View.GONE);
                    send.setVisibility(View.VISIBLE);
                } else {
                    // 无焦点
                    feeback.setCompoundDrawables(left, null, null, null);
                    feeback.setHint("跟帖");
                    share_outer.setVisibility(View.VISIBLE);
                    send.setVisibility(View.GONE);
                }
            }
        });

        mWebView.getSettings().setJavaScriptEnabled(true);
        replayCountTextView = findViewById(R.id.replayCcount);
        replayCountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mWebView.addJavascriptInterface(this, "demo");

        // Handler
        mHandler = new MyHandler(this);

    }

    public void request() {
        doc_Id = getIntent().getStringExtra(DOCID);
        HttpUtil util = HttpUtil.getInstance();
        String url = Constant.getDetailUrl(doc_Id);

        util.getDate(url, new HttpRespon<String>(String.class) {
            @Override
            public void onError(String msg) {

            }

            @Override
            public void onSuccess(String json) {
                try {
                    JSONObject js = new JSONObject(json);
                    JSONObject need_js = js.optJSONObject(doc_Id);
                    DetailWeb web = JsonUtil.parseJson(need_js.toString(), DetailWeb.class);

                    if (web != null) {
                        body = web.getBody();
                        images = (ArrayList<DetailWebImage>) web.getImg();
                        for (int i = 0; i < images.size(); i++) {
                            String src = images.get(i).getSrc();
                            String imageTag = "<img src='" + src + "'onclick=\"show()\"/>";
                            String tag = "<!--IMG#" + i + "-->";
                            body = body.replace(tag, imageTag);
                        }
                        //p 标签代表一个段落
                        String titleHTML = "<p><span style='font-size:18px;'><strong>" + web.getTitle() + "</strong></span></p>";// 标题

                        titleHTML = titleHTML+ "<p><span style='color:#666666;'>"+web.getSource()+"&nbsp&nbsp"+web.getPtime()+"</span></p>";//来源与时间

                        body = titleHTML + body;
                        body = "<html><head><style>img{width:100%}</style><script type='text/javascript'>function show(){window.demo.javaShow()} </script></head><body>" + body + "</body></html>";
                        replayCount = web.getReplyCount();
                        mHandler.sendEmptyMessage(INITSUCCESS);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void initWebView() {

        mWebView.loadDataWithBaseURL(null, body, "text/html", "utf-8", null);
        replayCountTextView.setText(String.valueOf(replayCount));
    }

    @JavascriptInterface
    public void javaShow() {
        Log.i("caiiiac", "javaShow"+images.size());
        Intent intent = new Intent();
        intent.setClass(this, DetailImageActivity.class);
        intent.putExtra("image", images);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        if (hasFocus) {
            // 有焦点
            // 失去焦点的做法就是让另外一个控件获取焦点
            mWebView.requestFocus();
        } else {
            finish();
        }
    }

    static class MyHandler extends Handler {
        WeakReference<DetailActivity> activityWeakReference;

        public MyHandler(DetailActivity activity) {
            this.activityWeakReference = new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            DetailActivity detailActivity = activityWeakReference.get();
            if (detailActivity == null) {
                return;
            }

            detailActivity.initWebView();
        }
    }
}
