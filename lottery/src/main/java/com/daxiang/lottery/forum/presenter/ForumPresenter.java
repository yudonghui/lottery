package com.daxiang.lottery.forum.presenter;

import android.content.Context;
import android.os.Bundle;

import com.daxiang.lottery.forum.contract.ForumContract;
import com.daxiang.lottery.forum.cxinterface.ICallBack;
import com.daxiang.lottery.forum.fragment.ForumFragment;
import com.daxiang.lottery.forum.model.ForumModel;

/**
 * Created by Android on 2018/8/2.
 */

public class ForumPresenter extends ForumContract.Presenter {
    public ForumPresenter(ForumFragment view) {
        this.mView = view;
        this.mModel = new ForumModel();
        setVM(mView, mModel);
    }

    @Override
    public void getHeaderData(Bundle parameters, Context mContext) {
        mModel.getHeaderData(parameters, mContext, new ICallBack() {
            @Override
            public void onSuccess(String result) {
                mView.getHeaderSuccess(result);
            }

            @Override
            public void onError() {
                mView.getHeaderError();
            }
        });
    }
}
