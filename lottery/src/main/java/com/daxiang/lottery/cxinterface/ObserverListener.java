package com.daxiang.lottery.cxinterface;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;

/**
 * Created by Administrator on 2016/7/31 0031.
 */
public interface ObserverListener {
    void observerListener(Map<Integer, Integer> map, ImageView mImageView, TextView mBallNumber, int position);
}
