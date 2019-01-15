package com.daxiang.lottery.activity.lotteryactivity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BaseNoTitleActivity;
import com.daxiang.lottery.adapter.balladapter.DltViewPagerAdapter;
import com.daxiang.lottery.common.TablayoutTabView;
import com.daxiang.lottery.forum.fragment.MessageFragment;
import com.daxiang.lottery.forum.fragment.PostFormFragment;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

public class PostMessageActivity extends BaseNoTitleActivity {
    private TitleBar mTitleBar;
    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private MessageFragment mPostMessageFragment;
    private MessageFragment mSystemMessageFragment;
    private PostFormFragment mPostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_message);
        initView();
        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mTitleBar = (TitleBar) findViewById(R.id.titlBar);
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitleVisibility(true);
        mTitleBar.setTitle("帖子消息");
        mTablayout = (TabLayout) findViewById(R.id.tablayout);
        mTablayout.setTabMode(TabLayout.MODE_FIXED);//设置TabLayout的模式
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mPostFragment = new PostFormFragment();
        mPostFragment.setData(2,mContext);
        mPostMessageFragment = new MessageFragment();
        mPostMessageFragment.setData(0);
        mSystemMessageFragment = new MessageFragment();
        mPostMessageFragment.setData(1);

        ArrayList<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(mPostFragment);
        mFragmentList.add(mPostMessageFragment);
        mFragmentList.add(mSystemMessageFragment);
        List<String> mTitleList = new ArrayList<>();
        mTitleList.add("帖子");
        mTitleList.add("帖子消息");
        mTitleList.add("系统消息");
        mTablayout.addTab(mTablayout.newTab().setText(mTitleList.get(0)));
        mTablayout.addTab(mTablayout.newTab().setText(mTitleList.get(1)));
        mTablayout.addTab(mTablayout.newTab().setText(mTitleList.get(2)));
        mTablayout.post(new Runnable() {
            @Override
            public void run() {
                new TablayoutTabView().setIndicator(mTablayout, DisplayUtil.dip2px(20), DisplayUtil.dip2px(20));
            }
        });

        DltViewPagerAdapter mVpAdapter = new DltViewPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        //填充适配器
        mViewPager.setAdapter(mVpAdapter);
        //TabLayout加载viewpager
        mTablayout.setupWithViewPager(mViewPager);
    }
}
