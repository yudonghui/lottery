package com.daxiang.lottery.forum.presenter;

import android.content.Context;
import android.os.Bundle;

import com.daxiang.lottery.forum.contract.PostContract;
import com.daxiang.lottery.forum.cxinterface.ICallBack;
import com.daxiang.lottery.forum.fragment.AllPostFragment;
import com.daxiang.lottery.forum.model.PostModel;

/**
 * Created by Android on 2018/8/3.
 */

public class PostPresenter extends PostContract.Presenter {
    public PostPresenter(AllPostFragment view) {
        this.mView = view;
        this.mModel = new PostModel();
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
    public void getDeleteData(Bundle parameters, Context mContext) {
        mModel.getDeleteData(parameters, mContext, new ICallBack() {
            @Override
            public void onSuccess(String result) {
                mView.getDeleteSuccess(result);
            }

            @Override
            public void onError() {
                mView.getError();
            }
        });
    }
}
