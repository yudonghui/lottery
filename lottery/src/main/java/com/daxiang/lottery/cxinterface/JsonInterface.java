package com.daxiang.lottery.cxinterface;

/**
 * Created by Administrator on 2016/8/1 0001.
 */
public interface JsonInterface {
    //网络请求成功
    void callback(String result);
    //网络请求错误
    void onError();
}
