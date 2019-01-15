package com.daxiang.lottery.forum.contract;

import android.content.Context;
import android.os.Bundle;

import com.daxiang.lottery.forum.activity.PostDetailActivity;
import com.daxiang.lottery.forum.base.BaseModel;
import com.daxiang.lottery.forum.base.BasePresenter;
import com.daxiang.lottery.forum.base.BaseView;
import com.daxiang.lottery.forum.cxinterface.ICallBack;
import com.daxiang.lottery.forum.model.PostDetailModel;

/**
 * Created by Android on 2018/8/21.
 */

public interface PostDetailContract {
    interface View extends BaseView {
        void getPostDetailSuccess(String result);

        void getPostDetailError();

        void getCommentSuccess(String result);

        void getCommentError();

        void sendCommentSuccess(String result);

        void sendCommentError();

        void sendReplySuccess(String result);

        void sendReplyError();

        void getMatchBySessionSuccess(String result);

        void getMatchBySessionError();
    }

    interface Model extends BaseModel {
        void getPostDetailData(Bundle parameters, Context mContext, ICallBack callBack);

        void getCommentData(Bundle parameters, Context mContext, ICallBack callBack);

        void sendCommentData(Bundle parameters, Context mContext, ICallBack callBack);

        void sendReplyData(Bundle parameters, Context mContext, ICallBack callBack);

        void getMatchBySession(Bundle parameters, Context mContext, ICallBack callBack);
    }

    abstract class Presenter extends BasePresenter<PostDetailActivity, PostDetailModel> {
        public abstract void getPostDetailData(Bundle parameters, Context mContext);

        public abstract void getCommentData(Bundle parameters, Context mContext);

        public abstract void sendCommentData(Bundle parameters, Context mContext);

        public abstract void sendReplyData(Bundle parameters, Context mContext);

        public abstract void getMatchBySession(Bundle parameters, Context mContext);
    }
}
