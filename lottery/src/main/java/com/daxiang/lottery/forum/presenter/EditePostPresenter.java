package com.daxiang.lottery.forum.presenter;

import android.content.Context;
import android.os.Bundle;

import com.daxiang.lottery.forum.activity.EditePostActivity;
import com.daxiang.lottery.forum.contract.EditePostContract;
import com.daxiang.lottery.forum.cxinterface.ICallBack;
import com.daxiang.lottery.forum.model.EditePostModel;

/**
 * Created by Android on 2018/8/3.
 */

public class EditePostPresenter extends EditePostContract.Presenter{
    public EditePostPresenter(EditePostActivity view){
        this.mView=view;
        this.mModel=new EditePostModel();
        setVM(mView,mModel);
    }

    @Override
    public void getCommitData(Bundle parameters, Context mContext) {
        mModel.getCommitData(parameters, mContext, new ICallBack() {
            @Override
            public void onSuccess(String result) {
                mView.getCommitSuccess(result);
            }

            @Override
            public void onError() {
                mView.getCommitError();
            }
        });
    }
}
