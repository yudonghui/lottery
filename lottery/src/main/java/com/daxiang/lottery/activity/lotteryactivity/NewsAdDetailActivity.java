package com.daxiang.lottery.activity.lotteryactivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BaseActivity;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.NewsAdDetailData;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.google.gson.Gson;

public class NewsAdDetailActivity extends BaseActivity {
    // TextView mTextView;
    WebView mWebView;
    int fragmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_news_ad_detail);

        Intent intent = getIntent();
        fragmentId = intent.getIntExtra("fragmentId", 0);
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitle("新闻内容");
        mTitleBar.setTitleVisibility(true);
        // mTextView= (TextView) findViewById(R.id.news_content);
        mWebView = (WebView) findViewById(R.id.webview);
        addData();
    }

    private void addData() {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(this);
        String url = Url.NEWS_DETAIL_URL + "?fragmentId=" + fragmentId;
        // mWebView.loadUrl(url);
        HttpInterface2 mHttp = new HttpUtils2(this);
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("timeStamp", System.currentTimeMillis() + "");
        params.putInt("id", fragmentId);
        mHttp.post(Url.NEWS_DETAIL_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                Gson gson = new Gson();
                NewsAdDetailData newsAdDetailData = gson.fromJson(result, NewsAdDetailData.class);
                if (newsAdDetailData.getCode() == 0) {
                    if (newsAdDetailData.getData().getContent() != null) {
                        String content = newsAdDetailData.getData().getContent();
                        content = content.replace("\r\n", "<br />");
                        content = content.replace("/../", "/");
                        String html = "<head><style>img{max-width:100%;}</style></head>" + content;
                        mWebView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
                    }
                }
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
            }
        });
    }
}
