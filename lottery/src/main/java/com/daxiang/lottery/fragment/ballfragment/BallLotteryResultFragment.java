package com.daxiang.lottery.fragment.ballfragment;

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
import android.widget.ListView;
import android.widget.Toast;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.AwardResultActivity;
import com.daxiang.lottery.adapter.balladapter.LotteryResultListAdapter;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.LotteryResultListData;
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

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public class BallLotteryResultFragment extends Fragment {
    private View mInflate;
    private SmartRefreshLayout mRefresh;
    private ListView mListView;
    private String lotcode;
    private int page = 1;
    private int totalPage = 0;
    private List<LotteryResultListData.DataBean.ItemBean> mItemsList = new ArrayList<>();
    private LotteryResultListAdapter mAdapter;
    private Context mContext;
    private NoDataView mNoData;

    public void setData(String lotcode, Context mContext) {
        this.lotcode = lotcode;
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_lotteryresult, null);
            //LoadingDialogUtils.loading(getContext());
            mNoData = (NoDataView) mInflate.findViewById(R.id.no_data);
            mNoData.setButtonText(mNoData.REFRESH);
            mRefresh = (SmartRefreshLayout) mInflate.findViewById(R.id.refresh);
            mListView = (ListView) mInflate.findViewById(R.id.lottery_result_detail_lv);
        }

        mAdapter = new LotteryResultListAdapter(getContext(), mItemsList);
        mListView.setAdapter(mAdapter);
        //addData();
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

    private void addData() {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        String url = Url.RESULT_DETAIL_URL + "?lotCode=" + lotcode + "&pageSize=20&pageStart=" + page;
        HttpInterface2 mHttp = new HttpUtils2(getActivity());
        mHttp.jsonByUrl(url, new JsonInterface() {

            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();

                Gson gson = new Gson();
                LotteryResultListData listData = gson.fromJson(result, LotteryResultListData.class);
                if (listData.getCode() == 0) {
                    totalPage = listData.getData().getTotalNu();
                    mItemsList.addAll(listData.getData().getItem());
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), listData.getMsg(), Toast.LENGTH_SHORT).show();
                }
                if (mItemsList.size() == 0) {
                    mNoData.setVisibility(View.VISIBLE);
                } else mNoData.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();

                if (mItemsList.size() == 0) {
                    mNoData.setVisibility(View.VISIBLE);
                } else mNoData.setVisibility(View.GONE);
            }
        });
    }

    private void addListener() {
        mNoData.setOnClickListener(RefreshListener);

        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mItemsList.clear();
                page = 1;
                addData();
            }
        });
        mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (page < totalPage) {
                    page++;
                    addData();
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
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!"21406".equals(lotcode) && !"36".equals(lotcode)
                        && !"50".equals(lotcode)) {
                    Intent intent = new Intent(getActivity(), AwardResultActivity.class);
                    intent.putExtra("lotcode", lotcode);
                    intent.putExtra("issue", mItemsList.get(position).getIssue());
                    intent.putExtra("awardNum", mItemsList.get(position).getAwardNumber());
                    startActivity(intent);
                }
            }
        });
    }

    View.OnClickListener RefreshListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mItemsList.clear();
            page = 1;
            totalPage = 1;
            addData();
        }
    };
}
