package com.daxiang.lottery.common;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public class JcPlayMethod {
    static String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    public static String getPlayMethodJl(String str) {
        String playMethod;
        switch (str) {
            case "主胜":
                playMethod = "SF";
                break;
            case "客胜":
                playMethod = "SF";
                break;
            case "主胜[让]":
                playMethod = "RFSF";
                break;
            case "客胜[让]":
                playMethod = "RFSF";
                break;
            case "大分":
                playMethod = "DXF";
                break;
            case "小分":
                playMethod = "DXF";
                break;
            default:
                playMethod = "SFC";
        }
        return playMethod;
    }

    public static String getPlayMethod(String str) {
        String playMethod;
        switch (str) {
            case "主胜":
                playMethod = "SPF";
                break;
            case "平":
                playMethod = "SPF";
                break;
            case "主负":
                playMethod = "SPF";
                break;
            case "让胜":
                playMethod = "RQSPF";
                break;
            case "让平":
                playMethod = "RQSPF";
                break;
            case "让负":
                playMethod = "RQSPF";
                break;
            case "胜胜":
                playMethod = "BQC";
                break;
            case "胜平":
                playMethod = "BQC";
                break;
            case "胜负":
                playMethod = "BQC";
                break;
            case "平平":
                playMethod = "BQC";
                break;
            case "平胜":
                playMethod = "BQC";
                break;
            case "平负":
                playMethod = "BQC";
                break;
            case "负胜":
                playMethod = "BQC";
                break;
            case "负平":
                playMethod = "BQC";
                break;
            case "负负":
                playMethod = "BQC";
                break;
            case "0":
                playMethod = "JQS";
                break;
            case "1":
                playMethod = "JQS";
                break;
            case "2":
                playMethod = "JQS";
                break;
            case "3":
                playMethod = "JQS";
                break;
            case "4":
                playMethod = "JQS";
                break;
            case "5":
                playMethod = "JQS";
                break;
            case "6":
                playMethod = "JQS";
                break;
            case "7+":
                playMethod = "JQS";
                break;
            default:
                playMethod = "CBF";
        }
        return playMethod;
    }

    public static String getFormatIssue(String issue, String mid) {
        if (TextUtils.isEmpty(issue)) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = sdf.parse(issue);
            Calendar cal = Calendar.getInstance();
            cal.setTime(parse);
            int i1 = cal.get(Calendar.DAY_OF_WEEK);
            //设置比赛场次的编号
            String substring = mid.substring(8, 11);
            return weekDays[i1 - 1] + " " + substring;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
