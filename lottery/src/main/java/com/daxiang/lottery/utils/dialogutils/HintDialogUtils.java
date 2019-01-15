package com.daxiang.lottery.utils.dialogutils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.cxinterface.DialogHintInterface;

/**
 * Created by Administrator on 2016/10/20 0020.
 */
public class HintDialogUtils {
    public static Dialog mHintDialog;
    private static TextView tvCancel;
    private static TextView tvConfirm;
    private static TextView mMessage;
    private static TextView mTitle;
    private static View viewLine;
    private static TextView mVersionMessage;

    public static void setHintDialog(Context mContext) {
        mHintDialog = new Dialog(mContext, R.style.HintDialog);
        mHintDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(mContext, R.layout.dialog_hint, null);
        mTitle = (TextView) view.findViewById(R.id.hint_dialog_title);
        mVersionMessage = (TextView) view.findViewById(R.id.version_content);
        viewLine = view.findViewById(R.id.viewline);
        tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        mMessage = (TextView) view.findViewById(R.id.hint_dialog_msm);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintDialog.dismiss();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintDialog.dismiss();
            }
        });
        mHintDialog.setContentView(view);
        mHintDialog.show();
    }

    public static void setConfirm(String confirm, final DialogHintInterface dialogHintInterface) {
        tvConfirm.setText(confirm);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintDialog.dismiss();
                dialogHintInterface.callBack(v);
            }
        });
    }

    public static void setCancel(String cancel, final DialogHintInterface dialogHintInterface) {
        tvCancel.setText(cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintDialog.dismiss();
                dialogHintInterface.callBack(v);
            }
        });
    }

    public static void setMessage(String message) {
        mMessage.setText(message);
    }

    public static void setVersionMessage(String message) {
        mMessage.setVisibility(View.GONE);
        mVersionMessage.setVisibility(View.VISIBLE);
        mVersionMessage.setText(message);
    }

    public static void setTitle(String title) {
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText(title);
    }

    public static void setTvCancel(String cancel) {
        tvCancel.setText(cancel);
    }

    public static void setTvConfirm(String confirm) {
        tvConfirm.setText(confirm);
    }

    public static void setVisibilityCancel() {
        tvCancel.setVisibility(View.GONE);
        viewLine.setVisibility(View.GONE);
    }

    public static void setCancelable(boolean isClick) {
        mHintDialog.setCancelable(isClick);
    }

    public static void setTitleVisiable(boolean isVisiable) {
        if (isVisiable) mTitle.setVisibility(View.VISIBLE);
        else mTitle.setVisibility(View.GONE);
    }
}
