package com.daxiang.lottery.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.Number;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
public class GendanView extends LinearLayout {
    private View mInflate;
    private Context mContext;
    // Content View Elements

    private EditText mEd_fangan;
    private EditText mEd_ticheng;
    private TextView mTv_jz;
    private TextView mTv_wq;
    private TextView mTv_cy;
    private TextView mTv_hemai_dissmiss;
    private TextView mTv_hemai_comit;
    private OnclickgdComit onClickComit;
    int GenDanType = 3;

    public GendanView(Context context) {
        super(context);
        mContext = context;
        mInflate = View.inflate(context, R.layout.dialog_gendan_layout, this);
        initView();
        addListener();
    }

    public GendanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mInflate = View.inflate(context, R.layout.dialog_gendan_layout, this);
    }

    private void initView() {
        mEd_fangan = (EditText)mInflate.findViewById(R.id.ed_fangan);
        mEd_ticheng = (EditText) mInflate.findViewById(R.id.ed_ticheng);
        mTv_jz = (TextView) mInflate.findViewById(R.id.tv_jz);
        mTv_wq = (TextView) mInflate.findViewById(R.id.tv_wq);
        mTv_cy= (TextView) mInflate.findViewById(R.id.tv_cy);
        mTv_hemai_dissmiss = (TextView) mInflate.findViewById(R.id.tv_hemai_dissmiss);
        mTv_hemai_comit = (TextView) mInflate.findViewById(R.id.tv_hemai_comit);
    }

    public void setData(OnclickgdComit onClickComit) {
        this.onClickComit = onClickComit;
    }

    private void addListener() {
        mEd_ticheng.addTextChangedListener(mTextWatcher);
        mTv_hemai_comit.setOnClickListener(comitListener);
        mTv_hemai_dissmiss.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickComit.onClickComit("","",0);
            }
        });
        mTv_wq.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTv_wq.setBackgroundResource(R.drawable.shape_orange_deep);
                mTv_wq.setTextColor(Color.WHITE);
                mTv_cy.setBackgroundResource(R.drawable.shape_whitebg_gray);
                mTv_cy.setTextColor(Color.BLACK);
                mTv_jz.setBackgroundResource(R.drawable.shape_whitebg_gray);
                mTv_jz.setTextColor(Color.BLACK);
                GenDanType = Number.FULLY_OPEN;
            }
        });

        mTv_cy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTv_cy.setBackgroundResource(R.drawable.shape_orange_deep);
                mTv_cy.setTextColor(Color.WHITE);
                mTv_wq.setBackgroundResource(R.drawable.shape_whitebg_gray);
                mTv_wq.setTextColor(Color.BLACK);
                mTv_jz.setBackgroundResource(R.drawable.shape_whitebg_gray);
                mTv_jz.setTextColor(Color.BLACK);
                GenDanType = Number.OPEN_AFTER_FOLLOW;
            }
        });

        mTv_jz.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTv_jz.setBackgroundResource(R.drawable.shape_orange_deep);
                mTv_jz.setTextColor(Color.WHITE);
                mTv_cy.setBackgroundResource(R.drawable.shape_whitebg_gray);
                mTv_cy.setTextColor(Color.BLACK);
                mTv_wq.setBackgroundResource(R.drawable.shape_whitebg_gray);
                mTv_wq.setTextColor(Color.BLACK);
                GenDanType = Number.OPEN_AFTER_FOLLOW_AND_DEADLINE;
            }
        });
    }

    OnClickListener comitListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            String str1 = mEd_fangan.getText().toString();
            String str2 = mEd_ticheng.getText().toString();
            if (TextUtils.isEmpty(str2)) {
                str2 = "0";
            }
            onClickComit.onClickComit(str1,str2,GenDanType);
        }
    };
    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int length = String.valueOf(s).length();
            if (length == 0) {
            } else if (Integer.parseInt(String.valueOf(s)) > 10) {
                mEd_ticheng.setText("10");
            }
        }
    };

    public interface OnclickgdComit {
        void onClickComit(String str1, String str2, int GenDanType);
    }
}
