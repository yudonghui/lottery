package com.daxiang.lottery.forum.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.daxiang.lottery.dialog.LoadingDialog;

/**
 * Created by Android on 2018/8/3.
 */

public abstract class BaseMvpFragment<T extends BasePresenter> extends Fragment {
    public View view;
    public Context mContext;
    public T mPresenter;
    public LoadingDialog mLoadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getInflateId(), null);
        }
        mContext = getContext();
        init();
        addListener();
        return view;
    }

    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(mContext);
        }
        mLoadingDialog.show();
        WindowManager.LayoutParams lp = mLoadingDialog.getWindow().getAttributes();
        lp.alpha = 0.8f;
        mLoadingDialog.getWindow().setAttributes(lp);
    }

    public void dismissLoading() {
        if (mLoadingDialog == null) return;
        if (mLoadingDialog.isShowing())
            mLoadingDialog.dismiss();
    }

    public abstract int getInflateId();

    //初始化
    public abstract void init();

    //点击事件
    public abstract void addListener();

    protected void startActivity(Class<? extends AppCompatActivity> targetClass) {
        ((Activity) mContext).startActivity(new Intent(mContext, targetClass));
    }

    protected void startActivity(Class<? extends AppCompatActivity> targetClass, Bundle params) {
        Intent intent = new Intent(mContext, targetClass);
        intent.putExtras(params);
        ((Activity) mContext).startActivity(intent);
    }

    protected void startActivityForResult(Class<? extends AppCompatActivity> targetClass, int resultCode) {
        Intent intent = new Intent(mContext, targetClass);
        ((Activity) mContext).startActivityForResult(intent, resultCode);
    }

    protected void startActivityForResult(Class<? extends AppCompatActivity> targetClass, int resultCode, Bundle params) {
        Intent intent = new Intent(mContext, targetClass);
        intent.putExtras(params);
        ((Activity) mContext).startActivityForResult(intent, resultCode);
    }

    /**
     * 切换软键盘的状态
     * 如当前为收起变为弹出,若当前为弹出变为收起
     */
    public void toggleInput() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 强制隐藏输入法键盘
     */
    public void hideInput(View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
