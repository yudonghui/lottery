package com.daxiang.lottery.score;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.CollectionListener;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.ColletionDb;
import com.daxiang.lottery.utils.DbManager;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils2;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.NoDataView;
import com.google.gson.Gson;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author yudonghui
 * @date 2017/6/8
 * @describe May the Buddha bless bug-free!!!
 */
public class AttentionFragment extends Fragment {
    View view;
    private Context mContext;
    private NoDataView mNoData;
    private SmartRefreshLayout mRefresh;
    private ListView mListView;
    private boolean isBasketball;
    private HashMap<String, String> mapFoot = new HashMap<>();
    ArrayList<ScoreBean.DataBean.ItemsBean> mListData = new ArrayList<>();
    DbManager dbManager = new DbManager();
    private BaseAdapter mAdapter;
    CollectionListener mCollectionListener;
    private List<ScoreBean.DataBean.ItemsBean> items = new ArrayList<>();

    public void setData(Context mContext, boolean isBasketball, CollectionListener mCollectionListener) {
        this.mContext = mContext;
        this.isBasketball = isBasketball;
        this.mCollectionListener = mCollectionListener;
    }

    public void setRefresh() {
        mListData.clear();
        mapFoot.clear();
        //把收藏的数据从数据库取出来
        colltionData();
        //重新填充adapter的数据源
        for (int i = 0; i < items.size(); i++) {
            if (mapFoot.containsKey(items.get(i).getuMid())) {
                mListData.add(items.get(i));
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public void timedTask() {
        colltionData();
        addData();
    }

    public void changData(boolean isBasketball, CollectionListener mCollectionListener) {
        this.isBasketball = isBasketball;
        this.mCollectionListener = mCollectionListener;
        setAdapter();
        mLoadingDialogUtils = new LoadingDialogUtils(mContext);
        colltionData();
        addData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_attention2, null);
            initView();
        }
        // colltionData();
        // addData();
        setAdapter();
        addListener();
        return view;
    }

    private boolean isFirst = true;
    private LoadingDialogUtils mLoadingDialogUtils;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirst) {
            isFirst = false;
            mLoadingDialogUtils = new LoadingDialogUtils(mContext);
            colltionData();
            addData();
        }
    }

    public void setAdapter() {
        if (isBasketball) {
            mAdapter = new AttentionBasketAdapter();
            ((AttentionBasketAdapter) mAdapter).setData(mListData, mContext, mCollectionListener);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter = new AttentionAdapter();
            ((AttentionAdapter) mAdapter).setData(mListData, mContext, mCollectionListener);
            mListView.setAdapter(mAdapter);
        }

    }

    private void colltionData() {
        mapFoot.clear();
        try {
            List<ColletionDb> colletionDbs = dbManager.queryAll();
            if (colletionDbs != null && colletionDbs.size() != 0) {
                visibleListView();
                for (int i = 0; i < colletionDbs.size(); i++) {
                    String match_id;
                    if (isBasketball)
                        match_id = colletionDbs.get(i).getBasket_match_id();//篮球的收藏
                    else
                        match_id = colletionDbs.get(i).getMatch_id();//足球的收藏
                    if (!TextUtils.isEmpty(match_id)) {
                        mapFoot.put(match_id, match_id);
                    }
                }
            } else {
                visibleNoData();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void visibleListView() {
        if (mListView == null) return;
        mListView.setVisibility(View.VISIBLE);
        mNoData.setVisibility(View.GONE);
    }

    private void visibleNoData() {
        if (mListView == null) return;
        mListView.setVisibility(View.GONE);
        mNoData.setVisibility(View.VISIBLE);
    }

    private void initView() {
        mRefresh = (SmartRefreshLayout) view.findViewById(R.id.refresh);
        mNoData = (NoDataView) view.findViewById(R.id.no_data);
        mNoData.setRemark("您还没有关注信息");
        mListView = (ListView) view.findViewById(R.id.lv_attention);
    }

    private void addListener() {

        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                addData();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String m_id = mListData.get(position).getuMid();
                if (!TextUtils.isEmpty(m_id)) {
                    if (isBasketball) {
                        /*Intent intent = new Intent(mContext, ScoreBasketDetailActivity.class);
                        intent.putExtra("mId", m_id);
                        startActivity(intent);*/
                    } else {
                        Intent intent = new Intent(mContext, ScoreDetailActivity.class);
                        intent.putExtra("mId", m_id);
                        intent.putExtra("from", "score");
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void addData() {
        String url;
        if (isBasketball) {
            url = Url.SCORE_BASKET_URL;
        } else url = Url.SCORE_NEW_URL;
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        //状态（Fixture：未开始，Playing：进行中，Played：已结束） （多个用英文逗号隔开)
        params.putString("status", "Playing,Fixture,Played");
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
                    mListData.clear();
                    ScoreBean.DataBean data = scoreBean.getData();
                    items = data.getItems();
                    for (int i = 0; i < items.size(); i++) {
                        if (mapFoot.containsKey(items.get(i).getuMid())) {
                            mListData.add(items.get(i));
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                    HintDialogUtils2 hintDialogUtils2 = new HintDialogUtils2(mContext);
                    hintDialogUtils2.setMessage(msg);
                }
                if (mListData.size() == 0)
                    visibleNoData();
                else visibleListView();
            }

            @Override
            public void onError() {
                if (mRefresh != null)
                    mRefresh.finishRefresh();
                if (mLoadingDialogUtils != null)
                    mLoadingDialogUtils.setDimiss();
                if (mListData.size() == 0)
                    visibleNoData();
                else visibleListView();
            }
        });
    }


}
