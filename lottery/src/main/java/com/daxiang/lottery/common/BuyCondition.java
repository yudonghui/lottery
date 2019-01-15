package com.daxiang.lottery.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.activity.lotteryactivity.BindPhoneActivity;
import com.daxiang.lottery.activity.lotteryactivity.CardActivity;
import com.daxiang.lottery.activity.lotteryactivity.PhoneIsExistActivity;
import com.daxiang.lottery.cxinterface.DialogHintInterface;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;

/**
 * Created by Android on 2018/6/5.
 */

public class BuyCondition {
    public boolean isPhone(final Context mContext) {
        if (!LotteryApp.phoneFlag) {
            HintDialogUtils.setHintDialog(mContext);
            HintDialogUtils.setMessage("您还没有绑定手机号，请先绑定手机号");
            HintDialogUtils.setTitleVisiable(true);
            HintDialogUtils.setConfirm("确定", new DialogHintInterface() {

                @Override
                public void callBack(View view) {
                    //手机号未绑定
                    if (LotteryApp.isThird) {
                        Intent intent = new Intent(mContext, PhoneIsExistActivity.class);
                        ((Activity) mContext).startActivityForResult(intent, 10);
                    } else {
                        Intent intent = new Intent(mContext, BindPhoneActivity.class);
                        intent.putExtra("isBind", false);
                        mContext.startActivity(intent);
                    }
                }
            });
            return false;
        }
        return true;
    }

    public boolean isCard(final Context mContext) {
        if (!LotteryApp.cardFlag) {
            HintDialogUtils.setHintDialog(mContext);
            HintDialogUtils.setMessage("您还没有绑定身份证，请先绑定身份证");
            HintDialogUtils.setTitleVisiable(true);
            HintDialogUtils.setConfirm("确定", new DialogHintInterface() {

                @Override
                public void callBack(View view) {
                    //身份证未绑定
                    Intent intent = new Intent(mContext, CardActivity.class);
                    mContext.startActivity(intent);
                }
            });
            return false;
        }
        return true;
    }
}
