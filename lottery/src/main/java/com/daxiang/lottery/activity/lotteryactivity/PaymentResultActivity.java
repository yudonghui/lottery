package com.daxiang.lottery.activity.lotteryactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BaseActivity;
import com.daxiang.lottery.utils.ClickUtils;

public class PaymentResultActivity extends BaseActivity {
    private Context mContext;
    private ImageView mImageView;
    private TextView mTitle;
    private TextView mRemark;
    private TextView mLeft_btn;
    private TextView mRight_btn;
    private String allMoney;
    private int allCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_payment_result);
        mContext = this;
        Intent intent = getIntent();
        allMoney = intent.getStringExtra("allMoney");
        allCount = intent.getIntExtra("allCount", 0);
        initView();
        addData();
        addListener();
    }

    private void initView() {
        mTitleBar.setTitle("支付成功");
        mTitleBar.setImageTitleVisibility(false);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mTitle = (TextView) findViewById(R.id.title);
        mRemark = (TextView) findViewById(R.id.remark);
        mLeft_btn = (TextView) findViewById(R.id.left_btn);
        mRight_btn = (TextView) findViewById(R.id.right_btn);
        mRemark.setText("本次订单金额为" + allMoney + "元，您可在我的订单中查看购彩详情。");
    }

    private void addListener() {
        mLeft_btn.setOnClickListener(LeftListener);
        mRight_btn.setOnClickListener(RightListener);
    }

    View.OnClickListener LeftListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            setResult(102, intent);
            finish();
        }
    };
    View.OnClickListener RightListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //防止连续点击
            if (ClickUtils.isFastClick(2000)) {
                return;
            }
            Intent intent = new Intent(mContext, OrderFormActivity.class);
            if (allCount > 1) {//跳转到追号列表
                intent.putExtra("type", 4);
            } else {//跳转到投注列表
                intent.putExtra("type", 0);
            }
            startActivity(intent);
            finish();
        }
    };

    private void addData() {

    }
}
