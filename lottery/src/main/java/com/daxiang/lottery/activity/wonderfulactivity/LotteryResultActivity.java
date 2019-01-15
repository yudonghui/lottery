package com.daxiang.lottery.activity.wonderfulactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BaseActivity;
import com.daxiang.lottery.adapter.wonderfuladapter.LotteryResultAdapter;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.LotteryResultMode;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.NoDataView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class LotteryResultActivity extends BaseActivity {
    // Content View Elements
  /*  private LinearLayout mLr_zuqiu;
    private TextView mTv_zuqiu;
    private ImageView mImg_footboll;
    private View mView_1;
    private LinearLayout mLayout_lanqiu;
    private TextView mTv_lanqiu;
    private ImageView mImg_bbmatch;*/
    private ListView mList_result;
    private NoDataView mNoData;
    private List<String> order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_lottery_result);

        bindViews();
        addData();
        addListener();
    }

    private void bindViews() {
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitleVisibility(true);
        mTitleBar.setTitle("开奖结果");
        mList_result = (ListView) findViewById(R.id.list_result);
        mNoData = (NoDataView) findViewById(R.id.no_data);
        mNoData.setRemark("暂无数据");
        mNoData.setButtonText(mNoData.REFRESH);
        order = new ArrayList<>();
        //竞足，竞篮，胜负彩，任九场，七乐彩，双色球，大乐透，七星彩，十一运夺金，排列五，排列三，福彩3D
        order.add("42");
        order.add("43");
        order.add("11");
        order.add("19");
        order.add("23528");
        order.add("51");
        order.add("23529");
        order.add("10022");
        order.add("21406");
        order.add("35");
        order.add("33");
        order.add("52");

    }

    private void addData() {
       /* if (!NetworkUtils.isNetworkAvailable(this)) {
            HintDialogUtils.setHintDialog(this);
            HintDialogUtils.setMessage("您的网络未连接，请连接后重试！");
            HintDialogUtils.setConfirm("重新加载", new DialogHintInterface() {
                @Override
                public void callBack(View view) {
                    addData();
                }
            });
            return;
        }*/
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(LotteryResultActivity.this);
        HttpInterface2 mHttp = new HttpUtils2(this);
        mHttp.jsonByUrl(Url.LOTTERY_RESULT_URL, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                Gson gson = new Gson();
                LotteryResultMode lotteryResultMode = gson.fromJson(result, LotteryResultMode.class);
                if (lotteryResultMode.getCode() == 0) {
                    List<LotteryResultMode.DataBean> mDataBean = lotteryResultMode.getData();
                    if (mDataBean != null) {
                        List<LotteryResultMode.DataBean> mOrderList = new ArrayList<>();
                        for (int i = 0; i < order.size(); i++) {
                            for (int j = 0; j < mDataBean.size(); j++) {
                                LotteryResultMode.DataBean dataBean = mDataBean.get(j);
                                String lotCode = dataBean.getLotCode();
                                if (order.get(i).equals(lotCode)) {
                                    mDataBean.remove(j);
                                    mOrderList.add(dataBean);
                                    break;
                                }
                            }
                        }
                        mOrderList.addAll(mDataBean);
                        LotteryResultAdapter mAdapter = new LotteryResultAdapter(LotteryResultActivity.this, mOrderList);
                        mList_result.setAdapter(mAdapter);
                        if (mOrderList.size() == 0) mNoData.setVisibility(View.VISIBLE);
                        else mNoData.setVisibility(View.GONE);
                    } else mNoData.setVisibility(View.VISIBLE);


                } else {
                    mNoData.setVisibility(View.VISIBLE);
                    Toast.makeText(LotteryResultActivity.this, lotteryResultMode.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
                mNoData.setVisibility(View.VISIBLE);
            }
        });
    }

    private void addListener() {
        mNoData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });
    }
}
