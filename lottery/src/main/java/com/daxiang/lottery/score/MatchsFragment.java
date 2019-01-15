package com.daxiang.lottery.score;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.H5Activity;
import com.daxiang.lottery.adapter.MatchsAdapter;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.MatchsData;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.view.NoDataView;
import com.google.gson.Gson;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2018/3/28.
 */

public class MatchsFragment extends Fragment {
    private View mInflate;
    private GridView mGridView;
    private SmartRefreshLayout mRefresh;
    private NoDataView mNoData;
    private MatchsAdapter mMatchsAdapter;
    private Context mContext;
    List<MatchsData.DataBean.ItemsBean> mDataList = new ArrayList<>();

    public void setData(Context mContext) {
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_matchs, null);
            initView();
        }
        mContext = getContext();
        mMatchsAdapter = new MatchsAdapter(mContext, mDataList);
        mGridView.setAdapter(mMatchsAdapter);
        addListener();
        return mInflate;
    }

    private boolean isFirst = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirst) {
            isFirst = false;
            addData();
        }
    }

    private void initView() {
        mGridView = (GridView) mInflate.findViewById(R.id.gridView);
        mRefresh = (SmartRefreshLayout) mInflate.findViewById(R.id.refresh);
        mNoData = (NoDataView) mInflate.findViewById(R.id.no_data);
    }

    private void addListener() {
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MatchsData.DataBean.ItemsBean itemsBean = mDataList.get(position);
                String name = itemsBean.getLeague_name();
                String league_id = itemsBean.getLeague_id();
                if ("149".equals(league_id)) {
                    Intent intent = new Intent(mContext, H5Activity.class);
                    String url = "https://h5.51caixiang.com/live/league/m?leagueId=149&leagueName=世界杯";
                    intent.putExtra("url", url);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, MatchsDetailActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("league_id", league_id);
                    startActivity(intent);
                }

            }
        });
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                addData();
            }
        });
    }

    private void addData() {
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        mHttp.get(Url.LEAGUE_LIST, new Bundle(), new JsonInterface() {
            @Override
            public void callback(String result) {
                mRefresh.finishRefresh();
                Gson gson = new Gson();
                MatchsData matchsData = gson.fromJson(result, MatchsData.class);
                int code = matchsData.getCode();
                if (code == 0) {
                    mDataList.clear();
                    MatchsData.DataBean data = matchsData.getData();
                    List<MatchsData.DataBean.ItemsBean> items = data.getItems();
                    mDataList.addAll(items);
                    if (mMatchsAdapter != null)
                        mMatchsAdapter.notifyDataSetChanged();
                }
                if (mDataList.size() == 0) {
                    mNoData.setVisibility(View.VISIBLE);
                } else {
                    mNoData.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError() {
                mRefresh.finishRefresh();
                if (mDataList.size() == 0) {
                    mNoData.setVisibility(View.VISIBLE);
                } else {
                    mNoData.setVisibility(View.GONE);
                }
            }
        });

    }

}
