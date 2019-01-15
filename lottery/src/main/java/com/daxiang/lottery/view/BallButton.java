package com.daxiang.lottery.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
/**
 * Created by Administrator on 2016/8/15 0015.
 */
public class BallButton extends LinearLayout {
    private ImageView mImageView;
    private TextView mBallNumber;
    private View mInflate;
    private String color;
    private int blueTxt;
    private int redTxt;

    public BallButton(Context context) {
        super(context);
        blueTxt = context.getResources().getColor(R.color.blue_txt);
        redTxt = context.getResources().getColor(R.color.red_theme);
        mInflate = View.inflate(context, R.layout.item_ball, this);
    }

    public BallButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        blueTxt = context.getResources().getColor(R.color.blue_txt);
        redTxt = context.getResources().getColor(R.color.red_theme);
        mInflate = View.inflate(context, R.layout.item_ball, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mImageView = (ImageView) mInflate.findViewById(R.id.image_ball);
        mBallNumber = (TextView) mInflate.findViewById(R.id.text_ball);
    }

    public void setColor(String color) {
        this.color = color;
        if ("red".equals(color)) {
            mImageView.setImageResource(R.drawable.widget_ball_red_frame);
            mBallNumber.setTextColor(redTxt);
        } else {
            mImageView.setImageResource(R.drawable.widget_ball_blue_frame);
            mBallNumber.setTextColor(blueTxt);
        }
    }

    public void setSelectState(boolean flag){
        if(flag){
            if("red".equals(color)){
                mImageView.setImageResource(R.drawable.widget_ball_red);
                mBallNumber.setTextColor(Color.WHITE);
            }else{
                mImageView.setImageResource(R.drawable.widget_ball_blue);
                mBallNumber.setTextColor(Color.WHITE);
            }
        }else {
            if("red".equals(color)){
                mImageView.setImageResource(R.drawable.widget_ball_red_frame);
                mBallNumber.setTextColor(redTxt);
            }else{
                mImageView.setImageResource(R.drawable.widget_ball_blue_frame);
                mBallNumber.setTextColor(blueTxt);
            }
        }

    }
    public void setText(String num){
        mBallNumber.setText(num);
    }
    public String getText(){
        return mBallNumber.getText().toString();
    }
}
