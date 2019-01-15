package com.daxiang.lottery.entity;

import java.util.List;

/**
 * @author yudonghui
 * @date 2017/6/22
 * @describe May the Buddha bless bug-free!!!
 */
public class RankingData {

    /**
     * code : 0
     * msg : 查询成功
     * data : [{"userId":7040715523500001,"userName":"Phoenix","buyNum":6320},{"userId":7052014383100001,"userName":"CX_136111kmo","buyNum":3},{"userId":7033100335700013,"userName":"yudonghui","buyNum":2},{"userId":7033113174100002,"userName":"清霆","buyNum":1},{"userId":7041119145000002,"userName":"dongshuo","buyNum":1}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userId : 7040715523500001
         * userName : Phoenix
         * buyNum : 6320
         */

        private String userId;
        private String userName;
        private String buyNum; //购彩数量
        private String totalBuy; //共计购买订单数
        private String totalWin; //共计中奖订单数
        private String winRate; //命中率
        private String totalCost; //共计购买金额
        private String totalPrize; //共计中奖金额
        private String gainRate; //盈利率
        private String maxNum; //最大连红数
        private String endDate; //连红结束时间
        private String totalJoin; //参与数
        private String allPrize; //中奖金额
        private String description;//累计中奖xxx元；累计跟单xxx元
        private String canBuy;
        private String isCertified;

        public String getIsCertified() {
            return isCertified;
        }

        public void setIsCertified(String isCertified) {
            this.isCertified = isCertified;
        }

        public String getCanBuy() {
            return canBuy;
        }

        public void setCanBuy(String canBuy) {
            this.canBuy = canBuy;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getBuyNum() {
            return buyNum;
        }

        public void setBuyNum(String buyNum) {
            this.buyNum = buyNum;
        }

        public String getTotalBuy() {
            return totalBuy;
        }

        public void setTotalBuy(String totalBuy) {
            this.totalBuy = totalBuy;
        }

        public String getTotalWin() {
            return totalWin;
        }

        public void setTotalWin(String totalWin) {
            this.totalWin = totalWin;
        }

        public String getWinRate() {
            return winRate;
        }

        public void setWinRate(String winRate) {
            this.winRate = winRate;
        }

        public String getTotalCost() {
            return totalCost;
        }

        public void setTotalCost(String totalCost) {
            this.totalCost = totalCost;
        }

        public String getTotalPrize() {
            return totalPrize;
        }

        public void setTotalPrize(String totalPrize) {
            this.totalPrize = totalPrize;
        }

        public String getGainRate() {
            return gainRate;
        }

        public void setGainRate(String gainRate) {
            this.gainRate = gainRate;
        }

        public String getMaxNum() {
            return maxNum;
        }

        public void setMaxNum(String maxNum) {
            this.maxNum = maxNum;
        }


        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getTotalJoin() {
            return totalJoin;
        }

        public void setTotalJoin(String totalJoin) {
            this.totalJoin = totalJoin;
        }

        public String getAllPrize() {
            return allPrize;
        }

        public void setAllPrize(String allPrize) {
            this.allPrize = allPrize;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
