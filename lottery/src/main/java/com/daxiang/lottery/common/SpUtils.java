package com.daxiang.lottery.common;

import android.app.Activity;
import android.content.SharedPreferences;

import com.daxiang.lottery.LotteryApp;

/**
 * Created by Android on 2018/5/18.
 */

public class SpUtils {
    public static String SALE_FLAG="saleFlag";
    public static String IS_BUY="isBuy";
    public static int getBuyPrivilege() {
        SharedPreferences sp = LotteryApp.getLotteryApp().getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        return  sp.getInt(IS_BUY, 0);//1审核中，0审核完成
    }
    public static void setBuyPrivilege(String key,String value){
        SharedPreferences sp = LotteryApp.getLotteryApp().getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }
    public static void setBuyPrivilege(String key,int value){
        SharedPreferences sp = LotteryApp.getLotteryApp().getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.commit();
    }
    public static void setString(String key,String value){
        SharedPreferences sp = LotteryApp.getLotteryApp().getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }
    public static void setInt(String key,int value){
        SharedPreferences sp = LotteryApp.getLotteryApp().getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.commit();
    }
    public static String getString(String key){
        SharedPreferences sp = LotteryApp.getLotteryApp().getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        String string = sp.getString(key,"");
        return string;
    }
    public static int getInt(String key){
        SharedPreferences sp = LotteryApp.getLotteryApp().getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        int string = sp.getInt(key,0);
        return string;
    }
}
