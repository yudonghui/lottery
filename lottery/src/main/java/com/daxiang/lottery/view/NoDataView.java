package com.daxiang.lottery.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.activity.NumberActivity;
import com.daxiang.lottery.utils.ClickUtils;
import com.daxiang.lottery.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import static com.daxiang.lottery.R.id.remark;

/**
 * Created by Android on 2017/12/26.
 */

public class NoDataView extends LinearLayout {
    private Context mContext;
    private View mInflate;

    private ImageView mImageView;
    private TextView mRemark;
    private TextView mButton;
    public final String TOUZHU = "预约大乐透";
    public final String REFRESH = "刷新";

    public NoDataView(Context context) {
        super(context);
        mContext = context;
        mInflate = View.inflate(context, R.layout.no_data_view, this);
        initView();
    }

    public NoDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mInflate = View.inflate(context, R.layout.no_data_view, this);
        initView();
        setAttrs(attrs);
    }

    private void setAttrs(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.NoDataView);
        //是否显示按钮。默认不能
        boolean isButton = typedArray.getBoolean(R.styleable.NoDataView_yu_isButton, false);
        //是否显示备注。默认不显示
        boolean isRemark = typedArray.getBoolean(R.styleable.NoDataView_yu_isRemark, false);
        //备注信息
        String remark = typedArray.getString(R.styleable.NoDataView_yu_remark);
        //按钮显示文字
        String buttonText = typedArray.getString(R.styleable.NoDataView_yu_buttontext);
        //显示的图片背景
        int backGroundId = typedArray.getResourceId(R.styleable.NoDataView_yu_background, 0);
        //显示的图片
        int resourceId = typedArray.getResourceId(R.styleable.NoDataView_yu_src, 0);
        //图片的大小
        float imgWidth = typedArray.getDimension(R.styleable.NoDataView_yu_image_width, DisplayUtil.dip2px(150));
        float imageHeight = typedArray.getDimension(R.styleable.NoDataView_yu_image_height, DisplayUtil.dip2px(150));
        LayoutParams layoutParams = new LayoutParams((int) imgWidth, (int) imageHeight);
        mImageView.setLayoutParams(layoutParams);
        if (isRemark) {
            mRemark.setVisibility(VISIBLE);
        } else {
            mRemark.setVisibility(GONE);
        }
        if (isButton) {
            mButton.setVisibility(VISIBLE);
        } else {
            mButton.setVisibility(GONE);
        }
        if (TextUtils.isEmpty(buttonText)) {
            mButton.setVisibility(GONE);
        } else {
            mButton.setVisibility(VISIBLE);
            mButton.setText(buttonText);
        }
        if (TextUtils.isEmpty(remark)) {
            mRemark.setVisibility(GONE);
        } else {
            mRemark.setVisibility(VISIBLE);
            mRemark.setText(remark);
        }
        if (backGroundId != 0)
            mImageView.setBackgroundResource(backGroundId);
        if (resourceId != 0)
            mImageView.setImageResource(resourceId);
        typedArray.recycle();
    }

    public NoDataView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mInflate = View.inflate(context, R.layout.no_data_view, this);
        initView();
    }

    private void initView() {
        mImageView = (ImageView) mInflate.findViewById(R.id.imageView);
        mRemark = (TextView) mInflate.findViewById(remark);
        mButton = (TextView) mInflate.findViewById(R.id.button);
        mButton.setOnClickListener(ButtonClickListener);
    }

    public void setImageView(int resId) {
        mImageView.setImageResource(resId);
    }

    public void setBackGround(int resId) {
        mImageView.setBackgroundResource(resId);
    }

    public void setRemark(String remark) {
        if (TextUtils.isEmpty(remark)) return;
        mRemark.setVisibility(VISIBLE);
        mRemark.setText(remark);
    }

    public void setButtonText(String button) {
        if (TextUtils.isEmpty(button)) return;
        mButton.setVisibility(VISIBLE);
        mButton.setText(button);
    }

    private OnClickListener mClickListener;

    public void setOnClickListener(OnClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    OnClickListener ButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            String s = mButton.getText().toString();
            switch (s) {
                case TOUZHU:
                    if (ClickUtils.isFastClick()) {
                        return;
                    }
                    randomBall();
                    intentskip();
                    break;
                case REFRESH:
                    if (ClickUtils.isFastClick(2000)) {
                        return;
                    }
                    mClickListener.onClick(v);
                    break;
            }
        }
    };

    private void intentskip() {
        Intent intent = new Intent(mContext, NumberActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("listRed", redRandom);
        bundle.putSerializable("listBlue", blueRandom);
        bundle.putString("lotcode", "23529");
        bundle.putBoolean("whereFlag", false);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    private ArrayList<Integer> redRandom = new ArrayList<>();
    private ArrayList<Integer> blueRandom = new ArrayList<>();
    private ArrayList<String> listRed = new ArrayList<>();
    private ArrayList<String> listBlue = new ArrayList<>();

    private void randomBall() {
        //随机选择红球
        redRandom.clear();
        listRed.clear();
        for (int i = 0; i < 5; i++) {
            int j = getRandom(redRandom, 35);
            redRandom.add(j);
            if (j < 10) {
                listRed.add("0" + j);
            } else {
                listRed.add(j + "");
            }
        }
        //随机选择蓝球
        blueRandom.clear();
        listBlue.clear();
        for (int i = 0; i < 2; i++) {
            int j = getRandom(blueRandom, 12);
            blueRandom.add(j);
            if (j < 10) {
                listBlue.add("0" + j);
            } else {
                listBlue.add(j + "");
            }
        }
    }

    //递归判断是否随机有重复的球
    private int getRandom(List<Integer> random, int r) {
        int j = (int) (Math.random() * r + 1);
        if (!random.contains(j)) {
            return j;
        } else {
            return getRandom(random, r);
        }

    }
}
