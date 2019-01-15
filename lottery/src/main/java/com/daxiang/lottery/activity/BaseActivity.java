package com.daxiang.lottery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.daxiang.lottery.utils.ClickUtils;
import com.daxiang.lottery.utils.NetworkUtils;
import com.daxiang.lottery.utils.dialogutils.HintDialogUtils;
import com.daxiang.lottery.view.TitleBar;

import com.daxiang.lottery.R;
import com.umeng.message.PushAgent;

public class BaseActivity extends AppCompatActivity {
    public TitleBar mTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        // PushAgent.getInstance(this).onAppStart();
        if (!NetworkUtils.isNetworkAvailable(this)) {
            HintDialogUtils.setHintDialog(this);
            HintDialogUtils.setMessage("您的网络未连接，请连接后重试！");
        }
    }

    public void initContentView(int layoutResID) {
        PushAgent.getInstance(this).onAppStart();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_content);
        mTitleBar = (TitleBar) findViewById(R.id.titlebar_base);
        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        View inflate = View.inflate(this, layoutResID, null);
        linearLayout.addView(inflate);
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
/*
    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(0, R.anim.slide_out_right);
    }*/
}
