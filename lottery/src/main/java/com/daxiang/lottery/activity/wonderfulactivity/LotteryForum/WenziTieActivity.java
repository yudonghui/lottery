package com.daxiang.lottery.activity.wonderfulactivity.LotteryForum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.daxiang.lottery.activity.BaseActivity;

import com.daxiang.lottery.R;
public class WenziTieActivity extends BaseActivity {
   EditText mEditText;
    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_wenzi_tie);
        initView();
        addListener();
    }

    private void initView() {
        mEditText= (EditText) findViewById(R.id.fatie_content);
        mTextView= (TextView) findViewById(R.id.look_privilege);
        mTitleBar.setTitle("发帖");
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitleVisibility(true);
        mTitleBar.setTitleRegisterVisibility(true);
        mTitleBar.mTextRegister.setText("发送");
    }

    private void addListener() {
     //发送 帖子
        mTitleBar.mTextRegister.setOnClickListener(SendListener);
        //设置看的人的权限
        mTextView.setOnClickListener(PrivilegeListener);
    }
    View.OnClickListener SendListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    View.OnClickListener PrivilegeListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(WenziTieActivity.this,LookPrivilegeActivity.class);
            startActivity(intent);
        }
    };
}
