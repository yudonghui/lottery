package com.daxiang.lottery.fragment.jcfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.adapter.jcadapter.JclqResultAdapter;
import com.daxiang.lottery.adapter.jcadapter.JczqResultAdapter;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.JclqData;
import com.daxiang.lottery.entity.LotteryResultData;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.NoDataView;
import com.google.gson.Gson;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/8/17 0017.
 */
public class JczqLotteryResultFragment extends Fragment {
    View v;
    private ExpandableListView mListView;
    private SmartRefreshLayout mRefresh;
    private NoDataView mNoData;
    private String mLotcode;
    public HashMap<String, ArrayList<LotteryResultData.DataBean.ItemBean>> mapJZ = new HashMap<>();
    public HashMap<String, ArrayList<JclqData.DataBean>> mapJL = new HashMap<>();
    public ArrayList<ArrayList<LotteryResultData.DataBean.ItemBean>> mListDataJZ = new ArrayList<>();
    public ArrayList<ArrayList<JclqData.DataBean>> mListDataJL = new ArrayList<>();
    private Context mContext;

    public void setData(String lotcode, Context mContext) {
        this.mContext = mContext;
        if ("1000".equals(lotcode) || "42".equals(lotcode)) {
            this.mLotcode = "42";
        }
        if ("1001".equals(lotcode) || "43".equals(lotcode)) {
            this.mLotcode = "43";
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (v == null) {
            v = inflater.inflate(R.layout.jczq_lottery_result, null);
            mRefresh = (SmartRefreshLayout) v.findViewById(R.id.refresh);
            mListView = (ExpandableListView) v.findViewById(R.id.lottery_result_lv);
            mListView.setGroupIndicator(null);
            mNoData = (NoDataView) v.findViewById(R.id.no_data);
            mNoData.setButtonText(mNoData.REFRESH);
        }
        mContext = getContext();
       /* mListDataJZ.clear();
        mListDataJL.clear();
        mapJZ.clear();
        mapJL.clear();
        addData();*/
        addListener();
        return v;
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

    private void addData() {
        // LoadingDialogUtils.loading(getContext());
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String issue = simpleDateFormat.format(date);

        HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("lotCode", mLotcode);
        params.putString("queryDays", "4");
        mHttp.get(Url.RESULT_DETAIL_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
                Gson gson = new Gson();
                LotteryResultData jczqData = gson.fromJson(result, LotteryResultData.class);
                if (jczqData.getCode() == 0) {
                    List<LotteryResultData.DataBean.ItemBean> data = jczqData.getData().getItem();
                    //用于排序
                    ArrayList<String> orderList = new ArrayList<String>();
                    for (int i = 0; i < data.size(); i++) {
                        ArrayList<LotteryResultData.DataBean.ItemBean> list;
                        if (mapJZ.containsKey(data.get(i).getIssue())) {
                            list = mapJZ.get(data.get(i).getIssue());
                        } else {
                            list = new ArrayList<>();
                        }
                        list.add(data.get(i));
                        mapJZ.put(data.get(i).getIssue(), list);
                        if (!orderList.contains(data.get(i).getIssue())) {
                            orderList.add(data.get(i).getIssue());
                        }
                    }
                    for (int i = 0; i < orderList.size(); i++) {
                        mListDataJZ.add(mapJZ.get(orderList.get(i)));
                    }
                    if ("42".equals(mLotcode)) {
                        JczqResultAdapter mAdapter = new JczqResultAdapter();
                        mAdapter.setData(getContext(), mLotcode, mListDataJZ);
                        mListView.setAdapter(mAdapter);
                    } else {
                        JclqResultAdapter mAdapter = new JclqResultAdapter();
                        mAdapter.setData(getContext(), mLotcode, mListDataJZ);
                        mListView.setAdapter(mAdapter);
                    }


                    for (int i = 0; i < mListDataJZ.size(); i++) {
                        mListView.expandGroup(i);
                    }
                }  //如果没有数据是显示个图片
                if (mListDataJZ != null && mListDataJZ.size() != 0) {
                    mListView.setVisibility(View.VISIBLE);
                    mNoData.setVisibility(View.GONE);
                } else {
                    mListView.setVisibility(View.GONE);
                    mNoData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
                if (mListDataJZ != null && mListDataJZ.size() != 0) {
                    mListView.setVisibility(View.VISIBLE);
                    mNoData.setVisibility(View.GONE);
                } else {
                    mListView.setVisibility(View.GONE);
                    mNoData.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void addListener() {
        mNoData.setOnClickListener(RefreshListener);
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mapJZ.clear();
                mapJL.clear();
                mListDataJZ.clear();
                mListDataJL.clear();
                addData();
            }
        });
    }

    View.OnClickListener RefreshListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mapJZ.clear();
            mapJL.clear();
            mListDataJZ.clear();
            mListDataJL.clear();
            addData();
        }
    };
}
