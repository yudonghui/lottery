package com.daxiang.lottery.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.daxiang.lottery.view.JcTitleBar;
import com.daxiang.lottery.R;
public class BaseJcActivity extends AppCompatActivity {
    public JcTitleBar mTitleBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_jc);
       // PushAgent.getInstance(this).onAppStart();
    }
    public void initContentView(int layoutResID) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_content);
        mTitleBar = (JcTitleBar) findViewById(R.id.titlebar_base);
        View inflate = View.inflate(this, layoutResID, null);
        linearLayout.addView(inflate);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
