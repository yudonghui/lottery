package com.daxiang.lottery.score;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.CollectionListener;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils2;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.NoDataView;
import com.google.gson.Gson;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * @author yudonghui
 * @date 2017/6/8
 * @describe May the Buddha bless bug-free!!!
 */
public class FinishedFragment extends Fragment {
    View view;
    private Context mContext;
    private SmartRefreshLayout mRefresh;
    private NoDataView mNoData;
    private ExpandableListView mListView;
    private boolean isBasketball;
    private HashMap<String, ArrayList<ScoreBean.DataBean.ItemsBean>> map = new HashMap<>();
    ArrayList<ArrayList<ScoreBean.DataBean.ItemsBean>> mListData = new ArrayList<>();
    private BaseExpandableListAdapter mAdapter;
    CollectionListener mCollectionListener;
    private LoadingDialogUtils mLoadingDialogUtils;

    public void setData(Context mContext, boolean isBasketball, CollectionListener mCollectionListener) {
        this.isBasketball = isBasketball;
        this.mContext = mContext;
        this.mCollectionListener = mCollectionListener;
    }

    public void setRefresh() {
        setAdapter();
        for (int i = 0; i < mListData.size(); i++) {
            mListView.expandGroup(i);
        }
    }

    public void changData(boolean isBasketball, CollectionListener mCollectionListener) {
        this.isBasketball = isBasketball;
        this.mCollectionListener = mCollectionListener;
        setAdapter();
        mLoadingDialogUtils = new LoadingDialogUtils(mContext);
        addData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_score_new, null);
            initView();
        }
        //  addData();
        setAdapter();
        addListener();
        return view;
    }

    private boolean isFirst = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirst) {
            isFirst = false;
            mLoadingDialogUtils = new LoadingDialogUtils(mContext);
            addData();
        }
    }

    private void initView() {
        mRefresh = (SmartRefreshLayout) view.findViewById(R.id.refresh);
        mNoData = (NoDataView) view.findViewById(R.id.no_data);
        mListView = (ExpandableListView) view.findViewById(R.id.lv_score);
        mListView.setGroupIndicator(null);
    }

    public void setAdapter() {
        if (isBasketball) {
            mAdapter = new ScoreBasketAdapter();
            ((ScoreBasketAdapter) mAdapter).setData(mListData, mContext, mCollectionListener);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter = new ScoreAdapter();
            ((ScoreAdapter) mAdapter).setData(mListData, mContext, mCollectionListener);
            mListView.setAdapter(mAdapter);
        }

    }

    private void addListener() {
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                addData();
            }
        });

    }

    public void addData() {
        String url;
        if (isBasketball) {
            url = Url.SCORE_BASKET_URL;
        } else url = Url.SCORE_NEW_URL;
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        /*
        *'Fixture':'未','Playing':'进行','Postponed':'推迟'
        *,'Suspended':'暂停','Played':'完','Cancelled':'取消'
        *（多个用英文逗号隔开)
        */
        params.putString("status", "Played,Cancelled,Postponed,Suspended");
        params.putString("dateTime", "");
        mHttp.get(url, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                if (mRefresh != null)
                    mRefresh.finishRefresh();
                if (mLoadingDialogUtils != null)
                    mLoadingDialogUtils.setDimiss();
                Gson gson = new Gson();
                ScoreBean scoreBean = gson.fromJson(result, ScoreBean.class);
                int code = scoreBean.getCode();
                String msg = scoreBean.getMsg();
                if (code == 0) {
                    map.clear();
                    mListData.clear();
                    ScoreBean.DataBean data = scoreBean.getData();
                    List<ScoreBean.DataBean.ItemsBean> items = data.getItems();
                    //用于排序
                    ArrayList<String> orderList = new ArrayList<String>();
                    for (int i = 0; i < items.size(); i++) {
                        ArrayList<ScoreBean.DataBean.ItemsBean> list;
                        if (map.containsKey(items.get(i).getDate())) {
                            list = map.get(items.get(i).getDate());
                        } else {
                            list = new ArrayList<>();
                        }
                        list.add(items.get(i));
                        map.put(items.get(i).getDate(), list);
                        if (!orderList.contains(items.get(i).getDate())) {
                            orderList.add(items.get(i).getDate());
                        }
                    }
                    for (int i = 0; i < orderList.size(); i++) {
                        ArrayList<ScoreBean.DataBean.ItemsBean> e = map.get(orderList.get(i));
                        Collections.sort(e, new Comparator<ScoreBean.DataBean.ItemsBean>() {
                            @Override
                            public int compare(ScoreBean.DataBean.ItemsBean o1, ScoreBean.DataBean.ItemsBean o2) {
                                String num1 = o1.getNum();
                                String num2 = o2.getNum();
                                if (TextUtils.isEmpty(num1) || TextUtils.isEmpty(num1)) return 0;
                                return num2.compareTo(num1);
                            }
                        });
                        mListData.add(e);
                    }
                    if (mAdapter != null && mListView != null) {
                        mAdapter.notifyDataSetChanged();
                        for (int i = 0; i < mListData.size(); i++) {
                            mListView.expandGroup(i);
                        }
                    }

                } else {
                    HintDialogUtils2 hintDialogUtils2 = new HintDialogUtils2(mContext);
                    hintDialogUtils2.setMessage(msg);
                }
                noData();
            }

            @Override
            public void onError() {
                if (mRefresh != null)
                    mRefresh.finishRefresh();
                if (mLoadingDialogUtils != null)
                    mLoadingDialogUtils.setDimiss();
                noData();
            }
        });
    }

    private void noData() {
        if (mNoData != null) {
            if (mListData.size() == 0)
                mNoData.setVisibility(View.VISIBLE);
            else mNoData.setVisibility(View.GONE);
        }
    }

    View.OnClickListener RefreshListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addData();
        }
    };
}
