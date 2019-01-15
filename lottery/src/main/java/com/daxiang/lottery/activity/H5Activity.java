package com.daxiang.lottery.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogBase;
import com.daxiang.lottery.view.TitleBar;

public class H5Activity extends BaseNoTitleActivity {
    private TitleBar mTitleBar;
    private WebView mWebView;
    private String from;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5);
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        if (from == null) from = "";
        url = intent.getStringExtra("url");
        init();
        addListener();

    }

    private void addListener() {
        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        switch (from) {
            case "RecommendFragment":
                url = Url.RECOMMEND_RULE;
                break;
            case "HallFragment":
                url = Url.LESAN_RULE;
                break;
        }
        mTitleBar = (TitleBar) findViewById(R.id.titlBar);
        mTitleBar.setTitle("彩象彩票");
        mTitleBar.setImageTitleVisibility(false);
        mWebView = (WebView) findViewById(R.id.webview);
        final LoadingDialogBase loadingDialogBase = new LoadingDialogBase(mContext);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                loadingDialogBase.dismiss();
                String title = view.getTitle();
                if (TextUtils.isEmpty(title)) {
                    switch (from) {
                        case "RecommendFragment":
                            title = "说明";
                            break;
                        case "HallFragment":
                            title = "乐善奖说明";
                            break;
                        case "jdpay":
                            title = "京东支付";
                            break;
                        default:
                            title = "彩象彩票";
                            break;
                    }
                }
                mTitleBar.setTitle(title);
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                loadingDialogBase.show();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                loadingDialogBase.dismiss();
                super.onReceivedError(view, request, error);
            }
        });
    }
}
