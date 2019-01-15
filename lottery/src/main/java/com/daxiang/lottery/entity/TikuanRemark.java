package com.daxiang.lottery.entity;

/**
 * @author yudonghui
 * @date 2017/5/22
 * @describe May the Buddha bless bug-free!!!
 */
public class TikuanRemark {

    /**
     * sign : 9fc1b7b11157fa95c3c225e6b35c127d
     * msg : 成功
     * code : 0
     * data : {"minAmount":10,"remark":"1.提现最低额度不低于10元；\n2.提现额度不足100元时，需运营及财务人工审核处理，请耐心等待；\n3.提现金额为100元及以上时，经财务审核以后走实时支付自动到账。","timeStamp":1495689009363}
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

    public static class DataBean {
        /**
         * minAmount : 10.0
         * remark : 1.提现最低额度不低于10元；
         2.提现额度不足100元时，需运营及财务人工审核处理，请耐心等待；
         3.提现金额为100元及以上时，经财务审核以后走实时支付自动到账。
         * timeStamp : 1495689009363
         */

        private double minAmount;
        private String remark;
        private long timeStamp;

        public double getMinAmount() {
            return minAmount;
        }

        public void setMinAmount(double minAmount) {
            this.minAmount = minAmount;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
        }
    }
}
