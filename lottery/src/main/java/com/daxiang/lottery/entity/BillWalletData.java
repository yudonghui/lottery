package com.daxiang.lottery.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/12 0012.
 */
public class BillWalletData implements Serializable {


    /**
     * sign : 670B14728AD9902AECBA32E22FA4F6BD
     * msg : 成功
     * code : 0
     * data : {"totalBalance":"350","freezeBalance":"50.00","freeBalance":"100.00","consumeBalamnce":"200.00","totalAward":"20","totalTrade":"55","timeStamp":"138000212121"}
     */

    private String sign;
    private String msg;
    private int code;
    private DataBean data;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * totalBalance : 350
         * freezeBalance : 50.00
         * freeBalance : 100.00
         * consumeBalamnce : 200.00
         * totalAward : 20
         * totalTrade : 55
         * timeStamp : 138000212121
         */

        private String totalBalance;//总金额
        private String freezeBalance;//冻结金额
        private String freeBalance;//可提现的
        private String consumeBalamnce;//仅消费金额
        private String totalAward;
        private String totalTrade;
        private String timeStamp;

        public String getTotalBalance() {
            return totalBalance;
        }

        public void setTotalBalance(String totalBalance) {
            this.totalBalance = totalBalance;
        }

        public String getFreezeBalance() {
            return freezeBalance;
        }

        public void setFreezeBalance(String freezeBalance) {
            this.freezeBalance = freezeBalance;
        }

        public String getFreeBalance() {
            return freeBalance;
        }

        public void setFreeBalance(String freeBalance) {
            this.freeBalance = freeBalance;
        }

        public String getConsumeBalamnce() {
            return consumeBalamnce;
        }

        public void setConsumeBalamnce(String consumeBalamnce) {
            this.consumeBalamnce = consumeBalamnce;
        }

        public String getTotalAward() {
            return totalAward;
        }

        public void setTotalAward(String totalAward) {
            this.totalAward = totalAward;
        }

        public String getTotalTrade() {
            return totalTrade;
        }

        public void setTotalTrade(String totalTrade) {
            this.totalTrade = totalTrade;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }
    }
}
