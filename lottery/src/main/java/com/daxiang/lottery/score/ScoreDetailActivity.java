package com.daxiang.lottery.score;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BaseNoTitleActivity;
import com.daxiang.lottery.activity.JczqActivity;
import com.daxiang.lottery.adapter.balladapter.DltViewPagerAdapter;
import com.daxiang.lottery.common.SpUtils;
import com.daxiang.lottery.common.TablayoutTabView;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.ScoreHeaderBean;
import com.daxiang.lottery.utils.ClickUtils;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ScoreDetailActivity extends BaseNoTitleActivity {

    private String mId;
    private Context mContext;
    private TextView mNoData;
    private ImageView mImage_titlebar_back;
    private TextView mTitlebar;
    private LinearLayout mLlTitle;//红色的头部
    private TextView mHomeTeam;
    private TextView mTime;
    private TextView mHalf_score;
    private TextView mCurrentScore;
    private TextView mGuestTeam;
    private TextView mHomeRank;
    private TextView mGuestRank;
    private ImageView mHomeAvatar;
    private ImageView mGuestAvatar;
    private TextView mBuyConfirm;
    private DltViewPagerAdapter mVpAdapter;
    private ArrayList<Fragment> mFragmentList;
    private List<String> mTitleList;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private int refreshTime = 30000;
    private LoadingDialogUtils loadingDialogUtils;
    private ScoreLiveFragment mScoreLiveFragment;
    private AnalysisFragment mAnalysisFragment;
    private String from;
    private OddsFragment mOddsFragment;
    private AsiaOddsFragment mAsiaOddsFragment;
    private String league_name;
    private int buyPrivilege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_detail);
        buyPrivilege = SpUtils.getBuyPrivilege();
        mContext = this;
        mId = getIntent().getStringExtra("mId");
        from = getIntent().getStringExtra("from");
        initView();
        loadingDialogUtils = new LoadingDialogUtils(mContext);
        addData();
        mVpAdapter = new DltViewPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        //填充适配器
        mViewPager.setAdapter(mVpAdapter);
        //TabLayout加载viewpager
        mTabLayout.setupWithViewPager(mViewPager);
        addListener();
        if ("score".equals(from)) {//从比分直播列表跳转过来
            mViewPager.setCurrentItem(0);
        } else {//从赛事详情跳转过来
            mViewPager.setCurrentItem(1);
        }
    }

    private void initView() {
        mNoData = (TextView) findViewById(R.id.no_data);
        mImage_titlebar_back = (ImageView) findViewById(R.id.image_titlebar_back);
        mTitlebar = (TextView) findViewById(R.id.titlebar);

        mTitlebar.setFocusable(true);
        mTitlebar.setFocusableInTouchMode(true);//这三个设置是为了刚进入这个界面的时候不是显示最下面。
        mTitlebar.requestFocus();//参考网址http://blog.csdn.net/jifashihan/article/details/51918345

        mLlTitle = (LinearLayout) findViewById(R.id.ll_title);
        mHomeTeam = (TextView) findViewById(R.id.homeTeam);
        mTime = (TextView) findViewById(R.id.time);
        mHalf_score = (TextView) findViewById(R.id.half_score);
        mCurrentScore = (TextView) findViewById(R.id.currentScore);
        mGuestTeam = (TextView) findViewById(R.id.guestTeam);
        mHomeRank = (TextView) findViewById(R.id.home_rank);
        mGuestRank = (TextView) findViewById(R.id.guest_rank);
        mHomeAvatar = (ImageView) findViewById(R.id.home_avatar);
        mGuestAvatar = (ImageView) findViewById(R.id.guest_avatar);
        mBuyConfirm = (TextView) findViewById(R.id.buy_confirm);
        if (buyPrivilege==1){
            mBuyConfirm.setVisibility(View.GONE);
        }else {
            mBuyConfirm.setVisibility(View.VISIBLE);
        }

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
        mScoreLiveFragment = new ScoreLiveFragment();
        mScoreLiveFragment.setVp(mContext, RefreshData);
        mAnalysisFragment = new AnalysisFragment();
        mAnalysisFragment.setVp(mContext, mId);
        mOddsFragment = new OddsFragment();
        mOddsFragment.setVp(mContext, mId);
        mAsiaOddsFragment = new AsiaOddsFragment();
        mAsiaOddsFragment.setVp(mContext, mId);
        mFragmentList.add(mScoreLiveFragment);
        mFragmentList.add(mAnalysisFragment);
        mFragmentList.add(mOddsFragment);
        mFragmentList.add(mAsiaOddsFragment);
        mTitleList.add("直播");
        mTitleList.add("分析");
        mTitleList.add("欧赔");
        mTitleList.add("亚盘");
        //设置TabLayout的模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(3)));
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                new TablayoutTabView().setIndicator(mTabLayout, DisplayUtil.dip2px(20), DisplayUtil.dip2px(20));
            }
        });
    }

    private String away_name;
    private String home_name;
    private String score;

    private void addData() {
        final HttpInterface2 mHttp = new HttpUtils2(mContext);
        Bundle params = new Bundle();
        params.putString("mId", mId);
        mHttp.get(Url.SCORE_DETAIL_URL, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                Gson gson = new Gson();
                ScoreDetailBean scoreDetailBean = gson.fromJson(result, ScoreDetailBean.class);
                int code = scoreDetailBean.getCode();
                String msg = scoreDetailBean.getMsg();
                if (code == 0) {
                    ScoreDetailBean.DataBean data = scoreDetailBean.getData();
                    ScoreDetailBean.DataBean.ItemsBean items = data.getItems();
                    List<ScoreDetailBean.DataBean.ItemsBean.EventBean> event = items.getEvent();
                    mScoreLiveFragment.setData(event);
                    // handler.postDelayed(runnable, refreshTime);
                } else {
                    // Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
            }
        });
        Bundle params1 = new Bundle();
        params1.putString("mId", mId);
        mHttp.get(Url.SCORE, params1, new JsonInterface() {
            @Override
            public void callback(String result) {
                Gson gson = new Gson();
                ScoreHeaderBean scoreHeaderBean = gson.fromJson(result, ScoreHeaderBean.class);
                int code = scoreHeaderBean.getCode();
                String msg = scoreHeaderBean.getMsg();
                if (code == 0) {
                    ScoreHeaderBean.DataBean data = scoreHeaderBean.getData();
                    ScoreHeaderBean.DataBean.ItemsBean items = data.getItems();
                    setHeaderView(items);
                } else {

                }
            }

            @Override
            public void onError() {

            }
        });

    }


    private void addListener() {
        mImage_titlebar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBuyConfirm.setOnClickListener(BuyConfirmListener);
    }

    View.OnClickListener BuyConfirmListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //防止连续点击
            if (ClickUtils.isFastClick()) {
                return;
            }
            Intent intent = new Intent();
            intent.setClass(mContext, JczqActivity.class);
            intent.putExtra("lotcode", "42");
            intent.putExtra("bunch", true);
            mContext.startActivity(intent);
        }
    };

    private void setHeaderView(ScoreHeaderBean.DataBean.ItemsBean items) {
        String date = items.getDate();
        String minute = items.getMinute();
        String away_logo = items.getAway_logo();
        String home_logo = items.getHome_logo();
        away_name = items.getAway_name();
        home_name = items.getHome_name();
        score = TextUtils.isEmpty(items.getScore()) ? "VS" : items.getScore();
        String away_rank = items.getAway_rank();
        String home_rank = items.getHome_rank();
        String status = items.getStatus();
        String state = items.getState();
        league_name = items.getLeague_name();
        String half_score = items.getHalf_score();
        if (!TextUtils.isEmpty(half_score)) {
            mHalf_score.setVisibility(View.VISIBLE);
            mHalf_score.setText("半场: " + half_score);
        } else {
            mHalf_score.setVisibility(View.GONE);
        }

        mCurrentScore.setText(TextUtils.isEmpty(score)?"VS":score);
        mHomeTeam.setText(TextUtils.isEmpty(home_name) ? "--" : home_name);
        mGuestTeam.setText(TextUtils.isEmpty(away_name) ? "--" : away_name);
        mTitlebar.setText(TextUtils.isEmpty(league_name) ? "直播详情" : league_name);
        if (!TextUtils.isEmpty(away_logo))
            HttpUtils2.display(mGuestAvatar, away_logo, R.mipmap.guest_team);
        if (!TextUtils.isEmpty(home_logo))
            HttpUtils2.display(mHomeAvatar, home_logo, R.mipmap.home_team);
        mHomeRank.setText("排名: " + (TextUtils.isEmpty(home_rank) ? "--" : home_rank));
        mGuestRank.setText("排名: " + (TextUtils.isEmpty(away_rank) ? "--" : away_rank));
        String ssss = TextUtils.isEmpty(status) ? (TextUtils.isEmpty(state) ? "--" : state) : status;
        if ("Fixture".equals(ssss)) {//未开始
            mTime.setText(TextUtils.isEmpty(date) ? "--" : date);
            mCurrentScore.setText("VS");
        } else if ("Playing".equals(ssss)) {//进行中
            mCurrentScore.setText(TextUtils.isEmpty(score)?"VS":score);

            if ("中场".equals(minute)){
                mTime.setText(TextUtils.isEmpty(minute) ? "--" : minute);
            }else {
                mTime.setText((TextUtils.isEmpty(minute) ? "--" : minute)+"'");
            }
        } else if ("Played".equals(ssss)) {
            mTime.setText("已完赛");
            mCurrentScore.setText(TextUtils.isEmpty(score)?"VS":score);

        } else {
            mTime.setText(TextUtils.isEmpty(minute) ? "--" : minute);
            mCurrentScore.setText(TextUtils.isEmpty(score)?"VS":score);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        handler.postDelayed(runnable, refreshTime);
        // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            addData();
            handler.postDelayed(this, refreshTime);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }

    ScoreLiveFragment.RefreshListener RefreshData = new ScoreLiveFragment.RefreshListener() {
        @Override
        public void callBack() {
            addData();
        }
    };
}
