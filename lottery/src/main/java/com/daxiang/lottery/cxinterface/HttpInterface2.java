package com.daxiang.lottery.cxinterface;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by Administrator on 2016/12/5 0005.
 */
public interface HttpInterface2 {
    void jsonByUrl(String url, JsonInterface mJsonInterface);
    void post(String url, Bundle params, JsonInterface mJsonInterface);
    void postLaunch(String url,Bundle params,JsonInterface mJsonInterface);
    //童
    void postH(String url, Bundle params, JsonInterface mJsonInterface);
    void get(String url, Bundle params, JsonInterface mJsonInterface);
    void updateFile(String url, Context mContext, JsonInterface mJsonInterface);
}
