package com.daxiang.lottery.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.daxiang.lottery.R;

/**
 * Created by Android on 2017/12/25.
 */

public class RuleFragment extends Fragment {
    private View mInflate;
    private Context mContext;
    private WebView mWebView;
    private String mLotcode;
    private boolean isFirst = true;

    public void setData(String mLotcode,Context mContext) {
        this.mLotcode = mLotcode;
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //  mContext = getContext();
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_rule, null);
            initView();
            WebSettings settings = mWebView.getSettings();
            settings.setLoadWithOverviewMode(true);
            settings.setUseWideViewPort(true);
            mWebView.loadUrl("file:///android_asset/"+mLotcode+".html");
        }
        return mInflate;
    }

    private void initView() {
        mWebView = (WebView) mInflate.findViewById(R.id.webview);
    }

   /* @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirst) {
            isFirst = false;
            addData();
        }
    }

    private LoadingDialogUtils loadingDialogUtils;

    private void addData() {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loadingDialogUtils = new LoadingDialogUtils(mContext);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadingDialogUtils.setDimiss();
            }
        });
        mWebView.loadUrl(Url.LOTTERY_RULE + mLotcode + "/m");
    }*/
}
