package com.daxiang.lottery.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.common.LotCode;
import com.daxiang.lottery.view.TitleBar;

public class BonusRuleActivity extends AppCompatActivity {

    private String mLotCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus_rule);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        TitleBar mTitleBar = (TitleBar) findViewById(R.id.titlBar);
        ImageView mImageView= (ImageView) findViewById(R.id.imageView);
        mLotCode = getIntent().getStringExtra("lotCode");
        if (LotCode.JCZQ_CODE.equals(mLotCode)){
            mImageView.setBackgroundResource(R.mipmap.bonus_rule_footer);
        }else if (LotCode.JCLQ_CODE.equals(mLotCode)){
            mImageView.setBackgroundResource(R.mipmap.bonus_rule_basket);
        }
        mTitleBar.setTitle("奖金算法");
        mTitleBar.setImageTitleVisibility(false);
        mTitleBar.setTitleVisibility(true);
        mTitleBar.mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
