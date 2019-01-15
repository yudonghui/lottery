package com.daxiang.lottery.forum.model;

import android.content.Context;
import android.os.Bundle;

import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.forum.contract.MessageContract;
import com.daxiang.lottery.forum.cxinterface.ICallBack;
import com.daxiang.lottery.utils.HttpUtils2;

/**
 * Created by Android on 2018/8/17.
 */

public class MessageModel implements MessageContract.Model {
    private HttpInterface2 mHttp;

    @Override
    public void getData(Bundle parameters, Context mContext, final ICallBack callBack) {
        if (mHttp == null)
            mHttp = new HttpUtils2(mContext);
        String url = parameters.getString("url");
        parameters.remove("url");
        mHttp.get(url, parameters, new JsonInterface() {
            @Override
            public void callback(String result) {
                callBack.onSuccess(result);
            }

            @Override
            public void onError() {
                callBack.onError();
            }
        });
    }

    @Override
    public void getReplyData(Bundle parameters, Context mContext, final ICallBack callBack) {
        if (mHttp == null)
            mHttp = new HttpUtils2(mContext);
        mHttp.post(Url.POST_REPLY, parameters, new JsonInterface() {
            @Override
            public void callback(String result) {
                callBack.onSuccess(result);
            }

            @Override
            public void onError() {
                callBack.onError();
            }
        });
    }

    @Override
    public void getDeleteData(Bundle parameters, Context mContext, final ICallBack callBack) {
        if (mHttp == null)
            mHttp = new HttpUtils2(mContext);
        mHttp.post(Url.POST_UPDATE, parameters, new JsonInterface() {
            @Override
            public void callback(String result) {
                callBack.onSuccess(result);
            }

            @Override
            public void onError() {
                callBack.onError();
            }
        });
    }
}
