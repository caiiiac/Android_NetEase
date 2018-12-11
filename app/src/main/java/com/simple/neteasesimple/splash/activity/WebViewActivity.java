package com.simple.neteasesimple.splash.activity;


import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.simple.neteasesimple.R;
import com.simple.neteasesimple.splash.bean.Action;
import com.simple.neteasesimple.until.BaseActivity;

import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_web_view)
public class WebViewActivity extends BaseActivity {

    public static final String ACTION_NAME = "action";

    WebView webview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Action action = (Action) getIntent().getSerializableExtra(ACTION_NAME);
        webview = (WebView) findViewById(R.id.webview);

        //启用javaScript
        webview.getSettings().setJavaScriptEnabled(true);


        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);

        webview.loadUrl(action.getLink_url());

        //处理url重定向不要抛到系统浏览器
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Log.i("caiiiac","onReceivedError");
                super.onReceivedError(view, request, error);

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.i("caiiiac","onReceivedError2 ");

            }
        });
    }

    @Override
    public void onBackPressed() {
        //如果webviwe能够回退到上一个页面
        if(webview.canGoBack()){
            webview.goBack();
            return;
        }
        super.onBackPressed();
    }
}
