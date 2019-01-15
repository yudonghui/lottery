package com.daxiang.lottery.activity.lotteryactivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.BaseActivity;


public class AboutUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_about_us);
        initView();
    }
    private void initView() {

        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitle("关于我们");
        mTitleBar.setTitleVisibility(true);
        ImageView mImageView= (ImageView) findViewById(R.id.about_us_image);
       // mImageView.setImageResource(R.drawable.tubiao);
    }
}
