package com.daxiang.lottery.utils.dialogutils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.daxiang.lottery.R;
/**
 * Created by Administrator on 2016/11/26 0026.
 */
public class IosDialogUtils {
    private static Dialog mDialog;
    private static TextView mFocus;
    private static TextView mReplay;
    private static TextView mSendto;
    private static Button mCancel;
    private static Window window;
    private static View dialogLines;

    public static void IosStyleDialog(Context mContext) {
        View inflate = View.inflate(mContext, R.layout.item_popup, null);
        mFocus = (TextView) inflate.findViewById(R.id.buttonCamera);
        mSendto = (TextView) inflate.findViewById(R.id.buttonPhoto_selector);
        mCancel = (Button) inflate.findViewById(R.id.buttoncancle);
        //当有回复这一项的时候用到
        mReplay = (TextView) inflate.findViewById(R.id.dialog_reply);
        dialogLines = inflate.findViewById(R.id.dialog_lines);
        mCancel.setText("取消");
        mDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
        window = mDialog.getWindow();
        mDialog.setContentView(inflate);
        mDialog.show();
        window.setGravity(Gravity.BOTTOM);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
    }

    public static void setDialogText(String dialogText) {

    }

    /*public static void setDimiss() {
        mDialog.dismiss();
    }*/

    public static void setReplayVisibility(boolean flag) {
        if (flag) {
            mReplay.setVisibility(View.VISIBLE);
            dialogLines.setVisibility(View.VISIBLE);
        } else {
            mReplay.setVisibility(View.GONE);
            dialogLines.setVisibility(View.GONE);
        }
    }

    public static void setFocusListener(String text, final DialogListener mDialogListener) {
        mFocus.setText(text);
        mFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogListener.callBack(mFocus);
                mDialog.dismiss();
            }
        });
    }

    public static void setReplayListener(String text, final DialogListener mDialogListener) {
        mReplay.setText(text);
        mReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogListener.callBack(mReplay);
                mDialog.dismiss();
            }
        });
    }

    public static void setSendtoListener(String text, final DialogListener mDialogListener) {
        mSendto.setText(text);
        mSendto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogListener.callBack(mSendto);
                mDialog.dismiss();
            }
        });
    }

   public interface DialogListener {
        void callBack(View view);
    }
}
