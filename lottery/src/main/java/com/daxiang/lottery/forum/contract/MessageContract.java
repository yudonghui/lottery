package com.daxiang.lottery.forum.contract;

import android.content.Context;
import android.os.Bundle;

import com.daxiang.lottery.forum.base.BaseModel;
import com.daxiang.lottery.forum.base.BasePresenter;
import com.daxiang.lottery.forum.base.BaseView;
import com.daxiang.lottery.forum.cxinterface.ICallBack;
import com.daxiang.lottery.forum.fragment.MessageFragment;
import com.daxiang.lottery.forum.model.MessageModel;

/**
 * Created by Android on 2018/8/17.
 */

public interface MessageContract {
    interface View extends BaseView {
        void getSuccess(String result);

        void getError();

        void getReplySuccess(String result);

        void getReplyError();

        void getDeleteSuccess(String result);

        void getDeleteError();
    }

    interface Model extends BaseModel {
        void getData(Bundle parameters, Context mContext, ICallBack callBack);

        void getReplyData(Bundle parameters, Context mContext, ICallBack callBack);

        void getDeleteData(Bundle parameters, Context mContext, ICallBack callBack);
    }

    abstract class Presenter extends BasePresenter<MessageFragment, MessageModel> {
        public abstract void getData(Bundle parameters, Context mContext);

        public abstract void getReplyData(Bundle parameters, Context mContext);

        public abstract void getDeleteData(Bundle parameters, Context mContext);
    }
}
