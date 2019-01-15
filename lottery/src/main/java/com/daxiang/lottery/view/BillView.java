package com.daxiang.lottery.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;

/**
 * Created by Android on 2018/3/22.
 */

public class BillView extends LinearLayout {
    private Context mContext;
    private View mInflate;
    public TextView mStart_time;
    public TextView mEnd_time;
    public TextView mSearch;
    public TextView mRecharge_money;
    public TextView mBuy_money;
    public TextView mWin_money;
    public TextView mDraw_money;
    public TextView mCancel;
    public TextView mFinish;

    public BillView(Context context) {
        super(context);
        mContext = context;
        mInflate = View.inflate(context, R.layout.dialog_bill_layout, this);
        initView();
        addListener();
    }

    public BillView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mInflate = View.inflate(context, R.layout.dialog_bill_layout, this);
        initView();
        addListener();
    }


    public BillView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    private void initView() {
        mStart_time = (TextView) mInflate.findViewById(R.id.start_time);
        mEnd_time = (TextView) mInflate.findViewById(R.id.end_time);
        mSearch = (TextView) mInflate.findViewById(R.id.search);
        mRecharge_money = (TextView) mInflate.findViewById(R.id.recharge_money);
        mBuy_money = (TextView) mInflate.findViewById(R.id.buy_money);
        mWin_money = (TextView) mInflate.findViewById(R.id.win_money);
        mDraw_money = (TextView) mInflate.findViewById(R.id.draw_money);
        mCancel = (TextView) mInflate.findViewById(R.id.cancel);
        mFinish = (TextView) mInflate.findViewById(R.id.finish);
    }

    private void addListener() {

    }

    public void setData(String totalCZ, String totalGC, String totalZJ, String totalTK) {
        mRecharge_money.setText(TextUtils.isEmpty(totalCZ) ? "--" : (totalCZ+"元"));
        mBuy_money.setText(TextUtils.isEmpty(totalGC) ? "--" : (totalGC+"元"));
        mWin_money.setText(TextUtils.isEmpty(totalZJ) ? "--" : (totalZJ+"元"));
        mDraw_money.setText(TextUtils.isEmpty(totalTK) ? "--" : (totalTK+"元"));
    }
}
