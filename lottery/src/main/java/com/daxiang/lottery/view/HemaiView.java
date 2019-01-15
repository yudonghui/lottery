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
import android.widget.Toast;
import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.Number;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
public class HemaiView extends LinearLayout {
    Context mContext;
    private View mInflate;
    // Content View Elements

    private EditText mEd_ticheng;
    private EditText mEd_rengou;
    private TextView mTv_rg_money;
    private EditText mEd_baodi;
    private TextView mTv_bd_money;
    private TextView mTv_wq;
    private TextView mTv_cy;
    private TextView mTv_jz;
    private TextView mTv_hemai_dissmiss;
    private TextView mTv_hemai_comit;
    int shakes;
    int mMulti;
    int totalMoney;
    int HeMainType=1;
    OnClickComit onClickComit;
    public HemaiView(Context context) {
        super(context);
        mContext = context;
        mInflate = View.inflate(context, R.layout.dialog_hemai_layout, this);
        initView();
        addListener();
    }

    public HemaiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mInflate = View.inflate(context, R.layout.dialog_hemai_layout, this);
    }

    protected void initView() {
        mEd_ticheng = (EditText) mInflate.findViewById(R.id.ed_ticheng);
        mEd_rengou = (EditText) mInflate.findViewById(R.id.ed_rengou);
        mTv_rg_money = (TextView) mInflate.findViewById(R.id.tv_rg_money);
        mEd_baodi = (EditText) mInflate.findViewById(R.id.ed_baodi);
        mTv_bd_money = (TextView) mInflate.findViewById(R.id.tv_bd_money);
        mTv_wq = (TextView) mInflate.findViewById(R.id.tv_wq);
        mTv_cy = (TextView) mInflate.findViewById(R.id.tv_cy);
        mTv_jz = (TextView) mInflate.findViewById(R.id.tv_jz);
        mTv_hemai_dissmiss = (TextView) mInflate.findViewById(R.id.tv_hemai_dissmiss);
        mTv_hemai_comit = (TextView) mInflate.findViewById(R.id.tv_hemai_comit);
    }

    public void setData(int shakes, int mMulti,OnClickComit onClickComit) {
        this.shakes = shakes;
        this.mMulti = mMulti;
        totalMoney = shakes * mMulti * 2;
        this.onClickComit=onClickComit;
        //this.maxTheoryBonus = maxTheoryBonus;
    }

    private void addListener() {
        mEd_ticheng.addTextChangedListener(mTextWatcher);
        mTv_hemai_comit.setOnClickListener(comitListener);
        mTv_hemai_dissmiss.setOnClickListener(dimissListener);
        mTv_wq.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTv_wq.setBackgroundResource(R.drawable.hemai_bg_red);
                mTv_wq.setTextColor(Color.WHITE);
                mTv_cy.setBackgroundResource(R.drawable.hemai_bg_lines);
                mTv_cy.setTextColor(Color.BLACK);
                mTv_jz.setBackgroundResource(R.drawable.hemai_bg_lines);
                mTv_jz.setTextColor(Color.BLACK);
                HeMainType = Number.FULLY_OPEN;
            }
        });

        mTv_cy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTv_cy.setBackgroundResource(R.drawable.hemai_bg_red);
                mTv_cy.setTextColor(Color.WHITE);
                mTv_wq.setBackgroundResource(R.drawable.hemai_bg_lines);
                mTv_wq.setTextColor(Color.BLACK);
                mTv_jz.setBackgroundResource(R.drawable.hemai_bg_lines);
                mTv_jz.setTextColor(Color.BLACK);
                HeMainType = Number.OPEN_AFTER_FOLLOW;
            }
        });

        mTv_jz.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTv_jz.setBackgroundResource(R.drawable.hemai_bg_red);
                mTv_jz.setTextColor(Color.WHITE);
                mTv_cy.setBackgroundResource(R.drawable.hemai_bg_lines);
                mTv_cy.setTextColor(Color.BLACK);
                mTv_wq.setBackgroundResource(R.drawable.hemai_bg_lines);
                mTv_wq.setTextColor(Color.BLACK);
                HeMainType = Number.OPEN_AFTER_DEADLINE;
            }
        });
    }
    OnClickListener dimissListener=new OnClickListener() {
        @Override
        public void onClick(View v) {
         onClickComit.onClick("","","",0);
        }
    };
    OnClickListener comitListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            String str1 = mEd_ticheng.getText().toString();
            String str2 = mEd_rengou.getText().toString();
            String str3 = mEd_baodi.getText().toString();
            double jczqRG;
            if (TextUtils.isEmpty(str1)) {
                Toast.makeText(mContext, "请输入提成比例", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(str2)) {
                Toast.makeText(mContext, "认购金额最低5%,至少1元", Toast.LENGTH_SHORT).show();
            } else if (!TextUtils.isEmpty(str2)) {
                if (TextUtils.isEmpty(str3)){
                    str3="0";
                }
                jczqRG = (Integer.parseInt(str2) * 100.0) / totalMoney;
                if (jczqRG > Integer.parseInt(str1) && jczqRG >= 5) {
                    if (Integer.parseInt(str2) + Integer.parseInt(str3) <= totalMoney) {
                        onClickComit.onClick(str1, str2, str3, HeMainType);
                        // mDialog.dismiss();
                    } else {
                        Toast.makeText(mContext, "保底金额+认购金额不能大于总金额", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "最低认购5%，至少1元，且不能低于提成比", Toast.LENGTH_SHORT).show();
                }
            }
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
                // mEd_ticheng.setText("0");
            } else if (Integer.parseInt(String.valueOf(s)) > 10) {
                mEd_ticheng.setText("10");
            }
        }
    };
    public interface OnClickComit {
        void onClick(String str1, String str2, String str3, int type);

    }
}
