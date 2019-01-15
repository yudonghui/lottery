package com.daxiang.lottery.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/27 0027.
 */
public class DateFormtUtils {
    public static String YMD_HMS = "yyyy-MM-dd HH:mm:ss";
    public static String YMD_HM = "yyyy-MM-dd HH:mm";
    public static String MD_HMS = "MM-dd HH:mm:ss";
    public static String MD_HM = "M-dd HH:mm";

    public static int[] obtainTime(long time) {
        int[] times = new int[4];
        int i1 = (int) (time / 1000);
        int i2 = i1 / 60;
        int i3 = i2 / 60;
        int i4 = i3 / 24;
        int second = i1 % 60;
        int minute = i2 % 60;
        int hour = i3 % 24;
        int day = i4;
        times[0] = day;
        times[1] = hour;
        times[2] = minute;
        times[3] = second;
        return times;
    }

    public static String longToDate(long date) {
        if (date == 0) return "--";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        return simpleDateFormat.format(date);
    }

    public static String strByDate(Date date) {
        if (date == null) return "--";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    public static String dateByDate(String year, String month, String day) {
        String yearStr = year.substring(0, year.length() - 1);
        String monthStr = month.substring(0, month.length() - 1);
        String dayStr = day.substring(0, day.length() - 1);

        if (Integer.parseInt(monthStr) < 10) {
            monthStr = "0" + monthStr;
        }
        if (Integer.parseInt(dayStr) < 10) {
            dayStr = "0" + dayStr;
        }
        return yearStr + "-" + monthStr + "-" + dayStr;
    }

    /**
     * 获取一个月前的日期
     *
     * @param date 传入的日期
     * @return
     */
    public static String getMonthAgo(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        String monthAgo = simpleDateFormat.format(calendar.getTime());
        return monthAgo;
    }

    public static boolean isTimeRange(boolean isJz) {
        Date curDates = new Date();// 获取当前时间
        Calendar cal = Calendar.getInstance();
        cal.setTime(curDates);
        int i1 = cal.get(Calendar.DAY_OF_WEEK);

        SimpleDateFormat formatters = new SimpleDateFormat("HH:mm");
        String strs = formatters.format(curDates);
        // 分取系统时间 小时分
        String[] dds = strs.split(":");
        int dhs = Integer.parseInt(dds[0]);
        int dms = Integer.parseInt(dds[1]);

        if (isJz) {//竞彩足球
            if (i1 <= 5 && i1 > 1) {//周二到周五
                if (9 <= dhs && dhs <= 23) {
                    return true;
                } else {
                    return false;
                }
            } else if (i1 == 1 || i1 == 0) {//周一周日  早上0点到0点59分，9点到23点59分
                if ((dhs >= 0 && dhs < 1) || (dhs <= 23 && dhs >= 9)) {
                    return true;
                } else return false;
            } else if (i1 == 6) {//周六周日
                if (9 <= dhs && dhs <= 23) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {//竞彩篮球
            if (i1 == 1 || i1 == 0) {
                if ((dhs >= 0 && dhs < 1) || (dhs <= 23 && dhs >= 9)) {
                    return true;
                } else return false;
            } else if (i1 == 2 || i1 == 5) {//周二周五
                if (9 <= dhs && dhs <= 23) {
                    return true;
                } else {
                    return false;
                }
            } else if (i1 == 6) {//周六周日
                if (9 <= dhs && dhs <= 23) {
                    return true;
                } else {
                    return false;
                }
            } else {//周三周四
                if (dhs == 7) {
                    if (dms > 30)
                        return true;
                    else return false;
                } else if (7 < dhs && dhs <= 23) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    static String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    public static String forecastDate(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        int i = cal.get(Calendar.DAY_OF_WEEK);
        return weekDays[i - 1];
    }
    // 将年月日转换成星期
    public static String addMatch(String split) {
        String substring = split.substring(0, 8);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
            Date parse = format.parse(substring);
            Calendar cal = Calendar.getInstance();
            cal.setTime(parse);
            int i1 = cal.get(Calendar.DAY_OF_WEEK);
            String substring1 = split.substring(8, 11);
            return weekDays[i1 - 1] + substring1;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String dateByLong(long time) {
        if (time == 0) return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return simpleDateFormat.format(cal.getTime());
    }

    public static long getLongByDate(String string) {
        if (TextUtils.isEmpty(string)) return 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds)));
    }

    public static String timeDifference(long beforeTime) {
        if (beforeTime == 0) return "";
        long currentTime = System.currentTimeMillis();
        long difTime = (currentTime - beforeTime) / 1000;
        if (difTime <= 60) {
            return "1分钟前";
        } else if (difTime > 60 && difTime <= 3600) {
            int time = (int) (difTime / 60);
            return time + "分钟前";
        } else if (difTime > 3600 && difTime <= 86400) {
            int time = (int) (difTime / 3600);
            return time + "小时前";
        } else if (difTime > 86400 && difTime <= 2538000) {
            int time = (int) (difTime / 86400);
            return time + "天前";
        }
        if (difTime > 2538000 && difTime <= 31536000) {
            int time = (int) (difTime / 2538000);
            return time + "月前";
        } else {
            int time = (int) (difTime / 31536000);
            return time + "年前";
        }

    }
}
