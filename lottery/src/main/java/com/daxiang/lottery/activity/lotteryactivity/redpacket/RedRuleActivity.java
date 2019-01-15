package com.daxiang.lottery.activity.lotteryactivity.redpacket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.view.TitleBar;

public class RedRuleActivity extends AppCompatActivity {
    private WebView mWebView;
    private TitleBar mTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_rule);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        mWebView = (WebView) findViewById(R.id.webview);
        mTitleBar = (TitleBar) findViewById(R.id.red_titlebar);
        mTitleBar.setTitle("红包使用规则");
        mTitleBar.setTitleVisibility(true);
        mTitleBar.setImageTitleVisibility(false);
        mWebView.loadUrl(Url.H5REDRULE_URL);
        //mWebView.loadUrl("file:///android_asset/redrule.html");
        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
