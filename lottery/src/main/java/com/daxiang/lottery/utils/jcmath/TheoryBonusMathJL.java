package com.daxiang.lottery.utils.jcmath;

import android.text.TextUtils;
import android.util.Log;

import com.daxiang.lottery.common.JcPlayMethod;
import com.daxiang.lottery.entity.JclqData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/21 0021.
 */
public class TheoryBonusMathJL {

    public String getResultType(BuyIssueData mBuyIssueData) {
        String mAllowType = "";
        // Log.d("ACT", "CountMaxOdds: mBuyIssueList.Mresult->" + mBuyIssueData.Mresult);
        if (mBuyIssueData.MType.equals("SF")) {
            if (mBuyIssueData.Mresult.equals("主胜")) {
                mAllowType = mBuyIssueData.MLet > 0 ? "3-3" : "3-3,3-0";
            } else mAllowType = mBuyIssueData.MLet > 0 ? "0-0,0-3" : "0-0";

        } else if (mBuyIssueData.MType.equals("RFSF")) {
            if (mBuyIssueData.Mresult.equals("主胜[让]")) {
                mAllowType = mBuyIssueData.MLet > 0 ? "3-3,0-3" : "3-3";
            } else mAllowType = mBuyIssueData.MLet > 0 ? "0-0" : "3-0,0-0";
        } else if (mBuyIssueData.MType.equals("DXF")) {
            mAllowType = "3-3,3-0,0-0,0-3";
        } else if (mBuyIssueData.MType.equals("SFC")) {
            String substring = mBuyIssueData.Mresult.substring(0, 2);
            String substring1 = mBuyIssueData.Mresult.substring(3, mBuyIssueData.Mresult.length());
            if ("主胜".equals(substring)) {
                mAllowType = mBuyIssueData.MLet > 0 ? "3-3" : "3-3,3-0";
            } else {
                mAllowType = mBuyIssueData.MLet > 0 ? "0-0" : "0-3,0-0";
            }
        }
        return mAllowType;
    }

    /**
     * 一个场次有多个选项,计算该场次最大可能的赔率值之和
     */
    public String countBonus(HashMap<JclqData.DataBean, HashMap<String, String>> mChoosedContentMap, int playMethod, ArrayList<String> danTuoList, HashMap<Integer, Integer> mBunchMap) {
        double notHHMaxOdds = 0;
        double notHHMinOdds = 100000;
        //键是期号，值是一个两位的数组，第一位是最大赔率，第二位是最小赔率；
        HashMap<String, double[]> mMap = new HashMap<>();
        ArrayList<Double> maxOddsList = new ArrayList<>();
        ArrayList<Double> minOddsList = new ArrayList<>();
        String[] resultArr = {"3-0", "3-3", "0-3", "0-0"};
        //#########################对于选中的内容进行二次封装####################################
        //用于存放每一场比赛
        ArrayList<ArrayList<BuyIssueData>> mList = new ArrayList<>();
        for (Map.Entry<JclqData.DataBean, HashMap<String, String>> entry : mChoosedContentMap.entrySet()) {
            //用于存放每一场比赛选中的信息
            ArrayList<BuyIssueData> mBuyIssueList = new ArrayList<>();
            //遍历每一期对应选择的玩法
            HashMap<String, String> value = entry.getValue();
            for (Map.Entry<String, String> entry1 : value.entrySet()) {
                BuyIssueData mBuyIssueData = new BuyIssueData();
                String let = entry.getKey().getLet();
                mBuyIssueData.MLet = Float.parseFloat(TextUtils.isEmpty(let)?"0":let);
                mBuyIssueData.MType = JcPlayMethod.getPlayMethodJl(entry1.getKey());
                mBuyIssueData.Modds = Double.parseDouble(entry1.getValue());
                mBuyIssueData.Mresult = entry1.getKey();
                mBuyIssueData.Mmid = entry.getKey().getSession();
                if (mBuyIssueData.Modds > notHHMaxOdds) {
                    notHHMaxOdds = mBuyIssueData.Modds;
                }
                if (notHHMinOdds > mBuyIssueData.Modds) {
                    notHHMinOdds = mBuyIssueData.Modds;
                }
                mBuyIssueList.add(mBuyIssueData);
            }
            mList.add(mBuyIssueList);
        }
        //########################################################################################
        for (int i = 0; i < mList.size(); i++) {
            ArrayList<Double> maxList = new ArrayList<>();
            ArrayList<Double> minList = new ArrayList<>();
            for (int j = 0; j < resultArr.length; j++) {
                //把出现某一种结果的（例如3-3）对应的满足选择的对象的玩法和赔率。
                HashMap<String, HashMap<String, Double>> playOddsMap = new HashMap<>();
                String s = resultArr[j];
                ArrayList<BuyIssueData> mBuyIssueDatas = mList.get(i);
                Log.e("mBuy的内容是：",mBuyIssueDatas.toString());
                double oddsminSpfRqspf = 0;
                for (int m = 0; m < mBuyIssueDatas.size(); m++) {
                    String resultType = getResultType(mBuyIssueDatas.get(m));
                    if (resultType.contains(s)) {

                        if (mBuyIssueDatas.get(m).MType.equals("SF") || mBuyIssueDatas.get(m).MType.equals("RFSF")) {
                            oddsminSpfRqspf += mBuyIssueDatas.get(m).Modds;
                        }

                        HashMap<String, Double> doubles;
                        if (playOddsMap.containsKey(mBuyIssueDatas.get(m).MType)) {
                            doubles = playOddsMap.get(mBuyIssueDatas.get(m).MType);
                        } else {
                            doubles = new HashMap<>();
                        }
                        doubles.put(mBuyIssueDatas.get(m).Mresult, mBuyIssueDatas.get(m).Modds);
                        playOddsMap.put(mBuyIssueDatas.get(m).MType, doubles);
                    }
                }

             /*  double oddsminSpfRqspf=0;
                if (playOddsMap.containsKey("SPF") || playOddsMap.containsKey("RQSPF")) {
                    for (int n = 0; n < mBuyIssueDatas.size(); n++) {
                        String resultType=getResultType(mBuyIssueDatas.get(n));
                        if (resultType.contains(s)&&(mBuyIssueDatas.get(n).MType.equals("SPF")||mBuyIssueDatas.get(n).MType.equals("SPF"))){
                            oddsminSpfRqspf+=mBuyIssueDatas.get(n).Modds;
                        }
                    }
                }*/


                //获取本次循环的最大和最小值
                String str = getMinAndMaxOdds(playOddsMap);
                String[] split = str.split("~");
                double oddsmin = oddsminSpfRqspf + Double.valueOf(split[0]);
                double oddsmax = Double.valueOf(split[1]);
                if (oddsmax > 0) {
                    maxList.add(oddsmax);
                }
                if (oddsmin > 0) {
                    minList.add(oddsmin);
                }
            }
            Log.e("maxList最大赔率集合", maxList.toString());
            Log.e("minList最小赔率集合", minList.toString());
            //遍历选中的赔率的集合,取出最大的赔率
            double[] maxAndMinOddsArray = {0, 10000};//第一位是最大的赔率，第二位是最小的赔率
            for (int n = 0; n < maxList.size(); n++) {
                if (maxAndMinOddsArray[0] < maxList.get(n)) {
                    maxAndMinOddsArray[0] = maxList.get(n);
                }

            }
            //遍历选中的赔率的集合,取出最小的赔率
            for (int u = 0; u < minList.size(); u++) {
                if (maxAndMinOddsArray[1] > minList.get(u)) {
                    maxAndMinOddsArray[1] = minList.get(u);
                }
            }
            maxOddsList.add(maxAndMinOddsArray[0]);
            minOddsList.add(maxAndMinOddsArray[1]);
            //每一场比赛对应的选中的最大和最小赔率
            mMap.put(mList.get(i).get(0).Mmid, maxAndMinOddsArray);
        }
//##############以上将每场比赛的选中的最大赔率组合，和最小赔率组合，以及对应的期号存入一个map集合中##############
        //将胆和拖分开
        ArrayList<Double> danMaxOddsList = new ArrayList<>();
        ArrayList<Double> danMinOddsList = new ArrayList<>();
        ArrayList<Double> tuoMaxOddsList = new ArrayList<>();
        ArrayList<Double> tuoMinOddsList = new ArrayList<>();
        for (Map.Entry<String, double[]> entry : mMap.entrySet()) {
            if (danTuoList.contains(entry.getKey())) {
                danMaxOddsList.add(mMap.get(entry.getKey())[0]);
                danMinOddsList.add(mMap.get(entry.getKey())[1]);
            } else {
                tuoMaxOddsList.add(mMap.get(entry.getKey())[0]);
                tuoMinOddsList.add(mMap.get(entry.getKey())[1]);
            }
        }
        Log.e("danmaxOddsList最大胆", danMaxOddsList.toString());
        Log.e("tuoMaxOddsList最大拖", tuoMaxOddsList.toString());
        Log.e("danMinOddsList最小胆", danMinOddsList.toString());
        Log.e("tuoMinOddsList最小拖", tuoMinOddsList.toString());
        //计算最小理论奖金
        double minBonus = 1000000;
        for (Map.Entry<Integer, Integer> entry : mBunchMap.entrySet()) {
            for (List<Double> list : Combination.of(tuoMinOddsList, (entry.getKey()) - danTuoList.size())) {
                double ss = 1;
                for (int j = 0; j < list.size(); j++) {
                    ss = ss * list.get(j);
                }
                for (int m = 0; m < danMinOddsList.size(); m++) {
                    ss = ss * danMinOddsList.get(m);
                }
                if (minBonus > ss) {
                    minBonus = ss;
                }
            }
        }

        //计算最大理论奖金
        double maxBonus = 0;
        //通过封装的算法得到在这个mlist集合中任意挑选bunchs个，每种组合方式的数据
        for (Map.Entry<Integer, Integer> entry : mBunchMap.entrySet()) {
            for (List<Double> list : Combination.of(tuoMaxOddsList, (entry.getKey()) - danTuoList.size())) {
                double ss = 1;
                for (int j = 0; j < list.size(); j++) {
                    ss = ss * list.get(j);
                }
                for (int m = 0; m < danMaxOddsList.size(); m++) {
                    ss = ss * danMaxOddsList.get(m);
                }
                maxBonus = maxBonus + ss;
            }
        }
        return minBonus + "-" + maxBonus;
    }

    private class BuyIssueData {
        //玩法
        public String MType;
        //让球数
        public float MLet;
        //选择的结果
        public String Mresult;
        //赔率
        public double Modds;
        //期号
        public String Mmid;

        @Override
        public String toString() {
            return "BuyIssueData{" +
                    "MType='" + MType + '\'' +
                    ", MLet=" + MLet +
                    ", Mresult='" + Mresult + '\'' +
                    ", Modds=" + Modds +
                    ", Mmid='" + Mmid + '\'' +
                    '}';
        }
    }

    private double getMinOdds(ArrayList<Double> oddList) {
        double minOdds = oddList.get(0);
        for (int i = 1; i < oddList.size(); i++) {
            if (minOdds > oddList.get(i)) {
                minOdds = oddList.get(i);
            }
        }
        return minOdds;
    }

    private String getMinAndMaxOdds(HashMap<String, HashMap<String, Double>> playOddsMap) {
        double oddsmin = 0;
        double oddsmax = 0;
        int bqc = 0;
        int cbf = 0;
        int cbfP = 0;
        int jqs = 0;
        ArrayList<Double> bqcList = new ArrayList<>();
        ArrayList<Double> cbfList = new ArrayList<>();
        ArrayList<Double> cbfPList = new ArrayList<>();
        ArrayList<Double> jqsList = new ArrayList<>();
        ArrayList<Double> spfOrRqspfList = new ArrayList<>();
        String maxJqsTitle = "-1";
        String maxCbfTitle = "-1:-1";
        double maxJqsOdds = 0;
        double maxCbfOdds = 0;
        for (Map.Entry<String, HashMap<String, Double>> entry : playOddsMap.entrySet()) {
            double maxOdds = 0;
            double minOdds = 100000;
            //遍历出每一种玩法的最大值
            if (entry.getKey().equals("DXF")) {
                for (Map.Entry<String, Double> entry1 : entry.getValue().entrySet()) {
                    if (maxJqsOdds < entry1.getValue()) {
                        maxJqsOdds = entry1.getValue();
                        maxJqsTitle = entry1.getKey();
                    }
                }
            } else if (entry.getKey().equals("SFC")) {
                for (Map.Entry<String, Double> entry1 : entry.getValue().entrySet()) {
                    if (maxCbfOdds < entry1.getValue()) {
                        maxCbfOdds = entry1.getValue();
                        maxCbfTitle = entry1.getKey();
                    }
                }
            } else {
                for (Map.Entry<String, Double> entry1 : entry.getValue().entrySet()) {
                    if (maxOdds < entry1.getValue()) {
                        maxOdds = entry1.getValue();
                    }
                }
                //将每一种玩法的最大值相加
                oddsmax += maxOdds;
            }


            //(((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((
            switch (entry.getKey()) {
                case "DXF":

                    for (Map.Entry<String, Double> entry1 : entry.getValue().entrySet()) {
                        if (bqc == 2) {
                            bqcList.add(entry1.getValue());
                            oddsmin += getMinOdds(bqcList);
                        } else {
                            bqcList.add(entry1.getValue());
                            bqc++;
                        }
                    }
                    break;
                case "SFC":
                    for (Map.Entry<String, Double> entry1 : entry.getValue().entrySet()) {
                        if (cbf < 12) {
                            cbfList.add(entry1.getValue());
                            cbf++;
                        } else {
                            cbfList.add(entry1.getValue());
                            oddsmin += getMinOdds(cbfList);
                        }
                    }
                    break;
            }
            //)))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))
        }
        oddsmax = oddsmax + maxCbfOdds + maxJqsOdds;
        return oddsmin + "~" + oddsmax;
    }
}
