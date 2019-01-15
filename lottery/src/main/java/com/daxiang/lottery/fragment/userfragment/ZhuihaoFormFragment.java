package com.daxiang.lottery.fragment.userfragment;

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

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.lotteryactivity.ZhuihaoDetailActivity;
import com.daxiang.lottery.adapter.ZhuihaoFormAdapter;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.ZhuiHaoBean;
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
 * @author yudonghui
 * @date 2017/12/18
 * @describe May the Buddha bless bug-free!!!
 */
public class ZhuihaoFormFragment extends Fragment {
    private Context mContext;
    private SmartRefreshLayout mRefresh;
    private View mInflate;
    private ListView mListView;
    private NoDataView mNoData;
    private ZhuihaoFormAdapter mAdapter;
    private LoadingDialogUtils loadingDialogUtils;
    private int pn = 1;
    private int ps = 20;
    private int totalPages = 1;
    List<ZhuiHaoBean.DataBean.ListBean> mList = new ArrayList<>();

    public void setData(Context mContext) {
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_zhuihao_form, null);
            initView();
        }
        mContext = getActivity();
        mAdapter = new ZhuihaoFormAdapter(mList);
        mListView.setAdapter(mAdapter);
        //  addData();
        addListener();
        return mInflate;
    }

    private void initView() {
        mListView = (ListView) mInflate.findViewById(R.id.listview);
        mRefresh = (SmartRefreshLayout) mInflate.findViewById(R.id.refresh);
        mNoData = (NoDataView) mInflate.findViewById(R.id.no_data);
    }

    private boolean isFirst = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFirst) {
            isFirst = false;
            loadingDialogUtils = new LoadingDialogUtils(mContext);
            addData();
        }
    }

    private void addData() {
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("pn", pn + "");
        params.putString("ps", ps + "");
        mHttp.get(Url.ZHUIHAO_FORM_URL, params, new JsonInterface() {

            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
                Gson gson = new Gson();
                ZhuiHaoBean zhuiHaoBean = gson.fromJson(result, ZhuiHaoBean.class);
                int code = zhuiHaoBean.getCode();
                String msg = zhuiHaoBean.getMsg();
                if (code == 0) {
                    totalPages = zhuiHaoBean.getData().getTotalPages();
                    mList.addAll(zhuiHaoBean.getData().getList());
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }
                isData();
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
                isData();
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
            }
        });
    }

    private void isData() {//是否有数据
        if (mList.size() == 0) {
            mNoData.setVisibility(View.VISIBLE);
            mNoData.setButtonText(mNoData.TOUZHU);
            mNoData.setBackGround(R.mipmap.no_data);
            mNoData.setRemark("暂追号订单信息");
        } else mNoData.setVisibility(View.GONE);
    }

    private void addListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ZhuiHaoBean.DataBean.ListBean listBean = mList.get(position);
                Intent intent = new Intent(mContext, ZhuihaoDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("listBean", listBean);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pn = 1;
                mList.clear();
                addData();
            }
        });
        mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pn < totalPages) {
                    pn++;
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
    }

}
