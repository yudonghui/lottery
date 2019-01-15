package com.daxiang.lottery.common;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.daxiang.lottery.R;
import com.daxiang.lottery.utils.MathUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Android on 2017/12/21.
 */

public class ComputeZhuShu {
    public long compute(Context mContext, String lotcode, String playMethod,
                        Map<Integer, List<String>> mapBall,
                        boolean isDan, int minNum, int minBlueNum,
                        int lvNum, MathUtils mathUtils, TextView mTextRemark) {
        if ("23529".equals(lotcode)) {
            //大乐透
            return groupBallDlt(mapBall, isDan, minNum, minBlueNum, mathUtils);
        } else if ("51".equals(lotcode)) {
            //双色球
            return groupBallSsq(mapBall, isDan, minNum, minBlueNum, mathUtils);
        } else if ("35".equals(lotcode) || "10022".equals(lotcode)) {
            //排列五和七星彩
            return groupBallWQ(mapBall, lvNum);
        } else if ("23528".equals(lotcode)) {
            //七乐彩
            return groupBallQL(mapBall, isDan, minNum, minBlueNum, mathUtils);
        } else if ("33".equals(lotcode) || "52".equals(lotcode)) {
            if ("1:1".equals(playMethod)) {
                return groupBallWQ(mapBall, lvNum);
            } else if ("3:3".equals(playMethod)) {
                return groupBallQL(mapBall, isDan, minNum, minBlueNum, mathUtils);
            } else if ("2:3".equals(playMethod)) {
                //组三（算法不同）
                return groupBallThree(mapBall, minNum);
            }
        } else if ("21406".equals(lotcode)) {
            if ("09:01".equals(playMethod) || "10:01".equals(playMethod)) {
                return groupBallWQ(mapBall, lvNum);
            } else {
                return groupBallQL(mapBall, isDan, minNum, minBlueNum, mathUtils);
            }
        } else if ("36".equals(lotcode)) {
            return groupJxks(mContext, playMethod, mapBall, mathUtils, mTextRemark);
        } else if ("50".equals(lotcode)) {
            return groupCqss(mContext, playMethod, mapBall, mathUtils, mTextRemark);
        }
        return 0;
    }

    private long groupBallDlt(Map<Integer, List<String>> mapBall, boolean isDan, int minNum, int minBlueNum, MathUtils mathUtils) {
        long sumGroup = 0;
        if (isDan) {
            if (mapBall.get(0) != null && mapBall.get(1) != null && mapBall.get(3) != null) {
                int red1 = mapBall.get(0).size();
                int red2 = mapBall.get(1).size();
                int blue1 = 0;
                if (mapBall.get(2) != null) {
                    blue1 = mapBall.get(2).size();
                }
                int blue2 = mapBall.get(3).size();
                if (red1 >= 1 && red2 >= 2 && blue2 >= 2 && red1 + red2 > minNum && blue1 + blue2 >= minBlueNum) {
                    long redGroup = mathUtils.C(red2, minNum - red1);
                    long blueGroup = mathUtils.C(blue2, minBlueNum - blue1);
                    sumGroup = redGroup * blueGroup;
                } else {
                    sumGroup = 0;
                }
            }
        } else {
            if (mapBall.size() == 2) {
                if (mapBall.get(0).size() >= minNum && mapBall.get(1).size() >= minBlueNum) {
                    long redGroup = mathUtils.C(mapBall.get(0).size(), minNum);
                    long blueGroup = mathUtils.C(mapBall.get(1).size(), minBlueNum);
                    sumGroup = redGroup * blueGroup;

                } else {
                    sumGroup = 0;
                }
            } else {
                sumGroup = 0;
            }
        }
        return sumGroup;
    }

    private long groupBallSsq(Map<Integer, List<String>> mapBall, boolean isDan, int minNum, int minBlueNum, MathUtils mathUtils) {
        long sumGroup = 0;
        if (isDan) {
            if (mapBall.size() == 3) {
                int red1 = mapBall.get(0).size();
                int red2 = mapBall.get(1).size();
                int blue = mapBall.get(2).size();
                if (red1 >= 1 && red2 >= 2 && blue >= 1 && red1 + red2 > minNum) {
                    long redGroup = mathUtils.C(red2, minNum - red1);
                    long blueGroup = mathUtils.C(blue, minBlueNum);
                    sumGroup = redGroup * blueGroup;
                } else {
                    sumGroup = 0;
                }
            }
        } else {
            if (mapBall.size() == 2) {
                if (mapBall.get(0).size() >= minNum && mapBall.get(1).size() >= minBlueNum) {
                    long redGroup = mathUtils.C(mapBall.get(0).size(), minNum);
                    long blueGroup = mathUtils.C(mapBall.get(1).size(), minBlueNum);
                    sumGroup = redGroup * blueGroup;
                } else {
                    sumGroup = 0;
                }
            } else {
                sumGroup = 0;
            }
        }
        return sumGroup;
    }

    private long groupBallWQ(Map<Integer, List<String>> mapBall, int lvNum) {
        long sumGroup = 0;
        //排列五和七星彩,监控
        if (mapBall.size() == lvNum) {
            //每次点击球都进行初始化
            sumGroup = 1;
            for (int i = 0; i < lvNum; i++) {
                sumGroup *= mapBall.get(i).size();
            }
        } else {
            sumGroup = 0;
        }
        return sumGroup;
    }

    private long groupBallQL(Map<Integer, List<String>> mapBall, boolean isDan, int minNum, int minBlueNum, MathUtils mathUtils) {
        long sumGroup = 0;
        if (isDan) {
            if (mapBall.size() == 2) {
                if (mapBall.get(0).size() >= 1 && mapBall.get(1).size() + mapBall.get(0).size() > minNum) {
                    sumGroup = mathUtils.C(mapBall.get(1).size(), minNum - mapBall.get(0).size());
                } else {
                    sumGroup = 0;
                }
            }
        } else {
            if (mapBall.get(0).size() >= minNum) {
                sumGroup = mathUtils.C(mapBall.get(0).size(), minNum);
            } else {
                sumGroup = 0;
            }
        }
        return sumGroup;
    }

    private long groupBallThree(Map<Integer, List<String>> mapBall, int minNum) {
        long sumGroup = 0;
        if (mapBall.get(0).size() >= minNum) {
            MathUtils mathUtils = new MathUtils();
            sumGroup = mathUtils.zusan(mapBall.get(0).size(), minNum);
        } else {
            sumGroup = 0;
        }
        return sumGroup;
    }

    //江西快三
    private long groupJxks(Context mContext, String playMethod, Map<Integer, List<String>> mapBall, MathUtils mathUtils, TextView mTextRemark) {
        long sumGroup = 0;
        String award = "";//奖金
        String profit = "";//盈利
        if ("1".equals(playMethod)) {//和值
            if (mapBall.get(0) != null && mapBall.get(0).size() > 0) {
                sumGroup = mapBall.get(0).size();
                int maxValue = 0;
                int minValue = 1000;
                if (mapBall.get(0).size() > 1) {
                    for (int i = 0; i < mapBall.get(0).size(); i++) {
                        String s = mapBall.get(0).get(i);
                        int value = getAward(s);
                        if (value < minValue) minValue = value;
                        if (value > maxValue) maxValue = value;
                    }
                    award = minValue + "~" + maxValue;
                    profit = (minValue - sumGroup * 2) + "~" + (maxValue - sumGroup * 2);
                } else {
                    int award1 = getAward(mapBall.get(0).get(0));
                    award = award1 + "";
                    profit = award1 - sumGroup * 2 + "";
                }

            } else {
                sumGroup = 0;
            }
        } else if ("2".equals(playMethod)) {//三同号
            if (mapBall.get(0) != null) {
                sumGroup = mapBall.get(0).size();
                award = "240";
                profit = 240 - (int) sumGroup * 2 + "";
            } else {
                sumGroup = 0;
            }
        } else if ("3".equals(playMethod)) {//三不同号
            if (mapBall.get(0) != null) {
                sumGroup = mathUtils.C(mapBall.get(0).size(), 3);
                award = "40";//奖金
                profit = 40 - sumGroup * 2 + "";//盈利
            } else sumGroup = 0;
        } else if ("4".equals(playMethod)) {//三连号通选
            if (mapBall.get(0) != null) {
                sumGroup = mapBall.get(0).size();
                award = "10";
                profit = "8";
            } else {
                sumGroup = 0;
            }
        } else if ("5".equals(playMethod)) {//二同号单选 两行
            if (mapBall.size() >= 2) {
                sumGroup = mapBall.get(0).size() * mapBall.get(1).size();
                award = "80";//奖金
                profit = 80 - sumGroup * 2 + "";//盈利
            } else {
                sumGroup = 0;
            }
        } else if ("6".equals(playMethod)) {//二同号复选
            if (mapBall.get(0) != null) {
                sumGroup = mapBall.get(0).size();
                award = "15";//奖金
                profit = 15 - sumGroup * 2 + "";//盈利
            } else sumGroup = 0;
        } else if ("7".equals(playMethod)) {//二不同号
            if (mapBall.get(0) != null) {
                sumGroup = mathUtils.C(mapBall.get(0).size(), 2);
                if (sumGroup > 2) {
                    award = "8~24";
                    profit = (8 - sumGroup * 2) + "~" + (24 - sumGroup * 2);
                } else {
                    award = "8";
                    profit = 8 - sumGroup * 2 + "";
                }
            } else sumGroup = 0;
        } else if ("8".equals(playMethod)) {//三同号通选
            if (mapBall.get(0) != null) {
                sumGroup = mapBall.get(0).size();
                award = "40";
                profit = "38";
            } else {
                sumGroup = 0;
            }
        }
        if (sumGroup == 0) {
            mTextRemark.setVisibility(View.GONE);
        } else {
            mTextRemark.setVisibility(View.VISIBLE);
            String hint = "本方案若中奖，奖金" + award + "元，盈利" + profit + "元";
            SpannableString ss = new SpannableString(hint);
            int indexOf1 = hint.indexOf(award);
            int indexOf2 = hint.indexOf(profit);
            if (hint.contains("~")) {
                int sign1 = award.indexOf("~");
                int sign2 = profit.indexOf("~");
                String[] split = profit.split("\\~");

                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), indexOf1, indexOf1 + sign1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), indexOf1 + sign1 + 1, indexOf1 + award.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(split[0].contains("-") ? R.color.blue_txt : R.color.red_txt)), indexOf2, indexOf2 + sign2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(split[1].contains("-") ? R.color.blue_txt : R.color.red_txt)), indexOf2 + sign2 + 1, indexOf2 + profit.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            } else {
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), indexOf1, indexOf1 + award.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), indexOf2, indexOf2 + profit.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            mTextRemark.setText(ss);
        }
        return sumGroup;
    }

    private int getAward(String value) {
        switch (value) {
            case "3":
                return 240;
            case "4":
                return 80;
            case "5":
                return 40;
            case "6":
                return 25;
            case "7":
                return 16;
            case "8":
                return 12;
            case "9":
                return 10;
            case "10":
                return 9;
            case "11":
                return 9;
            case "12":
                return 10;
            case "13":
                return 12;
            case "14":
                return 16;
            case "15":
                return 25;
            case "16":
                return 40;
            case "17":
                return 80;
            case "18":
                return 240;
            default:
                return 0;

        }
    }

    //重庆时时彩
    private long groupCqss(Context mContext, String playMethod, Map<Integer, List<String>> mapBall, MathUtils mathUtils, TextView mTextRemark) {
        long sumGroup = 0;
        //String profit="";
        String award = "";//奖金
        String profit = "";//盈利
        if ("10:1".equals(playMethod)) {//大小单双
            if (mapBall.size() == 2 && mapBall.get(0).size() > 0 && mapBall.get(1).size() > 0) {
                sumGroup = 1;
                award = "4";
                profit = "2";
            } else {
                sumGroup = 0;
                award = "";
                profit = "";
            }
        } else if ("1:1".equals(playMethod)) {//一星直选
            if (mapBall.get(0) != null) {
                sumGroup = mapBall.get(0).size();
                award = "10";
                profit = 10 - (int) sumGroup * 2 + "";
            } else {
                sumGroup = 0;
                award = "";
                profit = "";
            }
        } else if ("2:1".equals(playMethod)) {//二星直选
            if (mapBall.size() == 0 || mapBall.size() < 2) {
                sumGroup = 0;
            } else {
                sumGroup = 1;
            }
            for (int i = 0; i < mapBall.size(); i++) {
                sumGroup = sumGroup * mapBall.get(i).size();
            }
            if (sumGroup == 0) {
                award = "";
                profit = "";
            } else {
                award = "100";
                profit = 100 - (int) sumGroup * 2 + "";
            }
        } else if ("3:1".equals(playMethod)) {//三星直选
            if (mapBall.size() == 0 || mapBall.size() < 3)
                sumGroup = 0;
            else
                sumGroup = 1;
            for (int i = 0; i < mapBall.size(); i++) {
                sumGroup = sumGroup * mapBall.get(i).size();
            }
            if (sumGroup == 0) {
                award = "";
                profit = "";
            } else {
                award = "1000";
                profit = 1000 - (int) sumGroup * 2 + "";
            }
        } else if ("5:1".equals(playMethod) || "9:1".equals(playMethod)) {//五星直选五星通选
            if (mapBall.size() == 0 || mapBall.size() < 5)
                sumGroup = 0;
            else
                sumGroup = 1;
            for (int i = 0; i < mapBall.size(); i++) {
                sumGroup = sumGroup * mapBall.get(i).size();
            }
            if (sumGroup == 0) {
                award = "";
                profit = "";
            } else {
                if ("5:1".equals(playMethod)) {
                    award = "100000";
                    profit = 100000 - (int) sumGroup * 2 + "";
                } else {
                    award = "20440";
                    profit = 20440 - (int) sumGroup * 2 + "";
                }
            }
        } else if ("6:1".equals(playMethod)) {//二星组选
            if (mapBall.get(0) != null) {
                sumGroup = mathUtils.C(mapBall.get(0).size(), 2);
                award = "50";
                profit = 50 - (int) sumGroup * 2 + "";
            } else {
                sumGroup = 0;
                award = "";
                profit = "";
            }
        } else if ("7:3".equals(playMethod)) {//三星组三
            if (mapBall.get(0) != null) {
                sumGroup = mathUtils.C(mapBall.get(0).size(), 2) * 2;
                award = "320";
                profit = 320 - (int) sumGroup * 2 + "";
            } else {
                sumGroup = 0;
                award = "";
                profit = "";
            }
        } else if ("8:3".equals(playMethod)) {//三星组六
            if (mapBall.get(0) != null) {
                sumGroup = mathUtils.C(mapBall.get(0).size(), 3);
                award = "160";
                profit = 160 - (int) sumGroup * 2 + "";
            } else {
                sumGroup = 0;
                award = "";
                profit = "";
            }
        }
        if (sumGroup == 0) {
            mTextRemark.setVisibility(View.GONE);
        } else {
            mTextRemark.setVisibility(View.VISIBLE);
            String hint = "本方案若中奖，奖金" + award + "元，盈利" + profit + "元";
            int indexOf1 = hint.indexOf(award);
            int indexOf2 = hint.indexOf(profit);
            SpannableString ss = new SpannableString(hint);
            ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_txt)), indexOf1, indexOf1 + award.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(profit.contains("-") ? R.color.blue_txt : R.color.red_txt)), indexOf2, indexOf2 + profit.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            mTextRemark.setText(ss);
        }
        return sumGroup;
    }
}
