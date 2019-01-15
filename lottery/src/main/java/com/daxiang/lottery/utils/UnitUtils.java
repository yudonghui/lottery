package com.daxiang.lottery.utils;

import java.text.DecimalFormat;

/**
 * @author yudonghui
 * @date 2017/12/14
 * @describe May the Buddha bless bug-free!!!
 */
public class UnitUtils {
    /**
     * 1000000 return 1百万 10000 return 1万 1000 return 1千
     *
     * @param amount
     * @return
     */
    public static String amountConversion(double amount) {
        DecimalFormat df = new DecimalFormat("#####0.00");
        if (amount >= 100000000) {
            return df.format(amount / 100000000) + "亿";
        }
        if (amount >= 10000000) {
            return df.format(amount / 10000000) + "百万";
        }
        if (amount >= 1000000) {
            return df.format(amount / 1000000) + "百万";
        }

        if (amount >= 10000) {
            return df.format(amount / 10000) + "万";
        }

        return (int) amount + "";
    }
}
