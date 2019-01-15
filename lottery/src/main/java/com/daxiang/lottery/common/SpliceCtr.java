package com.daxiang.lottery.common;


import com.daxiang.lottery.constant.LotteryTypes;
import com.daxiang.lottery.entity.ChoosedContentFormBean;
import com.daxiang.lottery.entity.JczqSpliteData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public class SpliceCtr {
    public static String spliteStr(ArrayList<ArrayList<ChoosedContentFormBean>> choosedContentFormList, ArrayList<String> danList) {
        HashMap<String, HashMap<String, ArrayList<JczqSpliteData>>> map = new HashMap<>();
        for (int i = 0; i < choosedContentFormList.size(); i++) {
            ArrayList<ChoosedContentFormBean> choosedContentFormBeen = choosedContentFormList.get(i);
            for (int j = 0; j < choosedContentFormBeen.size(); j++) {
                String playMethod = JcPlayMethod.getPlayMethod(choosedContentFormBeen.get(j).getContent());
                JczqSpliteData mData = new JczqSpliteData();
                switch (playMethod) {
                    case "SPF":
                        mData.setTitle(LotteryTypes.getSpfByStr(choosedContentFormBeen.get(j).getContent()));
                        break;
                    case "RQSPF":
                        mData.setTitle(LotteryTypes.getRqSpfByStr(choosedContentFormBeen.get(j).getContent()));
                        mData.setLet((int)choosedContentFormBeen.get(j).getLet() + "");
                        break;
                    case "JQS":
                        mData.setTitle(choosedContentFormBeen.get(j).getContent());
                        break;
                    case "CBF":
                        if (choosedContentFormBeen.get(j).getContent().equals("胜其他")) {
                            mData.setTitle("9:0");
                        } else if (choosedContentFormBeen.get(j).getContent().equals("平其他")) {
                            mData.setTitle("9:9");
                        } else if (choosedContentFormBeen.get(j).getContent().equals("负其他")) {
                            mData.setTitle("0:9");
                        } else {
                            mData.setTitle(choosedContentFormBeen.get(j).getContent());
                        }
                        break;
                    case "BQC":
                        mData.setTitle(LotteryTypes.getBqcByStr(choosedContentFormBeen.get(j).getContent()));
                        break;
                }


                mData.setOdds(choosedContentFormBeen.get(j).getOdds());
                HashMap<String, ArrayList<JczqSpliteData>> stringArrayListHashMap;
                if (map.containsKey(playMethod)) {
                    //如果包含这种玩儿法再往里面进行判断
                    stringArrayListHashMap = map.get(playMethod);
                    ArrayList<JczqSpliteData> jczqSpliteDatas;
                    if (stringArrayListHashMap.containsKey(choosedContentFormBeen.get(j).getMid())) {
                        //如果包含这种玩儿法
                        jczqSpliteDatas = stringArrayListHashMap.get(choosedContentFormBeen.get(j).getMid());
                    } else {
                        jczqSpliteDatas = new ArrayList<>();
                    }
                    jczqSpliteDatas.add(mData);
                    stringArrayListHashMap.put(choosedContentFormBeen.get(j).getMid(), jczqSpliteDatas);
                } else {
                    stringArrayListHashMap = new HashMap<>();
                    ArrayList<JczqSpliteData> jczqSpliteDatas = new ArrayList<>();
                    jczqSpliteDatas.add(mData);
                    stringArrayListHashMap.put(choosedContentFormBeen.get(j).getMid(), jczqSpliteDatas);
                }
                map.put(playMethod, stringArrayListHashMap);
            }
            // map.put(choosedContentFormList.get(i))
        }
//******************************************************************************************
        StringBuilder strBuilder = new StringBuilder();
        if (map.size() > 1) {

//胜平负 让球胜平负在一场比赛中同时选择的话要进行对将二者先抽出来进行排序
            if (map.containsKey("SPF") && (map.containsKey("RQSPF"))) {
                int j = 0;
                strBuilder.append("HH|");
                HashMap<String, ArrayList<JczqSpliteData>> spf = map.get("SPF");
                for (Map.Entry<String, ArrayList<JczqSpliteData>> entry2 : spf.entrySet()) {
                    strBuilder.append("SPF>" + entry2.getKey() + "=");
                    for (int i = 0; i < entry2.getValue().size(); i++) {
                        if (i == entry2.getValue().size() - 1) {
                            strBuilder.append(entry2.getValue().get(i).getTitle() + "(" + entry2.getValue().get(i).getOdds() + "),");
                        } else {
                            strBuilder.append(entry2.getValue().get(i).getTitle() + "(" + entry2.getValue().get(i).getOdds() + ")/");
                        }

                    }
                }
                map.remove("SPF");
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
                for (Map.Entry<String, HashMap<String, ArrayList<JczqSpliteData>>> entry : map.entrySet()) {
                    j++;
                    HashMap<String, ArrayList<JczqSpliteData>> value = entry.getValue();
                    int m = 0;
                    for (Map.Entry<String, ArrayList<JczqSpliteData>> entry1 : value.entrySet()) {
                        m++;
                        String key = entry.getKey();
                        strBuilder.append(entry.getKey() + ">" + entry1.getKey() + "=");
                        for (int i = 0; i < entry1.getValue().size(); i++) {
                            String title = entry1.getValue().get(i).getTitle();
                            String odds = entry1.getValue().get(i).getOdds();
                            if (key.equals("RQSPF")) {
                                String let = entry1.getValue().get(i).getLet();
                                if (i == entry1.getValue().size() - 1) {
                                    if (j == map.size() && m == value.size()) {
                                        strBuilder.append(title + "{" + let + "}" + "(" + odds + ")|");
                                    } else {
                                        strBuilder.append(title + "{" + let + "}" + "(" + odds + "),");
                                    }

                                } else {
                                    strBuilder.append(title + "{" + let + "}" + "(" + odds + ")" + "/");
                                }

                            } else {
                                if (i == entry1.getValue().size() - 1) {
                                    if (j == map.size() && m == value.size()) {
                                        strBuilder.append(title + "(" + odds + ")|");
                                    } else {
                                        strBuilder.append(title + "(" + odds + "),");
                                    }
                                } else {
                                    strBuilder.append(title + "(" + odds + ")" + "/");
                                }

                            }
                        }
                    }
                }
                //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

            } else {
                //混合
                int j = 0;
                strBuilder.append("HH|");
                for (Map.Entry<String, HashMap<String, ArrayList<JczqSpliteData>>> entry : map.entrySet()) {
                    j++;
                    HashMap<String, ArrayList<JczqSpliteData>> value = entry.getValue();
                    int m = 0;
                    for (Map.Entry<String, ArrayList<JczqSpliteData>> entry1 : value.entrySet()) {
                        m++;
                        String key = entry.getKey();
                        strBuilder.append(entry.getKey() + ">" + entry1.getKey() + "=");
                        for (int i = 0; i < entry1.getValue().size(); i++) {
                            String title = entry1.getValue().get(i).getTitle();
                            String odds = entry1.getValue().get(i).getOdds();
                            if (key.equals("RQSPF")) {
                                String let = entry1.getValue().get(i).getLet();
                                if (i == entry1.getValue().size() - 1) {
                                    if (j == map.size() && m == value.size()) {
                                        strBuilder.append(title + "{" + let + "}" + "(" + odds + ")|");
                                    } else {
                                        strBuilder.append(title + "{" + let + "}" + "(" + odds + "),");
                                    }

                                } else {
                                    strBuilder.append(title + "{" + let + "}" + "(" + odds + ")" + "/");
                                }

                            } else {
                                if (i == entry1.getValue().size() - 1) {
                                    if (j == map.size() && m == value.size()) {
                                        strBuilder.append(title + "(" + odds + ")|");
                                    } else {
                                        strBuilder.append(title + "(" + odds + "),");
                                    }
                                } else {
                                    strBuilder.append(title + "(" + odds + ")" + "/");
                                }

                            }
                        }
                    }
                }
            }


            //不是混合彩的情况

        } else {
            //
          /* String key = null;
           StringBuilder strBuilder1=new StringBuilder();*/
            int j = 0;
            for (Map.Entry<String, HashMap<String, ArrayList<JczqSpliteData>>> entry : map.entrySet()) {
                strBuilder.append(entry.getKey() + "|");
                HashMap<String, ArrayList<JczqSpliteData>> value = entry.getValue();
                for (Map.Entry<String, ArrayList<JczqSpliteData>> entry1 : value.entrySet()) {
                    j++;
                    //key是玩法
                    String key = entry.getKey();
                    strBuilder.append(entry1.getKey() + "=");
                    for (int i = 0; i < entry1.getValue().size(); i++) {
                        String title = entry1.getValue().get(i).getTitle();
                        String odds = entry1.getValue().get(i).getOdds();
                        if (key.equals("RQSPF")) {
                            String let = entry1.getValue().get(i).getLet();
                            if (i == entry1.getValue().size() - 1) {
                                if (j == value.size()) {
                                    strBuilder.append(title + "{" + let + "}" + "(" + odds + ")|");
                                } else {
                                    strBuilder.append(title + "{" + let + "}" + "(" + odds + "),");
                                }

                            } else {
                                strBuilder.append(title + "{" + let + "}" + "(" + odds + ")" + "/");
                            }

                        } else {
                            if (i == entry1.getValue().size() - 1) {
                                if (j == value.size()) {
                                    strBuilder.append(title + "(" + odds + ")|");
                                } else {
                                    strBuilder.append(title + "(" + odds + "),");
                                }
                            } else {
                                strBuilder.append(title + "(" + odds + ")" + "/");
                            }
                        }
                    }
                }

            }
        }
//******************************************************************************************
     /*  HashMap<String, HashMap<String, ArrayList<JczqSpliteData>>> danMap=new HashMap<>();
       HashMap<String, HashMap<String, ArrayList<JczqSpliteData>>> tuoMap=new HashMap<>();
       for (Map.Entry<String, HashMap<String, ArrayList<JczqSpliteData>>> entry:map.entrySet()){
           //将某一种玩法的存在蛇胆的期号找出
           for (Map.Entry<String,ArrayList<JczqSpliteData>> entry1:entry.getValue().entrySet()){
               if(danList.contains(entry1.getKey())){
                   //包含这个期号
                  if(){

                  } else {

                  }
               }else {
                   //不包含这个期号
               }
           }
       }*/
        return String.valueOf(strBuilder);
    }

    //flag为true是足球，false为篮球
    public static String spliteStr1(ArrayList<ArrayList<ChoosedContentFormBean>> choosedContentFormList, ArrayList<String> danList, boolean flag) {
        HashMap<String, HashMap<String, ArrayList<JczqSpliteData>>> map = new HashMap<>();
        for (int i = 0; i < choosedContentFormList.size(); i++) {
            ArrayList<ChoosedContentFormBean> choosedContentFormBeen = choosedContentFormList.get(i);
            for (int j = 0; j < choosedContentFormBeen.size(); j++) {
                String playMethod;
                if (flag) {
                    playMethod = JcPlayMethod.getPlayMethod(choosedContentFormBeen.get(j).getContent());
                } else {
                    playMethod = JcPlayMethod.getPlayMethodJl(choosedContentFormBeen.get(j).getContent());
                }

                JczqSpliteData mData = new JczqSpliteData();
                switch (playMethod) {
                    case "SPF":
                        mData.setTitle(LotteryTypes.getSpfByStr(choosedContentFormBeen.get(j).getContent()));
                        break;
                    case "RQSPF":
                        mData.setTitle(LotteryTypes.getRqSpfByStr(choosedContentFormBeen.get(j).getContent()));
                        mData.setLet((int)choosedContentFormBeen.get(j).getLet() + "");
                        break;
                    case "JQS":
                        if (choosedContentFormBeen.get(j).getContent().equals("7+"))
                            mData.setTitle("7");
                        else
                            mData.setTitle(choosedContentFormBeen.get(j).getContent());
                        break;
                    case "CBF":
                        if (choosedContentFormBeen.get(j).getContent().equals("胜其他")) {
                            mData.setTitle("9:0");
                        } else if (choosedContentFormBeen.get(j).getContent().equals("平其他")) {
                            mData.setTitle("9:9");
                        } else if (choosedContentFormBeen.get(j).getContent().equals("负其他")) {
                            mData.setTitle("0:9");
                        } else {
                            mData.setTitle(choosedContentFormBeen.get(j).getContent());
                        }
                        break;
                    case "BQC":
                        mData.setTitle(LotteryTypes.getBqcByStr(choosedContentFormBeen.get(j).getContent()));
                        break;
                    //篮球的
                    case "SF":
                        mData.setTitle(LotteryTypes.getSpfByStr(choosedContentFormBeen.get(j).getContent()));
                        break;
                    case "RFSF":
                        mData.setTitle(LotteryTypes.getRqSpfByStr(choosedContentFormBeen.get(j).getContent()));
                        mData.setLet(choosedContentFormBeen.get(j).getLet() + "");
                        break;
                    case "DXF":
                        mData.setTitle(LotteryTypes.getRqSpfByStr(choosedContentFormBeen.get(j).getContent()));
                        break;
                    case "SFC":
                        mData.setTitle(LotteryTypes.getSfcByStr(choosedContentFormBeen.get(j).getContent()));
                        break;
                }


                mData.setOdds(choosedContentFormBeen.get(j).getOdds());
                HashMap<String, ArrayList<JczqSpliteData>> stringArrayListHashMap;
                if (map.containsKey(playMethod)) {
                    //如果包含这种玩儿法再往里面进行判断
                    stringArrayListHashMap = map.get(playMethod);
                    ArrayList<JczqSpliteData> jczqSpliteDatas;
                    if (stringArrayListHashMap.containsKey(choosedContentFormBeen.get(j).getMid())) {
                        //如果包含这个期号
                        jczqSpliteDatas = stringArrayListHashMap.get(choosedContentFormBeen.get(j).getMid());
                    } else {
                        jczqSpliteDatas = new ArrayList<>();
                    }
                    jczqSpliteDatas.add(mData);
                    stringArrayListHashMap.put(choosedContentFormBeen.get(j).getMid(), jczqSpliteDatas);
                } else {
                    stringArrayListHashMap = new HashMap<>();
                    ArrayList<JczqSpliteData> jczqSpliteDatas = new ArrayList<>();
                    jczqSpliteDatas.add(mData);
                    stringArrayListHashMap.put(choosedContentFormBeen.get(j).getMid(), jczqSpliteDatas);
                }
                map.put(playMethod, stringArrayListHashMap);
            }
            // map.put(choosedContentFormList.get(i))
        }
//******************************************************************************************
        HashMap<String, HashMap<String, ArrayList<JczqSpliteData>>> danMap = new HashMap<>();
        HashMap<String, HashMap<String, ArrayList<JczqSpliteData>>> tuoMap = new HashMap<>();
        for (Map.Entry<String, HashMap<String, ArrayList<JczqSpliteData>>> entry : map.entrySet()) {
            //将某一种玩法的存在蛇胆的期号找出
            HashMap<String, ArrayList<JczqSpliteData>> danQMap = new HashMap<>();
            HashMap<String, ArrayList<JczqSpliteData>> tuoQMap = new HashMap<>();
            for (Map.Entry<String, ArrayList<JczqSpliteData>> entry1 : entry.getValue().entrySet()) {
                if (danList.contains(entry1.getKey())) {
                    //包含这个期号
                    danQMap.put(entry1.getKey(), entry1.getValue());

                } else {
                    //不包含这个期号
                    tuoQMap.put(entry1.getKey(), entry1.getValue());
                }
            }
            if (danQMap.size() > 0) {
                danMap.put(entry.getKey(), danQMap);
            }
            if (tuoQMap.size() > 0) {
                tuoMap.put(entry.getKey(), tuoQMap);
            }
        }
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^以上为分出胆拖^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        StringBuilder strBuilder = new StringBuilder();
        if (map.size() > 1 || danList.size() > 0) {

//胜平负 让球胜平负在一场比赛中同时选择的话要进行对将二者先抽出来进行排序
            if (danMap.containsKey("SPF") && (danMap.containsKey("RQSPF"))) {
                int j = 0;
                strBuilder.append("HH|");
                HashMap<String, ArrayList<JczqSpliteData>> spf = danMap.get("SPF");
                for (Map.Entry<String, ArrayList<JczqSpliteData>> entry2 : spf.entrySet()) {
                    strBuilder.append("SPF>" + entry2.getKey() + "=");
                    for (int i = 0; i < entry2.getValue().size(); i++) {
                        if (i == entry2.getValue().size() - 1) {
                            strBuilder.append(entry2.getValue().get(i).getTitle() + "(" + entry2.getValue().get(i).getOdds() + "),");
                        } else {
                            strBuilder.append(entry2.getValue().get(i).getTitle() + "(" + entry2.getValue().get(i).getOdds() + ")/");
                        }

                    }
                }
                danMap.remove("SPF");
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
                for (Map.Entry<String, HashMap<String, ArrayList<JczqSpliteData>>> entry : danMap.entrySet()) {
                    j++;
                    HashMap<String, ArrayList<JczqSpliteData>> value = entry.getValue();
                    int m = 0;
                    for (Map.Entry<String, ArrayList<JczqSpliteData>> entry1 : value.entrySet()) {
                        m++;
                        String key = entry.getKey();
                        strBuilder.append(entry.getKey() + ">" + entry1.getKey() + "=");
                        for (int i = 0; i < entry1.getValue().size(); i++) {
                            String title = entry1.getValue().get(i).getTitle();
                            String odds = entry1.getValue().get(i).getOdds();
                            if (key.equals("RQSPF") || key.equals("RFSF")) {
                                String let = entry1.getValue().get(i).getLet();
                                if (i == entry1.getValue().size() - 1) {
                                    if (j == danMap.size() && m == value.size()) {
                                        strBuilder.append(title + "{" + let + "}" + "(" + odds + ")$");
                                    } else {
                                        strBuilder.append(title + "{" + let + "}" + "(" + odds + "),");
                                    }

                                } else {
                                    strBuilder.append(title + "{" + let + "}" + "(" + odds + ")" + "/");
                                }

                            } else {
                                if (i == entry1.getValue().size() - 1) {
                                    if (j == danMap.size() && m == value.size()) {
                                        strBuilder.append(title + "(" + odds + ")$");
                                    } else {
                                        strBuilder.append(title + "(" + odds + "),");
                                    }
                                } else {
                                    strBuilder.append(title + "(" + odds + ")" + "/");
                                }

                            }
                        }
                    }
                }
                //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

            } else {

                int j = 0;
                strBuilder.append("HH|");
                for (Map.Entry<String, HashMap<String, ArrayList<JczqSpliteData>>> entry : danMap.entrySet()) {
                    j++;
                    HashMap<String, ArrayList<JczqSpliteData>> value = entry.getValue();
                    int m = 0;
                    for (Map.Entry<String, ArrayList<JczqSpliteData>> entry1 : value.entrySet()) {
                        m++;
                        String key = entry.getKey();
                        strBuilder.append(entry.getKey() + ">" + entry1.getKey() + "=");
                        for (int i = 0; i < entry1.getValue().size(); i++) {
                            String title = entry1.getValue().get(i).getTitle();
                            String odds = entry1.getValue().get(i).getOdds();
                            if (key.equals("RQSPF") || key.equals("RFSF")) {
                                String let = entry1.getValue().get(i).getLet();
                                if (i == entry1.getValue().size() - 1) {
                                    if (j == danMap.size() && m == value.size()) {
                                        strBuilder.append(title + "{" + let + "}" + "(" + odds + ")$");
                                    } else {
                                        strBuilder.append(title + "{" + let + "}" + "(" + odds + "),");
                                    }

                                } else {
                                    strBuilder.append(title + "{" + let + "}" + "(" + odds + ")" + "/");
                                }

                            } else {
                                if (i == entry1.getValue().size() - 1) {
                                    if (j == danMap.size() && m == value.size()) {
                                        strBuilder.append(title + "(" + odds + ")$");
                                    } else {
                                        strBuilder.append(title + "(" + odds + "),");
                                    }
                                } else {
                                    strBuilder.append(title + "(" + odds + ")" + "/");
                                }

                            }
                        }
                    }
                }
            }
            //循环 拖
            if (tuoMap.containsKey("SPF") && (tuoMap.containsKey("RQSPF"))) {
                int j = 0;
                HashMap<String, ArrayList<JczqSpliteData>> spf = tuoMap.get("SPF");
                for (Map.Entry<String, ArrayList<JczqSpliteData>> entry2 : spf.entrySet()) {
                    strBuilder.append("SPF>" + entry2.getKey() + "=");
                    for (int i = 0; i < entry2.getValue().size(); i++) {
                        if (i == entry2.getValue().size() - 1) {
                            strBuilder.append(entry2.getValue().get(i).getTitle() + "(" + entry2.getValue().get(i).getOdds() + "),");
                        } else {
                            strBuilder.append(entry2.getValue().get(i).getTitle() + "(" + entry2.getValue().get(i).getOdds() + ")/");
                        }

                    }
                }
                tuoMap.remove("SPF");
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
                for (Map.Entry<String, HashMap<String, ArrayList<JczqSpliteData>>> entry : tuoMap.entrySet()) {
                    j++;
                    HashMap<String, ArrayList<JczqSpliteData>> value = entry.getValue();
                    int m = 0;
                    for (Map.Entry<String, ArrayList<JczqSpliteData>> entry1 : value.entrySet()) {
                        m++;
                        String key = entry.getKey();
                        strBuilder.append(entry.getKey() + ">" + entry1.getKey() + "=");
                        for (int i = 0; i < entry1.getValue().size(); i++) {
                            String title = entry1.getValue().get(i).getTitle();
                            String odds = entry1.getValue().get(i).getOdds();
                            if (key.equals("RQSPF") || key.equals("RFSF")) {
                                String let = entry1.getValue().get(i).getLet();
                                if (i == entry1.getValue().size() - 1) {
                                    if (j == tuoMap.size() && m == value.size()) {
                                        strBuilder.append(title + "{" + let + "}" + "(" + odds + ")|");
                                    } else {
                                        strBuilder.append(title + "{" + let + "}" + "(" + odds + "),");
                                    }

                                } else {
                                    strBuilder.append(title + "{" + let + "}" + "(" + odds + ")" + "/");
                                }

                            } else {
                                if (i == entry1.getValue().size() - 1) {
                                    if (j == tuoMap.size() && m == value.size()) {
                                        strBuilder.append(title + "(" + odds + ")|");
                                    } else {
                                        strBuilder.append(title + "(" + odds + "),");
                                    }
                                } else {
                                    strBuilder.append(title + "(" + odds + ")" + "/");
                                }

                            }
                        }
                    }
                }
                //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

            } else {

                int j = 0;
                for (Map.Entry<String, HashMap<String, ArrayList<JczqSpliteData>>> entry : tuoMap.entrySet()) {
                    j++;
                    HashMap<String, ArrayList<JczqSpliteData>> value = entry.getValue();
                    int m = 0;
                    for (Map.Entry<String, ArrayList<JczqSpliteData>> entry1 : value.entrySet()) {
                        m++;
                        String key = entry.getKey();
                        strBuilder.append(entry.getKey() + ">" + entry1.getKey() + "=");
                        for (int i = 0; i < entry1.getValue().size(); i++) {
                            String title = entry1.getValue().get(i).getTitle();
                            String odds = entry1.getValue().get(i).getOdds();
                            if (key.equals("RQSPF") || key.equals("RFSF")) {
                                String let = entry1.getValue().get(i).getLet();
                                if (i == entry1.getValue().size() - 1) {
                                    if (j == tuoMap.size() && m == value.size()) {
                                        strBuilder.append(title + "{" + let + "}" + "(" + odds + ")|");
                                    } else {
                                        strBuilder.append(title + "{" + let + "}" + "(" + odds + "),");
                                    }

                                } else {
                                    strBuilder.append(title + "{" + let + "}" + "(" + odds + ")" + "/");
                                }

                            } else {
                                if (i == entry1.getValue().size() - 1) {
                                    if (j == tuoMap.size() && m == value.size()) {
                                        strBuilder.append(title + "(" + odds + ")|");
                                    } else {
                                        strBuilder.append(title + "(" + odds + "),");
                                    }
                                } else {
                                    strBuilder.append(title + "(" + odds + ")" + "/");
                                }

                            }
                        }
                    }
                }
            }

            //不是混合彩的情况

        } else {
            //
          /* String key = null;
           StringBuilder strBuilder1=new StringBuilder();*/
            int j = 0;
            for (Map.Entry<String, HashMap<String, ArrayList<JczqSpliteData>>> entry : map.entrySet()) {
                strBuilder.append(entry.getKey() + "|");
                HashMap<String, ArrayList<JczqSpliteData>> value = entry.getValue();
                for (Map.Entry<String, ArrayList<JczqSpliteData>> entry1 : value.entrySet()) {
                    j++;
                    //key是玩法
                    String key = entry.getKey();
                    strBuilder.append(entry1.getKey() + "=");
                    for (int i = 0; i < entry1.getValue().size(); i++) {
                        String title = entry1.getValue().get(i).getTitle();
                        String odds = entry1.getValue().get(i).getOdds();
                        if (key.equals("RQSPF") || key.equals("RFSF")) {
                            String let = entry1.getValue().get(i).getLet();
                            if (i == entry1.getValue().size() - 1) {
                                if (j == value.size()) {
                                    strBuilder.append(title + "{" + let + "}" + "(" + odds + ")|");
                                } else {
                                    strBuilder.append(title + "{" + let + "}" + "(" + odds + "),");
                                }

                            } else {
                                strBuilder.append(title + "{" + let + "}" + "(" + odds + ")" + "/");
                            }

                        } else {
                            if (i == entry1.getValue().size() - 1) {
                                if (j == value.size()) {
                                    strBuilder.append(title + "(" + odds + ")|");
                                } else {
                                    strBuilder.append(title + "(" + odds + "),");
                                }
                            } else {
                                strBuilder.append(title + "(" + odds + ")" + "/");
                            }
                        }
                    }
                }

            }

        }
//******************************************************************************************

        return String.valueOf(strBuilder);
    }
}
