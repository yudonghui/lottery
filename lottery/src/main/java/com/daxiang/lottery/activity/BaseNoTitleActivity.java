package com.daxiang.lottery.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.daxiang.lottery.R;
import com.daxiang.lottery.utils.ClickUtils;
import com.umeng.message.PushAgent;

public class BaseNoTitleActivity extends AppCompatActivity {
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_no_title);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mContext = this;
        PushAgent.getInstance(this).onAppStart();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void startActivity(Class<? extends AppCompatActivity> classs, Bundle params) {
        if (ClickUtils.isFastClick(1500)) {
            return;
        }
        Intent intent = new Intent(this, classs);
        intent.putExtras(params);
        startActivity(intent);
    }

    public void startActivity(Class<? extends AppCompatActivity> classs) {
        if (ClickUtils.isFastClick(1500)) {
            return;
        }
        Intent intent = new Intent(this, classs);
        startActivity(intent);
    }

    public void startActivityForResult(Class<? extends AppCompatActivity> classs, Bundle params, int requestCode) {
        if (ClickUtils.isFastClick(1500)) {
            return;
        }
        Intent intent = new Intent(this, classs);
        intent.putExtras(params);
        startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(Class<? extends AppCompatActivity> classs, int requestCode) {
        if (ClickUtils.isFastClick(1500)) {
            return;
        }
        Intent intent = new Intent(this, classs);
        startActivityForResult(intent, requestCode);
    }
}
