package com.daxiang.lottery.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.constant.Url;
import com.daxiang.lottery.cxinterface.HttpInterface2;
import com.daxiang.lottery.cxinterface.JsonInterface;
import com.daxiang.lottery.entity.AwardDetailData;
import com.daxiang.lottery.utils.DisplayUtil;
import com.daxiang.lottery.utils.HttpUtils2;
import com.daxiang.lottery.utils.StringUtil;
import com.daxiang.lottery.utils.dialogutils.LoadingDialogUtils;
import com.daxiang.lottery.view.AwardResultView;
import com.daxiang.lottery.view.TitleBar;
import com.google.gson.Gson;

import java.util.List;

public class AwardResultActivity extends AppCompatActivity {
    private AwardResultView mAwardResultView;
    private TitleBar mTitlebar;
    private TextView mLotcode;
    private TextView mIssue;
    private LinearLayout mAwardNum;
    private TextView mSale;
    private TextView mPool;
    private String lotcode;
    private String issue;
    private Context mContext;
    private String awardNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_award_result);
        mContext = this;
        Intent intent = getIntent();
        lotcode = intent.getStringExtra("lotcode");
        issue = intent.getStringExtra("issue");
        awardNum = intent.getStringExtra("awardNum");
        initView();
        addData();
        setAwardNum();
        addListener();
    }

    private void initView() {
        mAwardResultView = (AwardResultView) findViewById(R.id.awardResultView);
        mTitlebar = (TitleBar) findViewById(R.id.titlebar_award);
        mTitlebar.setTitle("期次详情");
        mTitlebar.setImageTitleVisibility(false);
        mTitlebar.setTitleVisibility(true);
        mLotcode = (TextView) findViewById(R.id.lotcode);
        mIssue = (TextView) findViewById(R.id.issue);
        mAwardNum = (LinearLayout) findViewById(R.id.awardNum);
        mSale = (TextView) findViewById(R.id.sale);
        mPool = (TextView) findViewById(R.id.pool);
    }

    private void addData() {
        final LoadingDialogUtils loadingDialogUtils = new LoadingDialogUtils(mContext);
        HttpInterface2 mHttp = new HttpUtils2(this);
        Bundle params = new Bundle();
        params.putString("lotCode", lotcode);
        params.putString("issueNo", issue);
        mHttp.get(Url.AWARD_DETAIL_RESULT, params, new JsonInterface() {
            @Override
            public void callback(String result) {
                loadingDialogUtils.setDimiss();
                Gson gson = new Gson();
                AwardDetailData awardDetailData = gson.fromJson(result, AwardDetailData.class);
                int code = awardDetailData.getCode();
                String msg = awardDetailData.getMsg();
                if (code == 0) {
                    List<AwardDetailData.DataBean.ItemsBean.DetailBean> detail = awardDetailData.getData().getItems().getDetail();
                    mAwardResultView.setData(detail);
                    AwardDetailData.DataBean.ItemsBean items = awardDetailData.getData().getItems();
                    String sale = items.getSale();
                    String pool = items.getPool();
                    if (!TextUtils.isEmpty(sale)) {
                        if (!sale.equals("--")) {
                            double v = Double.parseDouble(sale);
                            String string = StringUtil.getString((int) v + "");
                            mSale.setText(StringUtil.getString(sale));
                        }
                    }
                    if (!TextUtils.isEmpty(pool)) {
                        if (!pool.equals("--")) {
                            double v = Double.parseDouble(pool);
                            String string = StringUtil.getString((int) v + "");
                            mPool.setText(StringUtil.getString(pool));
                        }
                    }
                } else {
                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                loadingDialogUtils.setDimiss();
            }
        });
    }

    private void addListener() {
        mTitlebar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setAwardNum() {
        mLotcode.setText(LotteryTypes.getTypes(lotcode));
        mIssue.setText(issue + "期");
        if (TextUtils.isEmpty(awardNum)) return;
        //分割
        String[] splitnumber = strToArray(awardNum);
        mAwardNum.removeAllViews();
        //开始循环遍历
        for (int a = 0; a < splitnumber.length; a++) {
            TextView tv = new TextView(mContext);
            tv.setTextSize(16);
            tv.setTextColor(Color.WHITE);
            if (lotcode.equals("33")) {
                tv.setBackgroundResource(R.drawable.widget_ball_red);
                tv.setGravity(Gravity.CENTER);
            } else {
                tv.setBackgroundColor(Color.parseColor("#3b9c0e"));
            }
            if (lotcode.equals("35")) {
                tv.setBackgroundResource(R.drawable.widget_ball_red);
                tv.setGravity(Gravity.CENTER);
            } else if (lotcode.equals("51")) {
                if (a == splitnumber.length - 1) {
                    tv.setBackgroundResource(R.drawable.widget_ball_blue);
                    tv.setGravity(Gravity.CENTER);
                } else {
                    tv.setBackgroundResource(R.drawable.widget_ball_red);
                    tv.setGravity(Gravity.CENTER);
                }
            }
            if (lotcode.equals("52") || lotcode.equals("54")) {
                tv.setBackgroundResource(R.drawable.widget_ball_red);
                tv.setGravity(Gravity.CENTER);
            }

            if (lotcode.equals("10022") || lotcode.equals("21406")) {
                tv.setBackgroundResource(R.drawable.widget_ball_red);
                tv.setGravity(Gravity.CENTER);
            }
            if (lotcode.equals("23528")) {
                if (a == splitnumber.length - 1) {
                    tv.setBackgroundResource(R.drawable.widget_ball_blue);
                    tv.setGravity(Gravity.CENTER);
                } else {
                    tv.setBackgroundResource(R.drawable.widget_ball_red);
                    tv.setGravity(Gravity.CENTER);
                }
            }
            if (lotcode.equals("23529")) {
                if (a == splitnumber.length - 2) {
                    tv.setBackgroundResource(R.drawable.widget_ball_blue);
                    tv.setGravity(Gravity.CENTER);
                } else if (a == splitnumber.length - 1) {
                    tv.setBackgroundResource(R.drawable.widget_ball_blue);
                    tv.setGravity(Gravity.CENTER);
                } else {
                    tv.setBackgroundResource(R.drawable.widget_ball_red);
                    tv.setGravity(Gravity.CENTER);
                }
            }
            LinearLayout.LayoutParams layoutParams;
            if (lotcode.equals("16") || lotcode.equals("19") || lotcode.equals("11")) {
                layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            } else {
                layoutParams = new LinearLayout.LayoutParams(DisplayUtil.dip2px(35), DisplayUtil.dip2px(35));
                //layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            tv.setPadding(6, 0, 6, 0);
            tv.setText(splitnumber[a] + "");
            // LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50, 50);
            layoutParams.setMargins(5, 6, 5, 6);//4个参数按顺序分别是左上右下
            tv.setLayoutParams(layoutParams);
            mAwardNum.addView(tv);
        }
    }

    public String[] strToArray(String number) {
        String a = number.replace(":", ",");
        return a.split(",");
    }
}
