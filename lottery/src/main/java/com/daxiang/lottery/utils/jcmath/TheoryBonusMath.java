package com.daxiang.lottery.utils.jcmath;

import android.util.Log;

import com.daxiang.lottery.common.JcPlayMethod;
import com.daxiang.lottery.entity.JczqData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class TheoryBonusMath {
    public String getResultType(BuyIssueData mBuyIssueData) {
        String mAllowType = "";
        // Log.d("ACT", "CountMaxOdds: mBuyIssueList.Mresult->" + mBuyIssueData.Mresult);
        if (mBuyIssueData.MType.equals("SPF")) {
            if (mBuyIssueData.Mresult.equals("主胜")) {
                //三种情况，1.让球主队加球本来就胜 加球肯定还胜；2.让球主队减一个球那就有可能让胜和让平不可能出现让负
                //3.当让球减去2个球以上的时候，让球就有可能胜平负了
                mAllowType = mBuyIssueData.MLet > 0 ? "3-3" : mBuyIssueData.MLet < -1 ? "3-3,3-1,3-0" : "3-3,3-1";
            } else if (mBuyIssueData.Mresult.equals("平")) {
                //本来平局让球要么赢要么输
                mAllowType = mBuyIssueData.MLet > 0 ? "1-3" : "1-0";
            } else {
                /**主负
                 * 1.本来就负让球减球的话 就只能是让负了
                 * 2.加一球
                 * 3.加两球以上
                 * */
                mAllowType = mBuyIssueData.MLet > 0 ? mBuyIssueData.MLet > 1 ? "0-3,0-1,0-0" : "0-1,0-0" : "0-0";
            }
        } else if (mBuyIssueData.MType.equals("RQSPF")) {
            if (mBuyIssueData.Mresult.equals("让胜")) {
                mAllowType = mBuyIssueData.MLet > 0 ? mBuyIssueData.MLet > 1 ? "3-3,1-3,0-3" : "3-3,1-3" : "3-3";
            } else if (mBuyIssueData.Mresult.equals("让平")) {
                mAllowType = mBuyIssueData.MLet > 0 ? "0-1" : "3-1";
            } else {
                mAllowType = mBuyIssueData.MLet > 0 ? "0-0" : mBuyIssueData.MLet < -1 ? "3-0,1-0,0-0" : "1-0,0-0";
            }
        } else if (mBuyIssueData.MType.equals("CBF")) {
            if (mBuyIssueData.Mresult.equals("平其他")) {
                mAllowType = mBuyIssueData.MLet > 0 ? "1-3" : "1-0";
            } else if (mBuyIssueData.Mresult.equals("胜其他")) {
                mAllowType = mBuyIssueData.MLet > 0 ? "3-3" : "3-3,3-1,3-0";
            } else if (mBuyIssueData.Mresult.equals("负其他")) {
                mAllowType = mBuyIssueData.MLet > 0 ? "0-3,0-1,0-0" : "0-0";
            } else {
                int num1 = Integer.valueOf(mBuyIssueData.Mresult.split(":")[0]);
                int num2 = Integer.valueOf(mBuyIssueData.Mresult.split(":")[1]);

                if (num1 - num2 + mBuyIssueData.MLet > 0 && num1 != num2) {
                    //无论这是赢还是输，只要整体大于0那么让球肯定是胜。
                    mAllowType = num1 > num2 ? "3-3" : "0-3";
                } else if (num1 - num2 + mBuyIssueData.MLet == 0 && num1 != num2) {
                    mAllowType = num1 > num2 ? "3-1" : "0-1";
                } else if (num1 - num2 + mBuyIssueData.MLet < 0 && num1 != num2) {
                    mAllowType = num1 > num2 ? "3-0" : "0-0";
                } else if (num1 == num2) {
                    mAllowType = mBuyIssueData.MLet > 0 ? "1-3" : "1-0";
                }
            }
        } else if (mBuyIssueData.MType.equals("BQC")) {
            if (mBuyIssueData.Mresult.endsWith("平")) {
                mAllowType = mBuyIssueData.MLet > 0 ? "1-3" : "1-0";
            } else if (mBuyIssueData.Mresult.endsWith("胜")) {
                mAllowType = mBuyIssueData.MLet > 0 ? "3-3" : mBuyIssueData.MLet == -1 ? "3-3,3-1" : "3-3,3-1,3-0";
            } else {
                mAllowType = mBuyIssueData.MLet > 0 ? "0-0,0-1,0-3" : "0-0";
            }
        } else if (mBuyIssueData.MType.equals("JQS")) {
            if (mBuyIssueData.Mresult.equals("0")) {
                mAllowType = mBuyIssueData.MLet > 0 ? "1-3" : "1-0";
            } else if (mBuyIssueData.Mresult.equals("1")) {
                mAllowType = mBuyIssueData.MLet > 0 ? "0-1,0-3,3-3" : "3-0,3-1,0-0";
            } else if (Integer.valueOf(mBuyIssueData.Mresult.replace("+", "")) % 2 == 0) {
                mAllowType = mBuyIssueData.MLet > 0 ? "1-3,3-3,0-1,0-0" : "1-0,3-1,3-3,0-0";
            } else {
                mAllowType = mBuyIssueData.MLet > 0 ? "0-0,0-1,0-3,3-3" : "3-0,3-1,3-3,0-0";
            }
        }
        return mAllowType;
    }

    /**
     * 一个场次有多个选项,计算该场次最大可能的赔率值之和
     */
    public String countBonus(HashMap<JczqData.DataBean, HashMap<String, String>> mChoosedContentMap, ArrayList<String> danTuoList, HashMap<Integer, Integer> mBunchMap) {
        double notHHMaxOdds = 0;
        double notHHMinOdds = 100000;
        //键是期号，值是一个两位的数组，第一位是最大赔率，第二位是最小赔率；
        HashMap<String, double[]> mMap = new HashMap<>();
        ArrayList<Double> maxOddsList = new ArrayList<>();
        ArrayList<Double> minOddsList = new ArrayList<>();
        String[] resultArr = {"3-3", "3-1", "3-0", "1-3", "1-1", "1-0", "0-3", "0-1", "0-0"};
        //#########################对于选中的内容进行二次封装####################################
        //用于存放每一场比赛
        ArrayList<ArrayList<BuyIssueData>> mList = new ArrayList<>();
        for (Map.Entry<JczqData.DataBean, HashMap<String, String>> entry : mChoosedContentMap.entrySet()) {
            //用于存放每一场比赛选中的信息
            ArrayList<BuyIssueData> mBuyIssueList = new ArrayList<>();
            //遍历每一期对应选择的玩法
            HashMap<String, String> value = entry.getValue();
            for (Map.Entry<String, String> entry1 : value.entrySet()) {
                BuyIssueData mBuyIssueData = new BuyIssueData();
                mBuyIssueData.MLet = entry.getKey().getLet();
                mBuyIssueData.MType = JcPlayMethod.getPlayMethod(entry1.getKey());
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
                double oddsminSpfRqspf = 0;
                for (int m = 0; m < mBuyIssueDatas.size(); m++) {
                    String resultType = getResultType(mBuyIssueDatas.get(m));
                    if (resultType.contains(s)) {

                        if (mBuyIssueDatas.get(m).MType.equals("SPF") || mBuyIssueDatas.get(m).MType.equals("RQSPF")) {
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
            //Log.e("maxList最大赔率集合", maxList.toString());
            //Log.e("minList最小赔率集合", minList.toString());
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
        //计算最小理论奖金
        double minBonus = 1000000;
        for (Map.Entry<Integer, Integer> entry : mBunchMap.entrySet()) {
            for (List<Double> list : Combination.of(tuoMinOddsList, (entry.getKey()) - danTuoList.size())) {
                Log.e("数据", list.toString());
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
//***************************************************测试奖金优化里面的理论奖金*************************************************************

    /**
     * 一个场次有多个选项,计算该场次最大可能的赔率值之和
     */
    public String countBonusOptimize(HashMap<JczqData.DataBean, HashMap<String, String>> mChoosedContentMap, HashMap<String, Float> bonusMap, ArrayList<String> danTuoList, HashMap<Integer, Integer> mBunchMap) {
        double notHHMaxOdds = 0;
        double notHHMinOdds = 100000;
        //键是期号，值是一个两位的数组，第一位是最大赔率，第二位是最小赔率；
        HashMap<String, double[]> mMap = new HashMap<>();
        ArrayList<Double> maxOddsList = new ArrayList<>();
        ArrayList<Double> minOddsList = new ArrayList<>();
        String[] resultArr = {"3-3", "3-1", "3-0", "1-3", "1-1", "1-0", "0-3", "0-1", "0-0"};
        //#########################对于选中的内容进行二次封装####################################
        //用于存放每一场比赛
        ArrayList<ArrayList<BuyIssueData>> mList = new ArrayList<>();
        for (Map.Entry<JczqData.DataBean, HashMap<String, String>> entry : mChoosedContentMap.entrySet()) {
            //用于存放每一场比赛选中的信息
            ArrayList<BuyIssueData> mBuyIssueList = new ArrayList<>();
            //遍历每一期对应选择的玩法
            HashMap<String, String> value = entry.getValue();
            for (Map.Entry<String, String> entry1 : value.entrySet()) {
                BuyIssueData mBuyIssueData = new BuyIssueData();
                mBuyIssueData.MLet = entry.getKey().getLet();
                mBuyIssueData.MType = JcPlayMethod.getPlayMethod(entry1.getKey());
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
                double oddsminSpfRqspf = 0;
                for (int m = 0; m < mBuyIssueDatas.size(); m++) {
                    String resultType = getResultType(mBuyIssueDatas.get(m));
                    if (resultType.contains(s)) {

                        if (mBuyIssueDatas.get(m).MType.equals("SPF") || mBuyIssueDatas.get(m).MType.equals("RQSPF")) {
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
        ArrayList<String> danMaxOddsList = new ArrayList<>();
        ArrayList<String> danMinOddsList = new ArrayList<>();
        ArrayList<String> tuoMaxOddsList = new ArrayList<>();
        ArrayList<String> tuoMinOddsList = new ArrayList<>();
        for (Map.Entry<String, double[]> entry : mMap.entrySet()) {
            if (danTuoList.contains(entry.getKey())) {
                danMaxOddsList.add(mMap.get(entry.getKey())[0] + ";" + entry.getKey());
                danMinOddsList.add(mMap.get(entry.getKey())[1] + ";" + entry.getKey());
            } else {
                tuoMaxOddsList.add(mMap.get(entry.getKey())[0] + ";" + entry.getKey());
                tuoMinOddsList.add(mMap.get(entry.getKey())[1] + ";" + entry.getKey());
            }
        }
        //计算最小理论奖金
        double minBonus = 10000000;

        for (Map.Entry<Integer, Integer> entry : mBunchMap.entrySet()) {
            for (List<String> list : Combination.of(tuoMinOddsList, (entry.getKey()) - danTuoList.size())) {
                Log.e("list数据", list.toString());
                ArrayList<String> midList = new ArrayList<>();
                double ss = 1;
                for (int j = 0; j < list.size(); j++) {
                    String[] split = list.get(j).split("\\;");
                    ss = ss * Double.parseDouble(split[0]);
                    midList.add(split[1]);
                }
                for (int m = 0; m < danMinOddsList.size(); m++) {
                    String[] split = danMinOddsList.get(m).split("\\;");
                    ss = ss * Double.parseDouble(split[0]);
                    midList.add(split[1]);
                }
                for (Map.Entry<String, Float> entry1 : bonusMap.entrySet()) {
                    /*
                    * 循环这一组的期号
                    * */
                    int i;
                    for (i = 0; i < midList.size(); i++) {
                        /*
                        * 如果一直到循环到最后一个次还没有跳出循环说明完全吻合。
                        * */
                        if (!entry1.getKey().contains(midList.get(i))) {
                            break;
                        }
                    }
                    if (i == midList.size()) ss = entry1.getValue();
                }
                //int index = Arrays.binarySearch(orderOddsArray, (float) ss*2);
                // ss=orderMultiArray[index]*ss*2;
                if (minBonus > ss) {
                    minBonus = ss;
                }
            }
        }

        //计算最大理论奖金
        double maxBonus = 0;
        //通过封装的算法得到在这个mlist集合中任意挑选bunchs个，每种组合方式的数据
        for (Map.Entry<Integer, Integer> entry : mBunchMap.entrySet()) {
            for (List<String> list : Combination.of(tuoMaxOddsList, (entry.getKey()) - danTuoList.size())) {
                ArrayList<String> midList = new ArrayList<>();
                double ss = 1;
                for (int j = 0; j < list.size(); j++) {
                    String[] split = list.get(j).split("\\;");
                    ss = ss * Double.parseDouble(split[0]);
                    midList.add(split[1]);
                }
                for (int m = 0; m < danMaxOddsList.size(); m++) {
                    String[] split = danMaxOddsList.get(m).split("\\;");
                    ss = ss * Double.parseDouble(split[0]);
                    midList.add(split[1]);
                }
                for (Map.Entry<String, Float> entry1 : bonusMap.entrySet()) {
                    /*
                    * 循环这一组的期号
                    * */
                    int i;
                    for (i = 0; i < midList.size(); i++) {
                        /*
                        * 如果一直到循环到最后一个次还没有跳出循环说明完全吻合。
                        * */
                        if (!entry1.getKey().contains(midList.get(i))) {
                            break;
                        }
                    }
                    if (i == midList.size()) ss = entry1.getValue();
                }
                //int index = Arrays.binarySearch(orderOddsArray, (float) ss*2);
                // ss=orderMultiArray[index]*ss*2;
                maxBonus = maxBonus + ss;
            }
        }
        return minBonus + "-" + maxBonus;
    }


    private class BuyIssueData {
        //玩法
        public String MType;
        //让球数
        public int MLet;
        //选择的结果
        public String Mresult;
        //赔率
        public double Modds;
        //期号
        public String Mmid;
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
            if (entry.getKey().equals("JQS")) {
                for (Map.Entry<String, Double> entry1 : entry.getValue().entrySet()) {
                    if (maxJqsOdds < entry1.getValue()) {
                        maxJqsOdds = entry1.getValue();
                        maxJqsTitle = entry1.getKey();
                    }
                }
            } else if (entry.getKey().equals("CBF")) {
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
                case "BQC":
                    if (playOddsMap.containsKey("SPF") || playOddsMap.containsKey("RQSPF")) {
                        for (Map.Entry<String, Double> entry1 : entry.getValue().entrySet()) {
                            if (bqc >= 2) {
                                bqcList.add(entry1.getValue());
                                oddsmin += getMinOdds(bqcList);
                            } else {
                                bqcList.add(entry1.getValue());
                                bqc++;
                            }
                        }
                    } else {
                        for (Map.Entry<String, Double> entry1 : entry.getValue().entrySet()) {
                            bqcList.add(entry1.getValue());
                        }
                        oddsmin += getMinOdds(bqcList);
                    }

                    break;
                case "CBF":
                    if (playOddsMap.containsKey("SPF") || playOddsMap.containsKey("RQSPF")) {
                        for (Map.Entry<String, Double> entry1 : entry.getValue().entrySet()) {
                            int num1;
                            int num2;
                            if (entry1.getKey().equals("平其他")) {
                                num1 = 4;
                                num2 = 4;
                            } else if (entry1.getKey().equals("胜其他")) {
                                num1 = 9;
                                num2 = 1;
                            } else if (entry1.getKey().equals("负其他")) {
                                num1 = 1;
                                num2 = 9;
                            } else {
                                num1 = Integer.valueOf(entry1.getKey().split(":")[0]);
                                num2 = Integer.valueOf(entry1.getKey().split(":")[1]);
                            }
                            if (num1 == num2) {
                                if (cbfP < 4) {
                                    cbfPList.add(entry1.getValue());
                                    cbfP++;
                                } else {
                                    cbfPList.add(entry1.getValue());
                                    oddsmin += getMinOdds(cbfPList);
                                }
                            } else {
                                if (cbf < 12) {
                                    cbfList.add(entry1.getValue());
                                    cbf++;
                                } else {
                                    cbfList.add(entry1.getValue());
                                    oddsmin += getMinOdds(cbfList);
                                }
                            }
                        }
                    } else {
                        for (Map.Entry<String, Double> entry1 : entry.getValue().entrySet()) {
                            cbfList.add(entry1.getValue());
                        }
                        oddsmin += getMinOdds(cbfList);
                    }
                    break;
                case "JQS":
                    if (playOddsMap.containsKey("SPF") || playOddsMap.containsKey("RQSPF")) {
                        for (Map.Entry<String, Double> entry1 : entry.getValue().entrySet()) {
                            if (jqs >= 7) {
                                jqsList.add(entry1.getValue());
                                oddsmin += getMinOdds(jqsList);
                            } else {
                                jqsList.add(entry1.getValue());
                                jqs++;
                            }
                        }
                    } else {
                        for (Map.Entry<String, Double> entry1 : entry.getValue().entrySet()) {
                            jqsList.add(entry1.getValue());
                        }
                        oddsmin += getMinOdds(jqsList);
                    }
                    break;
                default:
                   /* for (Map.Entry<String, Double> entry1 : entry.getValue().entrySet()) {
                        if (minOdds > entry1.getValue()) {
                            minOdds = entry1.getValue();
                        }
                    }
                    Log.e("所有赔率：", spfOrRqspfList.toString());
                    oddsmin += minOdds;*/
                    break;
            }
            //)))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))
        }
        if (maxCbfOdds != 0 && maxJqsOdds != 0) {
            int num1;
            int num2;
            int num3;
            if (maxCbfTitle.equals("平其他")) {
                num1 = 5;
                num2 = 5;
            } else if (maxCbfTitle.equals("胜其他")) {
                num1 = 9;
                num2 = 1;
            } else if (maxCbfTitle.equals("负其他")) {
                num1 = 1;
                num2 = 9;
            } else {
                num1 = Integer.valueOf(maxCbfTitle.split(":")[0]);
                num2 = Integer.valueOf(maxCbfTitle.split(":")[1]);
            }
            if (maxJqsTitle.equals("7+")) {
                num3 = 10;
            } else {
                num3 = Integer.valueOf(maxJqsTitle);
            }

            if (num1 + num2 == num3 || (num1 + num2 == 7 && num3 == 10)) {
                oddsmax = oddsmax + maxCbfOdds + maxJqsOdds;
            } else {
                if (maxCbfOdds > maxJqsOdds) {
                    oddsmax = oddsmax + maxCbfOdds;
                } else {
                    oddsmax = oddsmax + maxJqsOdds;
                }
            }
        } else {
            oddsmax = oddsmax + maxCbfOdds + maxJqsOdds;
        }
        return oddsmin + "~" + oddsmax;
    }
}
