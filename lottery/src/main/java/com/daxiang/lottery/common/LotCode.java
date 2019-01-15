package com.daxiang.lottery.common;

import com.daxiang.lottery.R;

/**
 * Created by Android on 2018/1/2.
 */

public class LotCode {
    public final static String SSQ_CODE = "51";
    public final static String DLT_CODE = "23529";
    public final static String QLC_CODE = "23528";
    public final static String SD11X5_CODE = "21406";
    public final static String FC3D_CODE = "52";
    public final static String QXC_CODE = "10022";
    public final static String PL3_CODE = "33";
    public final static String PL5_CODE = "35";
    public final static String R9C_CODE = "19";
    public final static String SFC_CODE = "11";
    public final static String K3_CODE = "36";
    public final static String SSC_CODE = "50";
    public final static String JCLQ_CODE = "43";
    public final static String JCZQ_CODE = "42";
    public final static String GJ_CODE="30";
    public final static String JCZQ_D_CODE = "1000";
    public final static String JCLQ_D_CODE = "1001";

    public static int getResId(String lotcode) {
        int resId = R.drawable.jczq;
        switch (lotcode) {
            case "42":
                resId = R.drawable.jczq;
                break;
            case "43":
                resId = R.drawable.jclq;
                break;
            case "1000":
                resId = R.drawable.jczqd;
                break;
            case "1001":
                resId = R.drawable.jclqd;
                break;
            case "11":
                resId = R.drawable.sfc;
                break;
            case "19":
                resId = R.drawable.rj;
                break;
            case "23529":
                resId = R.drawable.dlt;
                break;
            case "33":
                resId = R.drawable.pls;
                break;
            case "35":
                resId = R.drawable.plw;
                break;
            case "10022":
                resId = R.drawable.qxc;
                break;
            case "23528":
                resId = R.drawable.qlc;
                break;
            case "51":
                resId = R.drawable.ssq;
                break;
            case "52":
                resId = R.drawable.fcsd;
                break;
            case "21406":
                resId = R.drawable.syydj;
                break;
            case "36":
                resId = R.drawable.jxks;
                break;
            case "50":
                resId = R.drawable.cqss;
                break;
        }
        return resId;
    }
}
