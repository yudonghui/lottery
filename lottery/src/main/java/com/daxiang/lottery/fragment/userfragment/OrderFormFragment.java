package com.daxiang.lottery.fragment.userfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.lotteryactivity.OrderFormDetailActivity;
import com.daxiang.lottery.adapter.BetRecordAdapter;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.BetRecordData;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.NoDataView;
import com.google.gson.Gson;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnLoadMoreListener;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yudonghui
 * @date 2017/5/25
 * @describe May the Buddha bless bug-free!!!
 */
public class OrderFormFragment extends Fragment {
    private View v;
    private ListView mPtrlRecord;
    private SmartRefreshLayout mRefresh;
    private NoDataView mNoData;
    List<BetRecordData.DataBean.ListBean> mList = new ArrayList<>();
    private BetRecordAdapter mAdapter;
    private int pn = 1;
    private int totalPage = 1;
    private Context mContext;
    private HttpInterface2 mHttp;
    //1.全部 2.待支付 3.待开奖  4.已中奖  5.追号单
    private int type;

    public void setData(int type, Context mContext) {
        this.type = type;
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("生命周期", "onCreateView");
        if (v == null) {
            v = inflater.inflate(R.layout.fragment_form_order, null);
            initView();
        }
        mContext = getActivity();
        addListener();
        //  addData();
        mAdapter = new BetRecordAdapter(mList);
        mPtrlRecord.setAdapter(mAdapter);
        return v;
    }

    private void initView() {
        mRefresh = (SmartRefreshLayout) v.findViewById(R.id.refresh);
        mPtrlRecord = (ListView) v.findViewById(R.id.ptrl_record);
        mNoData = (NoDataView) v.findViewById(R.id.no_data);
    }


    private boolean isFirst = true;
    private LoadingDialogUtils mLoadingDialogUtils;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("生命周期", "setUserVisibleHint");
        if (isVisibleToUser && isFirst) {

            isFirst = false;
            mLoadingDialogUtils = new LoadingDialogUtils(mContext);
            addData();
        }
    }

    private boolean isRefresh;

    private void addListener() {
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isRefresh = true;
                pn = 1;
                addData();
            }
        });
        mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
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
        });
        mPtrlRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BetRecordData.DataBean.ListBean itemsBean = mList.get(position);
                long createTime;
                String betTime = itemsBean.getBetTime();
                if (betTime.contains(":")) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = null;
                    try {
                        date = simpleDateFormat.parse(betTime);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    createTime = date.getTime();
                } else {
                    createTime = Long.parseLong(betTime);
                }
                Intent intent = new Intent(mContext, OrderFormDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("orderType", itemsBean.getOrderType());
                bundle.putString("orderCode", itemsBean.getOrderCode());
                bundle.putString("orderId", itemsBean.getOrderId());
                bundle.putInt("status", itemsBean.getOrderStatus());
                bundle.putString("allmoney", itemsBean.getTotalPrice());
                bundle.putString("buycontent", itemsBean.getBetContent());
                bundle.putString("lotCode", itemsBean.getLotCode());
                // String issue = itemsBean.getLotIssue().substring(0, 7);
                bundle.putString("issue", itemsBean.getIssueNo());
                bundle.putLong("creattime", createTime);
                bundle.putString("margin", itemsBean.getAftertaxBonus());
                bundle.putString("extraMargin", itemsBean.getExtraMargin());
                intent.putExtras(bundle);
                startActivityForResult(intent, 111);
            }
        });
        /*mPtrlRecord.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                BetRecordData.DataBean.ListBean listBean = mList.get(position);
                final String orderId = listBean.getOrderId();
                int orderStatus = listBean.getOrderStatus();
                int orderType = listBean.getOrderType();
                if (orderType == 1 || orderType == 7) return false;
                boolean msgByStatus = LotteryTypes.getMsgByStatus(orderStatus + "");
                HintDialogUtils2 mDialog = new HintDialogUtils2(mContext);
                mDialog.setTitleVisiable(true);
                mDialog.setTitle("温馨提示");
                String message;
                if (msgByStatus) {//可以删除
                    message = "订单删除之后将无法显示，请您谨慎！";
                    mDialog.setConfirm("删除", new DialogHintInterface() {
                        @Override
                        public void callBack(View view) {
                            deleteData(orderId);
                        }
                    });
                } else {//未开奖不能删除
                    message = "还没有开奖的方案无法删除，说不定就中个大奖呢？";
                    mDialog.setVisibilityCancel();
                }
                mDialog.setMessage(message);
                return true;
            }
        });*/
    }

    private void deleteData(String orderId) {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("orderId", orderId);
        mHttp.postH(Url.DELETE_ORDER, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("code");
                    String msg = jsonObject.getString("msg");
                    if (code == 0) {
                        mList.clear();
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
            public void onError() {
                loadingDialogUtils.setDimiss();
            }
        });
    }

    private void addData() {
        mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        // params.putString("type", "0,1,3,4,5,6,7,8");
        switch (type) {
            case 1:

                break;
            case 2:
                params.putString("status", "100");
                break;
            case 3:
                params.putString("status", "400,500");
                break;
            case 4:
                params.putString("status", "5000,2000");
                break;

        }
        params.putInt("pn", pn);
        params.putString("ps", "10");
        mHttp.get(Url.RECORD_FORM_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                if (isRefresh) {//如果是下拉刷新。清空数据源。
                    mList.clear();
                    isRefresh = false;
                }
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
                mLoadingDialogUtils.setDimiss();
                Gson gson = new Gson();
                BetRecordData betRecordData = gson.fromJson(result, BetRecordData.class);
                if (betRecordData.getCode() == 0) {
                    totalPage = betRecordData.getData().getTotalPages();
                    List<BetRecordData.DataBean.ListBean> list = betRecordData.getData().getList();
                    mList.addAll(list);
                    mAdapter.notifyDataSetChanged();
                }
                isData();
            }

            @Override
            public void onError() {
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
                mLoadingDialogUtils.setDimiss();
                isData();
            }
        });
    }

    //1.全部 2.待支付 3.待开奖  4.已中奖  5.追号单
    private void isData() {//是否有数据
        if (mList.size() == 0) {
            mNoData.setVisibility(View.VISIBLE);
            switch (type) {
                case 1:
                    mNoData.setBackGround(R.mipmap.no_data);
                    mNoData.setRemark("暂无订单信息");
                    break;
                case 2:
                    mNoData.setBackGround(R.mipmap.no_data_wait_payment);
                    mNoData.setRemark("暂无待支付订单信息");
                    break;
                case 3:
                    mNoData.setBackGround(R.mipmap.no_data_wait_award);
                    mNoData.setRemark("暂无待开奖订单信息");
                    break;
                case 4:
                    mNoData.setBackGround(R.mipmap.no_data_win_award);
                    mNoData.setRemark("暂无已中奖订单信息");
                    break;
            }
            mNoData.setButtonText(mNoData.TOUZHU);
        } else {
            mNoData.setVisibility(View.GONE);
        }

    }
}
