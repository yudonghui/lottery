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
import com.daxiang.lottery.entity.ShooterData;
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

public class ShooterFragment extends Fragment {
    private Context mContext;
    private View mInflate;
    private ListView mListView;
    private SmartRefreshLayout mRefresh;
    private NoDataView mNoData;
    List<ShooterData.DataBean.ItemsBean> mDataList = new ArrayList<>();
    private ShooterAdapter mShooterAdapter;

    private String leagueId;

    public void setData(Context mContext, String leagueId) {
        this.mContext = mContext;
        this.leagueId = leagueId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_shooter, null);
            initView();
        }
        mContext = getContext();
        mShooterAdapter = new ShooterAdapter(mContext, mDataList);
        mListView.setAdapter(mShooterAdapter);
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
        HttpInterface2 mHttp=new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("leagueId",leagueId);
        mHttp.get(Url.SHOOTER_BOARD, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                mRefresh.finishRefresh();
                ShooterData shooterData = new Gson().fromJson(result, ShooterData.class);
                int code = shooterData.getCode();
                if (code==0){
                    ShooterData.DataBean data = shooterData.getData();
                    List<ShooterData.DataBean.ItemsBean> items = data.getItems();
                    mDataList.clear();
                    mDataList.addAll(items);
                    if (mShooterAdapter != null)
                        mShooterAdapter.notifyDataSetChanged();
                }
                if (mDataList.size() == 0) {
                    mNoData.setVisibility(View.VISIBLE);
                } else {
                    mNoData.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError() {
                if (mNoData==null)return;
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
