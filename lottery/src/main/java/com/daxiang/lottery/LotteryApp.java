package com.daxiang.lottery;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;

import com.daxiang.lottery.activity.GodInfoActivity;
import com.daxiang.lottery.activity.lotteryactivity.NewsAdActivity;
import com.daxiang.lottery.activity.lotteryactivity.OrderFormActivity;
import com.daxiang.lottery.activity.lotteryactivity.TikuanRecordActivity;
import com.daxiang.lottery.activity.wonderfulactivity.LotteryResultActivity;
import com.daxiang.lottery.common.IntentSkip;
import com.daxiang.lottery.constant.Constants;
import com.daxiang.lottery.score.ScoreActivity;
import com.daxiang.lottery.utils.Logger;
import com.daxiang.lottery.utils.ProductUtil;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

import java.util.Map;

import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class LotteryApp extends MultiDexApplication {
    private static LotteryApp instance;
    //默认权限是不可购买，不可发起跟单（推荐）
    public static int buyPrivilege = 1;
    public static int recommendPrivilege = 1;
    public static String uid = "";
    public static boolean isLogin = false;
    public static boolean isThird = false;
    public static String token = "";
    public static String timeStamp;
    public static String bankName;
    public static IWXAPI api;
    public static boolean cardFlag;
    public static boolean bankFlag;
    public static boolean phoneFlag;
    public static String realName;
    public static String phone;
    public static String nikeName = "";
    public static String salt;
    //当打开app时候记录一下时间。
    public static long firstLogin;
    //判断是否有推广的权限
    public static String expand;
    //钱包余额
    public static String balance;
    //可用余额
    public static String consumeBanlance;
    //推广服务是否显示 0散户，1大户
    public static String userType = "0";

    private static LotteryApp mContext;

    {
        PlatformConfig.setWeixin("wx1ac7e4187017d73e", "3ce5c046a57206dc9974eab4dd9b50d8");
        PlatformConfig.setQQZone("100371282", "aed9b0303e3ed1e27bae87c33761161d");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Logger.setIsDebug(true);//false关闭log日志 true打开日志
        mContext = this;
        //APP产品类型
        //setType();

        MobclickAgent.enableEncrypt(true);//友盟 日志加密。加密模式可以有效防止网络攻击，提高数据安全性。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//Android7.0  file:///异常
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        Bmob.initialize(this, "2f65830a8426ff44ced6206fa7b24fca");
        x.Ext.init(this);
        initUmeng();
        api = WXAPIFactory.createWXAPI(this, null);
        api.registerApp(Constants.WX_APP_ID);
    }

    private void initUmeng() {
        UMShareAPI.get(this);
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "42a5d2380e9499791d59232efff8d320");
        UMConfigure.setLogEnabled(false);//友盟的log日志是否打印，默认是false不打印。
        //UMConfigure.init(this,"","",UMConfigure.DEVICE_TYPE_PHONE, "jmgbgqx9xvynwtl0udmuhdctixkogsr1");
  /*      if ("entp".equals(BuildConfig.FLAVOR)){//测试环境
            UMConfigure.init(this,"5ae1538e8f4a9d62960000f1","afff763640231c196e641367a7171e36", UMConfigure.DEVICE_TYPE_PHONE, "afff763640231c196e641367a7171e36");
        }else {//正式环境
            UMConfigure.init(this,"583252d0aed1793b02002264","42a5d2380e9499791d59232efff8d320", UMConfigure.DEVICE_TYPE_PHONE, "42a5d2380e9499791d59232efff8d320");
        }*/
        UMConfigure.init(this, "583252d0aed1793b02002264", "42a5d2380e9499791d59232efff8d320", UMConfigure.DEVICE_TYPE_PHONE, "42a5d2380e9499791d59232efff8d320");
        initPush();
    }

    private void initPush() {
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SERVER);
        // mPushAgent.setMergeNotificaiton(false);
        mPushAgent.setDisplayNotificationNumber(10);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Logger.e("设备token成功", deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e("设备token失败", s + "--" + s1);
            }
        });

        /**
         * 自定义行为的回调处理，参考文档：高级功能-通知的展示及提醒-自定义通知打开动作
         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {

            @Override
            public void launchApp(Context context, UMessage msg) {
                super.launchApp(context, msg);
                notifationSkip(context, msg);
            }

            @Override
            public void openUrl(Context context, UMessage msg) {
                super.openUrl(context, msg);
                notifationSkip(context, msg);
            }

            @Override
            public void openActivity(Context context, UMessage msg) {
                super.openActivity(context, msg);
                notifationSkip(context, msg);
            }

            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                notifationSkip(context, msg);

            }
        };
        //使用自定义的NotificationHandler
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
    }

    private void notifationSkip(Context context, UMessage msg) {
        Map<String, String> extra = msg.extra;
        Logger.e("消息", extra.toString());
        String type = extra.get("type");
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        switch (type) {
            case "recommend"://关注推荐通知
                String userId = extra.get("userId");
                if (!TextUtils.isEmpty(userId)) {
                    intent.setClass(context, GodInfoActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                }
                break;
            case "prize"://数字彩开奖通知
                //防止连续点击
                intent.setClass(context, LotteryResultActivity.class);
                startActivity(intent);
                break;
            case "result"://竞彩赛果/进球通知
                intent.setClass(context, ScoreActivity.class);
                startActivity(intent);
                break;
            case "win"://投注记录/订单详情
                intent.setClass(context, OrderFormActivity.class);
                intent.putExtra("type", 0);//0跳转到
                // 投注列表 4跳转到追号列表
                startActivity(intent);
                break;
            case "withdraw"://提款记录
                intent.setClass(context, TikuanRecordActivity.class);
                startActivity(intent);
                break;
            case "cancel"://公告通知,比赛取消通知
                intent.setClass(context, NewsAdActivity.class);
                intent.putExtra("newsOrAd", "news");
                startActivity(intent);
                break;
            case "notice"://公告通知,网站通知
                intent.setClass(context, NewsAdActivity.class);
                intent.putExtra("newsOrAd", "news");
                startActivity(intent);
                break;
            case "hot"://热门比赛。彩种跳转。
                String lotCode = extra.get("lotCode");
                if (!TextUtils.isEmpty(lotCode)) {
                    new IntentSkip().skipLotcode(context, lotCode);
                }
                break;
        }
    }

    public static Context getLotteryApp() {
        return mContext;
    }

    private void setType() {
        switch (BuildConfig.FLAVOR) {
            case "cloud":
                ProductUtil.setProductType("cloud");
                break;
            case "baidu04":
                ProductUtil.setProductType("baidu04");
                break;
            case "baidu05":
                ProductUtil.setProductType("baidu05");
                break;
            case "baidu06":
                ProductUtil.setProductType("baidu06");
                break;
            case "baidu07":
                ProductUtil.setProductType("baidu07");
                break;
            case "baidu08":
                ProductUtil.setProductType("baidu08");
                break;
            case "entp":
                ProductUtil.setProductType("entp");
                break;
        }
    }
    public static LotteryApp getInstance() {
        return instance;
    }
}
