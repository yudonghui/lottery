package com.daxiang.lottery.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.R;
import com.daxiang.lottery.adapter.JoinerAdapter;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.JoinData;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.view.NoDataView;
import com.daxiang.lottery.view.TitleBar;
import com.google.gson.Gson;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnLoadMoreListener;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import java.util.ArrayList;

public class JoinerActivity extends BaseNoTitleActivity {
    private TextView mJoin_username;
    private TextView mJoin_money;
    private TextView mJoin_time;
    private TitleBar mTitleBar;
    private NoDataView mNodata;
    private ListView mListView;
    private SmartRefreshLayout mRefresh;
    private JoinerAdapter mAdapter;
    private int pn = 1;
    private int ps = 20;
    private int totalPage;
    private String orderId;
    private HttpInterface2 mHttp;
    private String joinerUrl;
    ArrayList<JoinData.DataBean.ListBean> joinList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joiner);
        orderId = getIntent().getStringExtra("orderId");
        joinerUrl=getIntent().getStringExtra("joinerUrl");
        initView();
        mAdapter = new JoinerAdapter(joinList);
        mListView.setAdapter(mAdapter);
        addData();
        addListener();
    }

    private void initView() {
        mTitleBar = (TitleBar) findViewById(R.id.joiner_titlebar);
        mTitleBar.setTitle("参与人");
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitleVisibility(true);
        mJoin_username = (TextView) findViewById(R.id.join_username);
        mJoin_money = (TextView) findViewById(R.id.join_money);
        mJoin_time = (TextView) findViewById(R.id.join_time);
        mListView = (ListView) findViewById(R.id.lv_joiner);
        mNodata= (NoDataView) findViewById(R.id.no_data);
        mRefresh = (SmartRefreshLayout) findViewById(R.id.refresh);
        mHttp = new HttpUtils2(this);
        joinList = new ArrayList<>();
    }

    private void addData() {
        Bundle params = new Bundle();
        params.putString("orderId", orderId);
        params.putString("pn", pn + "");
        params.putString("ps", ps + "");
        mHttp.get(Url.JOINER_URL+joinerUrl, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();

                Gson gson = new Gson();
                JoinData joinData = gson.fromJson(result, JoinData.class);
                int code = joinData.getCode();
                if (code == 0) {
                    mNodata.setVisibility(View.GONE);
                    JoinData.DataBean data = joinData.getData();
                    totalPage = data.getTotalPages();
                    joinList.addAll(data.getList());
                    mAdapter.notifyDataSetChanged();
                } else {
                    // Toast.makeText(mContext,joinData.getMsg(),Toast.LENGTH_SHORT).show();
                    mNodata.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError() {
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();

                mNodata.setVisibility(View.VISIBLE);
            }
        });
    }

    private void addListener() {
        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                joinList.clear();
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
                } else{
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
}
