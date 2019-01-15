package com.daxiang.lottery.forum.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.GodInfoActivity;
import com.daxiang.lottery.activity.LoginActivity;
import com.daxiang.lottery.common.NetWorkData;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.forum.activity.PostDetailActivity;
import com.daxiang.lottery.forum.adapter.MessageAdapter;
import com.daxiang.lottery.forum.base.BaseMvpFragment;
import com.daxiang.lottery.forum.bean.MessageBean;
import com.daxiang.lottery.forum.bean.UserBean;
import com.daxiang.lottery.forum.contract.MessageContract;
import com.daxiang.lottery.forum.presenter.MessagePresenter;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.view.NoDataView;
import com.google.gson.Gson;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnLoadMoreListener;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

/**
 * Created by Android on 2018/8/24.
 */

public class MessageFragment extends BaseMvpFragment<MessagePresenter> implements MessageContract.View {
    private SmartRefreshLayout mRefresh;
    private RecyclerView mRecyclerView;
    private TextView mDelete;
    private View inflate;
    private TextView mShare;
    private Button mCancelPop;
    private Dialog mDialog;
    private Window window;
    private NoDataView mNoData;
    private ScrollView mScrollView;
    private LinearLayout mLl_editor;
    private EditText mContent;
    private TextView mSend;

    private MessageAdapter mRvAdapter;
    List<MessageBean.DataBean.ListBean> mMessageList = new ArrayList<>();

    private int pn = 1;
    private int ps = 10;
    private int totalPage = 1;
    private int type;//0帖子消息，1系统消息

    public void setData(int type) {
        this.type = type;
    }

    @Override
    public int getInflateId() {
        return R.layout.fragment_message;
    }

    @Override
    public void init() {
        mPresenter = new MessagePresenter(this);
        mRefresh = (SmartRefreshLayout) view.findViewById(R.id.refresh);
        mNoData = (NoDataView) view.findViewById(R.id.no_data);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        mScrollView = (ScrollView) view.findViewById(R.id.scrollView);
        mLl_editor = (LinearLayout) view.findViewById(R.id.ll_editor);
        mContent = (EditText) view.findViewById(R.id.sendContent);
        mSend = (TextView) view.findViewById(R.id.send);

        inflate = View.inflate(mContext, R.layout.item_popup, null);
        mDelete = (TextView) inflate.findViewById(R.id.buttonCamera);
        mShare = (TextView) inflate.findViewById(R.id.buttonPhoto_selector);
        mCancelPop = (Button) inflate.findViewById(R.id.buttoncancle);
        mDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
        window = mDialog.getWindow();
        mDialog.setContentView(inflate);
        mDelete.setText("删除");
        mShare.setText("分享");

        LinearLayoutManager linearLayout = new LinearLayoutManager(mContext);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayout);

        mRvAdapter = new MessageAdapter(mContext, mMessageList, ClickListener);
        mRecyclerView.setAdapter(mRvAdapter);

        addData();
    }

    private void addData() {
        Bundle parameters = new Bundle();
        if (type == 0) {
            parameters.putString("url", Url.POST_SMS_FORM);
        } else {
            parameters.putString("url", Url.SYSTEM_SMS_FORM);
        }
        parameters.putString("userId", LotteryApp.uid);
        parameters.putString("pageIndex", pn + "");
        parameters.putString("pageSize", ps + "");
        mPresenter.getData(parameters, mContext);
    }

    @Override
    public void addListener() {
        mDelete.setOnClickListener(DeleteListener);
        mShare.setOnClickListener(ShareListener);
        mCancelPop.setOnClickListener(CancelPopListener);
        mRefresh.setOnRefreshListener(RefreshListener);
        mRefresh.setOnLoadMoreListener(LoadMoreListener);
        mSend.setOnClickListener(SendListener);
        mScrollView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                //old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
                //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > DisplayUtil.getDisplayHeight() / 3)) {
                    // mLlEdit.setVisibility(View.VISIBLE);
                    //Toast.makeText(getContext(), "监听到软键盘弹起...", Toast.LENGTH_SHORT).show();
                    handler.sendEmptyMessage(1);
                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > DisplayUtil.getDisplayHeight() / 3)) {
                    // mLlEdit.setVisibility(View.GONE);
                    //Toast.makeText(getContext(), "监听到软件盘关闭...", Toast.LENGTH_SHORT).show();
                    handler.sendEmptyMessage(2);
                }
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://监听到软键盘弹起
                    mLl_editor.setVisibility(View.VISIBLE);
                    break;
                case 2://监听到软件盘关闭
                    mLl_editor.setVisibility(View.GONE);
                    break;
            }
        }
    };
    OnRefreshListener RefreshListener = new OnRefreshListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            mMessageList.clear();
            pn = 1;
            addData();
        }
    };

    OnLoadMoreListener LoadMoreListener = new OnLoadMoreListener() {
        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            if (pn < totalPage) {
                pn++;
                addData();
            } else {
                mRefresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, "已经是最后一页", Toast.LENGTH_SHORT).show();
                        mRefresh.finishLoadMore();
                    }
                }, 1000);
            }
        }
    };

    private int selectPosition;
    MessageAdapter.ClickCallBack ClickListener = new MessageAdapter.ClickCallBack() {
        @Override
        public void callBack(int position, int types) {
            selectPosition = position;
            MessageBean.DataBean.ListBean listBean = mMessageList.get(position);
            int postId = listBean.getPostId();
            // 1更多，2中间帖子，3回复
            if (types == 1) {//1更多
                mDialog.show();
                window.setGravity(Gravity.BOTTOM);
            } else if (types == 2) {//2中间帖子
                Bundle bundle = new Bundle();
                bundle.putString("postId", postId + "");
                startActivity(PostDetailActivity.class, bundle);
            } else if (types == 3) {//3回复
                mContent.setFocusable(true);
                mContent.setFocusableInTouchMode(true);
                mContent.requestFocus();
                toggleInput();
            }
        }

        @Override
        public void onUserClick(String commentsId, UserBean bean) {
            if (bean == null) return;
            Bundle bundle = new Bundle();
            bundle.putString("userId", bean.getUserId());
            startActivity(GodInfoActivity.class, bundle);
        }
    };
    View.OnClickListener DeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog.dismiss();
            MessageBean.DataBean.ListBean listBean = mMessageList.get(selectPosition);
            int id = listBean.getPostId();
            Bundle params = new Bundle();
            params.putString("id", id + "");
            params.putString("auditFlag", "4");
            params.putString("token", LotteryApp.token);
            params.putString("timeStamp", System.currentTimeMillis() + "");
            mPresenter.getDeleteData(params, mContext);
        }
    };
    View.OnClickListener ShareListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog.dismiss();
            MessageBean.DataBean.ListBean listBean = mMessageList.get(selectPosition);
            Toast.makeText(mContext, "分享条目" + selectPosition + "帖子id" + id, Toast.LENGTH_SHORT).show();
        }
    };
    View.OnClickListener CancelPopListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog.dismiss();
        }
    };
    View.OnClickListener SendListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (!LotteryApp.isLogin) {
                startActivity(LoginActivity.class);
                return;
            }
            if (TextUtils.isEmpty(LotteryApp.nikeName)) {
                Toast.makeText(mContext, "无法获取您的昵称", Toast.LENGTH_SHORT).show();
                new NetWorkData(mContext).refreshUserInfo();
                return;
            }

            String content = mContent.getText().toString();
            if (TextUtils.isEmpty(content)) {
                Toast.makeText(mContext, "请输入回复内容", Toast.LENGTH_SHORT).show();
                return;
            }
            MessageBean.DataBean.ListBean listBean = mMessageList.get(selectPosition);
            int postId = listBean.getPostId();
            int commentId = listBean.getCommentId();
            int toReplyId = listBean.getReplyId();
            String toUserId = listBean.getUserId();
            String toUserName = listBean.getUserName();
            int replyFlag = listBean.getReplyFlag();
            Bundle parameters = new Bundle();
            parameters.putString("commentsId", commentId + "");
            parameters.putString("postId", postId + "");
            parameters.putString("token", LotteryApp.token);
            parameters.putString("userName", LotteryApp.nikeName);
            parameters.putString("toReplyId", toReplyId + "");
            parameters.putString("toUserId", toUserId);
            parameters.putString("toUserName", toUserName);
            parameters.putString("replyContent", content);
            parameters.putString("replyFlag", replyFlag == 3 ? "1" : "0");
            parameters.putString("timeStamp", System.currentTimeMillis() + "");
            showLoading();
            mPresenter.getReplyData(parameters, mContext);
        }
    };
    

    @Override
    public void getSuccess(String result) {
        mRefresh.finishRefresh();
        mRefresh.finishLoadMore();
        Gson gson = new Gson();
        MessageBean messageBean = gson.fromJson(result, MessageBean.class);
        int code = messageBean.getCode();
        String msg = messageBean.getMsg();
        if (code == 0) {
            MessageBean.DataBean data = messageBean.getData();
            totalPage = data.getTotalPages();
            mMessageList.addAll(data.getList());
        } else {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
        mRvAdapter.notifyDataSetChanged();
        if (mMessageList.size() > 0) {
            mNoData.setVisibility(View.GONE);
        } else {
            mNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getError() {
        mRefresh.finishRefresh();
        mRefresh.finishLoadMore();
        if (mMessageList.size() > 0) {
            mNoData.setVisibility(View.GONE);
        } else {
            mNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getReplySuccess(String result) {
        dismissLoading();
        try {
            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.getInt("code");
            String msg = jsonObject.getString("msg");
            if (code == 0) {
                mContent.setText("");
                mContent.setHint("请输入回复内容......");
                hideInput(mContent);
            } else {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getReplyError() {
        dismissLoading();
    }

    @Override
    public void getDeleteSuccess(String result) {
        dismissLoading();
        try {
            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.getInt("code");
            String msg = jsonObject.getString("msg");
            if (code == 0) {
                mMessageList.clear();
                pn = 1;
                addData();
            } else {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getDeleteError() {
        dismissLoading();
    }
}
