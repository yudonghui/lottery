package com.daxiang.lottery.activity.lotteryactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.NewsData;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.NetworkUtils;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.NoDataView;
import com.google.gson.Gson;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnLoadMoreListener;
import com.ydh.refresh_layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;

public class NewsAdActivity extends AppCompatActivity implements View.OnClickListener {
    // Content View Elements

    private ImageView mBack;
    private TextView mAd;
    private TextView mNews;
    private ListView mPull_listview;
    private SmartRefreshLayout mRefresh;
    private int pageSize = 20;
    private int currentPage = 1;
    private int totalPage;
    private List<NewsData.DataBean.ItemsBean> mList = new ArrayList<>();
    private NewsAdAdapter mAdapter;
    boolean adFlag = true;
    boolean newsFlag = false;
    int category = 1;
    private String newsOrAd;
    private TextView mNewsOrAd;
    private LinearLayout mLlNewsOrAd;
    private NoDataView mNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_ad);
        Intent intent = getIntent();
        newsOrAd = intent.getStringExtra("newsOrAd");
        bindViews();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        mAdapter = new NewsAdAdapter();
        mPull_listview.setAdapter(mAdapter);

        newsAdData();
        addListener();
    }

    private void bindViews() {
        mNoData = (NoDataView) findViewById(R.id.no_data);
        mNewsOrAd = (TextView) findViewById(R.id.news_or_ad);
        mLlNewsOrAd = (LinearLayout) findViewById(R.id.ll_newsOrAd);
        mBack = (ImageView) findViewById(R.id.back);
        mAd = (TextView) findViewById(R.id.ad);
        mNews = (TextView) findViewById(R.id.news);
        mRefresh = (SmartRefreshLayout) findViewById(R.id.refresh);
        mPull_listview = (ListView) findViewById(R.id.pull_listview);
        if ("news".equals(newsOrAd)) {
            mLlNewsOrAd.setVisibility(View.GONE);
            mNewsOrAd.setVisibility(View.VISIBLE);
            mNewsOrAd.setText("新闻资讯");
            category = 3;
        } else if ("ad".equals(newsOrAd)) {
            mLlNewsOrAd.setVisibility(View.GONE);
            mNewsOrAd.setVisibility(View.VISIBLE);
            mNewsOrAd.setText("公告通知");
            category = 1;
        }
    }

    private void addListener() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //公告
        mAd.setOnClickListener(this);
        //新闻
        mNews.setOnClickListener(this);
        //下拉刷新
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mList.clear();
                currentPage = 1;
                newsAdData();
            }
        });
        mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (currentPage < totalPage) {
                    currentPage++;
                    newsAdData();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ad:
                if (!adFlag) {
                    mAd.setTextColor(Color.RED);
                    mAd.setBackgroundColor(Color.WHITE);
                    mNews.setTextColor(Color.WHITE);
                    mNews.setBackgroundResource(R.drawable.redright);
                    adFlag = true;
                    newsFlag = false;
                    category = 1;
                    mList.clear();
                    newsAdData();
                }
                break;
            case R.id.news:
                if (!newsFlag) {
                    mAd.setTextColor(Color.WHITE);
                    mAd.setBackgroundResource(R.drawable.redleft);
                    mNews.setBackgroundColor(Color.WHITE);
                    mNews.setTextColor(Color.RED);
                    adFlag = false;
                    newsFlag = true;
                    category = 3;
                    mList.clear();
                    newsAdData();
                }
                break;
        }
    }

    private void newsAdData() {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            HintDialogUtils.setHintDialog(this);
            HintDialogUtils.setMessage("您的网络未连接，请连接后重试！");
            mNoData.setVisibility(View.VISIBLE);
            mRefresh.finishRefresh();
            mRefresh.finishLoadMore();
            return;
        }
        // String url = Url.NEWS_URL + "?category=" + category + "&pageSize=" + pageSize + "&currentPage=" + currentPage;
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(this);
        HttpInterface2 mHttpInterface = new HttpUtils2(this);
        Bundle params = new Bundle();
        params.putString("token", LotteryApp.token);
        params.putString("timeStamp", System.currentTimeMillis() + "");
        params.putString("pageSize", "20");
        params.putInt("pageNumber", currentPage);
        params.putString("category", category + "");
        //状态(0，正常 1，隐藏) 无则获取所有
        params.putString("status", "0");
        mHttpInterface.post(Url.NEWS_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
                Gson gson = new Gson();
                NewsData newsData = gson.fromJson(result, NewsData.class);
                if (newsData.getCode() == 0) {
                    totalPage = newsData.getPageCount();
                    mList.addAll(newsData.getData().getItems());
                   /* NewsAdAdapter mAdapter = new NewsAdAdapter();
                    mPull_listview.setAdapter(mAdapter);*/
                    mAdapter.notifyDataSetChanged();
                }
                if (mList.size() == 0) {
                    mNoData.setVisibility(View.VISIBLE);
                } else mNoData.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
                mRefresh.finishRefresh();
                mRefresh.finishLoadMore();
                if (mList.size() == 0) {
                    mNoData.setVisibility(View.VISIBLE);
                } else mNoData.setVisibility(View.GONE);
            }
        });
//http://121.41.58.131:8090/cxcms/restful/content/fragment/list?category=3&pageSize=20&currentPage=1
//新闻详情
//http://121.41.58.131:8090/cxcms/restful/content/fragment/detail?fragmentId=209

    }

    class NewsAdAdapter extends BaseAdapter {

        @Override
        public int getCount() {
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mViewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(parent.getContext(), R.layout.item_news_ad, null);
                mViewHolder = new ViewHolder();
                mViewHolder.mContent = (TextView) convertView.findViewById(R.id.news_ad_content);
                mViewHolder.mTitle = (TextView) convertView.findViewById(R.id.news_ad_title);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }
            mViewHolder.mContent.setText(mList.get(position).getTitle());
            mViewHolder.mTitle.setText("[" + mList.get(position).getTagA() + "]");
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int fragmentId = mList.get(position).getId();
                    Intent intent = new Intent(NewsAdActivity.this, NewsAdDetailActivity.class);
                    intent.putExtra("fragmentId", fragmentId);
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }

    class ViewHolder {
        TextView mTitle;
        TextView mContent;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
