package com.daxiang.lottery.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.lotteryactivity.NewsAdActivity;
import com.daxiang.lottery.activity.wonderfulactivity.LotteryForum.DisuzActivity;
import com.daxiang.lottery.activity.wonderfulactivity.LotteryResultActivity;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.NewMatchData;
import com.daxiang.lottery.score.ScoreActivity;
import com.daxiang.lottery.score.ScoreDetailActivity;
import com.daxiang.lottery.utils.ClickUtils;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.view.TitleBar;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.ydh.refresh_layout.SmartRefreshLayout;
import com.ydh.refresh_layout.api.RefreshLayout;
import com.ydh.refresh_layout.listener.OnRefreshListener;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class FaxianFragment extends Fragment {
    private View v;
    // Content View Elements
    private View mTitleView;
    private TitleBar mTitlebar;
    private LinearLayout mRl_lottery_results;
    private LinearLayout mRl_gendan;
    private LinearLayout mRl_news;
    private LinearLayout mRl_ad;
    private LinearLayout mRl_forum;
    private Context mContext;
    private LinearLayout mLl_new_match;
    private ImageView mHome_logo;
    private TextView mHome_team;
    private TextView mScore;
    private ImageView mGuest_logo;
    private TextView mGuest_team;
    private SmartRefreshLayout mRefresh;
    private String mId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_faxian, null);
        mContext = getContext();
        bindViews();
        addListener();
        addData();
        return v;
    }

    private void bindViews() {
        mTitleView = v.findViewById(R.id.titleView);
        mTitlebar = (TitleBar) v.findViewById(R.id.wonderful_titlebar);
        mTitlebar.setImageTitleVisibility(false);
        mTitlebar.setTitle("精彩发现");
        mTitlebar.setTitleVisibility(true);
        mTitlebar.setBackVisibility(false);
        mRl_lottery_results = (LinearLayout) v.findViewById(R.id.rl_lottery_results);
        mRl_gendan = (LinearLayout) v.findViewById(R.id.rl_gendan);
        mRl_news = (LinearLayout) v.findViewById(R.id.rl_news);
        mRl_ad = (LinearLayout) v.findViewById(R.id.rl_ad);
        mRl_forum = (LinearLayout) v.findViewById(R.id.ll_forum);
        mLl_new_match = (LinearLayout) v.findViewById(R.id.ll_new_match);
        mHome_logo = (ImageView) v.findViewById(R.id.home_logo);
        mHome_team = (TextView) v.findViewById(R.id.home_team);
        mScore = (TextView) v.findViewById(R.id.score);
        mGuest_logo = (ImageView) v.findViewById(R.id.guest_logo);
        mGuest_team = (TextView) v.findViewById(R.id.guest_team);
        mRefresh = (SmartRefreshLayout) v.findViewById(R.id.refresh);
        //论坛目前没接入
        mRl_forum.setVisibility(View.VISIBLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)

        {
            mTitleView.setVisibility(View.VISIBLE);
            int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (identifier > 0) {
                int dimensionPixelSize = getResources().getDimensionPixelSize(identifier);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dimensionPixelSize);
                mTitleView.setLayoutParams(layoutParams);
            }
        } else mTitleView.setVisibility(View.GONE);

    }

    private void addListener() {
        //开奖结果
        mRl_lottery_results.setOnClickListener(ResultListener);
        mRl_gendan.setOnClickListener(GendanListener);
        mRl_news.setOnClickListener(NewsListener);
        mRl_ad.setOnClickListener(AdListener);
        //论坛
        mRl_forum.setOnClickListener(ForumListener);
        mLl_new_match.setOnClickListener(NewMatchListener);
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                addData();
            }
        });
    }

    View.OnClickListener NewMatchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (TextUtils.isEmpty(mId)) return;
            //防止连续点击
            if (ClickUtils.isFastClick(3000)) {
                return;
            }
            Intent intent = new Intent(mContext, ScoreDetailActivity.class);
            intent.putExtra("mId", mId);
            startActivity(intent);
        }
    };
    //论坛
    View.OnClickListener ForumListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //防止连续点击
            if (ClickUtils.isFastClick(2000)) {
                return;
            }
            Intent intent = new Intent(getActivity(), DisuzActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener ResultListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //防止连续点击
            if (ClickUtils.isFastClick(2000)) {
                return;
            }
            Intent intent = new Intent(getActivity(), LotteryResultActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener GendanListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //防止连续点击
            if (ClickUtils.isFastClick(2000)) {
                return;
            }
            Intent intent = new Intent(getActivity(), ScoreActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener NewsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //防止连续点击
            if (ClickUtils.isFastClick(2000)) {
                return;
            }
            Intent intent = new Intent(getActivity(), NewsAdActivity.class);
            intent.putExtra("newsOrAd", "news");
            startActivity(intent);
        }
    };
    View.OnClickListener AdListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //防止连续点击
            if (ClickUtils.isFastClick(2000)) {
                return;
            }
            Intent intent = new Intent(getActivity(), NewsAdActivity.class);
            intent.putExtra("newsOrAd", "ad");
            startActivity(intent);
        }
    };

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isStart) {
                addData();
                handler.postDelayed(this, refreshTime);
            }
        }
    };
    private boolean isStart = false;//最新的比赛是否开赛
    private int refreshTime = 30000;

    private void addData() {
        HttpInterface2 mHttp = new HttpUtils2(mContext);
        mHttp.jsonByUrl(Url.NEW_MATCH, new JsonInterface() {
            @Override
            public void callback(String result) {
                mRefresh.finishRefresh();
                Gson gson = new Gson();
                NewMatchData scoreDetailBean = gson.fromJson(result, NewMatchData.class);
                int code = scoreDetailBean.getCode();
                String msg = scoreDetailBean.getMsg();
                if (code == 0) {
                    mLl_new_match.setVisibility(View.VISIBLE);
                    NewMatchData.DataBean data = scoreDetailBean.getData();
                    NewMatchData.DataBean.ItemsBean items = data.getItems();
                    String a_pic = items.getA_pic();
                    String h_pic = items.getH_pic();
                    String guest_short_cn = items.getGuest_short_cn();
                    String home_short_cn = items.getHome_short_cn();
                    String full_score = items.getFull_score();
                    mId = items.getUMid();
                    mGuest_team.setText(TextUtils.isEmpty(guest_short_cn) ? "--" : guest_short_cn);
                    mHome_team.setText(TextUtils.isEmpty(home_short_cn) ? "--" : home_short_cn);
                    if (TextUtils.isEmpty(full_score) || "VS".equals(full_score)) {
                        mScore.setText("未开始");
                        isStart = false;
                    } else {
                        mScore.setText(full_score);
                        isStart = true;
                    }
                    if (!TextUtils.isEmpty(a_pic))
                        Picasso.with(mContext)
                                .load(a_pic)
                                .config(Bitmap.Config.RGB_565)
                                .placeholder(R.mipmap.guest_team)
                                .error(R.mipmap.guest_team)
                                .into(mGuest_logo);
                    //  HttpUtils2.display(mGuest_logo, a_pic, R.mipmap.guest_team);
                    if (!TextUtils.isEmpty(h_pic))
                        Picasso.with(mContext)
                                .load(h_pic)
                                .config(Bitmap.Config.RGB_565)

                                .placeholder(R.mipmap.home_team)
                                .error(R.mipmap.home_team)
                                .into(mHome_logo);
                    // HttpUtils2.display(mHome_logo, h_pic, R.mipmap.home_team);
                } else {
                    mLl_new_match.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError() {
                mRefresh.finishRefresh();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, refreshTime);
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }
}
