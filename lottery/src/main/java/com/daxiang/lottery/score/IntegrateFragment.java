package com.daxiang.lottery.score;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.ScoreBoardBean;
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

public class IntegrateFragment extends Fragment {
    private Context mContext;
    private View mInflate;
    private ListView mListView;
    private NoDataView mNoData;
    private SmartRefreshLayout mRefresh;
    List<ScoreBoardBean.DataBean.ItemsBean> mDataList = new ArrayList<>();
    private ScoreBoardAdapter mIntegrateAdapter;

    private String leagueId;
    private String home_id;
    private String away_id;

    public void setData(Context mContext, String leagueId, String home_id, String away_id) {
        this.mContext = mContext;
        this.leagueId = leagueId;
        this.home_id = home_id;
        this.away_id = away_id;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_integrate, null);
            initView();
        }
        mContext = getContext();
        mIntegrateAdapter = new ScoreBoardAdapter(mContext, mDataList,home_id,away_id);
        mListView.setAdapter(mIntegrateAdapter);
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
        mNoData = (NoDataView) mInflate.findViewById(R.id.no_data);
        mListView = (ListView) mInflate.findViewById(R.id.listView);
        mRefresh = (SmartRefreshLayout) mInflate.findViewById(R.id.refresh);
    }

    private void addListener() {
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                addData();
            }
        });
    }

    private void addData() {
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("leagueId", leagueId);
        mHttp.get(Url.SCORE_BOARD, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                mRefresh.finishRefresh();
                ScoreBoardBean scoreBoardBean = new Gson().fromJson(result, ScoreBoardBean.class);
                int code = scoreBoardBean.getCode();
                if (code == 0) {
                    ScoreBoardBean.DataBean data = scoreBoardBean.getData();
                    List<ScoreBoardBean.DataBean.ItemsBean> items = data.getItems();
                    mDataList.clear();
                    mDataList.addAll(items);
                    if (mIntegrateAdapter != null) {
                        mIntegrateAdapter.notifyDataSetChanged();
                    }
                }
                if (mNoData == null) return;
                if (mDataList.size() == 0) {
                    mNoData.setVisibility(View.VISIBLE);
                } else {
                    mNoData.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError() {
                mRefresh.finishRefresh();
                if (mNoData == null) return;
                if (mDataList.size() == 0) {
                    mNoData.setVisibility(View.VISIBLE);
                } else {
                    mNoData.setVisibility(View.GONE);
                }
            }
        });
    }

}
