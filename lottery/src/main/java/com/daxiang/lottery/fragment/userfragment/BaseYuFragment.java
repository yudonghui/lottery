package com.daxiang.lottery.fragment.userfragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.daxiang.lottery.utils.ClickUtils;

/**
 * Created by Android on 2018/4/20.
 */

public class BaseYuFragment extends Fragment {
    public Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    public void startActivity(Class<? extends AppCompatActivity> classs, Bundle params) {
        if (ClickUtils.isFastClick(1500)) {
            return;
        }
        Intent intent = new Intent(mContext, classs);
        intent.putExtras(params);
        startActivity(intent);
    }

    public void startActivity(Class<? extends AppCompatActivity> classs) {
        if (ClickUtils.isFastClick(1500)) {
            return;
        }
        Intent intent = new Intent(mContext, classs);
        startActivity(intent);
    }

    public void startActivityForResult(Class<? extends AppCompatActivity> classs, Bundle params, int requestCode) {
        if (ClickUtils.isFastClick(1500)) {
            return;
        }
        Intent intent = new Intent(mContext, classs);
        intent.putExtras(params);
        ((Activity) mContext).startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(Class<? extends AppCompatActivity> classs, int requestCode) {
        if (ClickUtils.isFastClick(1500)) {
            return;
        }
        Intent intent = new Intent(mContext, classs);
        ((Activity) mContext).startActivityForResult(intent, requestCode);
    }
}
