package com.daxiang.lottery.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.cxinterface.PlayMethodSelector;


/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class PlayMethodDialog {
    private String lotcode;
    private TextView mBunchPlayMethod;
    private TextView mBtn_spf;
    private TextView mBtn_rqspf;
    private TextView mBtn_cbf;
    private TextView mBtn_jqs;
    private TextView mSinglePlayMethod;
    private TextView mBtn_Bqc;
    private TextView mBtn_hh;
    private View mYinying;
    private boolean mPlayMethodBunchs;
    private Activity mContext;
    private PlayMethodSelector methodSelector;
    private View mInflate;
    private PopupWindow mPopupWindow;

    public PlayMethodDialog(Activity context, String lotcode, boolean mPlayMethodBunchs, PlayMethodSelector methodSelector) {
        this.mContext = context;
        this.lotcode = lotcode;
        this.mPlayMethodBunchs = mPlayMethodBunchs;
        this.methodSelector = methodSelector;
        mInflate = View.inflate(context, R.layout.playmethod_select_dialog, null);
        initView();
        //玩法判断
        playMethodJudge();
        //设置监听
        setListener();
        mYinying.setOnClickListener(DismissPopup);
        mPopupWindow = new PopupWindow(mInflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        ColorDrawable cd = new ColorDrawable(0x000000);
        mPopupWindow.setBackgroundDrawable(cd);
    }


    private void initView() {
        mBunchPlayMethod = (TextView) mInflate.findViewById(R.id.text_bunch_playmethod);
        mBtn_spf = (TextView) mInflate.findViewById(R.id.btn_spf);
        mBtn_rqspf = (TextView) mInflate.findViewById(R.id.btn_rqspf);
        mBtn_cbf = (TextView) mInflate.findViewById(R.id.btn_cbf);
        mBtn_jqs = (TextView) mInflate.findViewById(R.id.btn_jqs);
        mSinglePlayMethod = (TextView) mInflate.findViewById(R.id.text_single_playmethod);
        mBtn_Bqc = (TextView) mInflate.findViewById(R.id.btn_bqc);
        mBtn_hh = (TextView) mInflate.findViewById(R.id.btn_hh);
        mYinying = mInflate.findViewById(R.id.yinying);
    }

    public void show(View view) {
        if (mPopupWindow == null) return;
        if (Build.VERSION.SDK_INT < 24) {
            mPopupWindow.showAsDropDown(view);
        } else {
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, y + view.getHeight());
        }
    }

    private void playMethodJudge() {
        switch (lotcode) {
            case "42":
                if (mPlayMethodBunchs) {
                    //竞足串关
                    bunch();
                } else {
                    //竞足单关
                    single();
                }
                break;
            case "43":
                mBtn_Bqc.setVisibility(View.GONE);
                mBtn_spf.setText("胜负");
                mBtn_rqspf.setText("让分胜负");
                mBtn_cbf.setText("大小分");
                mBtn_jqs.setText("胜分差");
                if (mPlayMethodBunchs) {
                    bunch();
                } else {
                    //竞篮单关
                    single();
                }
                break;
        }
    }

    View.OnClickListener DismissPopup = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mPopupWindow != null && mPopupWindow.isShowing()) mPopupWindow.dismiss();
        }
    };

    private void setListener() {
        mBunchPlayMethod.setOnClickListener(playMethodClick);
        mSinglePlayMethod.setOnClickListener(playMethodClick);
        mBtn_hh.setOnClickListener(click);
        mBtn_spf.setOnClickListener(click);
        mBtn_cbf.setOnClickListener(click);
        mBtn_jqs.setOnClickListener(click);
        mBtn_rqspf.setOnClickListener(click);
        mBtn_Bqc.setOnClickListener(click);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_spf:
                    methodSelector.playMethod(LotteryTypes.SPF, mPlayMethodBunchs);
                    break;
                case R.id.btn_rqspf:
                    methodSelector.playMethod(LotteryTypes.RQSPF, mPlayMethodBunchs);
                    break;
                case R.id.btn_cbf:
                    methodSelector.playMethod(LotteryTypes.CBF, mPlayMethodBunchs);
                    break;
                case R.id.btn_jqs:
                    methodSelector.playMethod(LotteryTypes.JQS, mPlayMethodBunchs);
                    break;
                case R.id.btn_bqc:
                    methodSelector.playMethod(LotteryTypes.BQC, mPlayMethodBunchs);
                    break;
                case R.id.btn_hh:
                    methodSelector.playMethod(LotteryTypes.HH, mPlayMethodBunchs);
                    break;
            }
            mPopupWindow.dismiss();
        }
    };
    View.OnClickListener playMethodClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.text_bunch_playmethod:
                    bunch();
                    break;
                case R.id.text_single_playmethod:
                    single();
                    break;
            }
        }
    };

    private void single() {
        mPlayMethodBunchs = false;
        mBtn_hh.setVisibility(View.GONE);
        mBunchPlayMethod.setBackgroundColor(Color.WHITE);
        mBunchPlayMethod.setTextColor(Color.BLACK);
        mSinglePlayMethod.setBackgroundResource(R.drawable.rightkuang);
        mSinglePlayMethod.setTextColor(Color.RED);
    }

    private void bunch() {
        mPlayMethodBunchs = true;
        mBtn_hh.setVisibility(View.VISIBLE);
        mSinglePlayMethod.setBackgroundColor(Color.WHITE);
        mSinglePlayMethod.setTextColor(Color.BLACK);
        mBunchPlayMethod.setBackgroundResource(R.drawable.leftkuang);
        mBunchPlayMethod.setTextColor(Color.RED);
    }
}
