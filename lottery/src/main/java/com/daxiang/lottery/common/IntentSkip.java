package com.daxiang.lottery.common;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.activity.ChampionActivity;
import com.daxiang.lottery.activity.JclqActivity;
import com.daxiang.lottery.activity.JczqActivity;
import com.daxiang.lottery.activity.LoginActivity;
import com.daxiang.lottery.activity.NumberActivity;
import com.daxiang.lottery.activity.SfcAndRjcActivity;
import com.daxiang.lottery.activity.lotteryactivity.BindPhoneActivity;
import com.daxiang.lottery.activity.lotteryactivity.CardActivity;
import com.daxiang.lottery.activity.lotteryactivity.PhoneIsExistActivity;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.utils.ClickUtils;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;

/**
 * Created by Administrator on 2017/3/23.
 */

public class IntentSkip {
    public static void skip(final Context activity, String className) {
        final Intent intent = new Intent();
        try {

            switch (className) {
                case "com.daxiang.lottery.activity.lotteryactivity.RechargeActivity":
                    //充值界面
                    if (!LotteryApp.isLogin) {
                        intent.setClass(activity, LoginActivity.class);
                        activity.startActivity(intent);
                    } else if (!LotteryApp.phoneFlag) {
                        HintDialogUtils.setHintDialog(activity);
                        HintDialogUtils.setMessage("您还没有绑定手机号，请先绑定手机号");
                        HintDialogUtils.setTitleVisiable(true);
                        HintDialogUtils.setConfirm("确定", new DialogHintInterface() {

                            @Override
                            public void callBack(View view) {
                                //手机号未绑定
                                if (LotteryApp.isThird) {
                                    intent.setClass(activity, PhoneIsExistActivity.class);
                                    activity.startActivity(intent);
                                } else {
                                    intent.setClass(activity, BindPhoneActivity.class);
                                    intent.putExtra("isBind", false);
                                    activity.startActivity(intent);
                                }
                            }
                        });
                    } else if (!LotteryApp.cardFlag) {
                        HintDialogUtils.setHintDialog(activity);
                        HintDialogUtils.setMessage("您还没有绑定身份证，请先绑定身份证");
                        HintDialogUtils.setTitleVisiable(true);
                        HintDialogUtils.setConfirm("确定", new DialogHintInterface() {

                            @Override
                            public void callBack(View view) {
                                //身份证未绑定
                                intent.setClass(activity, CardActivity.class);
                                activity.startActivity(intent);
                            }
                        });
                    } else {
                        intent.setClass(activity, Class.forName(className));
                        activity.startActivity(intent);
                    }
                    break;
                default:
                    intent.setClass(activity, Class.forName(className));
                    activity.startActivity(intent);
                    break;
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void skipLotcode(Context mContext, String lotcode) {
        //防止连续点击
        if (ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent();
        switch (lotcode) {
            case "42":
                intent.setClass(mContext, JczqActivity.class);
                intent.putExtra("lotcode", lotcode);
                intent.putExtra("bunch", true);
                mContext.startActivity(intent);
                break;
            case "1000":
                intent.setClass(mContext, JczqActivity.class);
                intent.putExtra("lotcode", "42");
                intent.putExtra("bunch", false);
                mContext.startActivity(intent);
                break;
            case "11":
            case "19":
                intent.setClass(mContext, SfcAndRjcActivity.class);
                intent.putExtra("lotcode", lotcode);
                mContext.startActivity(intent);
                /*intent.setClass(mContext, ChampionActivity.class);
                mContext.startActivity(intent);*/
                break;
            case "1001":
                intent.setClass(mContext, JclqActivity.class);
                intent.putExtra("lotcode", "43");
                intent.putExtra("bunch", false);
                mContext.startActivity(intent);
                break;
            case "43":
                intent.setClass(mContext, JclqActivity.class);
                intent.putExtra("lotcode", lotcode);
                intent.putExtra("bunch", true);
                mContext.startActivity(intent);
                break;
            case "30":
                intent.setClass(mContext, ChampionActivity.class);
                mContext.startActivity(intent);
                break;
            default:
                intent.setClass(mContext, NumberActivity.class);
                intent.putExtra("lotcode", lotcode);
                intent.putExtra("whereFlag", true);
                mContext.startActivity(intent);
                break;
        }
    }
}

