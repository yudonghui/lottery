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
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yudonghui
 * @date 2017/6/16
 * @describe May the Buddha bless bug-free!!!
 */
public class ScoreLiveFragment extends Fragment {
    private Context mContext;
    private View view;

    private ListView mListview;
    private SmartRefreshLayout mRefresh;
    private TextView mNoData;
    private ScoreDetailAdapter mAdapter;
    List<ScoreDetailBean.DataBean.ItemsBean.EventBean> eventList = new ArrayList<>();

    public void setData(List<ScoreDetailBean.DataBean.ItemsBean.EventBean> event) {
        if (mRefresh != null) mRefresh.finishRefresh();
        eventList.clear();
        eventList.addAll(event);
        if (eventList.size() > 0) {
            mNoData.setVisibility(View.GONE);
            if (mAdapter == null) {
                mAdapter = new ScoreDetailAdapter(mContext, eventList);
                mListview.setAdapter(mAdapter);
            } else {
                mAdapter.notifyDataSetChanged();
            }

        } else mNoData.setVisibility(View.VISIBLE);

    }

    private RefreshListener mRefreshLis;

    public void setVp(Context mContext, RefreshListener mRefreshLis) {
        this.mContext = mContext;
        this.mRefreshLis = mRefreshLis;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_live, null);
            initView();
        }
        mContext = getContext();
        addListener();
        return view;
    }

    private void initView() {
        mNoData = (TextView) view.findViewById(R.id.no_data);
        mListview = (ListView) view.findViewById(R.id.listview);
        mRefresh = (SmartRefreshLayout) view.findViewById(R.id.refresh);

    }

    private void addListener() {
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mRefreshLis.callBack();
            }
        });
    }

    public interface RefreshListener {
        void callBack();
    }
}
