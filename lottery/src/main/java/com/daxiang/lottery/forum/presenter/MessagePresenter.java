package com.daxiang.lottery.forum.presenter;

import android.content.Context;
import android.os.Bundle;

import com.daxiang.lottery.forum.contract.MessageContract;
import com.daxiang.lottery.forum.cxinterface.ICallBack;
import com.daxiang.lottery.forum.fragment.MessageFragment;
import com.daxiang.lottery.forum.model.MessageModel;

/**
 * Created by Android on 2018/8/17.
 */

public class MessagePresenter extends MessageContract.Presenter {
    public MessagePresenter(MessageFragment view) {
        this.mView = view;
        this.mModel = new MessageModel();
        setVM(mView, mModel);
    }

    @Override
    public void getData(Bundle parameters, Context mContext) {
        mModel.getData(parameters, mContext, new ICallBack() {
            @Override
            public void onSuccess(String result) {
                mView.getSuccess(result);
            }

            @Override
            public void onError() {
                mView.getError();
            }
        });
    }

    @Override
    public void getReplyData(Bundle parameters, Context mContext) {
        mModel.getReplyData(parameters, mContext, new ICallBack() {
            @Override
            public void onSuccess(String result) {
                mView.getReplySuccess(result);
            }

            @Override
            public void onError() {
                mView.getReplyError();
            }
        });
    }

    @Override
    public void getDeleteData(Bundle parameters, Context mContext) {
        mModel.getDeleteData(parameters, mContext, new ICallBack() {
            @Override
            public void onSuccess(String result) {
                mView.getDeleteSuccess(result);
            }

            @Override
            public void onError() {
                mView.getDeleteError();
            }
        });
    }
}
