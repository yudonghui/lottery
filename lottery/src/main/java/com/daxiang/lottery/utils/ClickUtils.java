package com.daxiang.lottery.utils;

import android.util.Log;

/**
 * Created by Administrator on 2016/8/26 0026.
 */
public class ClickUtils {
    private static long lastClickTime;
    private static long lastClickTimeT;

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 3000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public synchronized static boolean isFastClick(int time) {
        long timeT = System.currentTimeMillis();
        Log.e("时间Click",lastClickTimeT+"");
        if (timeT - lastClickTimeT < time) {
            return true;
        }
        lastClickTimeT = timeT;
        return false;
    }


}
