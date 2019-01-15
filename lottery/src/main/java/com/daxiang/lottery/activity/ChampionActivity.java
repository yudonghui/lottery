package com.daxiang.lottery.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.daxiang.lottery.R;
import com.daxiang.lottery.adapter.balladapter.DltViewPagerAdapter;
import com.daxiang.lottery.common.TablayoutTabView;
import com.daxiang.lottery.fragment.champion.ChampionFragment;
import com.daxiang.lottery.fragment.champion.ChampionRunnerFragment;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

public class ChampionActivity extends BaseNoTitleActivity {
    private TitleBar mTitleBar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ChampionFragment mChampionFragment;
    private ChampionRunnerFragment mChampionRunnerFragment;
    private ArrayList<Fragment> mFragmentList;
    private List<String> mTitleList;
    private DltViewPagerAdapter mVpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champion);
        init();
        addListener();
    }

    private void init() {
        mTitleBar = (TitleBar) findViewById(R.id.titlbar);
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitleVisibility(true);
        mTitleBar.setTitle("冠亚军");
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
        mTitleList.add("世界杯冠军");
        mTitleList.add("世界杯冠亚军");
        mChampionFragment = new ChampionFragment();
        mChampionFragment.setData(mContext);
        mChampionRunnerFragment = new ChampionRunnerFragment();
        mChampionRunnerFragment.setData(mContext);
        mFragmentList.add(mChampionFragment);
        mFragmentList.add(mChampionRunnerFragment);
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                new TablayoutTabView().setIndicator(mTabLayout, DisplayUtil.dip2px(30), DisplayUtil.dip2px(30));
            }
        });
        mVpAdapter = new DltViewPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        mViewPager.setAdapter(mVpAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void addListener() {
        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
