package com.daxiang.lottery.activity.lotteryactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BaseNoTitleActivity;
import com.daxiang.lottery.adapter.balladapter.DltViewPagerAdapter;
import com.daxiang.lottery.common.TablayoutTabView;
import com.daxiang.lottery.fragment.userfragment.OrderFormFragment;
import com.daxiang.lottery.fragment.userfragment.ZhuihaoFormFragment;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

public class OrderFormActivity extends BaseNoTitleActivity {
    private DltViewPagerAdapter mVpAdapter;
    private TitleBar mTitleBar;
    private ArrayList<Fragment> mFragmentList;
    private List<String> mTitleList;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private int type;//0全部，1待支付，2待开奖 3已中奖 4追号单

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form);
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        initView();
        addFragment();
        mVpAdapter = new DltViewPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        //填充适配器
        mViewPager.setAdapter(mVpAdapter);
        //TabLayout加载viewpager
        mTabLayout.setupWithViewPager(mViewPager);
        setListener();
        mViewPager.setCurrentItem(type);
    }


    private void initView() {
        mTitleBar = (TitleBar) findViewById(R.id.titlebar_record);
        mTitleBar.setTitle("投注记录");
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitleVisibility(true);
        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
       // mViewPager.setOffscreenPageLimit(4);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
        mTitleList.add("全部");
        mTitleList.add("待支付");
        mTitleList.add("待开奖");
        mTitleList.add("已中奖");
        mTitleList.add("追号单");
        //设置TabLayout的模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(3)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(4)));
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                new TablayoutTabView().setIndicator(mTabLayout, DisplayUtil.dip2px(10), DisplayUtil.dip2px(10));
            }
        });
    }

    private void addFragment() {
        OrderFormFragment allFragment = new OrderFormFragment();
        allFragment.setData(1,this);
        OrderFormFragment moneyFragment = new OrderFormFragment();
        moneyFragment.setData(2,this);
        OrderFormFragment prepareFragment = new OrderFormFragment();
        prepareFragment.setData(3,this);
        OrderFormFragment winnedFragment = new OrderFormFragment();
        winnedFragment.setData(4,this);
        ZhuihaoFormFragment zhuihaoFormFragment = new ZhuihaoFormFragment();
        zhuihaoFormFragment.setData(this);
        mFragmentList.add(allFragment);
        mFragmentList.add(moneyFragment);
        mFragmentList.add(prepareFragment);
        mFragmentList.add(winnedFragment);
        mFragmentList.add(zhuihaoFormFragment);
    }


    private void setListener() {


    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 444) {
           /* mList.clear();
            pn = 1;
            addData();*/
        }

    }
}
