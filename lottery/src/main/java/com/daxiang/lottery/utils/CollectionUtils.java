package com.daxiang.lottery.utils;


import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.entity.OrderFormDetailData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/11 0011.
 */
public class CollectionUtils {
    private static Map<String, List<StringBuilder>> map = new HashMap<>();
    //存胆的期号
    private static ArrayList<String> danList = new ArrayList<>();
    private static Map<String, OrderFormDetailData.DataBean> mapSort = new HashMap<>();
    private static String playsResult;

    //对已经封装好的填充控件用的集合进行排序
    private static List<Map.Entry<OrderFormDetailData.DataBean, ArrayList<String>>> orderMap(HashMap<OrderFormDetailData.DataBean, ArrayList<String>> widgeMap) {
        //这里将map.entrySet()转换成list
        List<Map.Entry<OrderFormDetailData.DataBean, ArrayList<String>>> list = new ArrayList<>(widgeMap.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<OrderFormDetailData.DataBean, ArrayList<String>>>() {
            @Override
            public int compare(Map.Entry<OrderFormDetailData.DataBean, ArrayList<String>> lhs, Map.Entry<OrderFormDetailData.DataBean, ArrayList<String>> rhs) {
                return lhs.getKey().getSession().compareTo(rhs.getKey().getSession());
            }
        });

        return list;
    }

    public static List<Map.Entry<OrderFormDetailData.DataBean, ArrayList<String>>> table(String mBuycontent, List<OrderFormDetailData.DataBean> mData) {
        mapSort.clear();
        danList.clear();
        map.clear();
        for (int m = 0; m < mData.size(); m++) {
            mapSort.put(mData.get(m).getSession(), mData.get(m));
        }
        String[] strTimes = mBuycontent.split("\\|");
        /**
         * 创建一个map集合，键是选中的实体类，
         * 里面包含有比赛的比分，等详细信息。值是拼接list集合
         * 用于存放每一期比赛选择玩法拼接的字符串。
         * */
        HashMap<OrderFormDetailData.DataBean, ArrayList<String>> widgeMap = new HashMap<>();
        //如果不是混合
        if (!strTimes[0].equals("HH")) {
            //如果不是混合的话前面就是玩法的标志
            String playMethod = LotteryTypes.getPlayMethod(strTimes[0]);
            //获取“|”中间的字符串
            String[] split1 = strTimes[1].split("\\,");
            //循环场次
            for (int i = 0; i < split1.length; i++) {

                String keyMid = null;
                ArrayList<String> widgeList = new ArrayList<>();
                String[] split3 = split1[i].split("\\=|\\/");
                keyMid = split3[0];
                for (int j = 1; j < split3.length; j++) {
                    StringBuilder mStringBuilder = new StringBuilder();
                    mStringBuilder.append(playMethod);
                    if ("RQSPF".equals(strTimes[0]) || "RFSF".equals(strTimes[0])) {
                        String[] split = split3[j].split("\\{");
                        playsResult = LotteryTypes.getRqspfStr(split[0]) + "{" + split[1];
                    } else {
                        String[] split = split3[j].split("\\(");
                        if ("BQC".equals(strTimes[0])) {
                            playsResult = LotteryTypes.getBqcStr(split[0]) + "(" + split[1];
                        } else if ("SPF".equals(strTimes[0]) || "SF".equals(strTimes[0])) {
                            playsResult = LotteryTypes.getSpf(split[0]) + "(" + split[1];
                        } else if ("DXF".equals(strTimes[0])) {
                            playsResult = LotteryTypes.getDxf(split[0]) + "(" + split[1];
                        } else if ("JQS".equals(strTimes[0])) {
                            if ("7".equals(split[0])) {
                                playsResult = "7+(" + split1[1];
                            }else {
                                playsResult = split[0] + "(" + split[1];
                            }
                        } else if("SFC".equals(strTimes[0])){
                            playsResult=LotteryTypes.getSfcStr(split[0])+"("+split[1];
                        }else {
                            if ("9:0".equals(split[0])) {
                                playsResult = "胜其他(" + split1[1];
                            } else if ("9:9".equals(split[0])) {
                                playsResult = "平其他(" + split1[1];
                            } else if ("0:9".equals(split[0])) {
                                playsResult = "负其他(" + split1[1];
                            } else {
                                playsResult = split[0] + "(" + split[1];
                            }
                        }
                    }
                    mStringBuilder.append(playsResult);
                    widgeList.add(String.valueOf(mStringBuilder));
                }
                widgeMap.put(mapSort.get(keyMid), widgeList);
                /*String[] wanfa = split1[i].split("\\/");
                //循环每场的玩法
                //例如：20160810002=1:0(60.00),20160810001=2:2(11.00)/1:2(9.00)/2:0(11.00)
                for (int j = 0; j < wanfa.length; j++) {
                    StringBuilder mStringBuilder = new StringBuilder();
                    mStringBuilder.append(playMethod);
                    if (j == 0) {
                        String[] split2 = wanfa[0].split("\\=|\\(|\\)");
                        keyMid = split2[0];
                        *//**
                 * 判断玩法后选择的结果是主胜负平还是别的
                 * 只有胜平负 让球胜平负 半全场需要判断
                 * 用不同的正则表达式进行匹配
                 * *//*
                        if ("BQC".equals(strTimes[0])) {
                            playsResult = LotteryTypes.getBqcStr(split2[1]);
                        } else if ("SPF".equals(strTimes[0]) || "SF".equals(strTimes[0])) {
                            playsResult = LotteryTypes.getSpf(split2[1]);
                        } else if ("RQSPF".equals(strTimes[0]) || "RFSF".equals(strTimes[0])) {
                            String[] split = split2[1].split("\\{");
                            playsResult = LotteryTypes.getRqspfStr(split[0]) + "{" + split[1];
                        } else if ("DXF".equals(strTimes[0])) {
                            playsResult = LotteryTypes.getDxf(split2[1]);
                        } else {
                            playsResult = split2[1];
                        }
                        mStringBuilder.append(playsResult);
                        mStringBuilder.append("(" + split2[2] + ")");
                    } else {
                        String[] split2 = wanfa[0].split("\\(|\\)");
                        if ("BQC".equals(strTimes[0]) || "RQSPF".equals(strTimes[0]) || "SPF".equals(strTimes[0]) || "RFSF".equals(strTimes[0]) || "SF".equals(strTimes[0])) {
                            boolean isBqc = Pattern.matches("(\\d)-(\\d)", split2[0]);
                            boolean isSpf = Pattern.matches("\\d", split2[0]);
                            if (isBqc) {
                                playsResult = LotteryTypes.getBqcStr(split2[0]);
                            } else if (isSpf) {
                                playsResult = LotteryTypes.getSpf(split2[0]);
                            }
                        } else if ("DXF".equals(strTimes[0])) {
                            playsResult = LotteryTypes.getDxf(split2[0]);
                        } else {
                            playsResult = split2[0];
                        }

                        mStringBuilder.append(playsResult);
                        mStringBuilder.append(split2[1]);
                    }
                    widgeList.add(String.valueOf(mStringBuilder));
                }
                widgeMap.put(mapSort.get(keyMid), widgeList);*/
            }
        } else {
            //混合
            //取两个|中间的字符串进行分割
            String[] dantuoSplit = strTimes[1].split("\\$");
            //$符号前面和后面进行循环
            for (int j = 0; j < dantuoSplit.length; j++) {
                String[] split = dantuoSplit[j].split("\\,");
                for (int i = 0; i < split.length; i++) {

                    String[] split1 = split[i].split("\\>|\\=");
                    //如果有胆，如果是$符号前面的也就是设胆的场，那么就将期号存起来
                    if (dantuoSplit.length > 1) {
                        if (j == 0) {
                            if (!danList.contains(split1[1])) {
                                danList.add(split1[1]);
                            }
                        }
                    }
                    //两种情况，一是让球二是不让球
                    if ("RQSPF".equals(split1[0]) || "RFSF".equals(split1[0])) {
                        //RQSPF>20161107005=1{-1}(4.05)/3{-1}(6.35)/0{-1}(1.38);
                        List<StringBuilder> list;
                        //判断map中是否有这个场次,有的话取出来，没有创建
                        if (map.containsKey(split1[1])) {
                            list = map.get(split1[1]);
                            map.remove(split1[1]);
                        } else {
                            list = new ArrayList<>();
                        }
                        //把等号后面的字符串进行分离
                        String[] split2 = split1[2].split("\\/");
                        for (int k = 0; k < split2.length; k++) {
                            String[] split3 = split2[k].split("\\{");
                            StringBuilder strBuilder = new StringBuilder();
                            strBuilder.append(LotteryTypes.getPlayMethod(split1[0]) + LotteryTypes.getRqSpf(split3[0]) + "{" + split3[1]);
                            list.add(strBuilder);
                        }
                        map.put(split1[1], list);

                    } else {
                        //判断map中是否有这个场次
                        if (map.containsKey(split1[1])) {
                            List<StringBuilder> list = map.get(split1[1]);
                            map.remove(split1[1]);
                            sdfdsd(split1, list);
                        } else {
                            List<StringBuilder> list = new ArrayList<>();
                            sdfdsd(split1, list);
                        }
                    }

                }
            }
            for (Map.Entry<String, List<StringBuilder>> entry : map.entrySet()) {
                String keyMid = entry.getKey();
                ArrayList<String> widgeList = new ArrayList<>();
                for (int j = 0; j < entry.getValue().size(); j++) {
                    widgeList.add(String.valueOf(entry.getValue().get(j)));
                }
                widgeMap.put(mapSort.get(keyMid), widgeList);
            }
        }
        List<Map.Entry<OrderFormDetailData.DataBean, ArrayList<String>>> entries = orderMap(widgeMap);
        return entries;
    }

    //分割选择比赛的内容
    private static void sdfdsd(String[] split1, List<StringBuilder> list) {
        //把等号后面的字符串进行分离
        String[] split2 = split1[2].split("\\/");
        for (int j = 0; j < split2.length; j++) {
            StringBuilder strBuilder = new StringBuilder();
            //先拼接玩法
            strBuilder.append(LotteryTypes.getPlayMethod(split1[0]));
            //拼接玩法结果
            String[] split3 = split2[j].split("\\(|\\)");
            if ("BQC".equals(split1[0])) {
                playsResult = LotteryTypes.getBqcStr(split3[0]);
            } else if ("SPF".equals(split1[0]) || "SF".equals(split1[0])) {
                playsResult = LotteryTypes.getSpf(split3[0]);
            } else if ("DXF".equals(split1[0])) {
                playsResult = LotteryTypes.getDxf(split3[0]);
            } else if ("JQS".equals(split1[0])) {
                if ("7".equals(split3[0])) {
                    playsResult = "7+" ;
                }else {
                    playsResult = split3[0];
                }
            } else if("SFC".equals(split1[0])){
                playsResult=LotteryTypes.getSfcStr(split3[0]);
            }else {
                if ("9:0".equals(split3[0])) {
                    playsResult = "胜其他";
                } else if ("9:9".equals(split3[0])) {
                    playsResult = "平其他";
                } else if ("0:9".equals(split3[0])) {
                    playsResult = "负其他";
                } else {
                    playsResult = split3[0];
                }
            }
            strBuilder.append(playsResult);
            //拼接赔率
            strBuilder.append("(" + split3[1] + ")");
            list.add(strBuilder);
        }
        map.put(split1[1], list);
    }
}
