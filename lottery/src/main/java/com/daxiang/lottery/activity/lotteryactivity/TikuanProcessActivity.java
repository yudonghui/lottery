package com.daxiang.lottery.activity.lotteryactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BaseActivity;

public class TikuanProcessActivity extends BaseActivity {
    private Context mContext;
    private TextView mMoney;
    private TextView mName;
    private TextView mBankName;
    private TextView mConfirm;
    private String money;
    private String bankName;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_tikuan_process);
        Intent intent = getIntent();
        money = intent.getStringExtra("money");
        bankName = intent.getStringExtra("bankName");
        name = intent.getStringExtra("name");
        mContext = this;
        initView();
        addListener();
    }

    private void initView() {
        mTitleBar.setTitle("我的提款");
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitleVisibility(true);
        mMoney = (TextView) findViewById(R.id.money);
        mName = (TextView) findViewById(R.id.name);
        mBankName = (TextView) findViewById(R.id.bankName);
        mConfirm = (TextView) findViewById(R.id.confirm);
        mMoney.setText("申请提现金额: " + money + "元");
        mName.setText(TextUtils.isEmpty(name) ? "" : name);
        mBankName.setText(TextUtils.isEmpty(bankName) ? "" : bankName);
    }

    private void addListener() {
        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
