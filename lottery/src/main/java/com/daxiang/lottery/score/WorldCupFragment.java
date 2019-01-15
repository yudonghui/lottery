package com.daxiang.lottery.score;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.ScoreBoardBean;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.view.NoDataView;
import com.daxiang.lottery.view.stickyView.PerformerListAdapter;
import com.daxiang.lottery.view.stickyView.StickyItemDecoration;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Android on 2018/6/14.
 */

public class WorldCupFragment extends Fragment {
    private Context mContext;
    private View mInflate;
    private RecyclerView mRecyclerView;
    private NoDataView mNoData;
    // private SmartRefreshLayout mRefresh;
    List<ScoreBoardBean.DataBean.ItemsBean> mDataList = new ArrayList<>();

    private String leagueId;
    private String home_id;
    private String away_id;
    private PerformerListAdapter mRecyclerAdapter;

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
            mInflate = inflater.inflate(R.layout.fragment_world_cup, null);
            init();
        }
        mContext = getContext();
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

    private List<ScoreBoardBean.DataBean.ItemsBean> itemsBeen0;
    private List<ScoreBoardBean.DataBean.ItemsBean> itemsBeen1;
    private List<ScoreBoardBean.DataBean.ItemsBean> itemsBeen2;
    private List<ScoreBoardBean.DataBean.ItemsBean> itemsBeen3;
    private List<ScoreBoardBean.DataBean.ItemsBean> itemsBeen4;
    private List<ScoreBoardBean.DataBean.ItemsBean> itemsBeen5;
    private List<ScoreBoardBean.DataBean.ItemsBean> itemsBeen6;
    private List<ScoreBoardBean.DataBean.ItemsBean> itemsBeen7;

    private void init() {
        mNoData = (NoDataView) mInflate.findViewById(R.id.no_data);
        // mRefresh = (SmartRefreshLayout) mInflate.findViewById(R.id.refresh);
        mRecyclerView = (RecyclerView) mInflate.findViewById(R.id.rv);
        initMap();
        itemsBeen0 = new ArrayList<>();
        itemsBeen1 = new ArrayList<>();
        itemsBeen2 = new ArrayList<>();
        itemsBeen3 = new ArrayList<>();
        itemsBeen4 = new ArrayList<>();
        itemsBeen5 = new ArrayList<>();
        itemsBeen6 = new ArrayList<>();
        itemsBeen7 = new ArrayList<>();
    }


    private void addListener() {
        /*mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                addData();
            }
        });*/
    }

    private void addData() {
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("leagueId", leagueId);
        mHttp.get(Url.SCORE_BOARD, params, new JsonInterface() {
            @Override
            public void callback(String result) {
               // mRefresh.finishRefresh();
                ScoreBoardBean scoreBoardBean = new Gson().fromJson(result, ScoreBoardBean.class);
                int code = scoreBoardBean.getCode();
                if (code == 0) {
                    ScoreBoardBean.DataBean data = scoreBoardBean.getData();
                    List<ScoreBoardBean.DataBean.ItemsBean> items = data.getItems();
                    mDataList.clear();
                    itemsBeen0.clear();
                    itemsBeen1.clear();
                    itemsBeen2.clear();
                    itemsBeen3.clear();
                    itemsBeen4.clear();
                    itemsBeen5.clear();
                    itemsBeen6.clear();
                    itemsBeen7.clear();
                    for (int i = 0; i < items.size(); i++) {
                        ScoreBoardBean.DataBean.ItemsBean itemsBean = items.get(i);
                        itemsBean.setItemType(2222);
                        String team_id = itemsBean.getTeam_id();
                        if (mMap.get(team_id) == 0) {
                            itemsBeen0.add(itemsBean);
                        } else if (mMap.get(team_id) == 1) {
                            itemsBeen1.add(itemsBean);
                        } else if (mMap.get(team_id) == 2) {
                            itemsBeen2.add(itemsBean);
                        } else if (mMap.get(team_id) == 3) {
                            itemsBeen3.add(itemsBean);
                        } else if (mMap.get(team_id) == 4) {
                            itemsBeen4.add(itemsBean);
                        } else if (mMap.get(team_id) == 5) {
                            itemsBeen5.add(itemsBean);
                        } else if (mMap.get(team_id) == 6) {
                            itemsBeen6.add(itemsBean);
                        } else if (mMap.get(team_id) == 7) {
                            itemsBeen7.add(itemsBean);
                        }
                    }
                    ScoreBoardBean.DataBean.ItemsBean items0Bean = new ScoreBoardBean.DataBean.ItemsBean();
                    items0Bean.setTitle("A组");
                    mDataList.add(items0Bean);
                    mDataList.addAll(itemsBeen0);

                    ScoreBoardBean.DataBean.ItemsBean items1Bean = new ScoreBoardBean.DataBean.ItemsBean();
                    items1Bean.setTitle("B组");
                    mDataList.add(items1Bean);
                    mDataList.addAll(itemsBeen1);

                    ScoreBoardBean.DataBean.ItemsBean items2Bean = new ScoreBoardBean.DataBean.ItemsBean();
                    items2Bean.setTitle("C组");
                    mDataList.add(items2Bean);
                    mDataList.addAll(itemsBeen2);

                    ScoreBoardBean.DataBean.ItemsBean items3Bean = new ScoreBoardBean.DataBean.ItemsBean();
                    items3Bean.setTitle("D组");
                    mDataList.add(items3Bean);
                    mDataList.addAll(itemsBeen3);

                    ScoreBoardBean.DataBean.ItemsBean items4Bean = new ScoreBoardBean.DataBean.ItemsBean();
                    items4Bean.setTitle("E组");
                    mDataList.add(items4Bean);
                    mDataList.addAll(itemsBeen4);

                    ScoreBoardBean.DataBean.ItemsBean items5Bean = new ScoreBoardBean.DataBean.ItemsBean();
                    items5Bean.setTitle("F组");
                    mDataList.add(items5Bean);
                    mDataList.addAll(itemsBeen5);

                    ScoreBoardBean.DataBean.ItemsBean items6Bean = new ScoreBoardBean.DataBean.ItemsBean();
                    items6Bean.setTitle("G组");
                    mDataList.add(items6Bean);
                    mDataList.addAll(itemsBeen6);

                    ScoreBoardBean.DataBean.ItemsBean items7Bean = new ScoreBoardBean.DataBean.ItemsBean();
                    items7Bean.setTitle("H组");
                    mDataList.add(items7Bean);
                    mDataList.addAll(itemsBeen7);

                    mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                    mRecyclerView.addItemDecoration(new StickyItemDecoration());
                    mRecyclerAdapter = new PerformerListAdapter(mContext, mDataList, home_id, away_id);
                    mRecyclerView.setAdapter(mRecyclerAdapter);
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
               // mRefresh.finishRefresh();
                if (mNoData == null) return;
                if (mDataList.size() == 0) {
                    mNoData.setVisibility(View.VISIBLE);
                } else {
                    mNoData.setVisibility(View.GONE);
                }
            }
        });
    }

    private Map<String, Integer> mMap = new HashMap<>();

    private void initMap() {
        mMap.clear();
        mMap.put("414", 0);
        mMap.put("565", 0);
        mMap.put("152", 0);
        mMap.put("52", 0);
        mMap.put("912", 1);
        mMap.put("186", 1);
        mMap.put("16", 1);
        mMap.put("51", 1);
        mMap.put("410", 2);
        mMap.put("742", 2);
        mMap.put("632", 2);
        mMap.put("433", 2);
        mMap.put("580", 3);
        mMap.put("310", 3);
        mMap.put("471", 3);
        mMap.put("204", 3);
        mMap.put("381", 4);
        mMap.put("510", 4);
        mMap.put("165", 4);
        mMap.put("171", 4);
        mMap.put("431", 5);
        mMap.put("172", 5);
        mMap.put("208", 5);
        mMap.put("214", 5);
        mMap.put("481", 6);
        mMap.put("907", 6);
        mMap.put("13", 6);
        mMap.put("834", 6);
        mMap.put("467", 7);
        mMap.put("383", 7);
        mMap.put("173", 7);
        mMap.put("164", 7);
    }
}
