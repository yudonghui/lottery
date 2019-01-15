package com.daxiang.lottery.constant;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.utils.ProductUtil;

/**
 * Created by Administrator on 2016/12/5 0005.
 */
public class Number {
    /*
    * 只有下订单的时候用到的渠道号。
    * 这个是根据个人信息获取的到。
    * */
    public static String CHANNELIDBUY = "10000000";
    /**
     * app内置的渠道。
     * 注册 修改密码等用到。
     * 根据不同的平台上面下载的包，配置相应的渠道号
     */
    public static String CHANNELID = "10000000";//getChannelIda();//getChannelId();//app内置的渠道。
    /**
     lottery-mtyf-04
     lottery-mtyf-05
     lottery-mtyf-06
     lottery-mtyf-07
     lottery-mtyf-08
     lottery-sougou
     * */
    public static String getChannelId() {
        switch (ProductUtil.getProductType()) {
            case "cloud":
                return "10000000";
            case "entp":
                return "10000043";
            case "baidu04":
                return "10000004";
            case "baidu05":
                return "10000005";
            case "baidu06":
                return "10000006";
            case "baidu07":
                return "10000007";
            case "baidu08":
                return "10000008";
            case "shougou":
                return "10000010";
            default:
                return "10000000";

        }
    }

    /**
     * 获取市场渠道名
     *
     * @return
     */
    public static String getChannelIda() {
        String appType = "10000000";
        try {
            ApplicationInfo appInfo = LotteryApp.getLotteryApp().getPackageManager()
                    .getApplicationInfo(LotteryApp.getLotteryApp().getPackageName(),
                            PackageManager.GET_META_DATA);
            appType = String.valueOf(appInfo.metaData.get("UMENG_CHANNEL"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return appType;
    }

    public static int OBTAINCODE = 1123;//手机号不存在
    public static int OBTAINCODEEXIST = 1124;//手机号存在
    public static int THIRD_UID = 2000000;//第三方登录，虚拟uid
    public static int FULLY_OPEN = 1;                     //完全公开
    public static int OPEN_AFTER_FOLLOW = 2;              //跟单可见
    public static int OPEN_AFTER_DEADLINE = 3;            //截止可见
    public static int OPEN_AFTER_FOLLOW_AND_DEADLINE = 4; //跟单截止可见

    public static int TOKEN_YES = 6;    //token过期
    public static int TOKEN_NO = 17;    //token没有过期


}
