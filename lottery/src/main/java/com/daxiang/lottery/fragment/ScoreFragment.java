package com.daxiang.lottery.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.adapter.balladapter.DltViewPagerAdapter;
import com.daxiang.lottery.common.TablayoutTabView;
import com.daxiang.lottery.cxinterface.CollectionListener;
import com.daxiang.lottery.score.AttentionFragment;
import com.daxiang.lottery.score.FinishedFragment;
import com.daxiang.lottery.score.FinishingFragment;
import com.daxiang.lottery.score.MatchsFragment;
import com.daxiang.lottery.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2018/4/2.
 */

public class ScoreFragment extends Fragment {
    private Context mContext;
    private View mInflate;
    private View mTitleView;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("时间1", System.currentTimeMillis() + "");
        mInflate = inflater.inflate(R.layout.fragment_score, null);
        mContext = getContext();
        initView();
        addListener();
        addFragment();
        handler.postDelayed(runnable, refreshTime);
        Log.e("时间2", System.currentTimeMillis() + "");
        return mInflate;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("时间onResume", System.currentTimeMillis() + "");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("时间onStart", System.currentTimeMillis() + "");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("时间onAttach", System.currentTimeMillis() + "");
    }

    private void initView() {
        mTitleView = mInflate.findViewById(R.id.titleView);
        ImageView mTitleBar = (ImageView) mInflate.findViewById(R.id.image_titlebar_back);
        mTitleBar.setVisibility(View.GONE);
        mFootball = (TextView) mInflate.findViewById(R.id.football);
        mBasketball = (TextView) mInflate.findViewById(R.id.basketball);
        mTablayout = (TabLayout) mInflate.findViewById(R.id.score_tablayout);
        mViewPager = (ViewPager) mInflate.findViewById(R.id.score_vp);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mTitleView.setVisibility(View.VISIBLE);
            int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (identifier > 0) {
                int dimensionPixelSize = getResources().getDimensionPixelSize(identifier);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dimensionPixelSize);
                mTitleView.setLayoutParams(layoutParams);
            }
        } else mTitleView.setVisibility(View.GONE);

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
        mVpAdapter = new DltViewPagerAdapter(getFragmentManager(), mFragmentList, mTitleList);
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

    public void setStart() {//开始刷新比分
        if (runnable == null) return;
        handler.postDelayed(runnable, refreshTime);
    }

    public void setPause() {//停止刷新比分
        if (runnable == null) return;
        handler.removeCallbacks(runnable);
    }
}
