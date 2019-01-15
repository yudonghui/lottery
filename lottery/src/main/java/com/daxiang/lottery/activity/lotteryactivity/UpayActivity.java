package com.daxiang.lottery.activity.lotteryactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.daxiang.lottery.R;

public class UpayActivity extends AppCompatActivity {
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upay);
        Intent intent = getIntent();
        String uPayReturn = intent.getStringExtra("uPayReturn");
        mWebView = (WebView) findViewById(R.id.webview_upay);
        mWebView.loadUrl(uPayReturn);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // LogUtils.e("url网址：",url);
                if (url.contains("https://www.baidu.com")) {
                  /*  Intent intent1=new Intent(UpayActivity.this,RechargeActivity.class);
                    startActivity(intent1);*/
                    Intent intent1 = new Intent();
                    setResult(2223, intent1);
                    finish();
                }
            }

        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            setResult(311, intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
