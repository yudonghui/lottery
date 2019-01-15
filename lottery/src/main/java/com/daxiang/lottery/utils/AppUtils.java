package com.daxiang.lottery.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AppUtils
{

    public static String RELEASE_CHANNEL = "";

    public static void setReleaseChannel(Context context)
    {
        RELEASE_CHANNEL = getChannel(context);
    }

    public static String getReleaseChannel()
    {
        if (TextUtils.isEmpty(RELEASE_CHANNEL))
        {
            return "";
        }
        return RELEASE_CHANNEL;
    }

    public static boolean needCostWarn(Context context)
    {
        return getChannel(context).startsWith("204");
    }

    public static String getChannel(Context context)
    {
        try
        {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            if (applicationInfo.metaData.containsKey("UMENG_CHANNEL"))
            {
                return applicationInfo.metaData.get("UMENG_CHANNEL").toString();
            }
        }
        catch (NameNotFoundException e)
        {
        }
        return "unknown";
    }

    public static String getCorpName(Context context)
    {
        String packageName = context.getPackageName();
        String[] subs = packageName.split("\\.");
        if (subs.length > 1)
        {
            return subs[1];
        }
        else
        {
            return "";
        }
    }

    public static String getAppName(Context context)
    {
        String packageName = context.getPackageName();
        String[] subs = packageName.split("\\.");
        if (subs.length > 2)
        {
            return subs[2];
        }
        else
        {
            return "";
        }
    }

    public static String getVersionName(Context context)
    {
        String packageName = context.getPackageName();
        try
        {
            return context.getPackageManager().getPackageInfo(packageName, 0).versionName;
        }
        catch (NameNotFoundException e)
        {
            return "";
        }
    }

    public static int getVersionCode(Context context)
    {
        String packageName = context.getPackageName();
        try
        {
            return context.getPackageManager().getPackageInfo(packageName, 0).versionCode;
        }
        catch (NameNotFoundException e)
        {
            return 0;
        }
    }

    /*public static String getAppInfo(Context context)
    {
        AppInfo appInfo = new AppInfo(DeviceUtils.getID(context), Build.MANUFACTURER, Build.BOARD, Build.MODEL, 10,
                getVersionCode(context), DeviceUtils.getAndroidSdkVersionCode(), NetworkManager.getIPAddress(),
                NetworkManager.getMacAddress(), DeviceUtils.getImei(context), DeviceUtils.getPhoneNumber(context),
                getOriginalChannel(context), getChannel(context), NetworkManager.getNetworkType());
        LogUtils.info("wangjianwei:" + appInfo);
        return EncryptUtils.easyEncrypt(appInfo.toString(), "caixiang", '*');
    }*/

    public static boolean isFirstInstalled(Context context)
    {
        return TextUtils.isEmpty(getOriginalChannel(context));
    }

    public static String getOriginalChannel(Context context)
    {
        try
        {
            Scanner scanner = new Scanner(new File(DirManager.getLogCachePath() + "/channel"));
            String result = scanner.next();
            scanner.close();
            return result;
        }
        catch (Exception e)
        {
            return "";
        }
    }

    public static void saveChannel(Context context)
    {
        try
        {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            if (applicationInfo.metaData.containsKey("UMENG_CHANNEL"))
            {
                String channel = applicationInfo.metaData.get("UMENG_CHANNEL").toString();
                try
                {
                    FileWriter writer = new FileWriter(new File(DirManager.getLogCachePath() + "/channel"));
                    writer.write(channel);
                    writer.close();
                }
                catch (IOException e)
                {
                }
            }
        }
        catch (NameNotFoundException e)
        {
        }
    }
}
