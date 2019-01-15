package com.daxiang.lottery.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.adapter.balladapter.DltViewPagerAdapter;
import com.daxiang.lottery.common.TablayoutTabView;
import com.daxiang.lottery.fragment.RuleFragment;
import com.daxiang.lottery.fragment.ballfragment.BallLotteryResultFragment;
import com.daxiang.lottery.fragment.commonfragment.RecommendComFragment;
import com.daxiang.lottery.fragment.sfcr9cfragment.SfcHallFragment;
import com.daxiang.lottery.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

public class SfcAndRjcActivity extends BaseActivity {
    // private TitleBar mTitleBar;
    // private ImageView mBack;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TextView mTextTitle;
    private String title;
    private SfcHallFragment mHallFragment;
    List<Fragment> mFragmentList;
    List<String> mTitleList;
    AlertDialog alertDialog;
    private DltViewPagerAdapter mDltViewPagerAdapter;
    private String mLotcode;
    private String who;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_number);
        mContext = this;
        Intent intent = getIntent();
        mLotcode = intent.getStringExtra("lotcode");
        who = intent.getStringExtra("who");
        initView();
        //viewpager数据源
        addFragment();
        //创建适配器
        mDltViewPagerAdapter = new DltViewPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        //填充适配器
        mViewPager.setAdapter(mDltViewPagerAdapter);
        //TabLayout加载viewpager
        mTabLayout.setupWithViewPager(mViewPager);
        //tab_FindFragment_title.set
        //设置监听
        setListener();
        if ("result".equals(who)) {
            mTextTitle.setText(mTitleList.get(2));
            mViewPager.setCurrentItem(2);
        }
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_dlt);
        // mViewPager.setOffscreenPageLimit(3);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        //mTitleBar = (TitleBar) findViewById(R.id.text_daletou);
        mTitleBar.setImageTitleVisibility(false);
        //mBack = (ImageView) mTitleBar.findViewById(R.id.image_titlebar_back);
        mTextTitle = (TextView) mTitleBar.findViewById(R.id.titlebar);
        if (mLotcode.equals("11")) {
            title = "胜负彩";
        } else {
            title = "任9场";
        }
        mTextTitle.setText(title);
        mTextTitle.setVisibility(View.VISIBLE);
        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
        mTitleList.add("投注大厅");
        mTitleList.add("推荐中心");
        mTitleList.add("开奖结果");
        mTitleList.add("玩法介绍");
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
                new TablayoutTabView().setIndicator(mTabLayout, DisplayUtil.dip2px(10), DisplayUtil.dip2px(10));
            }
        });
    }

    private void addFragment() {
        mHallFragment = new SfcHallFragment();
        mHallFragment.setData(mLotcode);
        RecommendComFragment mRecommendFragment = new RecommendComFragment();
        mRecommendFragment.setData(mLotcode, mContext);
        BallLotteryResultFragment mLotteryResultFragment = new BallLotteryResultFragment();
        mLotteryResultFragment.setData(mLotcode, mContext);
        RuleFragment mRuleFragment = new RuleFragment();
        mRuleFragment.setData(mLotcode, mContext);
        mFragmentList.add(mHallFragment);
        mFragmentList.add(mRecommendFragment);
        mFragmentList.add(mLotteryResultFragment);
        mFragmentList.add(mRuleFragment);
    }

    private void setListener() {
      /*  mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mTextTitle.setText(title);

                } else {
                    mTextTitle.setText(mTitleList.get(position));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 100) {//点击继续购彩
            if (mHallFragment != null) {
                mHallFragment.paymentFinish();
            }
        }
    }
}
