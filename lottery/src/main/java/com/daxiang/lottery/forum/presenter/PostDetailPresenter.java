package com.daxiang.lottery.forum.presenter;

import android.content.Context;
import android.os.Bundle;

import com.daxiang.lottery.forum.activity.PostDetailActivity;
import com.daxiang.lottery.forum.contract.PostDetailContract;
import com.daxiang.lottery.forum.cxinterface.ICallBack;
import com.daxiang.lottery.forum.model.PostDetailModel;

/**
 * Created by Android on 2018/8/21.
 */

public class PostDetailPresenter extends PostDetailContract.Presenter {
    public PostDetailPresenter(PostDetailActivity view) {
        this.mView = view;
        this.mModel = new PostDetailModel();
        setVM(mView, mModel);
    }

    @Override
    public void getPostDetailData(Bundle parameters, Context mContext) {
        mModel.getPostDetailData(parameters, mContext, new ICallBack() {
            @Override
            public void onSuccess(String result) {
                mView.getPostDetailSuccess(result);
            }

            @Override
            public void onError() {
                mView.getPostDetailError();
            }
        });
    }

    @Override
    public void getCommentData(Bundle parameters, Context mContext) {
        mModel.getCommentData(parameters, mContext, new ICallBack() {
            @Override
            public void onSuccess(String result) {
                mView.getCommentSuccess(result);
            }

            @Override
            public void onError() {
                mView.getCommentError();
            }
        });
    }

    @Override
    public void sendCommentData(Bundle parameters, Context mContext) {
        mModel.sendCommentData(parameters, mContext, new ICallBack() {
            @Override
            public void onSuccess(String result) {
                mView.sendCommentSuccess(result);
            }

            @Override
            public void onError() {
                mView.sendCommentError();
            }
        });
    }

    @Override
    public void sendReplyData(Bundle parameters, Context mContext) {
        mModel.sendReplyData(parameters, mContext, new ICallBack() {
            @Override
            public void onSuccess(String result) {
                mView.sendReplySuccess(result);
            }

            @Override
            public void onError() {
                mView.sendReplyError();
            }
        });
    }

    @Override
    public void getMatchBySession(Bundle parameters, Context mContext) {
        mModel.getMatchBySession(parameters, mContext, new ICallBack() {
            @Override
            public void onSuccess(String result) {
                mView.getMatchBySessionSuccess(result);
            }

            @Override
            public void onError() {
                mView.getMatchBySessionError();
            }
        });
    }
}
