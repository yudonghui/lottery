package com.daxiang.lottery.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.lotteryactivity.BindPhoneActivity;
import com.daxiang.lottery.activity.lotteryactivity.CardActivity;
import com.daxiang.lottery.activity.lotteryactivity.OrderFormActivity;
import com.daxiang.lottery.activity.lotteryactivity.PhoneIsExistActivity;
import com.daxiang.lottery.activity.lotteryactivity.RechargeActivity;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.cxinterface.OnBetContinueListener;
import com.daxiang.lottery.cxinterface.OnBetFinishListener;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;

public class BuyResultDialog extends AlertDialog {
    static final String TAG = "_BuyResultDialog";

    private boolean mBetSucStatus;

    private boolean mNoMoney;

    private String mInfo;
    Activity mActivity;
    private OnBetContinueListener mOnContinueBetListener;

    private OnBetFinishListener onBetFinishListener;

    public BuyResultDialog(Context context, boolean status, String info, OnBetContinueListener onBackListener,
                           OnBetFinishListener onBetFinishListener) {
        super(context);
        this.mActivity = (Activity) context;
        mInfo = info;
        this.onBetFinishListener = onBetFinishListener;
        if (info.contains("金额不足")) {
            mNoMoney = true;
        } else {
            mNoMoney = false;
        }
        mBetSucStatus = status;
        mOnContinueBetListener = onBackListener;
       // setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.dialog_buy_result);
        TextView titleTextView = (TextView) findViewById(R.id.text_bet_result_title);
        TextView backButton = (TextView) findViewById(R.id.text_bet_result_back);
        TextView lotteryButton = (TextView) findViewById(R.id.text_bet_my_lottery);
        TextView moneyButton = (TextView) findViewById(R.id.text_bet_result_money);

        if (mBetSucStatus) {

            moneyButton.setVisibility(View.GONE);
            backButton.setOnClickListener(onContinueBetListener);
            lotteryButton.setOnClickListener(onLotteryClickListener);
        } else if (mNoMoney) {
            // moneyButton.setVisibility(View.VISIBLE);
            moneyButton.setOnClickListener(onMoneyClickListener);
            backButton.setOnClickListener(onContinueBetListener);
            titleTextView.setText(mInfo);
            lotteryButton.setVisibility(View.GONE);
        } else {
            backButton.setOnClickListener(onContinueBetListener);
            moneyButton.setVisibility(View.GONE);
            lotteryButton.setVisibility(View.GONE);
            titleTextView.setText(mInfo);
            backButton.setText("确定");
        }
    }

    private View.OnClickListener onContinueBetListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
            mOnContinueBetListener.onContinueBet();
        }
    };
    private View.OnClickListener onLotteryClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            dismiss();
            Intent intent = new Intent(mActivity, OrderFormActivity.class);
            mActivity.startActivity(intent);
            mActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            if (onBetFinishListener != null) {
                onBetFinishListener.onBetFinish();
            }
        }
    };

    private View.OnClickListener onMoneyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
            if(!LotteryApp.phoneFlag){
                HintDialogUtils.setHintDialog(mActivity);
                HintDialogUtils.setMessage("您还没有绑定手机号，请先绑定手机号");
                HintDialogUtils.setTitleVisiable(true);
                HintDialogUtils.setConfirm("确定",new DialogHintInterface(){

                    @Override
                    public void callBack(View view) {
                        //手机号未绑定
                        if (LotteryApp.isThird) {
                            Intent intent = new Intent(mActivity, PhoneIsExistActivity.class);
                            mActivity.startActivity(intent);
                        } else {
                            Intent intent = new Intent(mActivity, BindPhoneActivity.class);
                            intent.putExtra("isBind", false);
                            mActivity.startActivity(intent);
                        }
                    }
                });
            }else if(!LotteryApp.cardFlag){
                HintDialogUtils.setHintDialog(mActivity);
                HintDialogUtils.setMessage("您还没有绑定身份证，请先绑定身份证");
                HintDialogUtils.setTitleVisiable(true);
                HintDialogUtils.setConfirm("确定",new DialogHintInterface(){

                    @Override
                    public void callBack(View view) {
                        //身份证未绑定
                        Intent intent = new Intent(mActivity, CardActivity.class);
                        mActivity.startActivity(intent);
                    }
                });
            }/*else if(LotteryApp.bankFlag){
                HintDialogUtils.setHintDialog(mActivity);
                HintDialogUtils.setMessage("您还没有绑定银行卡，请先绑定银行卡");
                HintDialogUtils.setTitleVisiable(true);
                HintDialogUtils.setConfirm("确定",new DialogHintInterface(){

                    @Override
                    public void callBack(View view) {
                        //银行卡未绑定
                        Intent intent = new Intent(mActivity, BankActivity.class);
                        mActivity.startActivity(intent);
                    }
                });
            }*/else {
                Intent intent = new Intent(getContext(), RechargeActivity.class);
                getContext().startActivity(intent);
                if (onBetFinishListener != null) {
                    onBetFinishListener.onBetFinish();
                }
            }

        }
    };

}

