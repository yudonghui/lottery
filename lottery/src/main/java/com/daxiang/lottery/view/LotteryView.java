package com.daxiang.lottery.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author yudonghui
 * @date 2017/7/7
 * @describe May the Buddha bless bug-free!!!
 */
public class LotteryView extends TextView {
    public LotteryView(Context context) {
        super(context);
    }

    public LotteryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LotteryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setContent(String lotcode, String mBuyContent, String AwardContent) {

    }

    public void setContent(String lotcode, String mBuyContent) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] split3 = mBuyContent.split("\\;");
        for (int i = 0; i < split3.length; i++) {
            String[] split = split3[i].split("\\:");
            String typeToCN = getTypeToCN(split[1] + ":" + split[2], lotcode);
            stringBuilder.append(typeToCN);
            String string = split[0].replaceAll(",", " ");
            if (string.contains("|") && string.contains("$")) {//大乐透或者双色球或者七星彩
                String[] split2 = string.split("\\||\\$");
                if (split2.length == 4)
                    stringBuilder.append("胆码:" + split2[0] + "拖码:" + split2[1] + " | 胆码:" + split2[2] + "拖码:" + split2[3]);
                else
                    stringBuilder.append("胆码:" + split2[0] + "拖码:" + split2[1] + " | " + split2[2]);
            } else if (string.contains("|")) {
                String[] split2 = string.split("\\|");
                stringBuilder.append(split2[0] + "|" + split2[1]);
            } else {
                stringBuilder.append(string);
            }
            if (i < split3.length - 1) stringBuilder.append("\n");
        }
        super.setText(stringBuilder);

    }

    public String getTypeToCN(String type, String lotcode) {
        String playMethod = "";
        switch (lotcode) {
            case "23529"://大乐透
                if ("1:1".equals(type) || "135:5".equals(type)) playMethod = "[普通]";
                else if ("2:1".equals(type) || "135:1".equals(type)) playMethod = "[追加]";
                break;
            case "11"://胜负彩
            case "35"://排列五
            case "10022"://七星彩
            case "23528"://七乐彩
            case "51"://双色球
            case "19"://任九场
                playMethod = "";
                break;
            case "52"://福彩3D
            case "33"://排列三
                if ("1:1".equals(type)) playMethod = "[直选]";
                else if ("2:3".equals(type)) playMethod = "[组三]";
                else if ("3:3".equals(type)) playMethod = "[组六]";
                break;
            case "21406"://十一运夺金
                if ("01:01".equals(type)) playMethod = "[任选一]";
                else if ("02:01".equals(type)) playMethod = "[任选二]";
                else if ("03:01".equals(type)) playMethod = "[任选三]";
                else if ("04:01".equals(type)) playMethod = "[任选四]";
                else if ("05:01".equals(type)) playMethod = "[任选五]";
                else if ("06:01".equals(type)) playMethod = "[任选六]";
                else if ("07:01".equals(type)) playMethod = "[任选七]";
                else if ("08:01".equals(type)) playMethod = "[任选八]";
                else if ("09:01".equals(type)) playMethod = "[前二直选]";
                else if ("10:01".equals(type)) playMethod = "[前三直选]";
                else if ("11:01".equals(type)) playMethod = "[前二组选]";
                else if ("12:01".equals(type)) playMethod = "[前三组选]";
                break;

        }
        return playMethod;
    }
}
