package com.daxiang.lottery.activity.lotteryactivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.daxiang.lottery.activity.BaseActivity;
import com.daxiang.lottery.R;

public class ServiceAgreementActivity extends BaseActivity {
    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_service_agreement);

        initView();
        mWebView.loadUrl("file:///android_asset/service.html");
    }

    private void initView() {
        mTitleBar.setTitle("用户购彩服务协议");
        mTitleBar.setTitleVisibility(true);
        mTitleBar.setImageTitleVisibility(false);
        mWebView= (WebView) findViewById(R.id.webview_agreement);
    }
}
