package com.daxiang.lottery.score;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.OddsData;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.view.NoDataView;
import com.google.gson.Gson;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2018/3/28.
 */

public class OddsFragment extends Fragment {
    private View mInflate;
    private Context mContext;
    private SmartRefreshLayout mRefresh;
    private ListView mListView;
    private NoDataView mNoData;
    List<OddsData.DataBean.ItemsBean> mDataList = new ArrayList<>();
    private OddsAdapter mOddsAdapter;
    private String mId;

    public void setVp(Context mContext, String mId) {
        this.mContext = mContext;
        this.mId = mId;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_odds, null);
            initView();
            addData();
        }
        mContext = getContext();
        addListener();
        mOddsAdapter = new OddsAdapter(mContext, mDataList, 1);
        mListView.setAdapter(mOddsAdapter);
        return mInflate;
    }

    private boolean isFirst = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("生命周期", "setUserVisibleHint");
        if (isVisibleToUser && isFirst) {
            isFirst = false;
            addData();
        }
    }

    private void addData() {
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("mId", mId);
        mHttp.get(Url.ODDS_FORM, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                mRefresh.finishRefresh();
                Gson gson = new Gson();
                OddsData asiaOddsData = gson.fromJson(result, OddsData.class);
                int code = asiaOddsData.getCode();
                if (code == 0) {
                    OddsData.DataBean data = asiaOddsData.getData();
                    mDataList.clear();
                    mDataList.addAll(data.getItems());
                    mOddsAdapter.notifyDataSetChanged();
                } else {

                }
                if (mDataList.size() == 0) {
                    mNoData.setVisibility(View.VISIBLE);
                } else mNoData.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                mRefresh.finishRefresh();
                if (mDataList.size() == 0) {
                    mNoData.setVisibility(View.VISIBLE);
                } else mNoData.setVisibility(View.GONE);
            }
        });
    }

    private void initView() {
        mRefresh = (SmartRefreshLayout) mInflate.findViewById(R.id.refresh);
        mListView = (ListView) mInflate.findViewById(R.id.listView);
        mNoData = (NoDataView) mInflate.findViewById(R.id.no_data);
        mNoData.setRemark("暂无数据");
    }

    private void addListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OddsData.DataBean.ItemsBean itemsBean = mDataList.get(position);
                String cId = itemsBean.getId();
                Intent intent = new Intent(mContext, OddsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("mCompany", (Serializable) mDataList);
                bundle.putString("cId", cId);
                bundle.putString("mId", mId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mNoData.setButtonText(mNoData.REFRESH);
        mNoData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                addData();
            }
        });
    }

}
