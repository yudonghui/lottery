package com.daxiang.lottery.activity.lotteryactivity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.adapter.BillStreamAdapter;
import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.BillStreamData;
import com.daxiang.lottery.entity.BillWalletData;
import com.daxiang.lottery.utils.DateFormtUtils;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.dialogutils.BillTypeDialogUtils;
import com.daxiang.lottery.view.BillView;
import com.daxiang.lottery.view.JcTitleBar;
import com.daxiang.lottery.view.NoDataView;
import com.daxiang.lottery.wheelview.ChangeDatePopwindow;
import com.google.gson.Gson;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnLoadMoreListener;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillRecordActivity extends AppCompatActivity {
    private FrameLayout mFrameLayout;
    private JcTitleBar mTitleBar;
    private SmartRefreshLayout mRefresh;
    private TextView mBillBalance;
    private TextView mBillUsable;
    private TextView mBillFreeze;
    private ListView mListView;
    private FrameLayout mFl;
    private View mBg_view;
    private BillView mBillView;
    private int pn = 1;
    public List<BillStreamData.DataBean.ItemsBean> mTransList;
    private BillStreamAdapter mAdapter;
    private BillStreamData billStreamData;
    BillWalletData billWalletData;
    private int totalPage;
    private NoDataView mNoData;
    private String tradeType = "0";
    private Context mContext;
    //点击中心处，存放选中的条目
    ArrayList<String> lotteryList = new ArrayList<>();
    private HttpUtils2 mHttp;
    private boolean isRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_record);
        mContext = this;
        billWalletData = (BillWalletData) getIntent().getSerializableExtra("data");
        initView();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        addData();
        mAdapter = new BillStreamAdapter();
        mAdapter.setData(mTransList);
        mListView.setAdapter(mAdapter);
        setListener();
    }

    private void initView() {
        mFrameLayout = (FrameLayout) findViewById(R.id.framelayout);
        mTitleBar = (JcTitleBar) findViewById(R.id.titlebar_bill_record);
        mTitleBar.setText("账单明细");
        mTitleBar.mTitlebarSift.setText("统计");
        mBillBalance = (TextView) findViewById(R.id.bill_balance);
        mBillUsable = (TextView) findViewById(R.id.bill_usable);
        mBillFreeze = (TextView) findViewById(R.id.bill_freeze);
        mNoData = (NoDataView) findViewById(R.id.no_data);
        mListView = (ListView) findViewById(R.id.lv_bill);
        mRefresh = (SmartRefreshLayout) findViewById(R.id.refresh);
        mFl = (FrameLayout) findViewById(R.id.fl);
        mBg_view = (View) findViewById(R.id.bg_view);
        mBillView = (BillView) findViewById(R.id.billView);
        mTransList = new ArrayList<>();

    }

    private void addData() {
        //顶部钱包的数据
        if (billStreamData == null || billWalletData.getCode() != 0) {
            billData();
        } else {
            BillWalletData.DataBean data = billWalletData.getData();
            setHeaderView(data);
        }
        //流水账单
        billStreamData();

    }

    private void billData() {//用户钱包数据
        //用户信息
        HttpInterface2 mHttpInterface = new HttpUtils2(this);
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("userId", LotteryApp.uid);
        params.putString("timeStamp", System.currentTimeMillis() + "");
        mHttpInterface.post(Url.BILL_WALLET_URL, params, new JsonInterface() {

            @Override
            public void callback(String result) {
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
                Gson gson = new Gson();
                billWalletData = gson.fromJson(result, BillWalletData.class);
                if (billWalletData.getCode() == 0) {
                    BillWalletData.DataBean data = billWalletData.getData();
                    String totalBalance = data.getTotalBalance();
                    String freezeBalance = data.getFreezeBalance();//冻结金额
                    BigDecimal consumeBalance = new BigDecimal(totalBalance).subtract(new BigDecimal(freezeBalance));
                    LotteryApp.balance = totalBalance;
                    LotteryApp.consumeBanlance = consumeBalance + "";
                    setHeaderView(data);
                }
            }

            @Override
            public void onError() {
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
            }
        });
    }

    private void setHeaderView(BillWalletData.DataBean data) {
        mBillBalance.setText(LotteryApp.consumeBanlance);
        mBillUsable.setText(data.getFreeBalance() + "");
        mBillFreeze.setText(data.getFreezeBalance() + "");
    }

    private void billStreamData() {
        //详细的流水账单
        HttpInterface2 mBillHttp = new HttpUtils2(this);
        Bundle billParams = new Bundle();
        billParams.putString("token", LotteryApp.token);
        billParams.putString("pageStart", pn + "");
        billParams.putString("pageSize", "15");
        billParams.putString("timeStamp", System.currentTimeMillis() + "");
        billParams.putString("userId", LotteryApp.uid);
        billParams.putString("tradeType", tradeType);
        mBillHttp.post(Url.BILL_STREAM_URL, billParams, new JsonInterface() {

            @Override
            public void callback(String result) {
                if (isRefresh) {//如果是下拉刷新。清空数据源。
                    mTransList.clear();
                    isRefresh = false;
                }
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
                Gson gson = new Gson();
                billStreamData = gson.fromJson(result, BillStreamData.class);
                totalPage = billStreamData.getData().getPageNum();
                if (billStreamData.getCode() == 0) {
                    mTransList.addAll(billStreamData.getData().getItems());
                    mAdapter.notifyDataSetChanged();
                }
                isData();
            }

            @Override
            public void onError() {
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
                isData();
            }
        });
    }

    private void isData() {//是否有数据
        if (mTransList.size() == 0) {
            mNoData.setVisibility(View.VISIBLE);
        } else mNoData.setVisibility(View.GONE);
    }

    private void setListener() {
        mTitleBar.mTitlebarSift.setOnClickListener(FlowIngWaterListener);
        billListener();
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isRefresh = true;
                pn = 1;
                billStreamData();
            }
        });
        mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pn < totalPage) {
                    pn++;
                    billStreamData();
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
        mTitleBar.mPlayMethodTitleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BillTypeDialogUtils dialogUtils = new BillTypeDialogUtils();
                dialogUtils.selecte(mContext, lotteryList, new BillTypeDialogUtils.CenterClickListener() {

                    @Override
                    public void onClick(ArrayList<String> clickList, String type) {
                        lotteryList = clickList;
                        tradeType = type;
                        if (!"0".equals(tradeType)) {

                            mTitleBar.mTextTitlebar.setText(LotteryTypes.getMoneyStatus(Integer.parseInt(tradeType)));
                        } else {
                            mTitleBar.mTextTitlebar.setText("账单明细");
                        }
                        mTransList.clear();
                        pn = 1;
                        billStreamData();
                    }
                });
            }
        });
    }

    View.OnClickListener FlowIngWaterListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mFl.setVisibility(View.VISIBLE);
            endTime = DateFormtUtils.strByDate(new Date());
            startTime = DateFormtUtils.getMonthAgo(new Date());
            mBillView.mStart_time.setText(startTime);
            mBillView.mEnd_time.setText(endTime);
            moneyData();
        }
    };

    private void moneyData() {
        mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("userId", LotteryApp.uid);
        params.putString("startDate", DateFormtUtils.getLongByDate(startTime) + "");
        params.putString("endDate", DateFormtUtils.getLongByDate(endTime) + "");
        mHttp.get(Url.USER_MONEY, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("code");
                    String msg = jsonObject.getString("msg");
                    if (code == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        String totalCZ = data.getString("totalCZ");//指定时间段内累计充值
                        String totalGC = data.getString("totalGC");//指定时间段内累计购彩
                        String totalZJ = data.getString("totalZJ");//指定时间段内累计中奖
                        String totalTK = data.getString("totalTK");//指定时间段内累计提款
                        mBillView.setData(totalCZ, totalGC, totalZJ, totalTK);
                    } else Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError() {

            }
        });
    }

    private void billListener() {
        mBillView.mStart_time.setOnClickListener(StartTimeListener);
        mBillView.mSearch.setOnClickListener(SearchListener);
        mBillView.mEnd_time.setOnClickListener(EndTimeListener);
        mBillView.mCancel.setOnClickListener(CancelListener);
        mBillView.mFinish.setOnClickListener(FinishListener);
        mBg_view.setOnClickListener(CancelListener);
        mBillView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    private String startTime;
    private String endTime;
    private ChangeDatePopwindow mChangeBirthDialogStart;
    private ChangeDatePopwindow mChangeBirthDialogEnd;
    View.OnClickListener StartTimeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mChangeBirthDialogStart == null) {
                mChangeBirthDialogStart = new ChangeDatePopwindow(mContext);
            }
            mChangeBirthDialogStart.showAtLocation(mFrameLayout, Gravity.BOTTOM, 0, 0);
            String[] split = startTime.split("\\-");
            mChangeBirthDialogStart.setDate(split[0], split[1], split[2]);
            mChangeBirthDialogStart.setBirthdayListener(new ChangeDatePopwindow.OnBirthListener() {

                @Override
                public void onClick(String year, String month, String day) {
                    startTime = DateFormtUtils.dateByDate(year, month, day);
                    int i = startTime.compareTo(endTime);
                    if (i > 0) {//开始时间大于结束时间
                        String middleTime = startTime;
                        startTime = endTime;
                        endTime = middleTime;
                        mBillView.mEnd_time.setText(endTime);
                    }
                    mBillView.mStart_time.setText(startTime);

                }
            });
        }
    };

    View.OnClickListener EndTimeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mChangeBirthDialogEnd == null) {
                mChangeBirthDialogEnd = new ChangeDatePopwindow(mContext);
            }
            mChangeBirthDialogEnd.showAtLocation(mFrameLayout, Gravity.BOTTOM, 0, 0);
            String[] split = endTime.split("\\-");
            mChangeBirthDialogEnd.setDate(split[0], split[1], split[2]);
            mChangeBirthDialogEnd.setBirthdayListener(new ChangeDatePopwindow.OnBirthListener() {

                @Override
                public void onClick(String year, String month, String day) {
                    endTime = DateFormtUtils.dateByDate(year, month, day);
                    int i = startTime.compareTo(endTime);
                    if (i > 0) {//开始时间大于结束时间
                        String middleTime = startTime;
                        startTime = endTime;
                        endTime = middleTime;
                        mBillView.mStart_time.setText(startTime);
                    }
                    mBillView.mEnd_time.setText(endTime);

                }
            });
        }
    };
    View.OnClickListener SearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            moneyData();
        }
    };
    View.OnClickListener CancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mFl.setVisibility(View.GONE);
        }
    };
    View.OnClickListener FinishListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mFl.setVisibility(View.GONE);
        }
    };

    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
