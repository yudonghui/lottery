package com.daxiang.lottery.fragment.redpacketfragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.lotteryactivity.redpacket.RedRuleActivity;
import com.daxiang.lottery.adapter.redpacket.RedpacketFormAdapter;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.RedpacketData;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.view.NoDataView;
import com.google.gson.Gson;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnLoadMoreListener;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import java.util.ArrayList;


/**
 * 排序方式规则
 * 综合排序 是将最新获取的红包的显示在上面，降序排列 rule="1"，orderByColumn="createTime"
 * 过期时间 是将快要过期的红包显示在上面，  升序排列 rule="0", orderByColumn="expiredTime"
 * 红包金额 是将红包金额最大的显示在上面， 降序排列 rule="1"，orderByColumn="amount"
 */

public class AllRedpacketFrament extends Fragment {
    private View view;
    private LinearLayout mLlSortMethod;
    private TextView mTvSortMethod;
    private LinearLayout mLlRule;
    private NoDataView mNoData;
    private ListView mListView;
    private SmartRefreshLayout mRefresh;
    private Context mContext;
    private HttpInterface2 mHttp;
    private PopupWindow mPopupWindow;
    private View inflate;
    private TextView mOrderAll;
    private TextView mOrderTime;
    private TextView mOrderMoney;
    private int pageNum = 1;
    private int pageSize = 15;
    private int totalNum = 1;
    //排序方式 获得时间:createTime; 过期时间:expiredTime; 红包:amount
    private String orderByColumn = "createTime";
    private String rule = "1";
    private RedpacketFormAdapter mAdapter;
    ArrayList<RedpacketData.DataBean.ItemsBean> redList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_usable_redpacket, null);
        mContext = getActivity();
        initView();
        mAdapter = new RedpacketFormAdapter(redList);
        mListView.setAdapter(mAdapter);
        addData();
        addListener();
        return view;
    }

    private void initView() {
        mHttp = new HttpUtils2(mContext);
        mLlSortMethod = (LinearLayout) view.findViewById(R.id.ll_sort_method);
        mTvSortMethod = (TextView) view.findViewById(R.id.red_order_method);
        mLlRule = (LinearLayout) view.findViewById(R.id.ll_redpacket_rule);
        mRefresh = (SmartRefreshLayout) view.findViewById(R.id.refresh);
        mListView = (ListView) view.findViewById(R.id.ptlv_redpacket_usable);
        mNoData = (NoDataView) view.findViewById(R.id.no_data);
        mNoData.setButtonText(mNoData.REFRESH);
        inflate = View.inflate(mContext, R.layout.popup_redpacket, null);
        mOrderAll = (TextView) inflate.findViewById(R.id.order_all);
        mOrderTime = (TextView) inflate.findViewById(R.id.order_time);
        mOrderMoney = (TextView) inflate.findViewById(R.id.order_money);
        mPopupWindow = new PopupWindow();
        mPopupWindow.setWidth(DisplayUtil.dip2px(130));
        mPopupWindow.setHeight(DisplayUtil.dip2px(150));
        mPopupWindow.setContentView(inflate);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        ColorDrawable cd = new ColorDrawable(0x000000);
        mPopupWindow.setBackgroundDrawable(cd);
    }

    private void addListener() {
        mNoData.setOnClickListener(RefreshListener);
        mLlSortMethod.setOnClickListener(SortMethodListener);
        mLlRule.setOnClickListener(RuleListener);
        // mPopupWindow.setOnDismissListener(poponDismissListener);
        mOrderAll.setOnClickListener(PopupWindowListener);
        mOrderMoney.setOnClickListener(PopupWindowListener);
        mOrderTime.setOnClickListener(PopupWindowListener);
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                redList.clear();
                pageNum = 1;
                addData();
            }
        });
        mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageNum < totalNum) {
                    pageNum++;
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

    private void addData() {
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("status", "2,3");
        params.putString("pageNum", pageNum + "");
        params.putString("pageSize", pageSize + "");
        params.putString("orderByColumn", orderByColumn);
        params.putString("rule", rule);
        params.putString("timeStamp", System.currentTimeMillis() + "");
        mHttp.post(Url.USERREDINFO_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
                Gson gson = new Gson();
                RedpacketData redpacketData = gson.fromJson(result, RedpacketData.class);
                int code = redpacketData.getCode();
                String msg = redpacketData.getMsg();
                if (code == 0) {
                    totalNum = redpacketData.getData().getPageNum();
                    redList.addAll(redpacketData.getData().getItems());
                    mAdapter.notifyDataSetChanged();
                }
                if (redList.size() == 0) {
                    mNoData.setVisibility(View.VISIBLE);
                } else mNoData.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                mNoData.setVisibility(View.VISIBLE);
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
            }
        });
    }

    View.OnClickListener SortMethodListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPopupWindow.showAsDropDown(mLlSortMethod, 20, -10);
            switch (orderByColumn) {
                case "createTime":
                    mOrderAll.setTextColor(getResources().getColor(R.color.blue_txt));
                    mOrderTime.setTextColor(getResources().getColor(R.color.deep_txt));
                    mOrderMoney.setTextColor(getResources().getColor(R.color.deep_txt));
                    break;
                case "expiredTime":
                    mOrderTime.setTextColor(getResources().getColor(R.color.blue_txt));
                    mOrderAll.setTextColor(getResources().getColor(R.color.deep_txt));
                    mOrderMoney.setTextColor(getResources().getColor(R.color.deep_txt));
                    break;
                case "amount":
                    mOrderMoney.setTextColor(getResources().getColor(R.color.blue_txt));
                    mOrderTime.setTextColor(getResources().getColor(R.color.deep_txt));
                    mOrderAll.setTextColor(getResources().getColor(R.color.deep_txt));
                    break;

            }
          /*  WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
            lp.alpha = 0.6f;
            getActivity().getWindow().setAttributes(lp);*/
        }
    };
    View.OnClickListener PopupWindowListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.order_all:
                    orderByColumn = "createTime";
                    rule = "1";
                    mTvSortMethod.setText("综合排序");
                    break;
                case R.id.order_time:
                    rule = "0";
                    orderByColumn = "expiredTime";
                    mTvSortMethod.setText("过期时间");
                    break;
                case R.id.order_money:
                    rule = "1";
                    orderByColumn = "amount";
                    mTvSortMethod.setText("红包金额");
                    break;
            }
            mPopupWindow.dismiss();
            redList.clear();
            addData();
        }
    };
    View.OnClickListener RuleListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, RedRuleActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener RefreshListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            redList.clear();
            pageNum = 1;
            addData();
        }
    };
}
