package com.daxiang.lottery.activity.wonderfulactivity.LotteryForum;

import android.os.Bundle;

import com.daxiang.lottery.activity.BaseActivity;
import com.daxiang.lottery.R;
public class LookPrivilegeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_look_privilege);
        initView();
    }

    private void initView() {
        mTitleBar.setTitleVisibility(true);
        mTitleBar.setTitle("谁可以看");
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitleRegisterVisibility(true);
        mTitleBar.mTextRegister.setText("确定");
    }
}
