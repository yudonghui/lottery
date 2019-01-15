package com.daxiang.lottery.activity.lotteryactivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BaseActivity;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils2;


public class AlipayMyActivity extends BaseActivity {
    private static final String GOALIPILURL = "alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=";
    private TextView mRecharge_money;
    private TextView mOrder_form_number;
    private Button mBtn_copy;
    private Button mBtn_go_alipay;
    private String mTradeNo;
    private String mMoney;
    private String mCopyNumber = null;
    private String mOpenType;
    String URL;
    private String mMerQrCode;
    ClipboardManager cmb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_alipay_my);
        Intent intent = getIntent();
        mMoney = intent.getStringExtra("money");
        mTradeNo = intent.getStringExtra("tradeNo");
        mMerQrCode = intent.getStringExtra("merQrCode");
        mOpenType = intent.getStringExtra("openType");
        cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        bindViews();
        addListener();
    }

    private void bindViews() {
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitle("支付宝转账");
        mTitleBar.setTitleVisibility(true);
        mRecharge_money = (TextView) findViewById(R.id.recharge_money);
        mOrder_form_number = (TextView) findViewById(R.id.order_form_number);
        mBtn_copy = (Button) findViewById(R.id.btn_copy);
        mBtn_go_alipay = (Button) findViewById(R.id.btn_go_alipay);
        mRecharge_money.setText(mMoney);
        mOrder_form_number.setText(mTradeNo);
    }

    private void addListener() {
        //点击复制按钮
        mBtn_copy.setOnClickListener(CopyListener);
        //点击前往支付宝转账
        mBtn_go_alipay.setOnClickListener(GoAlipayListener);
    }

    View.OnClickListener CopyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mCopyNumber = mTradeNo;
            cmb.setText(mTradeNo.trim());
            Toast.makeText(AlipayMyActivity.this, "复制成功", Toast.LENGTH_SHORT).show();
        }
    };
    View.OnClickListener GoAlipayListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mCopyNumber == null) {
                HintDialogUtils2 hintDialogUtils2 = new HintDialogUtils2(AlipayMyActivity.this);
                hintDialogUtils2.setMessage("请先复制订单号,将订单号填入支付宝转账备注信息");
            } else {
                try {
                    URL = GOALIPILURL + mMerQrCode;
                    if ("1".equals(mOpenType)) {
                        //直接打开支付宝
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
                    } else {
                        //先打开页面再跳转到支付宝
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mMerQrCode)));
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(AlipayMyActivity.this, "请安装支付宝!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
}
