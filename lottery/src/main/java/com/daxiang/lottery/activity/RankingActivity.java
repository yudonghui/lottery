package com.daxiang.lottery.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.daxiang.lottery.R;
import com.daxiang.lottery.adapter.RankingAdapter;
import com.daxiang.lottery.common.ConstantNum;
import com.daxiang.lottery.constant.Constants;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.RankingData;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.TitleBar;
import com.google.gson.Gson;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author yudonghui
 * @ date 2017/6/22
 * @ describe May the Buddha bless bug-free！！
 */
public class RankingActivity extends BaseNoTitleActivity {
    private TitleBar mTitlebar;
    private ListView mListView;
    private SmartRefreshLayout mRefresh;
    private String title;
    private Context mContext;
    List<RankingData.DataBean> mList = new ArrayList<>();
    private RankingAdapter mAdapter;
    private LoadingDialogUtils loadingDialogUtils;
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        mContext = this;
        title = getIntent().getStringExtra("title");
        initView();
        loadingDialogUtils = new LoadingDialogUtils(mContext);
        addData();
        mAdapter = new RankingAdapter(mList, title);
        mListView.setAdapter(mAdapter);
        addListener();
    }

    private void initView() {
        mTitlebar = (TitleBar) findViewById(R.id.rank_titlebar);
        mTitlebar.setImageTitleVisibility(false);
        mTitlebar.setTitleVisibility(true);
        mListView = (ListView) findViewById(R.id.listview);
        mRefresh = (SmartRefreshLayout) findViewById(R.id.refresh);
        switch (title) {
            case Constants.JIANGJIN://奖金榜
                url = "statistic/prize?day=" + ConstantNum.DAYS + "&limit=20";
                mTitlebar.setTitle("奖金榜");
                break;
            case Constants.RENQI://人气榜
                url = "rank/followBuy/join/" + ConstantNum.DAYS + "/20";
                mTitlebar.setTitle("人气榜");
                break;
            case Constants.HUIBAO://回报榜
                url = "rank/gainRate/" + ConstantNum.DAYS + "/20";
                mTitlebar.setTitle("回报榜");
                break;
            case Constants.MINGZHONG://命中榜
                url = "rank/winRate/" + ConstantNum.DAYS + "/20";
                mTitlebar.setTitle("命中榜");
                break;
            case Constants.LIANHONG://连红榜
                url = "rank/contWin/" + ConstantNum.DAYS + "/20";
                mTitlebar.setTitle("连红榜");
                break;
            case Constants.HUOYUE://活跃榜
                url = "rank/buyNum/" + ConstantNum.DAYS + "/20";
                mTitlebar.setTitle("活跃榜");
                break;

        }
    }

    private void addData() {
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        mHttp.jsonByUrl(Url.RANKING_URL + url, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
                mList.clear();
                Gson gson = new Gson();
                RankingData rankingData = gson.fromJson(result, RankingData.class);
                int code = rankingData.getCode();
                String msg = rankingData.getMsg();
                if (code == 0) {
                    mList.addAll(rankingData.getData());
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
            }
        });
    }

    private void addListener() {
        mTitlebar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                addData();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("userId", mList.get(position).getUserId());
                startActivity(GodInfoActivity.class, bundle);
            }
        });
    }
}
