package com.daxiang.lottery.forum.contract;

import android.content.Context;
import android.os.Bundle;

import com.daxiang.lottery.forum.activity.EditePostActivity;
import com.daxiang.lottery.forum.base.BaseModel;
import com.daxiang.lottery.forum.base.BasePresenter;
import com.daxiang.lottery.forum.base.BaseView;
import com.daxiang.lottery.forum.cxinterface.ICallBack;
import com.daxiang.lottery.forum.model.EditePostModel;

/**
 * Created by Android on 2018/8/3.
 */

public interface EditePostContract {
    interface View extends BaseView {
        void getCommitSuccess(String result);

        void getCommitError();
    }

    interface Model extends BaseModel {
        void getCommitData(Bundle parameters, Context mContext, ICallBack callBack);
    }

    abstract class Presenter extends BasePresenter<EditePostActivity, EditePostModel> {
        public abstract void getCommitData(Bundle parameters, Context mContext);
    }
}
