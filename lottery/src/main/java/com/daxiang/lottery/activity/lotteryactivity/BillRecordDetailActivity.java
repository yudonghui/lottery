package com.daxiang.lottery.activity.lotteryactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BaseActivity;

public class BillRecordDetailActivity extends BaseActivity {
    // Content View Elements

    private TextView mTitle;
    private TextView mDate;
    private TextView mMoney;
    private TextView mSerialNumber;

    private String mSubject;
    private int mTransType;
    private double mMoneyStr;
    private long mPid;
    private String mTimeStr;
    private TextView mOrigin;
    private String origin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_bill_record_detail);

        Intent intent = getIntent();
        mSubject = intent.getStringExtra("subject");
        mTransType = intent.getIntExtra("transType", 100);
        mMoneyStr = intent.getDoubleExtra("money", 0);
        mPid = intent.getLongExtra("pid", 0);
        mTimeStr = intent.getStringExtra("timeStr");
        origin = intent.getStringExtra("origin");
        initView();
        addData();
    }

    private void initView() {
        mTitleBar.setTitle("账单详情");
        mTitleBar.setTitleVisibility(true);
        mTitleBar.setImageTitleVisibility(false);
        mTitle = (TextView) findViewById(R.id.bill_detail_title);
        mDate = (TextView) findViewById(R.id.bill_detail_date);
        mMoney = (TextView) findViewById(R.id.bill_detail_money);
        mSerialNumber = (TextView) findViewById(R.id.bill_detail_number);
        mOrigin = (TextView) findViewById(R.id.bill_detail_origin);
    }

    private void addData() {
        mTitle.setText(mSubject);
        mDate.setText(mTimeStr);
        if (mTransType == 1 || mTransType == 3 || mTransType == 4 || mTransType == 10) {
            mMoney.setText("-" + (int) mMoneyStr);
        } else {
            mMoney.setTextColor(Color.RED);
            mMoney.setText("+" + mMoneyStr);
        }
        mSerialNumber.setText(mPid + "");
        mOrigin.setText(origin);
      /*  HttpInterface mBillDetailHttp = new HttpUtils();
        Bundle params = new Bundle();
        params.putInt("pid", mPid);
        mBillDetailHttp.get(Url.BILL_DETAIL_URL, params, "billdetail", new JsonInterface() {
            @Override
            public void callback(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    mSerialNumber.setText(data.getString("outTradeNo"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError() {

            }
        });*/
    }
}
