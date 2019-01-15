package com.daxiang.lottery.forum.model;

import android.content.Context;
import android.os.Bundle;

import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.forum.contract.PostDetailContract;
import com.daxiang.lottery.forum.cxinterface.ICallBack;
import com.daxiang.lottery.utils.HttpUtils2;

/**
 * Created by Android on 2018/8/21.
 */

public class PostDetailModel implements PostDetailContract.Model {
    private HttpInterface2 mHttp;

    @Override
    public void getPostDetailData(Bundle parameters, Context mContext, final ICallBack callBack) {
        if (mHttp == null) mHttp = new HttpUtils2(mContext);
        mHttp.get(Url.POST_DETAIL, parameters, new JsonInterface() {
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
    public void getCommentData(Bundle parameters, Context mContext, final ICallBack callBack) {
        if (mHttp == null) mHttp = new HttpUtils2(mContext);
        mHttp.get(Url.POST_COMMENT_FORM, parameters, new JsonInterface() {
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
    public void sendCommentData(Bundle parameters, Context mContext, final ICallBack callBack) {
        if (mHttp == null) mHttp = new HttpUtils2(mContext);
        mHttp.post(Url.POST_COMMENT, parameters, new JsonInterface() {
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
    public void sendReplyData(Bundle parameters, Context mContext, final ICallBack callBack) {
        if (mHttp == null) mHttp = new HttpUtils2(mContext);
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
    public void getMatchBySession(Bundle parameters, Context mContext, final ICallBack callBack) {
        if (mHttp == null) mHttp = new HttpUtils2(mContext);
        mHttp.get(Url.MATCH_BY_SESSION, parameters, new JsonInterface() {
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
