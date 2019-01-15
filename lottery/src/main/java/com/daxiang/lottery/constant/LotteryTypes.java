package com.daxiang.lottery.constant;

import com.daxiang.lottery.R;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
public class LotteryTypes {
    public static String lotcode;
    public static String playMethod;
    public static String mSelectContent;
    public static String bqcStr;
    public static String rqspfStr;
    public static String moneyStatus;
    public static int SPF = 1;
    public static int RQSPF = 2;
    public static int CBF = 3;
    public static int JQS = 4;
    public static int BQC = 5;
    public static int HH = 6;
    public static String[] lotCodeArray = {"11", "19", "30", "33", "35", "42", "43", "51", "52", "10022", "21406", "23528", "23529"};

    public static String getTypes(String type) {
        if (type == null) return "";
        switch (type) {
            case "42":
                lotcode = "竞彩足球";
                break;
            case "43":
                lotcode = "竞彩篮球";
                break;
            case "1000":
                lotcode = "竞足单关";
                break;
            case "1001":
                lotcode = "竞篮单关";
                break;
            case "23529":
                lotcode = "大乐透";
                break;
            case "11":
                lotcode = "胜负彩";
                break;
            case "19":
                lotcode = "任九场";
                break;
            case "33":
                lotcode = "排列三";
                break;
            case "35":
                lotcode = "排列五";
                break;
            case "10022":
                lotcode = "七星彩";
                break;
            case "23528":
                lotcode = "七乐彩";
                break;
            case "51":
                lotcode = "双色球";
                break;
            case "21406":
                lotcode = "十一运夺金";
                break;
            case "52":
                lotcode = "福彩3D";
                break;
            case "36":
                lotcode = "江西快三";
                break;
            case "50":
                lotcode = "重庆时时彩";
                break;
            case "30":
                lotcode = "冠亚军";
                break;
            default:
                lotcode = "";
                break;


        }
        return lotcode;
    }

    public static int getImgId(String lotcode) {
        int resId = R.drawable.dlt;
        switch (lotcode) {
            case "42":
                resId = R.drawable.jczq;
                break;
            case "43":
                resId = R.drawable.jclq;
                break;
            case "1000":
                resId = R.drawable.jczqd;
                break;
            case "1001":
                resId = R.drawable.jclqd;
                break;
            case "23529":
                resId = R.drawable.dlt;
                break;
            case "11":
                resId = R.drawable.sfc;
                break;
            case "19":
                resId = R.drawable.rj;
                break;
            case "33":
                resId = R.drawable.pls;
                break;
            case "35":
                resId = R.drawable.plw;
                break;
            case "10022":
                resId = R.drawable.qxc;
                break;
            case "23528":
                resId = R.drawable.qlc;
                break;
            case "51":
                resId = R.drawable.ssq;
                break;
            case "21406":
                resId = R.drawable.syydj;
                break;
            case "52":
                resId = R.drawable.fcsd;
                break;
            case "36":
                resId = R.drawable.jxks;
                break;
            case "50":
                resId = R.drawable.cqss;
                break;
        }
        return resId;
    }

    public static String getPlayMethod(String method) {
        switch (method) {
            case "SPF":
                playMethod = "胜平负:";
                break;
            case "RQSPF":
                playMethod = "让球胜平负:";
                break;
            case "JQS":
                playMethod = "进球数:";
                break;
            case "CBF":
                playMethod = "猜比分:";
                break;
            case "BQC":
                playMethod = "半全场:";
                break;
            case "SF":
                playMethod = "胜负:";
                break;
            case "RFSF":
                playMethod = "让分胜负:";
                break;
            case "SFC":
                playMethod = "胜分差:";
                break;
            case "DXF":
                playMethod = "大小分:";
                break;
        }
        return playMethod;
    }

    public static String getSpf(String selectContent) {
        switch (selectContent) {
            case "1":
                mSelectContent = "主平";
                break;
            case "0":
                mSelectContent = "主负";
                break;
            case "3":
                mSelectContent = "主胜";
                break;
        }
        return mSelectContent;
    }

    public static String getRqSpf(String selectContent) {
        switch (selectContent) {
            case "1":
                mSelectContent = "让平";
                break;
            case "0":
                mSelectContent = "让负";
                break;
            case "3":
                mSelectContent = "让胜";
                break;
        }
        return mSelectContent;
    }

    public static String getDxf(String selectContent) {
        switch (selectContent) {
            case "0":
                mSelectContent = "小分";
                break;
            case "3":
                mSelectContent = "大分";
                break;
        }
        return mSelectContent;
    }

    public static String getSpfByStr(String selectContent) {
        switch (selectContent) {
            case "平":
                mSelectContent = "1";
                break;
            case "主负":
                mSelectContent = "0";
                break;
            case "主胜":
                mSelectContent = "3";
                break;
            case "客胜":
                mSelectContent = "0";
                break;
        }
        return mSelectContent;
    }

    public static String getRqSpfByStr(String selectContent) {
        switch (selectContent) {
            case "让平":
                mSelectContent = "1";
                break;
            case "让负":
                mSelectContent = "0";
                break;
            case "让胜":
                mSelectContent = "3";
                break;
            case "主胜[让]":
                mSelectContent = "3";
                break;
            case "客胜[让]":
                mSelectContent = "0";
                break;
            case "大分":
                mSelectContent = "3";
                break;
            case "小分":
                mSelectContent = "0";
                break;
        }
        return mSelectContent;
    }

    public static String getSfcByStr(String code) {
        switch (code) {
            case "客胜1-5":
                bqcStr = "11";
                break;
            case "客胜6-10":
                bqcStr = "12";
                break;
            case "客胜11-15":
                bqcStr = "13";
                break;
            case "客胜16-20":
                bqcStr = "14";
                break;
            case "客胜21-25":
                bqcStr = "15";
                break;
            case "客胜26+":
                bqcStr = "16";
                break;
            case "主胜1-5":
                bqcStr = "01";
                break;
            case "主胜6-10":
                bqcStr = "02";
                break;
            case "主胜11-15":
                bqcStr = "03";
                break;
            case "主胜16-20":
                bqcStr = "04";
                break;
            case "主胜21-25":
                bqcStr = "05";
                break;
            case "主胜26+":
                bqcStr = "06";
                break;
        }
        return bqcStr;
    }

    public static String getSfcStr(String code) {
        switch (code) {
            case "11":
                bqcStr = "客胜1-5";
                break;
            case "12":
                bqcStr = "客胜6-10";
                break;
            case "13":
                bqcStr = "客胜11-15";
                break;
            case "14":
                bqcStr = "客胜16-20";
                break;
            case "15":
                bqcStr = "客胜21-25";
                break;
            case "16":
                bqcStr = "客胜26+";
                break;
            case "01":
                bqcStr = "主胜1-5";
                break;
            case "02":
                bqcStr = "主胜6-10";
                break;
            case "03":
                bqcStr = "主胜11-15";
                break;
            case "04":
                bqcStr = "主胜16-20";
                break;
            case "05":
                bqcStr = "主胜21-25";
                break;
            case "06":
                bqcStr = "主胜26+";
                break;
        }
        return bqcStr;
    }

    public static String getBqcStr(String code) {

        switch (code) {
            case "3-3":
                bqcStr = "胜胜";
                break;
            case "3-1":
                bqcStr = "胜平";
                break;
            case "3-0":
                bqcStr = "胜负";
                break;
            case "1-3":
                bqcStr = "平胜";
                break;
            case "1-1":
                bqcStr = "平平";
                break;
            case "1-0":
                bqcStr = "平负";
                break;
            case "0-3":
                bqcStr = "负胜";
                break;
            case "0-1":
                bqcStr = "负平";
                break;
            case "0-0":
                bqcStr = "负负";
                break;
        }
        return bqcStr;
    }

    public static String getBqcByStr(String code) {

        switch (code) {
            case "胜胜":
                bqcStr = "3-3";
                break;
            case "胜平":
                bqcStr = "3-1";
                break;
            case "胜负":
                bqcStr = "3-0";
                break;
            case "平胜":
                bqcStr = "1-3";
                break;
            case "平平":
                bqcStr = "1-1";
                break;
            case "平负":
                bqcStr = "1-0";
                break;
            case "负胜":
                bqcStr = "0-3";
                break;
            case "负平":
                bqcStr = "0-1";
                break;
            case "负负":
                bqcStr = "0-0";
                break;
        }
        return bqcStr;
    }

    public static String getRqspf(String rqspf) {
        switch (rqspf) {
            case "3":
                rqspfStr = "|让胜";
                break;
            case "0":
                rqspfStr = "|让负";
                break;
            case "1":
                rqspfStr = "|让平";
                break;
        }
        return rqspfStr;
    }

    public static String getRqspfStr(String rqspf) {
        String rqspfSt = null;
        switch (rqspf) {
            case "3":
                rqspfSt = "让胜";
                break;
            case "0":
                rqspfSt = "让负";
                break;
            case "1":
                rqspfSt = "让平";
                break;
        }
        return rqspfSt;
    }

    public static String getMoneyStatus(int num) {
        switch (num) {
            case 1:
                moneyStatus = "支付";
                break;
            case 2:
                moneyStatus = "平台补款";
                break;
            case 3:
                moneyStatus = "平台扣款";
                break;
            case 4:
                moneyStatus = "提款";
                break;
            case 5:
                moneyStatus = "提成";
                break;
            case 6:
                moneyStatus = "彩票返奖";
                break;
            case 7:
                moneyStatus = "充值";
                break;
            case 8:
                moneyStatus = "撤单返款";
                break;
            case 9:
                moneyStatus = "返点";
                break;
            case 10:
                moneyStatus = "合买保底退款";
                break;
            case 11:
                moneyStatus = "支付宝转账";
                break;
            case 12:
                moneyStatus = "红包支付";
                break;
            case 13:
                moneyStatus = "红包退回";
                break;
            case 0:
                moneyStatus = "所有";
                break;
        }
        return moneyStatus;
    }

    public static String getStatus(String code) {
        String status = null;
        switch (code) {
            case "100":
                status = "待付款";
                break;
            case "200":
                status = "等待出票";
                break;
            case "210":
                status = "出票中";
                break;
            case "220":
                status = "分配订单失败";
                break;
            case "230":
                status = "拆票失败";
                break;
            case "240":
                status = "出票中";
                break;
            case "300":
                status = "订单失效";
                break;
            case "400":
                status = "部分成功";
                break;
            case "500":
                status = "等待开奖";
                break;
            case "501":
                status = "出票失败";
                break;
            case "600":
                status = "出票取消";
                break;
            case "1000":
                status = "未中奖";
                break;
            case "1500":
                status = "大奖待审核";
                break;
            case "2000":
                status = "中奖";
                break;
            case "5000":
                status = "已派奖";
                break;
            case "-1":
                status = "未知状态";
                break;
        }
        return status;
    }

    public static boolean getMsgByStatus(String code) {
        boolean msg=false;
        switch (code) {
            case "200":
            case "210":
            case "240":
            case "400":
            case "500":
                msg =false;
                break;
            case "100":
            case "220":
            case "230":
            case "300":
            case "501":
            case "600":
            case "1000":
            case "1500":
            case "2000":
            case "5000":
                msg=true;
                break;
            case "-1":
                msg = true;
                break;
        }
        return msg;
    }

    public static String getPrintStatus(String code) {
        String status = null;
        switch (code) {
            case "0":
                status = "未出票";
                break;
            case "1":
                status = "出票中";
                break;
            case "2":
                status = "出票成功";
                break;
            case "3":
                status = "出票失败";
                break;

        }
        return status;
    }

    public static String getOrderType(int type) {
        String orderType = "";
        switch (type) {
            case 0:
                orderType = "自购";
                break;
            case 1:
                orderType = "发起合买";
                break;
            case 3:
                orderType = "参与合买";
                break;
            case 7:
                orderType = "发起推荐";
                break;
            case 8:
                orderType = "参加推荐";
                break;
            case 2:
                orderType = "追号总订单";
                break;
            case 4:
                orderType = "合买保底";
                break;
            case 5:
                orderType = "合买系统保底";
                break;
            case 6:
                orderType = "追号子订单";
                break;
        }
        return orderType;
    }

    public static String getPlayMethod(String lotcode, String type) {
        if ("36".equals(lotcode)) {
            switch (type) {
                case "1":
                    return "1:4";
                case "2":
                    return "1:1";
                case "3":
                    return "3:2";
                case "4":
                    return "3:3";
                case "5":
                    return "2:1";
                case "6":
                    return "2:5";
                case "7":
                    return "2:2";
                case "8":
                    return "1:3";
            }
        }
        return "";
    }
}
