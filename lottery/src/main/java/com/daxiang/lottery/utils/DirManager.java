package com.daxiang.lottery.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

import java.io.File;

public class DirManager
{
    private static Context sAppContext;

    public static void init(Context context)
    {
        sAppContext = context;
    }

    private final static String SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    private final static String SD_APP_PATH = SD_PATH + "/cxlottery";
    
    public static String CURRENT_CROP_AVATAR;
    
    public static File CROP_AVATAR_DIR;

    private static String getPrivateCachePath()
    {
      
        return sAppContext.getCacheDir().getAbsolutePath();

    }

    private static String getPrivateFilesPath()
    {
     
        return sAppContext.getFilesDir().getAbsolutePath();
    }

    @SuppressLint("SdCardPath")
    public static String getCachePath()
    {
        try
        {
            if (hasExternalStorage())
            {
      
                return sAppContext.getExternalCacheDir().getAbsolutePath();
            }
            else
            {
                return getPrivateCachePath();
            }
        }
        catch (Exception e)
        {
            // TODO: handle exception
            //LogUtils.error("wangjianwei" + e);
        }

        return "/data/data/" + sAppContext.getPackageName() + "/cache";
    }

    public static String getFilesPath()
    {
        try
        {
            if (hasExternalStorage())
            {
                return sAppContext.getExternalFilesDir(null).getAbsolutePath();
            }
            else
            {
                return getPrivateFilesPath();
            }
        }
        catch (Exception e)
        {
            // TODO: handle exception
            //LogUtils.error("wangjianwei" + e);
        }// /storage/emulated/0/Android/data/com.daxiang.lottery/files
        return "/storage/emulated/0/Android/data/" + sAppContext.getPackageName() + "/files";
    }

    public static String getAppPath()
    {
        if (hasExternalStorage())
        {
            return SD_APP_PATH;
        }
        else
        {
            return getPrivateFilesPath();
        }
    }

    public static String getImageCachePath()
    {
        return getCachePath() + "/image";
    }
    
    public static String getAvatarCachePath()
    {
        return getCachePath()+"/avatar/";
    }

    public static String getLogCachePath()
    {
        return getAppPath() + "/log";
    }

    public static String getDownloadPath()
    {
        return getAppPath() + "/download";
    }

    private static boolean hasExternalStorage()
    {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

}
