package com.daxiang.lottery.utils;

/**
 * Created by Administrator on 2017/3/10.
 */

public class UnitConvertUtils {
    public static double getM(double k){ // 该参数表示kb的值
        double m;
        m = k / 1024.0;

        return m; //返回kb转换之后的M值
    }
}
