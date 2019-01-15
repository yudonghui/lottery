package com.daxiang.lottery.utils.dialogutils;

import android.content.Context;
import android.view.WindowManager;

import com.daxiang.lottery.dialog.LoadingDialog;

/**
 * Created by Android on 2018/6/13.
 */

public class LoadingDialogBase {
    private LoadingDialog mLoadingDialog;
    private Context mContext;

    public LoadingDialogBase(Context mContext) {
        this.mContext = mContext;
    }

    public void show() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(mContext);
            mLoadingDialog.show();
            WindowManager.LayoutParams lp = mLoadingDialog.getWindow().getAttributes();
            lp.alpha = 0.8f;
            mLoadingDialog.getWindow().setAttributes(lp);
        } else {
            if (!mLoadingDialog.isShowing())
                mLoadingDialog.show();
        }
    }

    public void dismiss() {
        if (mLoadingDialog == null) return;
        if (mLoadingDialog.isShowing())
            mLoadingDialog.dismiss();
    }
}
