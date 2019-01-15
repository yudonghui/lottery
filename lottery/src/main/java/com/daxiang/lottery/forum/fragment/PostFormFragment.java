package com.daxiang.lottery.forum.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.forum.adapter.PostRvAdapter;
import com.daxiang.lottery.forum.base.BaseMvpFragment;
import com.daxiang.lottery.forum.bean.PostBean;
import com.daxiang.lottery.forum.contract.PostFormContract;
import com.daxiang.lottery.forum.presenter.PostFormPresenter;
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

/**
 * Created by Android on 2018/9/3.
 */

public class PostFormFragment extends BaseMvpFragment<PostFormPresenter> implements PostFormContract.View {
    private TextView mDelete;
    private TextView mShare;
    private Button mCancelPop;
    private View inflate;
    private Dialog mDialog;
    private Window window;
    private NoDataView mNoData;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefresh;
    private PostRvAdapter mRvAdapter;
    List<PostBean.DataBean.ListBean> mPostList = new ArrayList<>();
    private int type;
    private int totalPages;

    public void setData(int type, Context mContext) {
        this.type = type;
        this.mContext = mContext;
    }

    @Override
    public int getInflateId() {
        return R.layout.fragment_post_form;
    }

    @Override
    public void init() {
        initView();
        mRvAdapter = new PostRvAdapter(mContext, mPostList, moreListener);
        mRecyclerView.setAdapter(mRvAdapter);
        showLoading();
        mPresenter = new PostFormPresenter(this);
        addData();
    }

    private void initView() {
        mNoData = (NoDataView) view.findViewById(R.id.no_data);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRefresh = (SmartRefreshLayout) view.findViewById(R.id.refresh);
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

        mRecyclerView.setHasFixedSize(true);//固定自身size不受adapter变化影响
        //消除滑动卡顿现象 ...
        mRecyclerView.setNestedScrollingEnabled(false);//限制recyclerview自身滑动特性,滑动全部靠scrollview完成
    }


    private int qualityFlag = 0;//标志：0全部，1精华，2热门 默认0
    private int pn = 1;
    private int ps = 10;

    private void addData() {
        Bundle parameters = new Bundle();
        String formType = "0";//0获取全部， 1获取用户关注的人发的所有帖子（uid必传），2根据uid获取该用户发的所有帖子（uid必传）3查询只有新闻类型的帖子
        if (type == 0) {
            formType = "0";
        } else if (type == 1) {
            formType = "1";
        } else if (type == 2) {
            formType = "2";
            parameters.putString("toUserId", LotteryApp.uid);
        } else if (type == 3) {
            parameters.putString("postType", "1");//帖子类型 0普通 1新闻 2公告 不传获取所有的
        }
        parameters.putString("formType", formType);
        parameters.putString("qualityFlag", qualityFlag + "");
        parameters.putString("userId", LotteryApp.uid);
        parameters.putString("pageIndex", pn + "");
        parameters.putString("pageSize", ps + "");
        if (mPresenter != null)
            mPresenter.getData(parameters, mContext);
    }

    @Override
    public void addListener() {
        mDelete.setOnClickListener(DeleteListener);
        mShare.setOnClickListener(ShareListener);
        mCancelPop.setOnClickListener(CancelPopListener);
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pn = 1;
                mPostList.clear();
                addData();
            }
        });
        mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pn < totalPages) {
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
        });
    }

    private int selectPosition;
    PostRvAdapter.MoreListener moreListener = new PostRvAdapter.MoreListener() {
        @Override
        public void callBack(int position) {
            selectPosition = position;
            mDialog.show();
            window.setGravity(Gravity.BOTTOM);
        }
    };
    View.OnClickListener DeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog.dismiss();
            PostBean.DataBean.ListBean listBean = mPostList.get(selectPosition);
            int id = listBean.getId();
            Bundle params = new Bundle();
            params.putString("id", id + "");
            params.putString("auditFlag", "4");
            params.putString("token", LotteryApp.token);
            params.putString("timeStamp", System.currentTimeMillis() + "");
            showLoading();
            mPresenter.getDeleteData(params, mContext);
        }
    };
    View.OnClickListener ShareListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog.dismiss();
            PostBean.DataBean.ListBean listBean = mPostList.get(selectPosition);
            int id = listBean.getId();
            Toast.makeText(mContext, "分享条目" + selectPosition + "帖子id" + id, Toast.LENGTH_SHORT).show();
        }
    };
    View.OnClickListener CancelPopListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDialog.dismiss();
        }
    };

    @Override
    public void getSuccess(String result) {
        dismissLoading();
        mRefresh.finishRefresh();
        mRefresh.finishLoadMore();
        Gson gson = new Gson();
        PostBean postBean = gson.fromJson(result, PostBean.class);
        String msg = postBean.getMsg();
        if (postBean.getCode() == 0) {
            PostBean.DataBean data = postBean.getData();
            totalPages = data.getTotalPages();
            mPostList.addAll(data.getList());
        } else {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
        if (mPostList.size() == 0) {
            mNoData.setVisibility(View.VISIBLE);
        } else {
            mNoData.setVisibility(View.GONE);
        }
        mRvAdapter.notifyDataSetChanged();
    }

    @Override
    public void getDeleteSuccess(String result) {
        dismissLoading();
        try {
            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.getInt("code");
            String msg = jsonObject.getString("msg");
            if (code == 0) {
                pn = 1;
                // mPostList.clear();
                // addData();

                mPostList.remove(selectPosition);
                mRvAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getError() {
        dismissLoading();
        if (mPostList.size() == 0) {
            mNoData.setVisibility(View.VISIBLE);
        } else {
            mNoData.setVisibility(View.GONE);
        }
        Toast.makeText(mContext, "网络请求错误", Toast.LENGTH_SHORT).show();
    }

}
