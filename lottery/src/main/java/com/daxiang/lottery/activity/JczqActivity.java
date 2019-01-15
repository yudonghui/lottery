package com.daxiang.lottery.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.daxiang.lottery.R;
import com.daxiang.lottery.adapter.balladapter.DltViewPagerAdapter;
import com.daxiang.lottery.common.TablayoutTabView;
import com.daxiang.lottery.cxinterface.PlayMethodSelector;
import com.daxiang.lottery.dialog.PlayMethodDialog;
import com.daxiang.lottery.fragment.RuleFragment;
import com.daxiang.lottery.fragment.commonfragment.RecommendComFragment;
import com.daxiang.lottery.fragment.jcfragment.JczqHallFragment;
import com.daxiang.lottery.fragment.jcfragment.JczqLotteryResultFragment;
import com.daxiang.lottery.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

public class JczqActivity extends BaseJcActivity {
    private String mLotcode;
    private DltViewPagerAdapter mVpAdapter;
    private ArrayList<Fragment> mFragmentList;
    private List<String> mTitleList;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private JczqHallFragment mHallFragment;

    //true为串关 false为单关
    private boolean mPlayMethodBunchs;
    //从哪里跳转过来的
    private String who;
    private PlayMethodDialog mPlayMethodDialog;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_jczq);
        mContext = this;
        Intent intent = getIntent();
        mLotcode = intent.getStringExtra("lotcode");
        mPlayMethodBunchs = intent.getBooleanExtra("bunch", false);
        who = intent.getStringExtra("who");
        initView();
        //viewpager数据源
        addFragment();
        mVpAdapter = new DltViewPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        //填充适配器
        mViewPager.setAdapter(mVpAdapter);
        //TabLayout加载viewpager
        mTabLayout.setupWithViewPager(mViewPager);
        if ("result".equals(who)) {
            mTitleBar.mTitlebarSift.setVisibility(View.GONE);
            mTitleBar.mPlayMethodTitleBar.setVisibility(View.GONE);
            mTitleBar.mMiddleText.setVisibility(View.VISIBLE);
            mTitleBar.mMiddleText.setText(mTitleList.get(2));
            mViewPager.setCurrentItem(2);
        }
        //设置监听
        setListener();
        mPlayMethodDialog = new PlayMethodDialog(this, mLotcode, mPlayMethodBunchs, PlayMethodSelectorListener);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.lv_jczq);
        // mViewPager.setOffscreenPageLimit(3);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
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
        if (mPlayMethodBunchs) {
            mTitleBar.mTextTitlebar.setText("混合过关");
        } else {
            mTitleBar.mTextTitlebar.setText("胜平负(单关)");
        }
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                new TablayoutTabView().setIndicator(mTabLayout, DisplayUtil.dip2px(10), DisplayUtil.dip2px(10));
            }
        });
    }

    private void addFragment() {
        mHallFragment = new JczqHallFragment();
        mHallFragment.setData(mLotcode, mPlayMethodBunchs, mTitleBar);
        RecommendComFragment mRecommendFragment = new RecommendComFragment();
        mRecommendFragment.setData(mLotcode, mContext);
        JczqLotteryResultFragment mLotteryResultFragment = new JczqLotteryResultFragment();
        mLotteryResultFragment.setData(mLotcode, mContext);
        RuleFragment mRuleFragment = new RuleFragment();
        mRuleFragment.setData(mLotcode, mContext);
        mFragmentList.add(mHallFragment);
        mFragmentList.add(mRecommendFragment);
        mFragmentList.add(mLotteryResultFragment);
        mFragmentList.add(mRuleFragment);
    }

    private String selectTab = "投注大厅";//选中的fragment的标题

    private void setListener() {
        mTitleBar.mPlayMethodTitleBar.setOnClickListener(playMethodClick);
        mViewPager.addOnPageChangeListener(PagechangeListener);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectTab = tab.getText().toString();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    ViewPager.OnPageChangeListener PagechangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                mTitleBar.mTitlebarSift.setVisibility(View.VISIBLE);
                mTitleBar.mPlayMethodTitleBar.setVisibility(View.VISIBLE);
                mTitleBar.mMiddleText.setVisibility(View.GONE);
                //mTitleBar.mTextTitlebar.setText(LotteryTypes.getTypes(mLotcode));
           /* if ("33".equals(mLotcode) || "52".equals(mLotcode) || "21406".equals(mLotcode)) {
                mTitleBar.setLlVisibility(true);
                mTitleBar.setTitleVisibility(false);
            }
            mTextTitle.setText(LotteryTypes.getTypes(mLotcode));

        } else {
            if ("33".equals(mLotcode) || "52".equals(mLotcode) || "21406".equals(mLotcode)) {
                mTitleBar.setLlVisibility(false);
                mTitleBar.setTitleVisibility(true);
                mTitleBar.setTitleSize(20);
            }
            mTextTitle.setText(mTitleList.get(position));*/
            } else {
                mTitleBar.mTitlebarSift.setVisibility(View.GONE);
                mTitleBar.mPlayMethodTitleBar.setVisibility(View.GONE);
                mTitleBar.mMiddleText.setVisibility(View.VISIBLE);
                mTitleBar.mMiddleText.setText(mTitleList.get(position));
            }
        }
    };
    View.OnClickListener playMethodClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPlayMethodDialog.show(mTitleBar.mPlayMethodTitleBar);
        }
    };
    PlayMethodSelector PlayMethodSelectorListener = new PlayMethodSelector() {
        @Override
        public void playMethod(int playMethod, boolean mPlayMethodBunch) {
            //mPlayMethodBunchs为true是串关，否则为单关
            mPlayMethodBunchs = mPlayMethodBunch;
            if (mLotcode.equals("42")) {
                switch (playMethod) {
                    case 1:
                        //胜平负
                        if (mPlayMethodBunchs) {
                            mTitleBar.mTextTitlebar.setText("胜平负");
                        } else {
                            mTitleBar.mTextTitlebar.setText("胜平负(单关)");
                        }
                        mHallFragment.setData(1, mPlayMethodBunchs);
                        break;
                    case 2:
                        if (mPlayMethodBunchs) {
                            mTitleBar.mTextTitlebar.setText("让球胜平负");
                        } else {
                            mTitleBar.mTextTitlebar.setText("让球胜平负(单关)");
                        }
                        mHallFragment.setData(2, mPlayMethodBunchs);
                        break;
                    case 3:
                        if (mPlayMethodBunchs) {
                            mTitleBar.mTextTitlebar.setText("比分");
                        } else {
                            mTitleBar.mTextTitlebar.setText("比分(单关)");
                        }
                        mHallFragment.setData(3, mPlayMethodBunchs);
                        break;
                    case 4:
                        if (mPlayMethodBunchs) {
                            mTitleBar.mTextTitlebar.setText("总进球");
                        } else {
                            mTitleBar.mTextTitlebar.setText("总进球(单关)");
                        }
                        mHallFragment.setData(4, mPlayMethodBunchs);
                        break;
                    case 5:
                        if (mPlayMethodBunchs) {
                            mTitleBar.mTextTitlebar.setText("半全场");
                        } else {
                            mTitleBar.mTextTitlebar.setText("半全场(单关)");
                        }
                        mHallFragment.setData(5, mPlayMethodBunchs);
                        break;
                    case 6:
                        mTitleBar.mTextTitlebar.setText("混合过关");
                        mHallFragment.setData(6, mPlayMethodBunchs);
                        break;
                }
            }
        }
    };

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
