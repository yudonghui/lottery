package com.daxiang.lottery.utils;


import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import com.daxiang.lottery.LotteryApp;
import com.daxiang.lottery.R;
import com.daxiang.lottery.entity.GameList;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/11/16 0016.
 */
public class IsWinnig {
    private static String[] homeFu = {"0:1", "0:2", "0:3", "0:4", "0:5", "1:2", "1:3", "1:4", "1:5", "2:3", "2:4", "2:5"};
    private static String[] homeSheng = {"1:0", "2:0", "2:1", "3:0", "3:1", "3:2", "4:0", "4:1", "4:2", "5:0", "5:1", "5:2"};
    private static String[] homePing = {"0:0", "1:1", "2:2", "3:3"};

    //返回true是中奖，false是未中奖
    public static boolean isWinning(GameList dataBean, String str, String mLottypeNum) {
        String score;
        if ("43".equals(mLottypeNum) || "1001".equals(mLottypeNum)) {
            score = dataBean.getScore();
        } else {
            score = dataBean.getFullScore();
        }
        //如果没有比分结果，那么说明还没有开奖。返回false
    /*    if (score == null || !score.contains(":")) {
            return false;
        }*/
        if (score == null || !Pattern.matches("\\d+:\\d+", score)) {
            return false;
        }
        String[] scoreSplit = score.split("\\:");
        int score1 = Integer.parseInt(scoreSplit[0]);
        int score2 = Integer.parseInt(scoreSplit[1]);
        String scoreHalf = dataBean.getHalfScore();

        String preScore = dataBean.getPresetScore();
        String let1 = dataBean.getLet();
        double let;
        if (TextUtils.isEmpty(let1) || "null".equals(let1)) {
            let = 0;
        } else let = Float.valueOf(let1);
        String[] split = str.split("\\:|\\(|\\)|\\{|\\}");
        if ("胜平负".equals(split[0])) {
            /*
            * 如果比赛结果比分是第一个数大于第二个，比较投注的是否为“主胜”
            * 是就中奖，否则未中奖
            * */
            if (score1 > score2) {
                return "主胜".equals(split[1]);
            } else if (score1 < score2) {
                return "主负".equals(split[1]);
            } else {
                return "主平".equals(split[1]);
            }
        } else if ("胜负".equals(split[0])) {
            //篮球和足球是相反的，比分第一个是客队，第二个是主队
            if (score1 < score2) {
                return "主胜".equals(split[1]);
            } else if (score1 > score2) {
                return "主负".equals(split[1]);
            } else {
                return "主平".equals(split[1]);
            }
        } else if ("让球胜平负".equals(split[0])) {
            if (let + score1 > score2) {
                return "让胜".equals(split[1]);
            } else if (let + score1 < score2) {
                return "让负".equals(split[1]);
            } else {
                return "让平".equals(split[1]);
            }
        } else if ("让分胜负".equals(split[0])) {
            if (score1 < score2 + let) {
                return "让胜".equals(split[1]);
            } else if (score1 > score2 + let) {
                return "让负".equals(split[1]);
            } else {
                return "让平".equals(split[1]);
            }
        } else if ("半全场".equals(split[0])) {
            String[] scoreHalfSplit = scoreHalf.split("\\:");
            int scoreHalf1 = Integer.parseInt(scoreHalfSplit[0]);
            int scoreHalf2 = Integer.parseInt(scoreHalfSplit[1]);
            if (scoreHalf1 > scoreHalf2) {
                if (score1 > score2) {
                    return "胜胜".equals(split[1]);
                } else if (score1 < score2) {
                    return "胜负".equals(split[1]);
                } else {
                    return "胜平".equals(split[1]);
                }
            } else if (scoreHalf1 < scoreHalf2) {
                if (score1 > score2) {
                    return "负胜".equals(split[1]);
                } else if (score1 < score2) {
                    return "负负".equals(split[1]);
                } else {
                    return "负平".equals(split[1]);
                }
            } else {
                if (score1 > score2) {
                    return "平胜".equals(split[1]);
                } else if (score1 < score2) {
                    return "平负".equals(split[1]);
                } else {
                    return "平平".equals(split[1]);
                }
            }
        } else if ("进球数".equals(split[0])) {
            if (split[1].contains("+")) {
                return (score1 + score2) >= 7;
            } else
                return (score1 + score2) == Integer.parseInt(split[1]);
        } else if ("猜比分".equals(split[0])) {
            if ("负其他".equals(split[1])) {
                return score1 < score2 && !(Arrays.asList(homeFu).contains(score));
            } else if ("胜其他".equals(split[1])) {
                return score1 > score2 && !(Arrays.asList(homeSheng).contains(score));
            } else if ("平其他".equals(split[1])) {
                return score1 == score2 && !(Arrays.asList(homePing).contains(score));
            } else
                return score.equals(split[1] + ":" + split[2]);
        } else if ("大小分".equals(split[0])) {
            if ((score1 + score2) > Float.parseFloat(preScore)) {
                return "大分".equals(split[1]);
            } else {
                return "小分".equals(split[1]);
            }
        } else {
            int scoreSub = score2 - score1;
            if (scoreSub > 0) {
                if (scoreSub < 6) {
                    return "主胜1-5".equals(split[1]);
                } else if (scoreSub < 11) {
                    return "主胜6-10".equals(split[1]);
                } else if (scoreSub < 16) {
                    return "主胜11-15".equals(split[1]);
                } else if (scoreSub < 21) {
                    return "主胜16-20".equals(split[1]);
                } else if (scoreSub < 26) {
                    return "主胜21-25".equals(split[1]);
                } else {
                    return "主胜26+".equals(split[1]);
                }
            } else {
                if (scoreSub > -6) {
                    return "客胜1-5".equals(split[1]);
                } else if (scoreSub > -11) {
                    return "客胜6-10".equals(split[1]);
                } else if (scoreSub > -16) {
                    return "客胜11-15".equals(split[1]);
                } else if (scoreSub > -21) {
                    return "客胜16-20".equals(split[1]);
                } else if (scoreSub > -26) {
                    return "客胜21-25".equals(split[1]);
                } else {
                    return "客胜26+".equals(split[1]);
                }
            }
        }

    }

    public static SpannableStringBuilder isWinning2(GameList dataBean, String str, String mLotCode) {
        SpannableStringBuilder ss = new SpannableStringBuilder(str);
        String[] aaaa = str.split("\\:|\\(|\\)|\\{|\\}");
        if ("大小分".equals(aaaa[0])) {
            String score = dataBean.getScore();
            String preScore = dataBean.getPresetScore();
            if (score == null || !Pattern.matches("\\d+:\\d+", score) || preScore == null) {
                ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.gray_txt)), 0, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            } else {
                String[] preScoreSplit = preScore.split("\\,");
                if (preScoreSplit.length == 1) {//出票的大小分是一个值，正常的判断是否中奖了。
                    if (IsWinnig.isWinning(dataBean, str, mLotCode)) {
                        ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.red_txt)), 0, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    } else {
                        ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.gray_txt)), 0, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    }
                } else {//出票的时候大小分发生了变化的情况
                    String[] scoreSplit = score.split("\\:");
                    int score1 = Integer.parseInt(scoreSplit[0]);
                    int score2 = Integer.parseInt(scoreSplit[1]);
                    if ("大分".equals(aaaa[1])) {
                        boolean n = false;//true表示中奖了。
                        //先设置成红色，哪个没有中让哪个变灰
                        ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.red_txt)), 0, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        for (int i = 0; i < preScoreSplit.length; i++) {
                            int indexOf = str.indexOf(preScoreSplit[i]);
                            if (score1 + score2 > Float.parseFloat(preScoreSplit[i])) {
                                //ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.red_txt)), indexOf, indexOf + preScoreSplit[i].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                                n = true;
                            } else {
                                ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.gray_txt)), indexOf, indexOf + preScoreSplit[i].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                            }
                        }
                        if (!n) {//如果没有中奖，把整个一行都让他变灰色
                            ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.gray_txt)), 0, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        }
                    } else if ("小分".equals(aaaa[1])) {
                        boolean n = false;//true表示中奖了。
                        //先设置成红色，哪个没有中让哪个变灰
                        ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.red_txt)), 0, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        for (int i = 0; i < preScoreSplit.length; i++) {
                            int indexOf = str.indexOf(preScoreSplit[i]);
                            if (score1 + score2 < Float.parseFloat(preScoreSplit[i])) {
                                //ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.red_txt)), indexOf, indexOf + preScoreSplit[i].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                                n = true;
                            } else {
                                ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.gray_txt)), indexOf, indexOf + preScoreSplit[i].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                            }
                        }
                        if (!n) {//如果没有中奖，把整个一行都让他变灰色
                            ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.gray_txt)), 0, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        }
                    }

                }
            }

        } else if ("让分胜负".equals(aaaa[0])) {
            String score = dataBean.getScore();
            String let = dataBean.getLet();
            if (score == null || !Pattern.matches("\\d+:\\d+", score) || let == null) {
                ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.gray_txt)), 0, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            } else {
                String[] letSplit = let.split("\\,");
                if (letSplit.length == 1) {//出票的大小分是一个值，正常的判断是否中奖了。
                    if (IsWinnig.isWinning(dataBean, str, mLotCode)) {
                        ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.red_txt)), 0, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    } else {
                        ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.gray_txt)), 0, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    }
                } else {//出票的时候大小分发生了变化的情况
                    String[] scoreSplit = score.split("\\:");
                    int score1 = Integer.parseInt(scoreSplit[0]);
                    int score2 = Integer.parseInt(scoreSplit[1]);

                    if ("让胜".equals(aaaa[1])) {
                        boolean n = false;//true表示中奖了。
                        //先设置成红色，哪个没有中让哪个变灰
                        ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.red_txt)), 0, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        for (int i = 0; i < letSplit.length; i++) {
                            int indexOf = str.indexOf(letSplit[i]);
                            if (score1 < score2 + Float.parseFloat(letSplit[i])) {
                                //ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.red_txt)), indexOf, indexOf + preScoreSplit[i].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                                n = true;
                            } else {
                                ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.gray_txt)), indexOf, indexOf + letSplit[i].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                            }
                        }
                        if (!n) {//如果没有中奖，把整个一行都让他变灰色
                            ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.gray_txt)), 0, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        }
                    } else if ("让负".equals(aaaa[1])) {
                        boolean n = false;//true表示中奖了。
                        //先设置成红色，哪个没有中让哪个变灰
                        ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.red_txt)), 0, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        for (int i = 0; i < letSplit.length; i++) {
                            int indexOf = str.indexOf(letSplit[i]);
                            if (score1 > score2 + Float.parseFloat(letSplit[i])) {
                                //ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.red_txt)), indexOf, indexOf + preScoreSplit[i].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                                n = true;
                            } else {
                                ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.gray_txt)), indexOf, indexOf + letSplit[i].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                            }
                        }
                        if (!n) {//如果没有中奖，把整个一行都让他变灰色
                            ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.gray_txt)), 0, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        }
                    } else {
                        boolean n = false;//true表示中奖了。
                        //先设置成红色，哪个没有中让哪个变灰
                        ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.red_txt)), 0, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        for (int i = 0; i < letSplit.length; i++) {
                            int indexOf = str.indexOf(letSplit[i]);
                            if (score1 == score2 + Float.parseFloat(letSplit[i])) {
                                //ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.red_txt)), indexOf, indexOf + preScoreSplit[i].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                                n = true;
                            } else {
                                ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.gray_txt)), indexOf, indexOf + letSplit[i].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                            }
                        }
                        if (!n) {//如果没有中奖，把整个一行都让他变灰色
                            ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.gray_txt)), 0, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        }
                    }
                }
            }
        } else {
            if (IsWinnig.isWinning(dataBean, str, mLotCode)) {
                ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.red_txt)), 0, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            } else {
                ss.setSpan(new ForegroundColorSpan(LotteryApp.getLotteryApp().getResources().getColor(R.color.gray_txt)), 0, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
        return ss;
    }

}
