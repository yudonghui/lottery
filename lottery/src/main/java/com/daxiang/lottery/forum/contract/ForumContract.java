package com.daxiang.lottery.forum.contract;

import android.content.Context;
import android.os.Bundle;

import com.daxiang.lottery.forum.base.BaseModel;
import com.daxiang.lottery.forum.base.BasePresenter;
import com.daxiang.lottery.forum.base.BaseView;
import com.daxiang.lottery.forum.cxinterface.ICallBack;
import com.daxiang.lottery.forum.fragment.ForumFragment;
import com.daxiang.lottery.forum.model.ForumModel;

/**
 * Created by Android on 2018/8/2.
 */

public interface ForumContract {
    interface View extends BaseView {
        void getHeaderSuccess(String result);

        void getHeaderError();
    }

    interface Model extends BaseModel {
        void getHeaderData(Bundle parameters, Context mContext, ICallBack callBack);
    }

    abstract class Presenter extends BasePresenter<ForumFragment, ForumModel> {
        public abstract void getHeaderData(Bundle parameters, Context mContext);
    }
}
