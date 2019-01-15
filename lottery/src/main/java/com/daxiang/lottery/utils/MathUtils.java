package com.daxiang.lottery.utils;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class MathUtils {
/*
    public long redGroup(int reds, int selects) {
        Log.e("红球个数：", reds + "");
        Log.e("最小的个数：", selects + "");
        if (reds == selects) {
            return 1;
        } else {
            for (int i = 1; i <= reds; i++) {
                rm = rm * i;
            }
            for (int i = 1; i <= selects; i++) {
                rn = rn * i;
            }
            for (int i = 1; i <= reds - selects; i++) {
                rj = rj * i;
            }
            rsum = rm / (rn * rj);
            return rsum;
        }

    }*/

    public long C(int n, int i) {
        if (i < 0 || n < 0 || n < i) {
            return 0;
        }
        long s1 = 1;
        long s2 = 1;
        for (int j = 1; j <= i; j++) {
            s1 *= j;
            s2 *= (n - j + 1);
        }

        return s2 / s1;
    }

   /* public long blueGroup(int blues, int selects) {
        if (blues == selects) {
            return 1;
        } else {
            for (int i = 1; i <= blues; i++) {
                bm = bm * i;
            }
            for (int i = 1; i <= selects; i++) {
                bn = bn * i;
            }
            for (int i = 1; i <= blues - selects; i++) {
                bj = bj * i;
            }
            bsum = bm / (bn * bj);
            return bsum;
        }
    }*/

    public long zusan(int reds, int selects) {
        if (reds >= selects) {
            long sum = 1;
            for (int i = 0; i < selects; i++) {
                sum = reds * sum;
                reds--;
            }
            return sum;
        } else {
            return 0;
        }
    }
}
