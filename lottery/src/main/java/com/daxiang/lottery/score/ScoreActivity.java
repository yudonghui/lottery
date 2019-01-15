package com.daxiang.lottery.score;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BaseNoTitleActivity;
import com.daxiang.lottery.adapter.balladapter.DltViewPagerAdapter;
import com.daxiang.lottery.common.TablayoutTabView;
import com.daxiang.lottery.cxinterface.CollectionListener;
import com.daxiang.lottery.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

public class ScoreActivity extends BaseNoTitleActivity {
    private ImageView mTitleBar;
    private TextView mFootball;
    private TextView mBasketball;
    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private ArrayList<Fragment> mFragmentList;
    private List<String> mTitleList;
    private DltViewPagerAdapter mVpAdapter;
    private FinishedFragment finishedFragment;
    private FinishingFragment finishingFragment;
    private AttentionFragment attentionFragment;
    private MatchsFragment matchsFragment;
    private boolean isBasketball = false;//false 足球  true篮球
    private int refreshTime = 30000;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        bindViews();
        addListener();
        addFragment();
        handler.postDelayed(runnable, refreshTime);
    }

    private void bindViews() {
        mTitleBar = (ImageView) findViewById(R.id.image_titlebar_back);
        mFootball = (TextView) findViewById(R.id.football);
        mBasketball = (TextView) findViewById(R.id.basketball);
        mTablayout = (TabLayout) findViewById(R.id.score_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.score_vp);
        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
        mTitleList.add("已完赛");
        mTitleList.add("未完赛");
        mTitleList.add("已关注");
        mTitleList.add("赛事库");
        //设置TabLayout的模式
        mTablayout.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        mTablayout.addTab(mTablayout.newTab().setText(mTitleList.get(0)));
        mTablayout.addTab(mTablayout.newTab().setText(mTitleList.get(1)));
        mTablayout.addTab(mTablayout.newTab().setText(mTitleList.get(2)));
        mTablayout.addTab(mTablayout.newTab().setText(mTitleList.get(3)));
        mTablayout.post(new Runnable() {
            @Override
            public void run() {
                new TablayoutTabView().setIndicator(mTablayout, DisplayUtil.dip2px(10), DisplayUtil.dip2px(10));
            }
        });
    }

    private void addFragment() {
        finishedFragment = new FinishedFragment();
        finishingFragment = new FinishingFragment();
        attentionFragment = new AttentionFragment();
        matchsFragment = new MatchsFragment();


        finishedFragment.setData(mContext, isBasketball, mCollectionListener);
        finishingFragment.setData(mContext, isBasketball, mCollectionListener);
        attentionFragment.setData(mContext, isBasketball, mCollectionListener);
        matchsFragment.setData(mContext);
        mFragmentList.add(finishedFragment);
        mFragmentList.add(finishingFragment);
        mFragmentList.add(attentionFragment);
        mFragmentList.add(matchsFragment);
        mVpAdapter = new DltViewPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        //填充适配器
        mViewPager.setAdapter(mVpAdapter);
        //刚进来时显示未完赛
        mViewPager.setCurrentItem(1);
        //TabLayout加载viewpager
        mTablayout.setupWithViewPager(mViewPager);
    }

    private void addListener() {
        mFootball.setOnClickListener(TitleListener);
        mBasketball.setOnClickListener(TitleListener);
        mTitleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    View.OnClickListener TitleListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //LoadingDialogUtils.loading(getActivity());
            switch (v.getId()) {
                case R.id.football:
                    //如果目前是篮球那么刷新数据。如果不是那么不执行任何操作
                    if (isBasketball) {
                        mFootball.setTextColor(Color.RED);
                        mFootball.setBackgroundColor(Color.WHITE);
                        mBasketball.setTextColor(Color.WHITE);
                        mBasketball.setBackgroundResource(R.drawable.redright);
                        mFragmentList.add(matchsFragment);
                        mTitleList.add("赛事库");
                        mVpAdapter.notifyDataSetChanged();
                        setData();
                    }
                    break;
                case R.id.basketball:
                    //如果目前不是篮球那么刷新数据。如果不是那么不执行任何操作
                    if (!isBasketball) {
                        mFootball.setTextColor(Color.WHITE);
                        mFootball.setBackgroundResource(R.drawable.redleft);
                        mBasketball.setBackgroundColor(Color.WHITE);
                        mBasketball.setTextColor(Color.RED);
                        mFragmentList.remove(3);
                        mTitleList.remove(3);
                        mVpAdapter.notifyDataSetChanged();
                        setData();
                    }
                    break;
            }
        }
    };

    private void setData() {
        isBasketball = !isBasketball;
        finishedFragment.changData(isBasketball, mCollectionListener);
        finishingFragment.changData(isBasketball, mCollectionListener);
        attentionFragment.changData(isBasketball, mCollectionListener);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        handler.postDelayed(runnable, refreshTime);
    }

    /*
* 如果点击收藏，判断是从哪个fragment中点击的。只刷新相应的数据就行了。
* 这里 当点击我的关注里面的收藏按钮，删除，同时刷新未完赛和已完赛
*     否则只需要刷新我的关注
* */
    CollectionListener mCollectionListener = new CollectionListener() {
        @Override
        public void collListener(String type) {
            if ("attention".equals(type)) {
                finishedFragment.setRefresh();
                finishingFragment.setRefresh();
            } else {
                attentionFragment.setRefresh();
            }
        }
    };
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            finishedFragment.addData();
            finishingFragment.addData();
            attentionFragment.timedTask();
            handler.postDelayed(this, refreshTime);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }
}
