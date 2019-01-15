package com.daxiang.lottery.forum.presenter;

import android.content.Context;
import android.os.Bundle;

import com.daxiang.lottery.forum.activity.MoreNewsActivity;
import com.daxiang.lottery.forum.contract.MoreNewsContract;
import com.daxiang.lottery.forum.cxinterface.ICallBack;
import com.daxiang.lottery.forum.model.MoreNewsModel;

/**
 * Created by Android on 2018/9/5.
 */

public class MoreNewsPresenter extends MoreNewsContract.Presenter {
    public MoreNewsPresenter(MoreNewsActivity view) {
        this.mView = view;
        this.mModel = new MoreNewsModel();
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

    }
}
