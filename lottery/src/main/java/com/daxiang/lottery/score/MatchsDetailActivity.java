package com.daxiang.lottery.score;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BaseNoTitleActivity;
import com.daxiang.lottery.adapter.balladapter.DltViewPagerAdapter;
import com.daxiang.lottery.common.TablayoutTabView;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

public class MatchsDetailActivity extends BaseNoTitleActivity {
    private Context mContext;
    private TitleBar mTitleBar;
    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private String name;
    private ArrayList<Fragment> mFragmentList;
    private List<String> mTitleList;
    private MatchScheduleFragment mScheduleFragment;
    private IntegrateFragment mIntegrateFragment;
    private ShooterFragment mShooterFragment;
    private DltViewPagerAdapter mVpAdapter;
    private String league_id;
    private String from;
    private String home_id;//从历史数据的完整积分榜跳转过来的时候。
    private String away_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchs_detail);
        mContext = this;
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        league_id = intent.getStringExtra("league_id");
        home_id = intent.getStringExtra("home_id");
        away_id = intent.getStringExtra("away_id");
        from = intent.getStringExtra("from");
        initView();
        initTabFragment();
        addListener();
        addData();
    }

    private void initView() {
        mTitleBar = (TitleBar) findViewById(R.id.titlBar);
        mTitleBar.setTitleVisibility(true);
        mTitleBar.setTitle(TextUtils.isEmpty(name) ? "" : name);
        mTitleBar.setImageTitleVisibility(false);
        mTablayout = (TabLayout) findViewById(R.id.score_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.score_vp);
    }

    private void initTabFragment() {
        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
        mTitleList.add("赛程");
        mTitleList.add("积分榜");
        mTitleList.add("射手榜");
        //设置TabLayout的模式
        mTablayout.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        mTablayout.addTab(mTablayout.newTab().setText(mTitleList.get(0)));
        mTablayout.addTab(mTablayout.newTab().setText(mTitleList.get(1)));
        mTablayout.addTab(mTablayout.newTab().setText(mTitleList.get(2)));
        mTablayout.post(new Runnable() {
            @Override
            public void run() {
                new TablayoutTabView().setIndicator(mTablayout, DisplayUtil.dip2px(20), DisplayUtil.dip2px(20));
            }
        });
        mScheduleFragment = new MatchScheduleFragment();
        mIntegrateFragment = new IntegrateFragment();
        mShooterFragment = new ShooterFragment();
        mScheduleFragment.setData(mContext, league_id);
        mIntegrateFragment.setData(mContext, league_id,home_id,away_id);
        mShooterFragment.setData(mContext, league_id);



        mFragmentList.add(mScheduleFragment);
        mFragmentList.add(mIntegrateFragment);
        mFragmentList.add(mShooterFragment);
        mVpAdapter = new DltViewPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        //填充适配器
        mViewPager.setAdapter(mVpAdapter);
        //TabLayout加载viewpager
        mTablayout.setupWithViewPager(mViewPager);
        if ("AnalysisFragment".equals(from)) {
            mViewPager.setCurrentItem(1);
            // mIntegrateFragment.setMatchs();
        }
    }

    private void addListener() {
        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addData() {
    }
}
