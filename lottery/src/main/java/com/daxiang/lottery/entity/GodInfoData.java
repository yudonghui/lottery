package com.daxiang.lottery.entity;

/**
 * @author yudonghui
 * @date 2017/6/24
 * @describe May the Buddha bless bug-free!!!
 */
public class GodInfoData {

    /**
     * code : 0
     * msg : 查询成功
     * data : {"userId":7033100335700013,"userName":"CX_821161ruz","allPrize":718.33,"gdBuyNum":38,"gdBuyMoney":1020,"totalTC":0,"contWin":5,"winRate":"20.55","gainRate":"33.49"}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userId : 7033100335700013
         * userName : CX_821161ruz
         * allPrize : 718.33
         * gdBuyNum : 38
         * gdBuyMoney : 1020
         * totalTC : 0
         * contWin : 5
         * winRate : 20.55
         * gainRate : 33.49
         */

        private String userId;
        private String userName;
        private String allPrize;
        private String gdBuyNum;
        private String gdBuyMoney;
        private String totalTC;
        private String contWin;
        private String winRate;
        private String gainRate;
        private String totalGD;//近三个月累计跟单金额
        private String gdBuyPrize;//跟单中奖金额

        public String getGdBuyPrize() {
            return gdBuyPrize;
        }

        public void setGdBuyPrize(String gdBuyPrize) {
            this.gdBuyPrize = gdBuyPrize;
        }

        public String getTotalGD() {
            return totalGD;
        }

        public void setTotalGD(String totalGD) {
            this.totalGD = totalGD;
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

        public String getAllPrize() {
            return allPrize;
        }

        public void setAllPrize(String allPrize) {
            this.allPrize = allPrize;
        }

        public String getGdBuyNum() {
            return gdBuyNum;
        }

        public void setGdBuyNum(String gdBuyNum) {
            this.gdBuyNum = gdBuyNum;
        }

        public String getGdBuyMoney() {
            return gdBuyMoney;
        }

        public void setGdBuyMoney(String gdBuyMoney) {
            this.gdBuyMoney = gdBuyMoney;
        }

        public String getTotalTC() {
            return totalTC;
        }

        public void setTotalTC(String totalTC) {
            this.totalTC = totalTC;
        }

        public String getContWin() {
            return contWin;
        }

        public void setContWin(String contWin) {
            this.contWin = contWin;
        }

        public String getWinRate() {
            return winRate;
        }

        public void setWinRate(String winRate) {
            this.winRate = winRate;
        }

        public String getGainRate() {
            return gainRate;
        }

        public void setGainRate(String gainRate) {
            this.gainRate = gainRate;
        }
    }
}
