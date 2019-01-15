package com.daxiang.lottery.forum.model;

import android.content.Context;
import android.os.Bundle;

import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.forum.contract.EditePostContract;
import com.daxiang.lottery.forum.cxinterface.ICallBack;
import com.daxiang.lottery.utils.HttpUtils2;

/**
 * Created by Android on 2018/8/3.
 */

public class EditePostModel implements EditePostContract.Model {
    private HttpInterface2 mHttp;

    @Override
    public void getCommitData(Bundle parameters, Context mContext, final ICallBack callBack) {
        if (mHttp == null)
            mHttp = new HttpUtils2(mContext);
        mHttp.post(Url.POST_ADD, parameters, new JsonInterface() {
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
