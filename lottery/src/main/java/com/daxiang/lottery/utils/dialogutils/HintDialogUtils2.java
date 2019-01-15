package com.daxiang.lottery.utils.dialogutils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.cxinterface.DialogHintInterface;

/**
 * @author yudonghui
 * @date 2017/5/11
 * @describe May the Buddha bless bug-free!!!
 */
public class HintDialogUtils2 {
    public Dialog mHintDialog;
    private TextView tvCancel;
    private TextView tvConfirm;
    private TextView mMessage;
    private TextView mTitle;
    private View viewLine;
    private TextView mVersionMessage;

    public HintDialogUtils2(Context mContext) {
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

    public void setConfirm(String confirm, final DialogHintInterface dialogHintInterface) {
        tvConfirm.setText(confirm);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintDialog.dismiss();
                dialogHintInterface.callBack(v);
            }
        });
    }

    public void setCancel(String cancel, final DialogHintInterface dialogHintInterface) {
        tvCancel.setText(cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintDialog.dismiss();
                dialogHintInterface.callBack(v);
            }
        });
    }

    public void setMessage(String message) {
        mMessage.setText(TextUtils.isEmpty(message) ? "" : message);
    }

    public void setVersionMessage(String message) {
        mMessage.setVisibility(View.GONE);
        mVersionMessage.setVisibility(View.VISIBLE);
        mVersionMessage.setText(message);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setTvCancel(String cancel) {
        tvCancel.setText(cancel);
    }

    public void setTvConfirm(String confirm) {
        tvConfirm.setText(confirm);
    }

    public void setVisibilityCancel() {
        tvCancel.setVisibility(View.GONE);
        viewLine.setVisibility(View.GONE);
    }

    public void setCancelable(boolean isClick) {
        mHintDialog.setCancelable(isClick);
    }

    public void setTitleVisiable(boolean isVisiable) {
        if (isVisiable) mTitle.setVisibility(View.VISIBLE);
        else mTitle.setVisibility(View.GONE);
    }
}
