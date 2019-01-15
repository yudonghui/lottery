package com.daxiang.lottery.entity;

/**
 * @author yudonghui
 * @date 2017/5/10
 * @describe May the Buddha bless bug-free!!!
 */
public class OrderFormBean {

    /**
     * code : 0
     * msg : 成功
     * data : {"orderId":7051011570200047,"orderCode":"GC20170510165356_9466914","remark":"普通购彩_GC20170510165356_9466914"}
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
         * orderId : 7051011570200047
         * orderCode : GC20170510165356_9466914
         * remark : 普通购彩_GC20170510165356_9466914
         */

        private String orderId;
        private String orderCode;
        private String remark;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
