package com.daxiang.lottery.activity.lotteryactivity.redpacket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.adapter.redpacket.RedpacketFormAdapter;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.RedpacketData;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.NoDataView;
import com.daxiang.lottery.view.TitleBar;
import com.google.gson.Gson;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnLoadMoreListener;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import java.util.ArrayList;

public class RedpacketActivity extends AppCompatActivity {
    private TitleBar mTitleBar;
    private LinearLayout mLlRedpacketRule;
    private NoDataView mNoData;
    private ImageView mImageSelect;
    private ListView mListView;
    private SmartRefreshLayout mRefresh;
    private RedpacketFormAdapter mAdapter;
    private ArrayList<RedpacketData.DataBean.ItemsBean> redList = new ArrayList<>();
    //选择的对应的红包位置
    private int position;
    //订单金额
    private String money;
    private HttpInterface2 mHttp;
    private Context mContext;
    private int pageNum = 1;
    private int pageSize = 15;
    private int totalNum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redpacket);
        mContext = this;
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        money = intent.getStringExtra("money");
        initView();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        addListener();
        addData();
    }

    private void initView() {
        mHttp = new HttpUtils2(mContext);
        mTitleBar = (TitleBar) findViewById(R.id.red_titlebar);
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitle("选择红包");
        mTitleBar.setTitleVisibility(true);
        mLlRedpacketRule = (LinearLayout) findViewById(R.id.ll_redpacket_rule);
        mImageSelect = (ImageView) findViewById(R.id.image_select);
        mNoData = (NoDataView) findViewById(R.id.no_data);
        mRefresh = (SmartRefreshLayout) findViewById(R.id.refresh);
        mListView = (ListView) findViewById(R.id.ptrl_redpacket);
        if (position == 99999) {
            mImageSelect.setImageResource(R.drawable.selected);
        } else {
            mImageSelect.setImageResource(R.drawable.unselected);
        }
    }

    private void addListener() {
        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mListView.setOnItemClickListener(ListViewItemListener);
        //是否使用红包
        mImageSelect.setOnClickListener(UselessRedpacketListener);
        //红包使用规则
        mLlRedpacketRule.setOnClickListener(RuleListener);
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

    View.OnClickListener RuleListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, RedRuleActivity.class);
            startActivity(intent);
        }
    };
    AdapterView.OnItemClickListener ListViewItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (Double.parseDouble(redList.get(position).getLimitAmount()) > Double.parseDouble(money)) {
                Toast.makeText(mContext, "未达到使用金额", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("position", position);
            intent.putExtra("redMoney", redList.get(position).getAmount());
            intent.putExtra("voucher", redList.get(position).getId());
            setResult(222, intent);
            finish();
        }
    };
    View.OnClickListener UselessRedpacketListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.putExtra("position", 99999);
            intent.putExtra("redMoney", "0");
            intent.putExtra("voucher", "");
            setResult(222, intent);
            finish();
        }
    };

    private void addData() {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("orderAmount", money);
        params.putString("status", "1");
        params.putString("pageNum", pageNum+"");
        params.putString("pageSize", pageSize+"");
        params.putString("orderByColumn", "amount");
        params.putString("rule", "1");
        params.putString("timeStamp", System.currentTimeMillis() + "");
        mHttp.post(Url.USERREDINFO_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
                Gson gson = new Gson();
                RedpacketData redpacketData = gson.fromJson(result, RedpacketData.class);
                int code = redpacketData.getCode();
                String msg = redpacketData.getMsg();
                if (code == 0) {
                    mNoData.setVisibility(View.GONE);
                    totalNum = redpacketData.getData().getTotalNum();
                    redList.addAll(redpacketData.getData().getItems());
                    mAdapter = new RedpacketFormAdapter(redList);
                    mAdapter.setSelectRedId(position);
                    mListView.setAdapter(mAdapter);
                } else {
                    mNoData.setVisibility(View.VISIBLE);
                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
                mNoData.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
