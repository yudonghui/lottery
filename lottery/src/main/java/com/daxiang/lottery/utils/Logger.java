package com.daxiang.lottery.utils;

import android.util.Log;

public class Logger {

    public static boolean isDebug = true;
    public static void setIsDebug(boolean isDebu){
        isDebug=isDebu;
    }
    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (isDebug) {
            Log.w(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }

}
