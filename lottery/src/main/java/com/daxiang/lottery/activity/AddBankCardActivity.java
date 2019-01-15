package com.daxiang.lottery.activity;

import android.os.Bundle;

import com.daxiang.lottery.R;

public class AddBankCardActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_add_bank_card);
        initView();
    }

    private void initView() {
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitle("银行卡号");
        mTitleBar.setTitleVisibility(true);
    }
}
