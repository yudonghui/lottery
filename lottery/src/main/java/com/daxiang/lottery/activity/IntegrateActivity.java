package com.daxiang.lottery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.daxiang.lottery.R;
import com.daxiang.lottery.score.IntegrateFragment;
import com.daxiang.lottery.view.TitleBar;

public class IntegrateActivity extends BaseNoTitleActivity {
    private TitleBar mTitleBar;
    private FrameLayout mFl;
    private String league_id;
    private String home_id;//从历史数据的完整积分榜跳转过来的时候。
    private String away_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integrate);
        Intent intent = getIntent();
        league_id = intent.getStringExtra("league_id");
        home_id = intent.getStringExtra("home_id");
        away_id = intent.getStringExtra("away_id");
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
        mTitleBar.setTitle("积分榜");
        mFl = (FrameLayout) findViewById(R.id.fl);
        IntegrateFragment integrateFragment = new IntegrateFragment();
        integrateFragment.setData(mContext, league_id, home_id, away_id);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl, integrateFragment);
        fragmentTransaction.commit();
        integrateFragment.setUserVisibleHint(true);
    }
}
