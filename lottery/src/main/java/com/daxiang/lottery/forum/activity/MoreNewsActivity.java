package com.daxiang.lottery.forum.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.daxiang.lottery.R;
import com.daxiang.lottery.forum.base.BaseMvpActivity;
import com.daxiang.lottery.forum.contract.MoreNewsContract;
import com.daxiang.lottery.forum.fragment.PostFormFragment;
import com.daxiang.lottery.forum.presenter.MoreNewsPresenter;
import com.daxiang.lottery.view.TitleBar;

public class MoreNewsActivity extends BaseMvpActivity<MoreNewsPresenter> implements MoreNewsContract.View {
    private TitleBar mTitleBar;

    @Override
    public int getInflateId() {
        return R.layout.activity_more_news;
    }

    @Override
    public void init() {
        mTitleBar = (TitleBar) findViewById(R.id.titlBar);
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitleVisibility(true);
        mTitleBar.setTitle("新闻资讯");
        PostFormFragment postFormFragment = new PostFormFragment();
        postFormFragment.setData(3, mContext);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl, postFormFragment).show(postFormFragment).commit();
    }

    @Override
    public void addData() {
        super.addData();
    }

    @Override
    public void addListener() {
        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void getSuccess(String result) {

    }

    @Override
    public void getError() {

    }

    @Override
    public void getDeleteSuccess(String result) {

    }
}
