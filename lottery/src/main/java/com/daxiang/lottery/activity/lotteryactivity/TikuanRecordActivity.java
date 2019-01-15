package com.daxiang.lottery.activity.lotteryactivity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.TikuanRecordData;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.view.NoDataView;
import com.daxiang.lottery.view.TitleBar;
import com.google.gson.Gson;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnLoadMoreListener;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;

public class TikuanRecordActivity extends AppCompatActivity {
    private ListView mListView;
    private SmartRefreshLayout mRefresh;
    List<TikuanRecordData.DataBean.ItemsBean> mList;
    private int pageStart = 1;
    private int totalPage = 1;
    public TitleBar mTitleBar;
    private NoDataView mNoData;
    private boolean isRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tikuan_record);
        initView();
        addData();
        addListener();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void initView() {
        mTitleBar = (TitleBar) findViewById(R.id.titlebar_base);
        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitle("提款记录");
        mTitleBar.setTitleVisibility(true);
        mListView = (ListView) findViewById(R.id.tikuan_lv);
        mRefresh = (SmartRefreshLayout) findViewById(R.id.refresh);

        mNoData = (NoDataView) findViewById(R.id.no_data);
        mList = new ArrayList<>();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void addData() {
        HttpInterface2 mHttpInterface = new HttpUtils2(this);
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("timeStamp", System.currentTimeMillis() + "");
        params.putString("tradeType", "4");
        params.putString("userId", LotteryApp.uid);
        params.putString("pageSize", "20");
        params.putString("pageStart", pageStart + "");
        mHttpInterface.post(Url.DRAW_MONEY_RECORD_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                if (isRefresh) {//如果是下拉刷新。清空数据源。
                    mList.clear();
                    isRefresh = false;
                }
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
                Gson gson = new Gson();
                TikuanRecordData tikuanRecordData = gson.fromJson(result, TikuanRecordData.class);
                if (tikuanRecordData.getCode() == 0) {
                    totalPage = tikuanRecordData.getData().getPageNum();
                    mList.addAll(tikuanRecordData.getData().getItems());
                    if (mList == null || mList.size() == 0) {
                        mNoData.setVisibility(View.VISIBLE);
                        return;
                    }
                    RecordAdapter mAdapter = new RecordAdapter();
                    mListView.setAdapter(mAdapter);
                } else {
                    mNoData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError() {
                mNoData.setVisibility(View.VISIBLE);
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
            }
        });
    }

    private void addListener() {


        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isRefresh = true;
                pageStart = 1;
                addData();
            }
        });
        mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageStart < totalPage) {
                    pageStart++;
                    addData();
                } else  {
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

    class RecordAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mList == null) {
                return 0;
            }
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mViewHolder = null;
            Context context = parent.getContext();
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_tikuan_record, null);
                mViewHolder = new ViewHolder();
                mViewHolder.mTime = (TextView) convertView.findViewById(R.id.tikuan_time);
                mViewHolder.mBank = (TextView) convertView.findViewById(R.id.tikuan_bank);
                mViewHolder.mMoney = (TextView) convertView.findViewById(R.id.tikuan_money);
                mViewHolder.mUsername = (TextView) convertView.findViewById(R.id.tikuan_username);
                mViewHolder.mState = (TextView) convertView.findViewById(R.id.tikuan_state);
                mViewHolder.mNumber = (TextView) convertView.findViewById(R.id.tikuan_number);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }
            TikuanRecordData.DataBean.ItemsBean itemsBean = mList.get(position);
            SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            String time = format.format(itemsBean.getApplyTime());
            mViewHolder.mTime.setText(time);
            String status = "";
            switch (itemsBean.getStatus()) {
                case 0:
                    status = "提款成功";
                    break;
                case 1:
                case 3:
                case 5:
                case 7:
                    status = "审核中";
                    break;
                case 2:
                case 4:
                case 6:
                    status = "提款驳回";
                    break;
            }
            mViewHolder.mState.setText(status);
            mViewHolder.mUsername.setText(itemsBean.getApplyName());
            mViewHolder.mMoney.setText(itemsBean.getAmount());
            mViewHolder.mBank.setText(itemsBean.getCardType());
            String tradeRecordId = itemsBean.getTradeRecordId();
            mViewHolder.mNumber.setText(tradeRecordId);
            return convertView;
        }
    }

    class ViewHolder {
        private TextView mTime;
        private TextView mUsername;
        private TextView mBank;
        private TextView mMoney;
        private TextView mNumber;
        private TextView mState;
    }
}
