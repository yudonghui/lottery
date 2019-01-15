package com.daxiang.lottery.forum.contract;

import android.content.Context;
import android.os.Bundle;

import com.daxiang.lottery.forum.activity.MoreNewsActivity;
import com.daxiang.lottery.forum.base.BaseModel;
import com.daxiang.lottery.forum.base.BasePresenter;
import com.daxiang.lottery.forum.base.BaseView;
import com.daxiang.lottery.forum.cxinterface.ICallBack;
import com.daxiang.lottery.forum.model.MoreNewsModel;

/**
 * Created by Android on 2018/9/5.
 */

public interface MoreNewsContract {
    interface View extends BaseView {
        void getSuccess(String result);

        void getError();
        void getDeleteSuccess(String result);
    }

    interface Model extends BaseModel {
        void getData(Bundle parameters, Context mContext, ICallBack callBack);
        void getDeleteData(Bundle parameters, Context mContext, ICallBack callBack);
    }

    abstract class Presenter extends BasePresenter<MoreNewsActivity, MoreNewsModel> {
        public abstract void getData(Bundle parameters, Context mContext);
        public abstract void getDeleteData(Bundle parameters, Context mContext);
    }
}
