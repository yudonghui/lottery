package com.daxiang.lottery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.adapter.FansAdapter;
import com.daxiang.lottery.adapter.FollowAdapter;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.FansData;
import com.daxiang.lottery.entity.FollowData;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.NoDataView;
import com.google.gson.Gson;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnLoadMoreListener;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class FansFollowActivity extends BaseNoTitleActivity {
    private ImageView mImage_titlebar_back;
    private TextView mTitle_fans;
    private TextView mTitle_follow;
    private NoDataView mNoData;
    private ListView mListView;
    private String fansfollow;//fans粉丝  follow关注
    private SmartRefreshLayout mRefresh;
    private String userId;
    private FollowAdapter mFollowAdapter;
    private FansAdapter mFansAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans_follow);
        Intent intent = getIntent();
        fansfollow = intent.getStringExtra("fansfollow");
        userId = intent.getStringExtra("userId");
        initView();
        addListener();
        if ("fans".equals(fansfollow)) {
            mTitle_fans.setTextColor(getResources().getColor(R.color.red_txt));
            mTitle_fans.setBackgroundResource(R.drawable.shape_white_left);
            mTitle_follow.setTextColor(getResources().getColor(R.color.white));
            mTitle_follow.setBackgroundResource(R.drawable.shape_red_right);
            fansData();
        } else {
            mTitle_fans.setTextColor(getResources().getColor(R.color.white));
            mTitle_fans.setBackgroundResource(R.drawable.shape_red_left);
            mTitle_follow.setTextColor(getResources().getColor(R.color.red_txt));
            mTitle_follow.setBackgroundResource(R.drawable.shape_white_right);
            followData();
        }
    }

    private void initView() {
        mImage_titlebar_back = (ImageView) findViewById(R.id.image_titlebar_back);
        mTitle_fans = (TextView) findViewById(R.id.title_fans);
        mTitle_follow = (TextView) findViewById(R.id.title_follow);
        mListView = (ListView) findViewById(R.id.listView);
        mNoData = (NoDataView) findViewById(R.id.no_data);
        mRefresh = (SmartRefreshLayout) findViewById(R.id.refresh);
    }

    private void addListener() {
        mImage_titlebar_back.setOnClickListener(BackListener);
        mTitle_fans.setOnClickListener(TitleListener);
        mTitle_follow.setOnClickListener(TitleListener);
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if ("fans".equals(fansfollow)) {
                    pnFans = 1;
                    FansList.clear();
                    fansData();
                } else {
                    pnFollow = 1;
                    FollowList.clear();
                    followData();
                }
            }
        });
      mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
          @Override
          public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
              if ("fans".equals(fansfollow)) {
                  if (pnFans < totalPsFans) {
                      pnFans++;
                      fansData();
                  } else {
                      mRefresh.postDelayed(new Runnable() {
                          @Override
                          public void run() {
                              Toast.makeText(mContext, "已经是最后一页", Toast.LENGTH_SHORT).show();
                              mRefresh.finishLoadMore();
                          }
                      }, 1000);
                  }
              } else {
                  if (pnFollow < totalPsFollow) {
                      pnFollow++;
                      followData();
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
          }
      });
    }

    private int pnFollow = 1;
    private int psFollow = 20;
    private int totalPsFollow;
    private List<FollowData.DataBean.ListBean> FollowList = new ArrayList<>();

    private void followData() {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("userId", userId);
        params.putString("pn", pnFollow + "");
        params.putString("ps", psFollow + "");
        mHttp.get(Url.FOLLOW_LIST, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
                Gson gson = new Gson();
                FollowData followData = gson.fromJson(result, FollowData.class);
                int code = followData.getCode();
                if (code == 0) {
                    FollowData.DataBean data = followData.getData();
                    totalPsFollow = data.getTotalPages();
                    List<FollowData.DataBean.ListBean> list = data.getList();
                    FollowList.addAll(list);
                    if (FollowList.size() > 0) {
                        mNoData.setVisibility(View.GONE);
                        mFollowAdapter = new FollowAdapter(mContext, FollowList);
                        mListView.setAdapter(mFollowAdapter);

                    } else {
                        mNoData.setVisibility(View.VISIBLE);
                    }
                } else {
                    mNoData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
                mNoData.setVisibility(View.GONE);
            }
        });
    }

    private int pnFans = 1;
    private int psFans = 20;
    private int totalPsFans;
    private ArrayList<FansData.DataBean.ListBean> FansList = new ArrayList<>();

    private void fansData() {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("myUid", TextUtils.isEmpty(LotteryApp.uid) ? "0" : LotteryApp.uid);
        params.putString("targetUid", userId);
        params.putString("pn", pnFans + "");
        params.putString("ps", psFans + "");
        mHttp.get(Url.FANS_LIST, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
                Gson gson = new Gson();
                FansData fansData = gson.fromJson(result, FansData.class);
                int code = fansData.getCode();
                if (code == 0) {
                    FansData.DataBean data = fansData.getData();
                    totalPsFans = data.getTotalPages();
                    List<FansData.DataBean.ListBean> list = data.getList();
                    FansList.addAll(list);
                    if (FansList.size() > 0) {
                        mNoData.setVisibility(View.GONE);
                        mFansAdapter = new FansAdapter(mContext, FansList);
                        mListView.setAdapter(mFansAdapter);

                    } else {
                        mNoData.setVisibility(View.VISIBLE);
                    }
                } else mNoData.setVisibility(View.VISIBLE);
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

    View.OnClickListener TitleListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.title_fans:
                    mTitle_fans.setTextColor(getResources().getColor(R.color.red_txt));
                    mTitle_fans.setBackgroundResource(R.drawable.shape_white_left);
                    mTitle_follow.setTextColor(getResources().getColor(R.color.white));
                    mTitle_follow.setBackgroundResource(R.drawable.shape_red_right);
                    fansfollow = "fans";
                    pnFans = 1;
                    FansList.clear();
                    fansData();
                    break;
                case R.id.title_follow:
                    mTitle_fans.setTextColor(getResources().getColor(R.color.white));
                    mTitle_fans.setBackgroundResource(R.drawable.shape_red_left);
                    mTitle_follow.setTextColor(getResources().getColor(R.color.red_txt));
                    mTitle_follow.setBackgroundResource(R.drawable.shape_white_right);
                    fansfollow = "follow";
                    pnFollow = 1;
                    FollowList.clear();
                    followData();
                    break;
            }
        }
    };
    View.OnClickListener BackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            setResult(333, intent);
            finish();
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            setResult(333, intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
