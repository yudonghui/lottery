package com.daxiang.lottery.activity.lotteryactivity.redpacket;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BaseActivity;
import com.daxiang.lottery.adapter.balladapter.DltViewPagerAdapter;
import com.daxiang.lottery.common.TablayoutTabView;
import com.daxiang.lottery.fragment.redpacketfragment.AllRedpacketFrament;
import com.daxiang.lottery.fragment.redpacketfragment.UsableRedpacketFragment;
import com.daxiang.lottery.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

public class MyRedpacketActivity extends BaseActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ArrayList<Fragment> mFragmentList;
    private List<String> mTitleList;
    private DltViewPagerAdapter mVpAdapter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_my_redpacket);
        mContext = this;
        initView();
        addFrament();
        mVpAdapter = new DltViewPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        //填充适配器
        mViewPager.setAdapter(mVpAdapter);
        //TabLayout加载viewpager
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initView() {
        mTitleBar.setTitle("我的红包");
        mTitleBar.setTitleVisibility(true);
        mTitleBar.setImageTitleVisibility(false);

        mViewPager = (ViewPager) findViewById(R.id.vp_redpacket);
        mTabLayout = (TabLayout) findViewById(R.id.redpacket_tablayout);
        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
        mTitleList.add("有效红包");
        mTitleList.add("历史红包");
        //设置TabLayout的模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                new TablayoutTabView().setIndicator(mTabLayout, DisplayUtil.dip2px(40), DisplayUtil.dip2px(40));
            }
        });
    }

    private void addFrament() {
        UsableRedpacketFragment usableRedpacketFragment = new UsableRedpacketFragment();
        AllRedpacketFrament allRedpacketFrament = new AllRedpacketFrament();
        mFragmentList.add(usableRedpacketFragment);
        mFragmentList.add(allRedpacketFrament);
    }
}
