package com.daxiang.lottery.forum.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2018/8/2.
 */

public class ActivityCollector {
    private static List<Activity> activityList = new ArrayList<>();

    /** 添加一个Activity到集合中*/
    public static void addActivity(Activity activity){
        activityList.add(activity);
    }

    /** 从集合中删除一个Activity*/
    public static void removeActivity(Activity activity){
        activityList.remove(activity);
    }

    /**获取Activity栈中的栈顶的Activity
     * 需要注意的是，栈是先进后出，所以最上面的Activity是集合中的最后一个*/
    public static Activity getTopActivity(){
        if (activityList.isEmpty()){  //Activity栈为空
            return null;
        }else {  //不为空时
            return activityList.get(activityList.size() - 1);
        }
    }
}
