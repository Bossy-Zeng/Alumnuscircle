/**
 * @author Zhengfan
 * @date 16.08.28
 * @version 1
 * 功能：这个Activity是本应用中所有网页视图的基础载体。
 */

package com.ac.alumnuscircle.web;

import android.app.Activity;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ac.alumnuscircle.R;

public class BaseWebAct extends Activity {

    private WebView contentWv;
    private Intent receiveIntent;
    private String webUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_basewebact);
        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        contentWv.reload();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        contentWv.loadUrl("");
    }

    private void init(){
        initData();
        initWebView();
    }

    private void initData(){
        receiveIntent = getIntent();
        webUrl = receiveIntent.getStringExtra("webUrl");
    }

    /**
     * 初始化WebView的一些属性。
     */
    private void initWebView(){
        contentWv = (WebView)findViewById(R.id.web_basewebact_content_wv);
        contentWv.clearHistory();
        contentWv.getSettings().setSupportZoom(true);
        contentWv.getSettings().setBuiltInZoomControls(true);
        contentWv.getSettings().setJavaScriptEnabled(true);
        contentWv.getSettings().setUseWideViewPort(true);
        contentWv.getSettings().setLoadWithOverviewMode(true);
        contentWv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        contentWv.requestFocus();
        contentWv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        contentWv.loadUrl(webUrl);
    }
}
